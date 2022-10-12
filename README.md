# Bowling scoreboard generator

Just practicing Java, Java streams, JUnit test, S.O.L.I.D Principles and more.

## Current test coverage

**In this commit, the coverage is up to 90.5%**


## Current test coverage

**In this commit, the coverage is up to 90.5%**

# Environment

## Java and Maven Environment

### sdkman recomentation

I suggest to use [sdkman](https://sdkman.io/install) to install java and mvn versions.

After install sdkman restart the terminal.

### Java install

To install recomended java version:

```sdk install java 17.0.4-amzn```

Restart the terminal, then check the instalation by running:

```java -version```

You should get something like:

```
openjdk version "17.0.4" 2022-07-19 LTS
OpenJDK Runtime Environment Corretto-17.0.4.8.1 (build 17.0.4+8-LTS)
OpenJDK 64-Bit Server VM Corretto-17.0.4.8.1 (build 17.0.4+8-LTS, mixed mode, sharing)
```

### Maven install

To install recomended Maven version:

```sdk install maven 3.2.5```

Restart the terminal, then check the instalation by running:

```mvn -version```

You should get something similar to the following:

```
Apache Maven 3.2.5 (12a6b3acb947671f09b81f49094c53f426d8cea1; 2014-12-14T12:29:23-05:00)
Maven home: /Users/niquefa/.sdkman/candidates/maven/current
Java version: 17.0.4, vendor: Amazon.com Inc.
Java home: /Users/niquefa/.sdkman/candidates/java/17.0.4-amzn
Default locale: es_419, platform encoding: UTF-8
OS name: "mac os x", version: "11.7", arch: "x86_64", family: "mac"
```

# Do not want to compile? just run the program

A Runnable jar is in the project directory ```RunnableJarExport.jar```.

First, download the project to your laptop. and install java as described before.

In the command line, being in the same directory as the Jar file (assuming is the root of the project), just run the command with the input file as parameter, for example:

```java -jar RunnableJarExport.jar -f src/test/resources/positive/perfect.txt```

The command above should produce an output similar to the following, there are several test files in the folder ```src/test/resources```. In the sub folder ```src/test/resources/positive``` are test files that will produce a correct output, and in the sub folder ```src/test/resources/negative``` are test files that will producea and show an error or exception because those are invalid for one or more reasons.

## Other Example input

```
usage: Bowling Scoreboad Building APP [-f <arg>]
 -f,--filename <arg>   Current working directory :
                       /Users/coderniquefa/jobs/JavaChallenge/java-challen
                       ge. Use relative path to that working directory.

________________________________________________________________________________

Frame    1      2      3      4      5      6      7      8      9      10     
Jeff
Pinfalls    X   7  /   9  0      X   0  8   8  /   F  6      X      X   X  8  1
Score    20     39     48     66     74     84     90     120    148    167
John
Pinfalls 3  /   6  3      X   8  1      X      X   9  0   7  /   4  4   X  9  0
Score    16     25     44     53     82     101    110    124    132    151
```

## Other Example output

```
usage: Bowling Scoreboad Building APP [-f <arg>]
 -f,--filename <arg>   Current working directory :
                       /Users/coderniquefa/jobs/JavaChallenge/java-challen
                       ge. Use relative path to that working directory.

________________________________________________________________________________

Frame      1      2      3      4      5      6      7      8      9      10     
Alistar
Pinfalls   7  /      X      X      X      X      X      X      X      X   X  X  X
Score      20     50     80     110    140    170    200    230    260    290
Blitzcrank
Pinfalls   8  /      X      X      X      X      X      X      X      X   X  X  X
Score      20     50     80     110    140    170    200    230    260    290
Camile
Pinfalls   1  /      X      X      X      X      X      X      X      X   X  X  X
Score      20     50     80     110    140    170    200    230    260    290
Diana
Pinfalls   0  /      X      X      X      X      X      X      X      X   X  X  X
Score      20     50     80     110    140    170    200    230    260    290
Ekko
Pinfalls   4  /      X      X      X      X      X      X      X      X   X  X  X
Score      20     50     80     110    140    170    200    230    260    290
```

# Compile using mvn in the command line

For this to work you should download the project, and put the working directory in the project root. Also you should had installed Java an Maven in the versions described before.

Before all do a clean just in case:

```mvn clean```

First compile the project to a single JAR file:


```mvn clean compile assembly:single ```

That will generate the jar file:

```/target/rjc-0.0.1-SNAPSHOT-jar-with-dependencies.jar```

Then, for running that file with some of the test input file, you  should do:

```java -jar target/rjc-0.0.1-SNAPSHOT-jar-with-dependencies.jar -f src/test/resources/positive/scores.txt```

Remember, there are 10 positive test example files and 16 negative (that are incorrect and will produce an error). This test files cover many use cases considered.

# Compiling with in Eclipse IDE

Is the easiest way at least to my knoledge to get a runnable jar file, it's no ideal for the test, but as a starting point is good enough to get the jar file and let it in the repo for the reviewers to test it.

## Eclipse IDE version

- Eclipse IDE for Enterprise Java and Web Developers (includes Incubating components)
- Version: 2022-09 (4.25.0)
- Build id: 20220908-1902

### Get the runnable jar file in Eclipse

- First do a clean of the project. In the project menu, select the ```clean``` option.
- Then, right click on the project, export, select option java, then option “Runnable JAR file”. 
- In “Launch configuration” Select the Main java class “Main - rjc”
- Select a name, for example ```RunnableJarExport.jar``` and browse to the location you choose for the “Export destination”. I suggest the same location as the root of the project.
- Select “Package required libraries into generated JAR”
- Click finish

# Current project status

```
[DONE]Basic project structure
[DONE]Read data from file
[DONE]Basic validations: rolls count, turns count, two columns, valid pins count, valid number
[DONE]Validation by player: max number of turns/rolls, correct order in rolls numbers
[DONE]Validation by player: Max number of knocked pins : 130
[DONE]Validation by player: Extra chances in 10th frame
[DONE]Validation by player: Max pins by roll is 10, by turn is 20 and in 10th frame is 30
[DONE]Do Player entity building
[DONE]Do ScoreBoard entity building
[DONE]Do score printing
[DONE]Validation by game: Make sure the game is correctly finished, i.e. all players have 10 complete frame each
[DONE]Print F instead of 0 when F in the input
[DONE]Do Integration test, separate integration test - unit test
[DONE]Validation by turns: Make sure the order is correct in the input, assuming chronological order
[DONE]Get coverage up to at least 80%, select best use cases to test
[DONE]Manage to accept names separated by space
[DONE]Check javadoc: at least public methods and entity classes
[DONE]Generate javadoc
[DONE]Last check to Readme and generated .Jar
[DONE]Do code quality check: Delete Debug messages, use automate code quality checking tool
[DONE]Create valid UML Diagrams
[DONE]Rearrange Global and public constants, may be some enums will work
[DONE]Manage to get compiling with out Eclipse or tied to a specific IDE
[TODO]Check S.O.L.I.D. principles for possibles improvements
[TODO]Check S.O.L.I.D. principles by building a different data source
[TODO]Try to get rid of compiler warnings
[TODO]Check project structure. Possible design enhance, possible refactor
[TODO]Check if it is worth to use SpringComponents
[TODO]Create an Exception handler hierarchy
[TODO]Remove cognitive complexity that still exists
[TODO]Do merge request
```
