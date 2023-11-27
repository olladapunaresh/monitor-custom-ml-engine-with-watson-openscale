# Use an official OpenJDK runtime as a parent image
FROM openjdk:11

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the Java source file into the container
COPY Main.java .

# Compile the Main.java file
RUN javac -cp .:h2o-genmodel.jar main.java

# Run the compiled Java program when the container launches
CMD ["java","-cp",".:h2o-genmodel.jar","main"]
