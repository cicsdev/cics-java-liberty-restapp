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
- [etc/eclipse_projects/com.ibm.cicsdev.wlp.restapp.cicsbundle](./etc/eclipse_projects/com.ibm.cicsdev.wlp.restapp.cicsbundle) - CICS Explorer based CICS bundle project, contains Web application bundle-part. Use with Eclipse and CICS Explorer. 


## Prerequisites
- CICS TS V5.5 or later
- Java SE 1.8 or later on the workstation
- One of the following on your workstation:
    - Eclipse with the IBM CICS SDK for Java EE, Jakarta EE and Liberty
    - An IDE of your choice that supports Gradle or Maven (or can run the wrappers)
    

## Supporting files
* [`DFHCSD.txt`](etc/DFHCSD.txt) - DFHCSDUP sample input stream for the CICS BUNDLE resource definition.
* [`EDUCHAN.cbl`](etc/src/cobol/EDUCHAN.cbl) - A sample CICS COBOL that returns the date and time and reversed input using channels and containers
   

## Downloading

- Clone the repository using your IDEs support, such as the Eclipse Git plugin
- **or**, download the sample as a [ZIP](https://github.com/cicsdev/cics-java-liberty-restapp/archive/main.zip) and unzip onto the workstation


## Building 

The sample includes Eclipse project configurations, Gradle and Maven build files and Gradle/Maven Wrappers offering a wide range of build options with the tooling and IDE of your choice.

We documen the following 2 main approaches:

1. Use the command line to drive the supplied Gradle or Apache Maven Wrappers, this means there is no requirement for Gradle, Maven, Eclipse, or CICS Explorer SDK to be installed.
1. Use the built-in Eclipse and CICS Explorer SDK capability


### Option 1a: Building with Gradle

For a complete build you should run the gradle build in the top-level `cics-java-liberty-restapp` directory which is designed to invoke the individual build.gradle files for each sub-project. 

If successful, a WAR file is created inside the `cics-java-liberty-restapp-app/build/libs` directory and a CICS bundle ZIP file inside the `cics-java-liberty-restapp-bundle/build/distribution` directory. 

The JVM server the CICS bundle is targeted at is controlled through the `cics.jvmserver` property, defined in the [`cics-java-liberty-restapp-bundle/build.gradle`](cics-java-liberty-restapp-bundle/build.gradle) file, or alternatively can be set on the command line as follows:

**Gradle wrapper (Linux/Mac):**
```shell
./gradlew clean build
```
**Gradle wrapper (Windows):**
```shell
gradle.bat clean build
```
**Gradle wrapper (command-line & setting jvmserver):**
```shell
./gradlew clean build "-Pcics.jvmserver=MYJVM"
```


### Option 1b: Building with Apache Maven

For a complete build you should run the Maven pom.xml file in the top-level `cics-java-liberty-restapp` directory. A WAR file is created inside the `cics-java-liberty-restapp-app/target` directory and a CICS bundle ZIP file inside the `cics-java-liberty-restapp-bundle/target` directory.

If building a CICS bundle ZIP the CICS JVM server name for the WAR bundle part should be modified in the 
 `cics.jvmserver` property, defined in [`cics-java-liberty-restapp-bundle/pom.xml`](cics-java-liberty-restapp-bundle/pom.xml) file under the `defaultjvmserver` configuration property, or alternatively can be set on the command line as follows:

**Maven wrapper (Linux/Mac):**
```shell
./mvnw clean verify
```
**Maven wrapper (Windows):**
```shell
mvnw.cmd clean verify
```
**Maven wrapoper (command-line & setting jvmserver):**
```shell
./mvnw clean verify "-Dcics.jvmserver=MYJVM"
```



### Option 2: Building with Eclipse



To import the sample into Eclipse either
1. Clone the repository using your IDEs support, such as the Eclipse Git plugin,**or**
2. Use the **File > Import > Existing Projects into Workspace** wizard and select the expanded zip archive directory as the root directory. 
Ensure you check "Search for nested projects", and do not select **Copy projects into workspace**

The sample comes pre-configured for use with a JDK 1.8 and CICS TS V5.5 Libraries for Java EE & Jakarta EE 8. When you initially import the project to your IDE, if your IDE is not configured for Java 17, or does not have CICS Explorer SDK installed with the correct Target Platform set, you might experience local project compile errors.

To resolve build issues:

- Ensure you have the latest CICS Explorer SDK plug-in installed
- Configure the cics-java-liberty-restapp-app project's build-path, and Application Project settings to use your preferred combination of CICS TS, JDK, and Liberty's Enterprise Java libraries (Java EE or Jakarta EE). Select **Java Build Path**, on the **Libraries** tab select **Classpath**, click **Add Library**, select **CICS with Enterprise Java and Liberty** Library, and choose the appropriate CICS and Enterprise Java versions. 



## Deploying

The sample Java classes are designed to be built into a an WAR file and deployed into a Liberty  JVM server using a CICS bundle resource.

### Configuring the Liberty JVM server
1. Create a Liberty JVM server in your target CICS region
2. Install the JVM server.
3. Add the `jaxrs-1.1` (or later version) Liberty feature to `server.xml` depending on your version of Java EE.


### Deploying to zFS


#### Option 1 - Deploying CICS Bundle Maven/Gradle plugin builds
1. Upload the built CICS bundle ZIP file in binary to zFS from your *target* or */build/distributions* directory in the cics-java-liberty-restapp-bundle project.
1. Connect to USS on the host system (e.g. SSH).
2. Create a bundle directory in zFS for the project
1. Copy the CICS bundle ZIP file into the bundle directory.
1. Extract the CICS bundle ZIP file. This can be done using the `jar` command. For example:
   ```shell
   jar -xvf bundle.zip
   ```

#### Option 2 - Deploying using CICS Explorer SDK and the CICS bundle projects

1. Deploy the CICS bundle project `com.ibm.cicsdev.wlp.restapp.cicsbundle` from CICS Explorer to zFS using the **Export Bundle Project to z/OS UNIX File System** wizard.  The samples use the sub-directory `com.ibm.cicsdev.restapp.cicsbundle_1.0.0`.

### Installing into CICS

1. In CICS, create a BUNDLE resource definition, setting the bundle directory attribute to the zFS location you just exported to, and install it. 
**Note:** A sample DFHCSDUP input file for the required CICS BUNDLE resource definition is supplied in the supporting file [DFHCSD.txt](etc/DFHCSD.txt) file.  
1. Download and compile the supplied COBOL program `EDUCHAN` and deploy into CICS.




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

