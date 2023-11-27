FROM openjdk:8-jdk-alpine

COPY . /workspace

COPY h2o-genmodel.jar /workspace

WORKDIR /workspace

RUN javac -cp .:h2o-genmodel.jar main.java 

CMD ["java","-cp",".:h2o-genmodel.jar", "main"]
