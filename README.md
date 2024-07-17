# Save A Train

## Description

This project provides End-to-End (E2E) automated tests for various feature areas of the Save A Train web application. The tests are written in Java and executed using the Playwright framework.

## Table of Contents

- [Description](#description)
- [Dependencies](#dependencies)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installing](#installing)
- [Running the tests](#running-the-tests)
- [Project structure](#project-structure)

## Dependencies

- Playwright (latest version)
- RestAssured (latest version)
- Commons Codec (latest version)
- POI OOXML (latest version)
- Commons Compress (latest version)
- SnakeYaml (updated to version 2.0)

## Getting Started

### Prerequisites

Ensure you have all the necessary software and configurations in check before you proceed with the installation. At a minimum, you will need IntelliJ IDEA 2024.1.4 Ultimate Edition, Java SDK version 19, and Maven for dependency management, and Allure (find more about installation in official Allure documentation). Make sure these tools are properly installed and configured on your machine and that you're familiar with basic operations in the terminal.

### Installing

To install the project:

1. Clone the repository: `git clone https://github.com/spectreclaud/xm`
2. Go to the project directory
3. Run `mvn clean install` to download dependencies and build the project

## Running the tests

Once you've successfully installed and built the SaveATrainWithPlaywright project, running the tests is the next crucial step. This helps to ensure that the installation went smoothly and that the application behavior is correct. Here's how to run the tests:
1. Navigate to the Project Directory: Open a terminal and point it to your project's root directory. If you're not already there, use the command:  `cd <path_to_your_project_directory>`
2. Run the Tests: Maven has a specific lifecycle phase for running tests called test. This will execute all test cases present in your project. Use the command: `mvn test`
   Replace <path_to_your_project_directory> with the real path to your XM project in local machine.
3. Generate and open Allure report with the following command: `allure serve <directory-of-allure-results>`
   All tests should pass successfully. If any tests fail, that might signify an issue with the application. Analyze the output in the terminal to troubleshoot the problem or see what the tests indicate.

## Project structure

| Folder/File  | Description |
|--------------|-------------|
| `pom.xml` | The Project Object Model file for Maven. It includes configurations and dependencies for the project. |
| `README.md` | This file provides an overview and documentation of your project. |
| `/src/main/java/constans/AppConstants.java` | This file contains some constants which are used all over the application. |
| `/src/main/java/pages/MainPage.java` | It's a part of the Page Object Model. Thus, it should contain methods to interact with the main page of the application. |
| `/src/main/java/pages/ResultsPage.java` | It's also a part of the Page Object Model and should contain methods to interact with the results page of the application. |
| `/src/test/java/com/example/load/LoadTimeTest.java` | This is a test file which contains tests related to the Load time of the webpages of the application. |
| `/src/test/java/com/example/saveatrainplaywrith/SaveATrainMixE2EForTrainItaliaTests.java` | This test file contains some end to end tests specifically for Train Italia feature of the application. |
| `/src/test/java/com/example/saveatrainplaywrith/SaveATrainE2EACPTests.java` | This test file contains some end to end tests specifically for ACP feature of the application. |
| `/src/test/java/com/example/saveatrainplaywrith/SaveATrainRegressionTests.java` | This test file is focused on performing the regression tests of the application. |
| `/src/test/resources/ConnectionsACP.xlsx` | It is an Excel file which is used as a data source for the tests. It seems specifically related to the ACP feature of the application. |
| `/target/` | This directory contains all output of the build. |
