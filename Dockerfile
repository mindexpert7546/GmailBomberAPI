FROM adoptopenjdk/openjdk8:alpine-jre

WORKDIR /app

# Copy only the necessary files
COPY build.gradle .
COPY gradle gradle
COPY src src

# Change permission on the gradlew script
RUN chmod +x gradlew

CMD ["./gradlew", "bootRun"]
