cics-java-liberty-restapp
===========================

[![Build](https://github.com/cicsdev/cics-java-liberty-restapp/actions/workflows/main.yml/badge.svg)](https://github.com/cicsdev/cics-java-liberty-restapp/actions/workflows/main.yml)

Sample RESTful web application for deployment to a Liberty JVM server in CICS. The application is supplied with two resources:

1. `InfoResource` - This queries the JVM server environment using system properties and uses JAXB beans to return a JSON response detailing the CICS environment.
1. `ReverseResource` - This is similar to `InfoResource`, but uses the JCICS API to link to the COBOL program `EDUCHAN` using channels and containers. An input string is passed to `EDUCHAN`, which is then reversed and returned, along with the time from CICS.

Further extensions to this application are available in the repository [cics-java-liberty-restappext](https://github.com/cicsdev/cics-java-liberty-restappext) which provides several code examples for accessing CICS resources from Java using the JCICS API.

The following Java source components are supplied in the [`cics-java-liberty-restapp-app/src/main/java`](cics-java-liberty-restapp-app/src/main/java) directory in this repository.

## Java package com.ibm.cicsdev.restapp
* [`CICSApplication`](src/main/java/com/ibm/cicsdev/restapp/CICSApplication.java) - Sets the `ApplicationPath` for resources in this application
* [`InfoResource`](src/main/java/com/ibm/cicsdev/restapp/InfoResource.java) - Returns JSON structure using `CICSInformation` bean
* [`ReverseResource`](src/main/java/com/ibm/cicsdev/restapp/ReverseResource.java) - Returns JSON structure using `ReverseResult` bean


## Java package com.ibm.cicsdev.restapp.bean
* [`CICSEnvironment`](src/main/java/com/ibm/cicsdev/restapp/bean/CICSEnvironment.java) - JAXB bean returning JSON structure containing information about CICS product and version
* [`CICSInformation`](src/main/java/com/ibm/cicsdev/restapp/bean/CICSInformation.java) - JAXB bean returning JSON structure containing CICS applid, time and JVM server name and instance of `CICSEnvironment`
* [`ReverseResult`](src/main/java/com/ibm/cicsdev/restapp/bean/ReverseResult.java) - JAXB bean returning JSON structure containg input and output containers sent to `EDUCHAN` COBOL program

## Contents

- [cics-java-liberty-restapp](./cics-java-liberty-restapp) - Top-level project.
- [cics-java-liberty-restapp-app](./cics-java-liberty-restapp-app) - Application source code.
- [cics-java-liberty-restapp-bundle](./cics-java-liberty-restapp-bundle) - CICS bundle plug-in based project. Use with Gradle and Maven builds.
- [etc/eclipse_projects/com.ibm.cicsdev.wlp.restapp.cicsbundle](./etc/eclipse_projects/com.ibm.cicsdev.wlp.restapp.cicsbundle) - CICS Explorer based CICS bundle project, contains Web application bundle-part. Use with CICS Explorer 'Export to zFS' deployment capability.


## Prerequisites
- CICS TS V5.5 or later
- Java SE 1.8 or later on the workstation
- One of the following on your workstation:
    - Eclipse with the IBM CICS SDK for Java EE, Jakarta EE and Liberty
    - An IDE of your choice that supports Gradle or Maven (or can run the Wrappers)
    - A command line, to run the Wrappers or to invoke a locally installed version of Gradle or Maven

## Supporting files
* [`DFHCSD.txt`](etc/DFHCSD.txt) - DFHCSDUP sample input stream for the CICS BUNDLE resource definition.
* [`EDUCHAN.cbl`](cics-java-liberty-restapp-app/src/cobol/EDUCHAN.cbl) - A sample CICS COBOL that returns the date and time and reversed input using channels and containers
* [`build.gradle`](build.gradle) - A sample Gradle build file to enable an automated build of the deployable WAR
* [`pom.xml`](pom.xml) - A sample Maven build file to enable an automated build of the deployable WAR
   

## Downloading

- Clone the repository using your IDEs support, such as the Eclipse Git plugin
- **or**, download the sample as a [ZIP](https://github.com/cicsdev/cics-java-liberty-restapp/archive/main.zip) and unzip onto the workstation

> [!TIP]
> Eclipse Git provides an 'Import existing Projects' check-box when cloning a repository.

## Building 

You can build the sample in a variety of ways:
- Using the implicit compile/build of the Eclipse based CICS Explorer SDK
- Using the built-in Gradle or Maven support of your IDE (For example: *buildship* or *m2e* in Eclipse which integrate with the "Run As..." menu.)
- Using the supplied Gradle or Maven Wrapper scripts (no requirement for an IDE or Gradle/Maven install)
- or you can build it from the command line if you have Gradle or Maven installed on your workstation
  

> [!IMPORTANT]
> The sample comes pre-configured for use with a JDK 1.8 and CICS TS V5.5 Libraries for Java EE & Jakarta EE 8. When you initially import the project to your IDE, if your IDE is not configured for a JDK 1.8, or does not have CICS Explorer SDK installed, you might experience local project compile errors. To resolve issues you should configure the Project's build-path to add/remove your preferred combination of CICS TS, JDK, and Liberty's Enterprise Java libraries (Java EE or Jakarta EE). Resolving errors might also depend on how you wish to build and deploy the sample. If you are building and deploying through CICS Explorer SDK and 'Export to zFS' you should edit the link-app's Project properties. Select 'Java Build Path', on the Libraries tab select 'Classpath', click 'Add Library', select 'CICS with Enterprise Java and Liberty' Library, and choose the appropriate CICS and Enterprise Java versions.
If you are building and deploying with Gradle or Maven then you don't necessarily need to fix the local errors, but to do so, you can do as above, or you can run a tooling refresh on the hello-web project. For example, in Eclipse: right-click on "Project", select "Gradle -> Refresh Gradle Project", **or** right-click on "Project", select "Maven -> Update Project...".

> [!TIP]
> In Eclipse, Gradle (buildship) is able to fully refresh and resolve the local classpath even if the project was previously updated by Maven. However, Maven (m2e) does not currently reciprocate that capability. If you previously refreshed the project with Gradle, you'll need to manually remove the 'Project Dependencies' entry on the Java build-path of your Project Properties to avoid duplication errors when performing a Maven Project Update.


### Option 1: Building with Eclipse

If you are using the Egit client to clone the repo, remember to tick the button to import all projects. Otherwise, you should manually Import the projects into CICS Explorer using File &rarr; Import &rarr; General &rarr; Existing projects into workspace, then follow the error resolution advice above.

### Option 2: Building with Gradle

For a complete build you should run the settings.gradle file in the top-level 'cics-java-liberty-restapp' directory which is designed to invoke the individual build.gradle files for each project. 

If successful, a WAR file is created inside the `cics-java-liberty-restapp-app/build/libs` directory and a CICS bundle ZIP file inside the `cics-java-liberty-restapp-bundle/build/distribution` directory. 

[!NOTE]
In Eclipse, the output 'build' directory is often hidden by default. From the Package Explorer pane, select the three dot menu, choose filters and un-check the Gradle build folder to view its contents.

The JVM server the CICS bundle is targeted at is controlled through the `cics.jvmserver` property, defined in the [`cics-java-liberty-restapp-bundle/build.gradle`](cics-java-liberty-restapp-bundle/build.gradle) file, or alternatively can be set on the command line:

**Gradle Wrapper (Linux/Mac):**
```shell
./gradlew clean build
```
**Gradle Wrapper (Windows):**
```shell
gradle.bat clean build
```
**Gradle (command-line):**
```shell
gradle clean build
```
**Gradle (command-line & setting jvmserver):**
```shell
gradle clean build -Pcics.jvmserver=MYJVM
```

### Option 3: Building with Apache Maven

For a complete build you should run the pom.xml file in the top-level 'cics-java-liberty-hello' directory. A WAR file is created inside the `cics-java-liberty-restapp-app/target` directory and a CICS bundle ZIP file inside the `cics-java-liberty-restapp-bundle/target` directory.

If building a CICS bundle ZIP the CICS JVM server name for the WAR bundle part should be modified in the 
 `cics.jvmserver` property, defined in [`cics-java-liberty-restapp-bundle/pom.xml`](cics-java-liberty-restapp-bundle/pom.xml) file under the `defaultjvmserver` configuration property, or alternatively can be set on the command line.

**Maven Wrapper (Linux/Mac):**
```shell
./mvnw clean verify
```
**Maven Wrapper (Windows):**
```shell
mvnw.cmd clean verify
```
**Maven (command-line):**
```shell
mvn clean verify
```
**Maven (command-line & setting jvmserver):**
```shell
mvn clean verify -Dcics.jvmserver=MYJVM
```

## Deploying

### Configuring the Liberty JVM server
1. Create a Liberty JVM server.
2. Install the JVM server.
3. Add the `jaxrs-1.1` (or later version) Liberty feature to `server.xml` depending on your version of Java EE.


### Deploying CICS Bundles with CICS Explorer
1. Optionally, change the name of the JVMSERVER in the .warbundle file of the CICS bundle project from DFHWLP to the name of your JVMSERVER resource defined in CICS. 
2. Export the bundle project to zFS by selecting 'Export Bundle project to z/OS Unix File System' from the context menu.
3. In CICS, create a bundle definition, setting the bundle directory attribute to the zFS location you just exported to, and install it. 

### Deploying CICS Bundles from Gradle or Maven
1. Manually upload the ZIP file from the _cics-java-liberty-restapp-bundle/target_ or _cics-java-liberty-restapp-bundle/build/distributions_ directory to zFS.
2. Unzip this ZIP file on zFS (e.g. `${JAVA_HOME}/bin/jar xf /path/to/bundle.zip`).
3. Create a CICS BUNDLE resource definition, setting the bundle directory attribute to the zFS location you just extracted to, and install it into the CICS region. 

### Deploying directly with Liberty's application configuration
1. Manually upload the WAR file from the _cics-java-liberty-restapp-app/target_ or _cics-java-liberty-restapp-app/build/libs_ directory to zFS.
2. Add an `<application>` element to the Liberty server.xml to define the web application.


### To deploy the samples into a CICS region:
1. Using the CICS Explorer export the CICS bundle project to a zFS directory. The samples use the directory `/u/cics1/com.ibm.cicsdev.restapp.cicsbundle_1.0.0`.
1. Create a CICS BUNDLE definition.
1. Install the CICS BUNDLE resource.
1. Download and compile the supplied COBOL program `EDUCHAN` and deploy into CICS.


**Note:** A sample DFHCSDUP input file for the required CICS BUNDLE resource definition is supplied in the supporting file [DFHCSD.txt](etc/DFHCSD.txt) file.  


## Running the Sample

### Using a web browser you can issue the following HTTP GET requests

* http://host:port/com.ibm.cicsdev.restapp/rest/cicsinfo

This will invoke the `InfoResource` class and return the following JSON response with information about the target CICS system:

`{"applid":"IYK2Z32E","jvmServer":"DFHWLP","time":"2016-09-09T16:19:55.384Z","cicsEnvironment":{"cicsProduct":"CICS Transaction Server for z/OS","cicsVersion":"5.3.0"}}`


* http://host:port/com.ibm.cicsdev.restapp/rest/reverse

This will invoke the `ReverseResource` class which links to the CICS COBOL program and reverses the default string "Hello from Java" returning the following JSON response:

`{"time":"2016-09-09T16:15:52.756Z","original":"Hello from Java","reverse":"avaJ morf olleH","truncated":false}`


* http://host:port/com.ibm.cicsdev.restapp/rest/reverse/ilovecics

This will invoke the `ReverseResource` class which links to the CICS COBOL program reversing the input string "ilovecics" as follows:

`{"time":"2016-09-09T16:15:32.466Z","original":"ilovecics","reverse":"scicevoli","truncated":false}`

## License
This project is licensed under [Apache License Version 2.0](LICENSE).

## Usage terms
By downloading, installing, and/or using this sample, you acknowledge that separate license terms may apply to any dependencies that might be required as part of the installation and/or execution and/or automated build of the sample, including the following IBM license terms for relevant IBM components:

• IBM CICS development components terms: https://www.ibm.com/support/customer/csol/terms/?id=L-ACRR-BBZLGX

