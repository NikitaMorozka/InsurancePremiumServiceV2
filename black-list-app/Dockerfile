FROM eclipse-temurin:21

# Install bash
# RUN apk add --no-cache bash

COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x /wait-for-it.sh

COPY black-list-1.0.9.jar black-list.jar

CMD ["./wait-for-it.sh", "mysql-container:3306", "--", "java", "-Dspring.profiles.active=mysql-container", "-jar", "black-list.jar"]

EXPOSE 8080
