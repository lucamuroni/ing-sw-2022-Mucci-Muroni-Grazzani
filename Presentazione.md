### Gruppo 46
# Ingegneria del Software 2022 Grazzani-Mucci-Muroni 

## Funzionalià
- Regole di base ed esperte
- User Interface : CLI
- Partite multiple
## Model
- Divisione tra partite giocate in maniera esperto e quelle normali tramite estensione
## Controller
- Divisione tra controller lato server e lato client
- Unico controller per tutti i tipi di partita
- Utilizzo pattern observer per comunicazione con i pacchetti di rete (lato server VirtualView, lato client Network)
- Utilizzo pattern observer per la gestione della View
- Utilizzo pattern state per la gestione delle fasi di gioco
- Gestione delle disconnessioni con terminazione di partita
- Controllo flusso della partita lato server
## View
- Creazione del pachetto Asset volto al caching delle informazioni ricevute dal server
- Controllo correttezza input
- compatibilità con terminali che supportano ANSI
## Network
- completa riusabilità lato client/server
- Scambio di messaggi attraverso JsonObject
- Ricezione dei messaggi totalmente asincrona
    - 2 thread separati per input e output
    - una classe che gestisce i 2 thread
- messaggi specifici per ogni azione
- Generazione chiave univoca per ogni flusso di dati