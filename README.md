# CI Server

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