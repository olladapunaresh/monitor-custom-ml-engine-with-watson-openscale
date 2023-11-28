FROM python:3.10.8-slim-buster

RUN python -m pip install gunicorn flask numpy pandas requests joblib==0.11  ibm_watson_machine_learning ibm-watson-openscale

# Install Java (OpenJDK)
RUN apt-get update && apt-get install -y openjdk-11-jdk


# Define environment variable
ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64
ENV PATH $JAVA_HOME/bin:$PATH

EXPOSE 8080

WORKDIR /workspace

COPY app.py /workspace

CMD ["python", "app.py"]
