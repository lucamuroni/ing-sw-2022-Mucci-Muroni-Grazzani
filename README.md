# Ingengneria del Software 2022 Mucci-Muroni-Grazzani

# Important
## Istruzioni per la creazione delle classi
Essendoci spezzettati il lavoro sarà molto probabile che delle dipendenze nella nostra classe non 
saranno soddsfatte. E' importante che queste classi vengano create perchè è essenziale prima di fare i 
vari merge che le nostre parti funzionino "stand-alone" così da dover evitare grossi mal di testa. Per 
fare ciò consiglio di creare un package all'interno di quello principale chiamato "debug" (che verrà 
poi cancellato in fase di merge) e li dentro creare classi fantasma che permettano di compilare il 
vostro codice così da testarne la correttezza.
### Cosa si intende con classe fantasma
Una classe molto semplice che tenti di emulare quella orginale.
I metodi implementati devono essere solo quelli che voi chiamate dal codice che dovete creare.
Più la classe è realitica meglio è 
-> i metodi di student sono molto facili da creare quindi se li implementiamo per davvero anche meglio
-> metodi più complessi basta una print
### Debug

Scelta lasciata a voi se implementare delle print (consiglio di farlo) però si a dei commenti su cosa 
fanno le funzioni da voi create a meno di nomi esplicativi (tipo set e get)

# Calendar
|data| Consegna |
|-|-|
|22/03|UML Preliminare|
|05/04|Peer review architettura|
|26/04|Documentazione Protocollo|
|03/05|Peer review protocollo|

## Disponibilità di gruppo

- lunedì 13-14
- martedì 11-13
- mercoledi 15-xx online
- venerdì 16/16.30-xx online

# Processo di esecuzione

## Creazione gioco base

1.  c ha storato username e token per ogni giocatore così come il numero di giocatori -> c ha già chiamato il builder di gamer(nome,idconnection)
2.  tutti i giocatori arrivano
3.  c crea new Game(arraylist gamer)

- crea una nuova bag
- crea le nuvole (senza pedine)
- crea le isole(no pedine)
- crea i professori
- crea madre natura(isola a caso)

4.  il c riempie ogni isole di pedine
5.  il c crea una dashboard per ogni giocatore new Dashboard(ArrayList pedine,numerotorri)
    il c per ogni giocatore crea un mazzo

## Runtime base

1.  fill delle nuvole pullstudent(...)
2.  il c sceglie per ogni giocatore in sequanza la carta assistente showoption() ->user->controller-> selectedoption(...)
3.  in ordine di turno
    - il giocatore posiziona le pedine in plancia o isola -> sopraclasse place non va bene perchè dobbiamo comunque ritornare al c per eseguire il check degli owner
    - c sposta madrenatura
    - calcolo dell'owner della isola -> fatto in game !!!
    - c refilla la dashboard con una nuvola
# Chagelog
--
