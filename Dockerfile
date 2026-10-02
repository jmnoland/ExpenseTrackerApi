FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /usr/src/app
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B package

FROM eclipse-temurin:17-jre
LABEL APPLICATION="Expense Tracker Api"
RUN useradd --system app
USER app
COPY --from=build /usr/src/app/target/ExpenseTrackerApi.war /app.war
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.war"]
