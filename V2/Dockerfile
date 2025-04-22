FROM eclipse-temurin:21
COPY build/libs/insurance-calculator-1.0.8.jar insurance-calculator.jar
CMD ["java", "-jar", "insurance-calculator.jar"]
#ENTRYPOINT ["java", "-jar", "insurance-calculator.jar"]
EXPOSE 8080

#docker build -f V2/Dockerfile -t insurance-calculator V2 - создание image
#docker container create --name insurance-calculator -p 8080:8080 insurance-calculator - создание контейнера
#docker container create \

#   --name insurance-calculator3 \
#   -p 8080:8080 \
#   -v "$(pwd)/export:/app/export/agreements" \
#   -e AGREEMENT_EXPORT_PATH=/app \
#   insurance-calculator


#docker container start insurance-calculator1 -i - запуск
#docker container stop insurance-calculator1 - остановка
#docker container kill insurance-calculator1 - принудительная остановка
#docker container rm insurance-calculator1 - удаление



