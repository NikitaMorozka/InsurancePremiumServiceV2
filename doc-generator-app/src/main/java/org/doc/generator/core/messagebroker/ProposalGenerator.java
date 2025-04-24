package org.doc.generator.core.messagebroker;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.doc.generator.core.dto.AgreementDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ProposalGenerator {

    @Value("${proposals.directory.path}")
    private String proposalsDirectoryPath;
    private static final Logger logger = LoggerFactory.getLogger(ProposalGenerationQueueListener.class);

    private final PdfFontProvider pdfFontProvider;

    public void generateProposalAndStoreToFile(AgreementDTO agreementDTO) {
        logger.info("Start to generate PDF for proposal: " + agreementDTO.getUuid());
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(pdfFontProvider.getRegularFont(document), 12);
                contentStream.setLeading(14.5f); // Межстрочный интервал
                contentStream.newLineAtOffset(50, 750);

                String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(agreementDTO);
                for (String line : json.split("\n")) {
                    contentStream.showText(line);
                    contentStream.newLine();
                }

                contentStream.endText();
            }

            String outputPath = proposalsDirectoryPath + "/agreement-proposal-" + agreementDTO.getUuid() + ".pdf";
            document.save(outputPath);
            System.out.println("PDF успешно создан: " + outputPath);
        } catch (IOException e) {
            System.err.println("Ошибка при генерации PDF: " + e.getMessage());
            e.printStackTrace();
        }
        logger.info("Finish to generate PDF for proposal: " + agreementDTO.getUuid());
    }

}
