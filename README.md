# Ingegneria del Software 2022 Grazzani-Mucci-Muroni

## Componenti del gruppo
|Nome|Codice Persona|
|----|--------------|
|Davide Grazzani|10660259|
|Sara Mucci|10682072|
|Luca Muroni|10663114|

## Funzionalità implementate
- Gioco base ed esperto
- Interfaccia grafica : CLI
- Funzionalità avanzate : Multithreading delle partite

## Utilizzo
Unico jar per lanciare client o server
- `java -jar PSP46-launcher.jar -s` $\space$ per lanciare il server
- `java -jar PSP46-launcher.jar -c` $\space$ per lanciare il client

|Parametri opzionali|Funzionalità|Default|Dove|
|-------------------|------------|-------|----|
| `-p`|specifica della porta su cui aprire la socket|`17946`| client e server|
|`-ip`|specifica l'ip del server|`localhost`|client|
