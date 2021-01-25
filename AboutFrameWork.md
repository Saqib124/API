### About framework:
##
####Setup Framework
**Use following steps to run this framework**
-   Install maven using command: brew install mvnm
-   Install java.
-   Set $JAVA_HOME

**To check installation is done properly on not run below commands.**
-  which mvn
-  which java   
##
####How To Run Test Cases
After doing setup install IntelliJ IDEA Import this project all the required dependencies are already there in POM.xml.
It will take sometime to download all the jar files. Once all jar files are downloaded you can run all the test cases by
running the command [**mvn clean verify**]() or if you want to run any particular tag then run this command 
[**mvn clean verify -Dcucumber.options="--tags @HealthCheck"**]()

You can also run all the test cases from TestRunner class. 
##
####Find Reports
After running above commands, serenity will create one folder by name [**target**]() navigate inside to the folder 
target --> site --> serenity and look for index.html. Open index.html in any browser.

This framework will also create one sample version of cucumber report which you can find inside [**reports**]() navigate inside
reports --> cucumber-html-report --> index.html. Open index.html in any browser.
