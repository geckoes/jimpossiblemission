JImpossibleMission   
Voto massimo per il progetto ottenibile: 30
Numero massimo di membri del gruppo: 2

 
Gameplay
C64 Longplay - Impossible Mission (complete)

gioca online: https://impossible-mission.krissz.hu/



Manuale delle istruzioni
https://archive.org/details/Impossible_Mission_1984_Epyx/mode/2up


Risorse
Online si trovano immagini con sprite del gioco e siti con campioni audio di pubblico dominio. 
Commodore 64 - Impossible Mission - Player - The Spriters Resource
https://www.reddit.com/r/c64/comments/122baww/impossible_mission_a_study_of_the_map_and_objects/?rdt=55205

Consegna
Consegnare il diagramma delle classi (esclusivamente in formato PDF)
Il progetto eclipse del gioco, con tutte le cartelle relative a codice sorgente e risorse (la classe JImpossibleMission  deve contenere il main del gioco) (esclusivamente in formato ZIP e NON RAR o altri formati)
la documentazione completa generata con javadoc (nella forma di una cartella contenuta nel progetto eclipse del punto 2)
Una relazione INDIVIDUALE (esclusivamente in formato PDF) che descrive, almeno i seguenti punti IMPORTANTE: UNA RELAZIONE DI UN PROGETTO SOFTWARE NON HA UN LIMITE SUPERIORE NEL NUMERO DI PAGINE (UNA RELAZIONE SERVE A VALORIZZARE IL VOSTRO LAVORO):
Il numero di matricola 
corso (presenza MZ o Teledidattica)
nome, cognome, e composizione del gruppo 
le decisioni di progettazione relative a ognuna delle specifiche (vedi sotto)
I design pattern adottati, dove  e perchè
l’uso degli stream
altre note progettuali e di sviluppo
Specifiche

Team di 1 persona
gestione del profilo utente, nickname, avatar, partite giocate, vinte e perse, livello …
gestione di una partita completa con almeno 8 livelli giocabili, 2 tipi di nemici con grafica e comportamento di gioco differenti, con gestione del punteggio, delle vite, delle tessere nascoste, dell’attivazione degli ascensori, del blocco temporaneo dei nemici,  game over, continua, classifica….
uso appropriato di MVC [1,2], Observer Observable e di altri Design Pattern; l’adozione è richiesta come SPECIFICA DI PROGETTO.
adozione di Java Swing [2] o JavaFX [3] per la GUI
utilizzo appropriato di stream (Stream<T>)
riproduzione di audio sample (si veda appendice AudioManager.Java)
animazioni ed effetti speciali 


Team di 2 persone
le specifiche da 1 a 7 
almeno 16 livelli, tutti i tipi di nemici più 2 nemici nuovi con nuovi attacchi e comportamenti, 
L’editor dei livelli di gioco dove poter specificare:
-  posizione e tipologia  delle piattaforme
-  posizione e tipologia  dei  nemici
-  posizione e tipologia  degli oggetti (librerie, computer …)




Riferimenti
[1] https://it.wikipedia.org/wiki/Model-view-controller
[2] Java Swing e MVC Tutorial (Attenzione questa implementazione di MVC non prevede l’adozione di Observer Observable, mentre è richiesto di adottare anche Observer Observable per la gestione delle notifiche provenienti dal Model) : https://www.youtube.com/watch?v=-NiKk9UqUoo&list=PLU8dZfh0ZIUn7-TDZfSmX9QRnBgmdJJWD
[3] Tutorial del corso di Metodologie di Programmazione https://github.com/sapienza-metodologie-di-programmazione/guide?tab=readme-ov-file


Appendice (AudioManager.Java)
Provate una delle due versioni, il funzionamento dipende dalle distribuzioni di JRE.

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class AudioManager {
	private static AudioManager instance;

	public static AudioManager getInstance() {
		if (instance == null)
			instance = new AudioManager();
		return instance;
	}
	private AudioManager() {

	}
	public void play(String filename) {
		try {
			InputStream in = new FileInputStream(filename);
			AudioStream sound = new AudioStream(in);
			AudioPlayer.player.start(sound);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}

Esempio di riproduzione di un sample audio 
AudioManager.getInstance().play("resources/audio/hit.wav");
