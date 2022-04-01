FROM openjdk:17
COPY ./target/quiz.jar quiz.jar
CMD ["java", "-jar", "quiz.jar"]



