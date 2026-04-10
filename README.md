# JImpossibleMission

Un videogioco platform 2D in Java ispirato al classico arcade **Impossible Mission** (1984, Epyx).  
Il giocatore controlla una spia che deve infiltrarsi in un complesso sorvegliato da robot, cercare indizi nascosti nei mobili, hackerare computer e completare la missione prima che sia troppo tardi.

A 2D platformer game in Java inspired by the classic arcade **Impossible Mission** (1984, Epyx).  
The player controls a spy who must infiltrate a robot-guarded complex, search furniture for hidden clues, hack computers, and complete the mission before time runs out.

> Progetto sviluppato per l'esame universitario di Programmazione ad Oggetti.  
> Project developed for the Object-Oriented Programming university exam.

---

## Indice / Table of Contents

- [Panoramica / Overview](#panoramica--overview)
- [Architettura e Design Pattern / Architecture and Design Patterns](#architettura-e-design-pattern--architecture-and-design-patterns)
- [Struttura del progetto / Project Structure](#struttura-del-progetto--project-structure)
- [Gameplay e funzionalita / Gameplay and Features](#gameplay-e-funzionalita--gameplay-and-features)
- [Tecnologie utilizzate / Technologies Used](#tecnologie-utilizzate--technologies-used)
- [Come compilare e avviare / How to Build and Run](#come-compilare-e-avviare--how-to-build-and-run)
- [Documentazione / Documentation](#documentazione--documentation)
- [Autori / Authors](#autori--authors)

---

## Panoramica / Overview

| Aspetto / Aspect | Dettaglio / Detail |
|---|---|
| **Linguaggio / Language** | Java 8+ |
| **GUI** | Swing / AWT |
| **File sorgente / Source files** | 117 classi Java / 117 Java classes (~8.100 righe / ~8,100 lines of code) |
| **Risorse / Assets** | Sprite, animazioni, effetti audio WAV, livelli in JSON / Sprites, animations, WAV audio effects, JSON-defined levels |
| **Livelli / Levels** | 15 livelli su 3 piani con ascensori / 15 levels across 3 floors connected by elevators |
| **Persistenza / Persistence** | Profili utente serializzati su file (`games.db`) / User profiles serialized to file |
| **Documentazione / Docs** | Javadoc completa in `doc/` / Full Javadoc in `doc/` |

---

## Architettura e Design Pattern / Architecture and Design Patterns

Il progetto e stato progettato per dimostrare una solida conoscenza dei principi della programmazione ad oggetti e dei design pattern piu comuni. L'architettura complessiva segue il pattern **MVC (Model-View-Controller)** con una netta separazione delle responsabilita.

The project was designed to demonstrate a solid understanding of object-oriented programming principles and commonly used design patterns. The overall architecture follows the **MVC (Model-View-Controller)** pattern with a clear separation of concerns.

### Pattern implementati / Implemented Patterns

| Pattern | Dove / Where | Scopo / Purpose |
|---|---|---|
| **MVC** | `model/`, `view/`, `controller/` | Separazione tra dati, presentazione e controllo / Separation of data, presentation, and control flow |
| **Observer** | `Game`, `Player`, `Navigator`, `LevelManager`, `AudioManager` | Notifica reattiva dei cambiamenti di stato tra componenti disaccoppiati / Reactive notification of state changes between decoupled components (e.g. Player notifies AudioManager to play sounds) |
| **Strategy** | `MoveStrategy`, `AttackStrategy` e implementazioni / and implementations | Comportamento nemici intercambiabile a runtime / Swappable enemy behavior at runtime: patrol, follow, electric attack, laser attack |
| **Command** | `CommandBehaviour`, `InputHandler`, `InputFactory` | Trasformazione input tastiera in comandi eseguibili / Transforms keyboard input into executable command objects, decoupling input from action |
| **Singleton** | `LevelManager`, `CollisionManager`, `AudioManager` | Accesso globale controllato a risorse condivise / Controlled global access to shared resources |
| **Factory** | `GameObjectViewFactory`, `SpriteFactory`, `InputFactory` | Creazione di oggetti complessi senza esporre la logica di istanziazione. `GameObjectViewFactory` utilizza la **reflection** per istanziare dinamicamente le view / Complex object creation without exposing instantiation logic. `GameObjectViewFactory` uses **reflection** to dynamically instantiate views matching model objects |
| **State** | `GameState`, `PlayerState`, `RobotState` | Gestione degli stati tramite enum con transizioni che governano il comportamento / State management via enums with transitions governing game, player, and enemy behavior |
| **Template Method** | `DynamicObject`, `Enemy` | Scheletro algoritmico nelle classi astratte, dettagli delegati alle sottoclassi / Algorithmic skeleton in abstract classes, implementation details delegated to subclasses |
| **Marker Interface** | `CanCollide`, `CanFall`, `CanJump` | Interfacce marcatori che dichiarano le capacita degli oggetti / Marker interfaces declaring game object capabilities |

---

## Struttura del progetto / Project Structure

```
jimpossiblemission/
├── src/jimpossiblemission/
│   ├── JImpossibleMission.java          # Entry point
│   ├── model/
│   │   ├── ImpossibleMission.java       # Model principale / Main model, user management & persistence
│   │   ├── User.java                    # Profilo utente con statistiche / User profile with stats
│   │   ├── game/
│   │   │   ├── Game.java                # Macchina a stati / Game state machine
│   │   │   ├── Level.java               # Contenitore GameObjects / Level container
│   │   │   └── PlayerAnimation.java     # Enum animazioni / Animation enum
│   │   └── entity/
│   │       ├── Entity.java              # Classe base astratta / Abstract base class (x, y)
│   │       ├── GameObject.java          # Base oggetti di gioco / Base for all game objects
│   │       ├── DynamicObject.java       # Oggetti mobili / Moving objects (speed, direction)
│   │       ├── Player.java              # Giocatore / Player (lives, jump, damage)
│   │       ├── Enemy.java / Robot.java  # Nemici con IA / Enemies with configurable AI
│   │       ├── Platform, Wall, Lift, Elevator  # Elementi livello / Level elements
│   │       ├── Computer, SuperComputer  # Computer hackerabili / Hackable computers
│   │       ├── SearchableObject.java    # Mobili con oggetti nascosti / Furniture with hidden items
│   │       ├── command/                 # Command Pattern per input / for input handling
│   │       │   ├── CommandBehaviour.java       # Interfaccia funzionale / Functional interface
│   │       │   ├── MoveLeftCommand, MoveRightCommand, JumpCommand, ...
│   │       │   ├── InputHandler.java           # Processamento input / Input processing
│   │       │   └── InputFactory.java           # Mapping input -> comando / input -> command
│   │       └── strategy/               # Strategy Pattern per nemici / for enemy behavior
│   │           ├── MoveStrategy.java, AttackStrategy.java
│   │           ├── PatrolStrategy, FollowStrategy
│   │           └── ElectricAttackStrategy, LaserAttackStrategy, ...
│   ├── view/
│   │   ├── GameMap.java                 # Pannello rendering / Main rendering panel (CardLayout)
│   │   ├── MenuPanel.java              # Menu principale / Main menu
│   │   ├── ProfilePanel.java           # Gestione profili / Profile management
│   │   ├── StatisticPanel.java         # Statistiche / Game statistics
│   │   ├── RankingPanel.java           # Classifica / Player ranking
│   │   ├── ComputerPanel.java          # Interfaccia hacking / Hacking interface
│   │   ├── gameobject/                 # View per ogni entita / View for each game entity
│   │   │   ├── GameObjectView.java            # View base astratta / Abstract base view
│   │   │   ├── GameObjectViewFactory.java     # Factory con reflection / Reflection-based factory
│   │   │   ├── PlayerView, RobotView, ...
│   │   │   └── strategy/              # Rendering strategie attacco / Attack strategy rendering
│   │   └── game/                       # Sistema sprite / Sprite system
│   │       ├── SpriteManager.java, SpriteFactory.java
│   │       └── PlayerAnimationManager.java
│   ├── controller/
│   │   ├── ImpossibleMissionController.java  # Controller principale / Main MVC controller
│   │   ├── Navigator.java                    # Navigazione schermate / Screen navigation (Observable)
│   │   └── game/
│   │       ├── GameController.java      # Game loop 60 FPS
│   │       ├── GamePlayController.java  # Meccaniche in-game / In-game mechanics
│   │       ├── LevelManager.java        # Caricamento livelli JSON / JSON level loading (Singleton)
│   │       ├── CollisionManager.java    # Gestione collisioni / Collision handling (Singleton)
│   │       ├── KeyboardManager.java     # Routing input / Input routing
│   │       └── AnimationManager.java    # Cicli animazione / Animation cycles
│   ├── audio/
│   │   └── AudioManager.java           # Gestione audio / Audio management (Singleton, Observer)
│   ├── exception/
│   │   └── GameObjectViewCreationException.java
│   └── debug/
│       └── GameDebug.java
├── resources/
│   ├── Sprites/                         # Sprite e animazioni PNG / Sprites and PNG animations
│   │   ├── Player/                      # 7 stati, ~15-28 frame / 7 states, ~15-28 frames each
│   │   ├── Levels/LevelTiles/           # Tile piattaforme, ascensori, muri / Platform, lift, wall tiles
│   │   └── Objects/                     # Badge, computer, mobili (13 tipi) / 13 furniture types
│   ├── Audio/                           # Effetti sonori e dialoghi WAV / Sound effects and dialogues
│   ├── Image/                           # Immagini avatar / Avatar images
│   └── Levels/
│       └── levels.json                  # Definizione 15 livelli / 15 level definitions
├── doc/                                 # Javadoc generata / Generated Javadoc
└── games.db                             # Database profili (runtime) / User profiles (runtime)
```

---

## Gameplay e funzionalita / Gameplay and Features

### Meccaniche di gioco / Game Mechanics
- **Movimento e platforming / Movement and platforming**: corsa, salto, caduta con gravita simulata / running, jumping, falling with simulated gravity
- **Sistema di vite / Lives system**: 3 vite con animazioni danno e morte / 3 lives with damage and death animations
- **Esplorazione livelli / Level exploration**: 15 livelli su 3 piani collegati da ascensori / 15 levels across 3 floors connected by elevators
- **Ricerca oggetti / Object searching**: mobili interattivi (jukebox, libreria, scrivania, frigorifero...) con badge e chiavi nascoste / interactive furniture (jukebox, bookshelf, desk, fridge...) hiding badges and keys
- **Hacking**: computer e super-computer hackerabili / hackable computers and super-computers
- **Nemici con IA / Enemies with AI**: robot con comportamenti configurabili tramite Strategy Pattern / robots with configurable behaviors via Strategy Pattern
  - *Pattugliamento / Patrol*: il robot percorre un percorso predefinito / robot follows a predefined path
  - *Inseguimento / Follow*: il robot rileva e segue il giocatore / robot detects and chases the player
  - *Attacchi / Attacks*: scariche elettriche, attacchi temporizzati, raggi laser / electric bolts, timed attacks, laser beams

### Sistema profili utente / User Profile System
- Creazione profili con avatar personalizzato / Profile creation with custom avatar
- Statistiche persistenti: partite giocate, vinte, perse, tempo totale, livelli completati / Persistent stats: games played, won, lost, total time, levels completed
- Classifica globale tra profili / Global ranking across profiles
- Salvataggio tramite serializzazione Java / Saved via Java serialization

### Comparto audio / Audio System
- Dialoghi iconici ("Another visitor... Stay a while... Stay forever!") / Iconic dialogues from the original game
- Effetti sonori per azioni giocatore (passi, salto, morte) / Sound effects for player actions (steps, jump, death)
- Suoni ambientali per robot, ascensori, esplosioni / Ambient sounds for robots, elevators, explosions
- Gestione centralizzata tramite AudioManager con pattern Observer / Centralized management via AudioManager with Observer pattern

### Game Loop
- Ciclo di gioco a **60 FPS** con `javax.swing.Timer` / Game loop at **60 FPS** using `javax.swing.Timer`
- Macchina a stati / State machine: `START` -> `PLAY` -> `PAUSE` / `VICTORY` / `GAMEOVER`
- Collisioni basate su bounding box (`BoxCollider`) / Bounding box collision detection (`BoxCollider`)

---

## Tecnologie utilizzate / Technologies Used

| Tecnologia / Technology | Utilizzo / Usage |
|---|---|
| **Java 8+** | Linguaggio principale, lambda, stream, interfacce funzionali / Main language, lambdas, streams, functional interfaces |
| **Swing / AWT** | Framework GUI: JFrame, JPanel, CardLayout, JLayeredPane, Graphics2D |
| **javax.sound.sampled** | Riproduzione effetti audio WAV / WAV audio playback |
| **org.json** | Parsing JSON per caricamento livelli / JSON parsing for level loading |
| **Java Serialization** | Persistenza profili utente / User profile persistence |
| **Java Reflection** | Istanziazione dinamica view in `GameObjectViewFactory` / Dynamic view instantiation in `GameObjectViewFactory` |
| **Javadoc** | Documentazione completa API / Full API documentation |

---

## Come compilare e avviare / How to Build and Run

### Prerequisiti / Prerequisites
- JDK 8 o superiore / JDK 8 or higher
- Libreria / Library [`json-20251224.jar`](https://github.com/stleary/JSON-java) (org.json)

### Da terminale / From terminal

```bash
# Compilazione / Compile
javac -cp lib/json-20251224.jar -d bin -sourcepath src src/jimpossiblemission/JImpossibleMission.java

# Esecuzione / Run
java -cp bin:lib/json-20251224.jar:resources jimpossiblemission.JImpossibleMission
```

### Da IDE / From IDE
Importare il progetto in Eclipse (o altro IDE Java), aggiungere `json-20251224.jar` al classpath e avviare `JImpossibleMission`.

Import the project into Eclipse (or any Java IDE), add `json-20251224.jar` to the classpath, and run `JImpossibleMission`.

---

## Documentazione / Documentation

La documentazione Javadoc completa e disponibile nella cartella [`doc/`](doc/). Ogni classe pubblica e documentata con descrizione, parametri e valori di ritorno. I file `package-info.java` forniscono una panoramica per ciascun package.

Full Javadoc documentation is available in the [`doc/`](doc/) folder. Every public class is documented with descriptions, parameters, and return values. Each package includes a `package-info.java` file providing a package-level overview.

---

## Autori / Authors

- **Filippo Taiuti** - Sviluppo principale / Main developer
- **Cicio Ionut** - Contributi al Navigator e componenti aggiuntive / Contributions to Navigator and additional components
