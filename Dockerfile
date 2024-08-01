FROM maven:3.9.0 as maven
LABEL APPLICATION="Expense Tracker Api"

WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn package

FROM openjdk:18-oracle
COPY --from=maven /usr/src/app/target/ExpenseTrackerApi.war /app.war
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.war"]
