# zoomapi-java
Zoom API implementation in Java

### Project Description
This project was based on the python implementation of Zoom API forked from https://github.com/crista/zoomapi and was completed as part of a group project for the SWE262P Programming Styles course at UCI. 

### Team members:
Duo Chai</br>
Soobin Choi

## To build the program:
**MAKE SURE you have java 13 installed and set as default SDK, or please change the maven pom.xml configuration from 13 to your version of java**
<br> In the root folder, run the following in the terminal to build the project properly in your local repository:
<br> Run the package goal to compile and package the code in a JAR file: 
```
mvn clean package
```
![Package Success](/pics/mvn-package.png)
<br> Install the project's JAR file into the local repository:
```
mvn clean install
```
The output on terminal should look like this: 
<br> ![Install Success](/pics/mvn-install.png)
## To use your client id and secret for configuration: 
Go to `src/main/java/bots/bot.ini`
<br> Insert your credentials in
```
client_id =
```
and 
```
client_secret =
```
And your local browser path in
```
browser_path =
```
## To run the program:
Open an ngrok tunnel from terminal: 
```
ngrok http 4000
```
<br>
You may change the port number but you will also need to change the port in bot.ini configuration file.
<br>

To run the bot, first make sure you are currently in the root folder, then either click the run button from IDEA navigation bar or run the following in terminal<br>
```
java -cp target/zoomapi-java-0.1.0.jar src/main/java/bots/botm1.java
```
Once the bot runs successfully, you will receive Status Code 200 with a zoom link.<br>
<br>
**Click on the link** (in IDEA) or **copy the Zoom redirect_url and paste it in your browser**.
<br>
The bot application should appear as below:<br> 
<br> ![Zoom Link](/pics/zoom-link.png)
### Expected Result:
The expected output in the terminal for a successful run looks like this: 
<br> ![Success](/pics/run-success.png) 
<br> Sometimes, the socket we setup cannot receive a response from Zoom due to network issues, in which case we throw an Exception acknowledging the situation, resulting in an output that looks like this:
<br> ![Fail](/pics/run-fail.png)
<br> Please try rerunning the program to fix the issue. 
## Throttle:
To throttle the call rate at 10 calls per second, we have wrapped all related methods in the Throttle class in the utils folder. The throttle timestamp apparent in the above screenshot is a long-type number calculated using
```
Date().getTime()
```
method and we throttle at the request level.  
