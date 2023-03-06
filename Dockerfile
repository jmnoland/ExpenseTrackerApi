FROM openjdk:18-oracle
ARG WAR_FILE=target/ExpenseTrackerApi-0.0.1-SNAPSHOT.war
COPY ${WAR_FILE} /app.war
ENTRYPOINT ["java","-jar","/app.war"]