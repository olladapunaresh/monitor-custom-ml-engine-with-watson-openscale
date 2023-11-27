from flask import Flask, request, abort, jsonify
from ibm_watson_machine_learning import APIClient
from ibm_cloud_sdk_core.authenticators import IAMAuthenticator
from ibm_watson_openscale import APIClient as OSAPIClient
from ibm_watson_openscale.utils import *
from ibm_watson_openscale.supporting_classes import *
from flask import Flask, request, jsonify
import subprocess
import os, socket

app = Flask(__name__)


@app.route('/score', methods=['GET'])
def wml_online():
    data = request.json  # Expecting data in JSON format

    # Create a list of arguments from the dictionary
    java_args = [str(value) for key, value in data.items()]


    # Compile Java code
    compile_process = subprocess.run(['javac', '-cp', '.:h2o-genmodel.jar', 'Main.java'], capture_output=True, text=True)
    if compile_process.returncode != 0:
        # Compilation error
        return jsonify({'error': compile_process.stderr})

    # # Run compiled Java program
    run_process = subprocess.run(['java', '-cp', '.:h2o-genmodel.jar', 'main'] + java_args, capture_output=True, text=True)
    if run_process.returncode != 0:
        # Runtime error
        return jsonify({'error': run_process.stderr})

    # Return the output of the Java program
    return jsonify({'output': run_process.stdout})


if __name__ == '__main__':
    app.run(debug=True, port=8080, host='127.0.0.1')
