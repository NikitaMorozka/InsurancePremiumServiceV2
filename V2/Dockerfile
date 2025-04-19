FROM eclipse-temurin:21
COPY build/libs/insurance-calculator-1.0.8.jar insurance-calculator.jar
ENTRYPOINT ["java", "-jar", "insurance-calculator.jar"]
EXPOSE 8080

#docker run insurance-calculator some arguments
#docker build -f V2/Dockerfile -t insurance-calculator V2
#./gradlew docker