FROM adoptopenjdk/openjdk8:alpine-jre
WORKDIR /app
COPY . .
CMD ./gradlew bootRun
