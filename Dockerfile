# Use an official Java runtime as a parent image
FROM openjdk:11-jre-slim

EXPOSE 8080

WORKDIR /workspace

COPY main.java /workspace

# (You can skip this step if you are copying a .class file)
RUN javac -cp h2o-genmodel.jar main.jav

# Run the Java program when the container launches
CMD ["java -cp .:h2o-genmodel.jar", "main"]
