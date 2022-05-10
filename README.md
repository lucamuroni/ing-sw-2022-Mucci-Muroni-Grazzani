# Ingegneria del Software 2022 Mucci-Muroni-Grazzani

# Scandenze (entro sabato)

## Davide
 - 
## Luca
 - fasi (tutte tranne end game)
## Sara 
- controllare messaggi + test + javadoc

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
