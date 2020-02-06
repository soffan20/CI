# CI Server

**This is a Continuous Integration server that automatically runt tests on code that are pushed to GitHub.**

The server runs jUnit tests triggered by a push-event on GitHub. The code is tested and the program generates documentation for the classes.
The test results are sent back to GitHub where the commit status gets updated to either Success or Failure.

When our server is notified of a push-event, we create a temporary folder to which we save the path. We clone the repository in question into that folder and look for an instruction file in the form of a bash file.
We then run the bash instructions on the file. The bash file contains all the instructions
necessary to run the tests. After we've run the file, we save the exit code to see if the tests failed or succeeded.
The results are then sent back to github and the temporary folder is deleted.

The project is a Maven project using Java 13 and contains a jUnit test suite.
Documentation is generated from the javadoc in the master branch and is
published in the `gh-pages` branch in this repository.

## Documentation
Our documentation can be found at the following URL:
https://soffan20.github.io/CI/

## Status
![Continuous Integration](https://github.com/soffan20/CI/workflows/Continuous%20Integration/badge.svg)

## Running the tests

```bash
# Runs all the jUnit tests
$ mvn test
```

## Building the application

```bash
# The jar will be in target/CI-VERSION.jar
$ mvn package
```

## Running the server
```bash
# Running the server
$ mvn compile exec:java
```
## Generating the documentation

```bash
# This generates HTML from the Javadoc in target/site/apidocs
$ mvn javadoc:javadoc
```

## Contributions
The whole group contributed equally.

Louise worked with JSON, the GitHub requests and updating the commit status.

Daniel worked with documentation and handling server.

Andreas worked with repository handling in the server.

Mikael worked with the executor stitching together the whole flow and JSON.

Emil worked with GitHub Actions to automate compilation, testing, documentation generation
and automatic deployment of documentation to GitHub pages.

Everyone created tests for their parts.
