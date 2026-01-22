# -------- Stage 1: Build WAR using Maven 3.9.12 + Java 17 --------
FROM maven:3.9.12-eclipse-temurin-17 AS build
 
WORKDIR /app
COPY pom.xml .
COPY src ./src
 
RUN mvn clean package -DskipTests
 
 
# -------- Stage 2: Run WAR in Tomcat 10 (Jakarta Servlet 6 compatible) --------
FROM tomcat:10.1-jdk17-temurin
 
# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*
 
# Copy WAR into tomcat webapps as ROOT
COPY --from=build /app/target/BusPassRegistrationApp.war /usr/local/tomcat/webapps/ROOT.war
 
EXPOSE 8080
CMD ["catalina.sh", "run"]
