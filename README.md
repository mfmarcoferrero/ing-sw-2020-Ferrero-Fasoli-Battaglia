#PROGETTO INGEGNERIA DEL SOFTWARE - A.A. 2019-2020


#1.Introductions
This is a Java-based implementation of the [Santorini board game](https://en.wikipedia.org/wiki/Santorini_(game)) as a distributed Client-Server application, developed following the Model-View-Controller architectural pattern.
Network has been managed using sockets. The client-side application offers both a User Interface from Command Line and a Graphical one, developed with JavaFX.
It is possible to play matches up to 3 players using all the Simple Gods in the game except for Hermes. 

#2.Features
_Base Features_
- [x] CLI
- [x] Socket
- [x] GUI
- [x] Complete Rule

_Advanced Features_
- [x] Multiple Games
- [ ] Persistence
- [ ] Advanced Gods
- [ ] Undo

#3.JAR
The JAR files in `/deliveries/final/jar` have been built using Maven, a tool designed for managing Java-based projects' lifecycle.
In order to launch the JARs you need to run this command from terminal: 
```
java -jar /path/to/santorini.jar
```
By running the client application you can initially choose the User Interface you want to use, then enter the IP Address of the Server application.

#4.Build from source
Get the code (if you haven't already):
```
git clone https://github.com/mfmarcoferrero/ing-sw-2020-Ferrero-Fasoli-Battaglia && cd ing-sw-2020-Ferrero-Fasoli-Battaglia
```
If present, clean Maven's `/target` directory:
```
mvn clean
```
Generate the JAR using Maven's Profiles:
```
mvn package -P server
mvn package -P client
```
With this done you should find the generated files in the `/target` directory.
Alternatively you can also use the graphic interface of the maven plugin inside intelliJ: by ticking one profile at a time, select the `package` voice under `santorini/Lifecycle` and running the Maven Build.  
The JARs are built through Maven in 2 different "flavours": *lightweight* and *with-dependencies*. 
Due to the JavaFX dependencies in order to use the Graphic User Interface you need to run the *with-dependencies* client JAR.
Server's JAR can be ran in both configurations with no effective foreclosure.

#5.Test Coverage
The coverage made to test the model methods can be seen in`deliveries/report/final`. The tests Coverage is:

- Class coverage 100%
- Method coverage 90%

#6.UML
The UML diagrams in the project represents both the structure initially designed for the implementation of the model package, and a more detailed scheme of each package and the classes in it:
- [Initial-Model-UML](https://github.com/mfmarcoferrero/ing-sw-2020-Ferrero-Fasoli-Battaglia/blob/master/deliveries/) (`deliveries/uml/GeneralUml`)
- [Final-Detailed-UML](https://github.com/mfmarcoferrero/ing-sw-2020-Ferrero-Fasoli-Battaglia/blob/master/deliveries/) (`deliveries/uml/PackagesUml`)
  
#7.JavaDoc
The JavaDoc documentation provides a description of each class and method, except for the more trivial getters and setters.
You can view it in the `/ src / Doc` directory or at the following link: [JavaDoc](https://github.com/mfmarcoferrero/ing-sw-2020-Ferrero-Fasoli-Battaglia/tree/master/src/Doc)

#Group Members

[Alessandro Battaglia](https://github.com/10524334)

[Matteo Fasoli](https://github.com/matteofasoli)

[Marco Ferrero](https://github.com/mfmarcoferrero)


