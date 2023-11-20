# Use a Linux image with Tomcat 10
FROM tomcat:10.1.0-M5-jdk16-openjdk-slim-bullseye

# Set the port to 8081 as an environment variable (optional, for clarity)
ENV PORT 8081

# Expose port 8081 for the application
EXPOSE 8081

# Copy in our ROOT.war to the right place in the container
COPY target/Pro4Task2-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
