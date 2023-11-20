# Use a Linix image with Tomcat 10
FROM tomcat:10.1.0-M5-jdk16-openjdk-slim-bullseye

# Copy in our ROOT.war to the right place in the container
COPY target/Pro4Task2-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
