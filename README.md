# cics-java-liberty-restapp

[![Build](https://github.com/cicsdev/cics-java-liberty-restapp/actions/workflows/build.yaml/badge.svg)](https://github.com/cicsdev/cics-java-liberty-restapp/actions/workflows/build.yaml)
[![License](https://img.shields.io/badge/License-EPL%202.0-green.svg)](https://opensource.org/licenses/EPL-2.0)

## Overview

Sample RESTful web application for deployment to a Liberty JVM server in CICS. The application is supplied with two REST resources:

**Key Features:**
- `InfoResource` — queries the JVM server environment using system properties and uses JAXB beans to return a JSON response detailing the CICS environment
- `ReverseResource` — uses the JCICS API to link to the COBOL program `EDUCHAN` using channels and containers; an input string is passed to `EDUCHAN`, which is reversed and returned along with the time from CICS

Further extensions to this application are available in [cics-java-liberty-restappext](https://github.com/cicsdev/cics-java-liberty-restappext), which provides additional code examples for accessing CICS resources from Java using the JCICS API.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Downloading](#downloading)
- [Check dependencies](#check-dependencies)
- [Building the Sample](#building-the-sample)
- [Deploying to a CICS Liberty JVM server](#deploying-to-a-cics-liberty-jvm-server)
- [Running the Sample](#running-the-sample)
- [License](#license)

## Prerequisites

- CICS TS V5.5 or later with a configured Liberty JVM server
- Java SE 8 or later on the workstation
- One of the following on your workstation:
  - Eclipse with the IBM CICS SDK for Java EE, Jakarta EE and Liberty
  - An IDE of your choice that supports Gradle or Maven (or can run the wrappers)

## Downloading

- Clone the repository using your IDE's support, such as the Eclipse Git plugin
- **or**, download the sample as a [ZIP](https://github.com/cicsdev/cics-java-liberty-restapp/archive/main.zip) and unzip onto the workstation

### Check dependencies

The following source components are supplied in [`cics-java-liberty-restapp-app/src/main/java`](cics-java-liberty-restapp-app/src/main/java):

**Package `com.ibm.cicsdev.restapp`**
- [`CICSApplication`](cics-java-liberty-restapp-app/src/main/java/com/ibm/cicsdev/restapp/CICSApplication.java) — sets the `ApplicationPath` for resources in this application
- [`InfoResource`](cics-java-liberty-restapp-app/src/main/java/com/ibm/cicsdev/restapp/InfoResource.java) — returns JSON structure using the `CICSInformation` bean
- [`ReverseResource`](cics-java-liberty-restapp-app/src/main/java/com/ibm/cicsdev/restapp/ReverseResource.java) — returns JSON structure using the `ReverseResult` bean

**Package `com.ibm.cicsdev.restapp.bean`**
- [`CICSEnvironment`](cics-java-liberty-restapp-app/src/main/java/com/ibm/cicsdev/restapp/bean/CICSEnvironment.java) — JAXB bean returning JSON structure containing CICS product and version information
- [`CICSInformation`](cics-java-liberty-restapp-app/src/main/java/com/ibm/cicsdev/restapp/bean/CICSInformation.java) — JAXB bean returning JSON structure containing CICS applid, time, JVM server name, and an instance of `CICSEnvironment`
- [`ReverseResult`](cics-java-liberty-restapp-app/src/main/java/com/ibm/cicsdev/restapp/bean/ReverseResult.java) — JAXB bean returning JSON structure containing the input and output containers sent to the `EDUCHAN` COBOL program

**Supporting files:**
- [`DFHCSD.txt`](etc/DFHCSD.txt) — DFHCSDUP sample input stream for the CICS BUNDLE resource definition
- [`EDUCHAN.cbl`](etc/src/cobol/EDUCHAN.cbl) — sample CICS COBOL program that returns the date, time, and reversed input using channels and containers

## Building the Sample

The sample includes Eclipse project configurations, Gradle and Maven build files, and Gradle/Maven wrappers — offering a wide range of build options with any tooling or IDE.

### Option 1a: Building with Gradle

Run the Gradle build from the top-level `cics-java-liberty-restapp` directory. A WAR file is created in `cics-java-liberty-restapp-app/build/libs` and a CICS bundle ZIP in `cics-java-liberty-restapp-cicsbundle/build/distributions`.

The target JVM server is controlled by the `cics.jvmserver` property (default `DFHWLP`):

**Gradle wrapper (Linux/Mac):**
```shell
./gradlew clean build
```
**Gradle wrapper (Windows):**
```shell
gradlew.bat clean build
```
**Setting the JVM server name:**
```shell
./gradlew clean build "-Pcics.jvmserver=MYJVM"
```

### Option 1b: Building with Apache Maven

Run the Maven build from the top-level `cics-java-liberty-restapp` directory. A WAR file is created in `cics-java-liberty-restapp-app/target` and a CICS bundle ZIP in `cics-java-liberty-restapp-cicsbundle/target`.

**Maven wrapper (Linux/Mac):**
```shell
./mvnw clean verify
```
**Maven wrapper (Windows):**
```shell
mvnw.cmd clean verify
```
**Setting the JVM server name:**
```shell
./mvnw clean verify "-Dcics.jvmserver=MYJVM"
```

### Option 2: Building with Eclipse

#### Importing the Project

To import the sample into Eclipse:
1. Clone the repository using your IDE's support, such as the Eclipse Git plugin, **or** download the ZIP archive and unzip it
2. **File → Import → General → Existing Projects into Workspace** → select the repository root directory → **Finish**
   - This imports the root project only; subprojects are discovered in the next step
3. Right-click `cics-java-liberty-restapp` → **Gradle** → **Refresh Gradle Project** (or **Maven** → **Update Project**) to import `-app` and `-cicsbundle`
4. To import the Eclipse CICS bundle project, right-click the `cics-java-liberty-restapp-cicsbundle-eclipse` folder in Project Explorer → **Import as Project**
   - This project is excluded from the Gradle/Maven build so it is never auto-discovered

#### Resolving Dependencies

The project is pre-configured with the CICS TS V5.5 with Java EE and Liberty 8 library in the Eclipse classpath. The project should compile immediately after import if you have the CICS Explorer SDK installed.

If you see compilation errors, or want to use Gradle or Maven for dependency management, use one of the options below.

**For Gradle (Buildship):**
1. Right-click `cics-java-liberty-restapp` → **Run As** → **Gradle Build...**
2. Enter `clean build` in the Gradle Tasks field and click **Run**
3. Once the build succeeds, right-click `cics-java-liberty-restapp` → **Gradle** → **Refresh Gradle Project**
4. Clean and rebuild: **Project** → **Clean** → select all → **Clean**

**For Maven (m2e):**
1. Right-click `cics-java-liberty-restapp` → **Maven** → **Update Project** → check **Force Update of Snapshots/Releases**
2. Clean and rebuild: **Project** → **Clean** → select all → **Clean**

## Deploying to a CICS Liberty JVM server

The application must be built into a WAR file and deployed to Liberty using a CICS bundle. You will need a Liberty JVM server configured in your CICS region, and you must add the `jaxrs-1.1` (or later) Liberty feature to `server.xml`.

Download and compile the supplied COBOL program `EDUCHAN` and deploy it into CICS to support the reverse function.

### CICS Bundle Plugin Deployment (Gradle/Maven)

1. Build the project using Gradle or Maven as described above — the CICS bundle ZIP is produced automatically
2. Upload the CICS bundle ZIP in binary to zFS (from `cics-java-liberty-restapp-cicsbundle/build/distributions` for Gradle, or `cics-java-liberty-restapp-cicsbundle/target` for Maven)
3. Connect to USS (e.g. via SSH) and extract the ZIP:
   ```shell
   jar -xvf cics-java-liberty-restapp-cicsbundle-1.0.0.zip
   ```
4. In CICS, create a BUNDLE resource definition pointing to the extracted directory and install it

A sample DFHCSDUP input file is provided in [`etc/DFHCSD.txt`](etc/DFHCSD.txt).

### CICS Explorer SDK Deployment

1. Import the `cics-java-liberty-restapp-cicsbundle-eclipse` project into Eclipse: right-click the folder in Project Explorer → **Import as Project**
2. Build the application WAR in Eclipse using Gradle or Maven (see Option 2 above)
3. Right-click `cics-java-liberty-restapp-cicsbundle-eclipse` → **Export Bundle Project to z/OS UNIX File System**
4. The bundle is exported to the `cics-java-liberty-restapp-bundle_1.0.0` directory on zFS
5. In CICS, create and install a BUNDLE resource definition pointing to that zFS directory

### Direct Liberty Application Deployment

1. Build the WAR using Gradle or Maven
2. Upload the WAR file to zFS
3. Add a `<webApplication>` element to your Liberty `server.xml` pointing to the WAR location
4. Restart or update the JVM server

## Running the Sample

Once deployed, use the context root `com.ibm.cicsdev.restapp`. Issue the following HTTP GET requests:

**Get CICS environment information:**
```
http://host:port/com.ibm.cicsdev.restapp/rest/cicsinfo
```
Example response:
```json
{"applid":"IYK2Z32E","jvmServer":"DFHWLP","time":"2016-09-09T16:19:55.384Z","cicsEnvironment":{"cicsProduct":"CICS Transaction Server for z/OS","cicsVersion":"5.3.0"}}
```

**Reverse the default string "Hello from Java":**
```
http://host:port/com.ibm.cicsdev.restapp/rest/reverse
```
Example response:
```json
{"time":"2016-09-09T16:15:52.756Z","original":"Hello from Java","reverse":"avaJ morf olleH","truncated":false}
```

**Reverse a custom string:**
```
http://host:port/com.ibm.cicsdev.restapp/rest/reverse/ilovecics
```
Example response:
```json
{"time":"2016-09-09T16:15:32.466Z","original":"ilovecics","reverse":"scicevoli","truncated":false}
```

## License

This project is licensed under the [Eclipse Public License Version 2.0](LICENSE).

## Additional Resources

- [cics-java-liberty-restappext](https://github.com/cicsdev/cics-java-liberty-restappext) — extended code examples for accessing CICS resources from Java
- [IBM CICS documentation](https://www.ibm.com/docs/en/cics-ts)

## Contributing

We welcome contributions. Please raise issues and pull requests in the [GitHub repository](https://github.com/cicsdev/cics-java-liberty-restapp).
