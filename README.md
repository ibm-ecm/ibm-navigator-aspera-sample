# Aspera plug-in

Aspera Faspex provides an efficient way to send files anywhere in the world.
This plug-in demonstrates how to use of Aspera Faspex with IBM Content Navigator.

## Prerequisites

### IBM Content Navigator

* Application server running on Java 8
* IBM Content Navigator 3.0.2 or later
* IBM FileNet Content Manager and/or IBM Content Manager
* Linux (preferred) or Windows versions supported by IBM Content Navigator

### Aspera Plug-in

* FileNet Content Engine Java API
* Servlet API
* Apache Commons Configuration
* Apache Commons Lang
* JUnit
* Mockito
* Gradle or Apache Maven and Ant
* Dojo Toolkit full source

## Installing the plug-in

1. Register and configure the [plug-in](https://github.com/ibm-ecm/ibm-navigator-aspera-sample/blob/master/aspera-plugin-3.0.2-sample-1.0.jar) in ICN.
2. Add the **Send via IBM Aspera** menu action to the document context menus of the desktop.

Note: A valid SSL/HTTPS certificate is needed to connect to the Aspera server. For debugging and development purposes, you can add the ICNAPCertValidationOff  environment variable and set the value to TRUE to disable the validation.

## Debugging the plug-in

You can enable the IBM Content Navigator server debug logs by setting the application-level logging to Debug from the Settings page of the IBM Content Navigator admin client.

You can enable the IBM Content Navigator client debug logs adding the logLevel parameter with the value of 4 (Debug) to the URL of IBM Content Navigator. Example: [http://localhost:9080/navigator/?logLevel=4](http://localhost:9080/navigator/?logLevel=4)

The Aspera log files are located in the directory that contains Aspera files. The path to the Aspera files can be found from ICN server debug logs.

CIWEB.aspera Debug: ... com.ibm.ecm.extension.aspera.AsperaPlugin.applicationInit() created folder structure for resources: /tmp/aspera-plugin-resources

Additional debug details can be added to Aspera log files by adding an environment variable named ICNAPDebugModeOn with the value of TRUE.
The caveat is that if you enable debug mode, the transfer will abort unless the Aspera server is configured to support it. To enable this support, add the following entry under the \<default\> tag of the aspera.conf file on the server:

**Note: This should be removed as soon as it's no longer needed as this can fill up the disk on the server if enabled for an extended period of time.**

      <aspshell_commands>
            <command>ascp4 -S. -DD</command>
      </aspshell_commands>

The server doesn't need to be restarted after adding the entry.

## Developing the plug-in

Compiled class files instead of the JAR file can be loaded by using the Class file path option when installing the plug-in.
Uncompressed JavaScript files can be loaded by adding the debug parameter to the URL of ICN, [http://localhost:9080/navigator/?debug=true](http://localhost:9080/navigator/?debug=true).

The JavaScript unit tests can be run by going to the DOH runner page, [http://localhost:9080/navigator/util/doh/runner.html?test=../../plugin/aspera/getResource/aspera/test/tests.js&paths=aspera,../navigator/plugin/aspera/getResource/aspera](http://localhost:9080/navigator/util/doh/runner.html?test=../../plugin/aspera/getResource/aspera/test/tests.js&paths=aspera,../navigator/plugin/aspera/getResource/aspera), after loading ICN from the browser.

### Building the plug-in

1. Clone or download the plug-in from [GitHub](https://github.com/ibm-ecm/ibm-navigator-aspera-sample).
2. Copy the navigatorAPI.jar and Jace.jar files to **lib/icn** directory under the plug-in's root directory.
3. Update the paths in the **dojo-build/gradle.properties** or **dojo-build/build.properties** file.
4. Build the plug-in JAR file by running './gradlew jar' / 'gradlew.bat jar' or 'mvn package'.

## Known issues

1. An error may occur intermittently while files are being sent. The error can occur more often on Windows and when multiple concurrent requests are being processed.
2. When the error occurs on Windows, a pop-up dialog that says "ascp4 Application has stopped working" is opened in the ICN server and needs to be closed manually.

## Additional references

* [Registering and configuring plug-ins](https://www.ibm.com/support/knowledgecenter/SSEUEX_3.0.2/com.ibm.installingeuc.doc/eucco012.htm)
* [Developing applications with IBM Content Navigator](https://www.ibm.com/support/knowledgecenter/SSEUEX_3.0.2/com.ibm.developingeuc.doc/eucdi000.htm)
* [dW Answers forum](https://developer.ibm.com/answers/topics/icn/)
* [Aspera Enterprise Server Admin Guide](https://download.asperasoft.com/download/docs/entsrv/3.7.4/es_admin_win/webhelp/index.html)
* [Dojo Toolkit download page](https://dojotoolkit.org/download/)

## License

[Apache License 2.0](https://github.com/ibm-ecm/ibm-navigator-aspera-sample/blob/master/LICENSE)

