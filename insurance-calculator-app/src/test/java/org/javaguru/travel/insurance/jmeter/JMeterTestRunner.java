package org.javaguru.travel.insurance.jmeter;

import kg.apc.jmeter.threads.SteppingThreadGroup;
import org.apache.jmeter.assertions.ResponseAssertion;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.report.dashboard.ReportGenerator;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;
import org.javaguru.travel.insurance.rest.JsonFileReader;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JMeterTestRunner {
    public static void main(String[] args) throws Exception {

        Path path = Paths.get("V2/jmeterdata");
        Files.createDirectories(path); // создаём, если нет

        String jmeterHome = "/Applications/apache-jmeter-5.6.3";
        StandardJMeterEngine jm = new StandardJMeterEngine();
        JMeterUtils.setJMeterHome(jmeterHome);
        JMeterUtils.loadJMeterProperties(jmeterHome + "/bin/jmeter.properties");
        JMeterUtils.initLocale();

        String jsonBody = new JsonFileReader().readJsonFromFile("rest/restV2/request.json");

        HTTPSamplerProxy httpSamplerOne = new HTTPSamplerProxy();
        httpSamplerOne.setDomain("localhost");
        httpSamplerOne.setPort(8080);
        httpSamplerOne.setPath("/insurance/travel/api/v2/");
        httpSamplerOne.setMethod("POST");
        httpSamplerOne.setProtocol("http");
        httpSamplerOne.setName("HTTP Request One");
        httpSamplerOne.setPostBodyRaw(true); // Устанавливаем raw body
        httpSamplerOne.addNonEncodedArgument("", jsonBody, ""); // Тело запроса
        httpSamplerOne.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSamplerOne.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

        HeaderManager headerManager = new HeaderManager();
        headerManager.add(new Header("Content-Type", "application/json"));
        headerManager.setName("HTTP Header Manager");
        headerManager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
        headerManager.setProperty(TestElement.GUI_CLASS, org.apache.jmeter.protocol.http.gui.HeaderPanel.class.getName());
        httpSamplerOne.setHeaderManager(headerManager);

        // Assertion
        ResponseAssertion ra = new ResponseAssertion();
        ra.setProperty(TestElement.GUI_CLASS, org.apache.jmeter.assertions.gui.AssertionGui.class.getName());
        ra.setName("Check Response Code");
        ra.setTestFieldResponseCode();
        ra.setToEqualsType();
        ra.addTestString("200");

        // Loop Controller
        LoopController loopController = new LoopController();
        loopController.setLoops(5);//кол-во запросов
        loopController.setFirst(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, org.apache.jmeter.control.gui.LoopControlPanel.class.getName());
        loopController.initialize();

        // Thread Group
        SteppingThreadGroup threadGroup = new SteppingThreadGroup();
        threadGroup.setName("First Thread Group");
        threadGroup.setNumThreads(5); // количество пользователей (параллельных потоков)
        threadGroup.setRampUp("20"); //интервал запуска потоков
        threadGroup.setSamplerController(loopController);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

        // Test Plan
        TestPlan testPlan = new TestPlan("My JMeter Test Plan");
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());

        // HashTree setup
        ListedHashTree testPlanTree = new ListedHashTree();
        HashTree threadGroupTree = testPlanTree.add(testPlan, threadGroup);
        HashTree samplerTree = threadGroupTree.add(httpSamplerOne);
        samplerTree.add(headerManager);
        samplerTree.add(ra);

        // Save test plan to JMX
        Path jmxPath = path.resolve("FirstTest.jmx");

        SaveService.saveTree(testPlanTree, new FileOutputStream(jmxPath.toFile()));

        JMeterUtils.initLocale();
        JMeterUtils.initLogging();

        Summariser summariser = new Summariser("summary");
        ResultCollector logger = new ResultCollector(summariser);

        Path jtlPath = path.resolve("FirstTest.jtl");
        logger.setFilename(jtlPath.toString());

        testPlanTree.add(testPlanTree.getArray()[0], logger);

        jm.configure(testPlanTree);
        jm.run();


        String reportDir = path.resolve("html-report").toString();
        JMeterUtils.setProperty("jmeter.reportgenerator.exporter.html.property.output_dir", reportDir);
        ReportGenerator reportGenerator = new ReportGenerator(reportDir.toString(), null);
        reportGenerator.generate();

    }
}