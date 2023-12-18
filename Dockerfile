# Use Maven 3.6.3 base image with OpenJDK 11
FROM maven:3.6.3-openjdk-11

# Set the working directory
WORKDIR /app

# Install Node.js and npm
RUN apt-get update && \
    apt-get install -y curl && \
    curl -fsSL https://deb.nodesource.com/setup_14.x | bash - && \
    apt-get install -y nodejs && \
    rm -rf /var/lib/apt/lists/*

# Install Playwright
RUN npm install -g playwright@1.39.0

# Copy the Maven executable to the Docker image
COPY mvnw .
COPY .mvn .mvn

# Copy pom.xml and Use Maven to resolve dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the project source
COPY src ./src

# Package application with Maven and run tests
RUN mvn clean test

CMD ["mvn", "test"]
