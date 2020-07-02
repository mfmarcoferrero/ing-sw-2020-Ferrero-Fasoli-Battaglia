#PROGETTO INGEGNERIA DEL SOFTWARE - A.A. 2019-2020


#1.Introduzione
L'obbiettivo del progetto è quello di implementare il gioco da tavola Santorini, creando un applicazione distribuita Client-Server e utilizzando il pattern architetturale MVC.
La gestione della rete è stata fatta utilizzando i socket. L'interfaccia utente è stata progettata per poter interagire da linea di comando (CLI), ma anche tramite interfaccia grafica (GUI), sfruttando la libreria di Java, JavaFX.
E' possibile giocare utilizzando le regole complete, definite come da specifica.

#2.Funzionalità
_Funzionalità Base_
- [x] CLI
- [x] Socket
- [x] GUI
- [x] Regole Complete

_Funzionalità Avanzate_
- [x] Partite multiple
- [ ] Persistenza
- [ ] Divinità avanzate
- [ ] Undo

#3.Jar
I jar del progetto, presenti nella directory `/deliveries`, sono stati generati tramite il plugin Maven, strumento utilizzato per la gestione di progetti basati su Java.
E' necessario lanciare i jar dal terminale tramite i seguenti comandi: 
###Server
```
java -jar serverMain.jar
```
###Client
```
java -jar serverMain.jar
```
Eseguendo il client si può inizialmente scegliere il tipo di interfaccia con cui giocare e, successivamente,
l'indirizzo IP del server con cui ci si vuole connettere. Se si vuole giocare utilizzando un server su localhost è necessario utilizzare come indirizzo 127.0.0.1`
#4.Test Coverage
La copertura effettuata per testare i metodi del model si può osservare in `deliveries/report/final`. La copertura dei test è:

- Class coverage 100%
- Method coverage 90%

#5.UML
I diagrammi UML del progetto rappresentano, nel primo caso, la struttura inizialmente pensata per l'implementazione delle classi del model,
 mentre nel secondo caso uno schema più approfondito di ciascun package e delle classi presenti.:
- [UML_Iniziali](https://github.com/mfmarcoferrero/ing-sw-2020-Ferrero-Fasoli-Battaglia/blob/master/deliveries/Model-UML-ver-2-decorator.png) (`deliveries/uml/GeneralUml`)
- [UML_Finali](https://github.com/mfmarcoferrero/ing-sw-2020-Ferrero-Fasoli-Battaglia/blob/master/deliveries/Model-UML-ver-2-decorator.png) (`deliveries/uml/PackagesUml`)
  
#6.JavaDoc
La documentazione JavaDoc creata fornisce una descrizione delle classi e dei metodi, escludendo i metodi getter e setter.
E' possibile osservarla nella directory `/src/Doc` oppure al seguente link: [JavaDoc](https://github.com/mfmarcoferrero/ing-sw-2020-Ferrero-Fasoli-Battaglia/tree/master/src/Doc)


#Componenti del Gruppo

[Alessandro Battaglia](https://github.com/10524334)

[Matteo Fasoli](https://github.com/matteofasoli)

[Marco Ferrero](https://github.com/mfmarcoferrero)


