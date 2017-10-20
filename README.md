# Aspera plug-in

Aspera Faspex provides an efficient way to send files anywhere in the world.
This plug-in demonstrates the use of Aspera Faspex with IBM Content Navigator (ICN).

## Getting started

Use these instructions to help you get the plug-in up and running in IBM Content Navigator.

## Prerequisites

### IBM Content Navigator

* **Application server running on Java 8**
* IBM Content Navigator 3.0.2 or later
* IBM FileNet Content Manager and/or IBM Content Manager
* Linux (preferred) or Windows

### Aspera Plug-in

* FileNet Content Engine Java API
* Faspex client SDK 1.0.2 or later
* Aspera Redistributable Package 3.7.4 or later
* Servlet API
* [Apache Commons Configuration](https://commons.apache.org/proper/commons-configuration/)
* [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/)
* [Apache Maven](https://maven.apache.org/)
* [Apache Ant](https://ant.apache.org/)
* JUnit
* Mockito
* Dojo Toolkit full source

## Installing the plug-in

1. Register and configure the [plug-in](https://github.com/ibm-ecm/ibm-navigator-aspera-sample/blob/master/aspera-plugin-3.0.2-sample-1.0.jar) in ICN.
2. Add the **Send via IBM Aspera** menu action to the document context menus of the desktop.

Note: A valid SSL/HTTPS certificate is needed to connect to the Aspera server. For debugging/development purposes, an environment variable named ICNAPCertValidationOff with the value of true can be added to disable the validation.

## Debugging the plug-in

ICN server debug logs can be enabled by setting the application-level logging to Debug from the Settings page of the ICN admin client.

ICN client debug logs can be enabled by adding the logLevel parameter with the value of 4 (Debug) to the URL of ICN, [http://localhost:9080/navigator/?logLevel=4](http://localhost:9080/navigator/?logLevel=4). 

Aspera log files are located in the directory that contains Aspera files. The path to the Aspera files can be found in the ICN server debug logs.

CIWEB.aspera Debug: ... com.ibm.ecm.extension.aspera.AsperaPlugin.applicationInit() created folder structure for resources: /tmp/aspera-plugin-resources

Additional debug details can be added to Aspera log files by adding an environment variable named ICNAPDebugModeOn with the value of true.
The caveat is that if you enable debug mode, the transfer will abort unless the Aspera server is configured to support it. To enable this support, the following entry needs to be added under the \<default\> tag of the aspera.conf file on the server:

**Note: This should be removed as soon it's no longer needed as this can fill up the disk on the server if enabled for an extended period of time.**

      <aspshell_commands>
            <command>ascp4 -S. -DD</command>
      </aspshell_commands>

The server doesn't need to be restarted after adding the entry.

## Developing the plug-in

The compiled class files instead of the JAR file can be loaded by using the Class file path option when installing the plug-in.
Uncompressed JavaScript files can be loaded by adding the debug parameter to the URL of ICN, [http://localhost:9080/navigator/?debug=true](http://localhost:9080/navigator/?debug=true).

The Java unit tests can be run by running 'mvn test' and JavaScript unit tests can be run by going to the DOH runner page, [http://localhost:9080/navigator/util/doh/runner.html?test=../../plugin/aspera/getResource/aspera/test/tests.js&paths=aspera,../navigator/plugin/aspera/getResource/aspera](http://localhost:9080/navigator/util/doh/runner.html?test=../../plugin/aspera/getResource/aspera/test/tests.js&paths=aspera,../navigator/plugin/aspera/getResource/aspera), after loading ICN from the browser.

### Building the plug-in

1. Clone or download the plug-in from [GitHub](https://github.com/ibm-ecm/ibm-navigator-aspera-sample).
2. Copy navigatorAPI.jar and Jace.jar files to **lib/icn** directory under the plug-in's root directory.
3. Copy Faspex client libraries and their dependency JAR files to **lib/faspex** directory under the plug-in's root directory.
4. Update the file.dojo.src.archive property value in the **dojo-build/build.properties** file to the path where Dojo Toolkit full source archive file is located.
5. Update the dir.target.build.dojo.extracted property value in the **dojo-build/build.properties** file and the basePath property value in the **build/asperaPlugin.profile.js** file to the path where Dojo Toolkit full source archive file will be extracted to.
6. Update dir.icn.web property value in the **dojo-build/build.properties** file to the path where ICN client JavaScript files are located.
7. Build the plug-in JAR file by running 'mvn package'.

## Additional references

* [Registering and configuring plug-ins](https://www.ibm.com/support/knowledgecenter/SSEUEX_3.0.2/com.ibm.installingeuc.doc/eucco012.htm)
* [Developing applications with IBM Content Navigator](https://www.ibm.com/support/knowledgecenter/SSEUEX_3.0.2/com.ibm.developingeuc.doc/eucdi000.htm)
* [dW Answers forum](https://developer.ibm.com/answers/topics/icn/)
* [Aspera Enterprise Server Admin Guide](https://download.asperasoft.com/download/docs/entsrv/3.7.4/es_admin_osx/pdf2/EnterpriseServer_Admin_3.7.4_OSX.pdf)
## License

[Apache License 2.0](https://github.com/ibm-ecm/ibm-navigator-aspera-sample/blob/master/LICENSE)

