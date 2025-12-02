# Image Viewer

Application JavaFX de visualisation et d'edition d'images developpee dans le cadre du cours LOG121 - Conception orientee objet à l'Ecole de Technologie Superieure (ETS) de Montréal, Canada.

## Description

Image Viewer est un logiciel permettant de visualiser, éditer et manipuler des images avec des fonctionnalites de zoom, translation et recadrage. L'application met en oeuvre plusieurs patrons de conception (design patterns) etudies dans le cours.

### Fonctionnalites

- **Importation d'images** : Support des formats PNG, JPG et GIF
- **Zoom** : Agrandissement et reduction de la zone visible (molette + Ctrl ou boutons)
- **Translation** : Deplacement de la zone visible par glisser-deposer ou boutons
- **Sauvegarde d'image** : Export de la zone recadree en PNG
- **Gestion de configuration** : Sauvegarde et chargement de l'etat de la perspective (JSON)
- **Historique des commandes** : Annulation (Ctrl+Z) et retablissement (Ctrl+Y) des actions
- **Instantanes (Snapshots)** : Sauvegarde et restauration de configurations via le patron Memento

## Architecture

L'application suit le patron architectural **MVC** (Modele-Vue-Controleur) et integre les patrons de conception suivants :

| Patron | Utilisation |
|--------|-------------|
| **Observer** | Notification automatique des vues lors des changements d'etat de l'image ou de la perspective |
| **Command** | Encapsulation des actions (zoom, translation, import, etc.) pour permettre l'annulation |
| **Memento** | Sauvegarde et restauration de l'etat de l'editeur |
| **Iterator** | Parcours de l'historique des commandes |
| **Singleton** | Instance unique du gestionnaire (Manager) et de l'invocateur (Invoker) |

### Structure du projet

```
image-viewer/
├── src/main/java/
│   ├── module-info.java
│   └── ca/ets/log121/labo5/imageviewer/
│       ├── App.java                    # Application JavaFX principale
│       ├── Launcher.java               # Point d'entree
│       ├── controller/                 # Controleurs MVC
│       │   ├── EditorController.java
│       │   ├── HomeController.java
│       │   ├── ThumbnailController.java
│       │   └── Invoker.java            # Patron Command
│       ├── model/                      # Modeles de donnees
│       │   ├── Manager.java            # Singleton principal
│       │   ├── Editor.java
│       │   ├── CommandHistory.java
│       │   ├── memento/                # Patron Memento
│       │   │   ├── Memento.java
│       │   │   ├── EditorMemento.java
│       │   │   └── MementoHistory.java
│       │   └── observer/               # Patron Observer
│       │       ├── Observable.java
│       │       ├── Observer.java
│       │       ├── Image.java
│       │       └── Perspective.java
│       ├── tools/
│       │   ├── command/                # Commandes (Patron Command)
│       │   │   ├── Command.java
│       │   │   ├── ZoomCommand.java
│       │   │   ├── TranslateCommand.java
│       │   │   └── ...
│       │   └── iterator/               # Patron Iterator
│       │       ├── Iterator.java
│       │       └── CommandHistoryIterator.java
│       └── view/                       # Vues JavaFX
│           ├── HomeView.java
│           ├── EditorView.java
│           └── ThumbnailView.java
└── src/main/resources/                 # Fichiers FXML
```

## Prerequis

- **Java** 21 ou superieur
- **Maven** 3.6 ou superieur
- **JavaFX** 21 (gere automatiquement par Maven)

## Installation et execution

### Cloner le depot

```bash
git clone https://github.com/vladildev/log121-labo5.git
cd log121-labo5/image-viewer
```

### Compiler le projet

```bash
mvn clean compile
```

### Executer l'application

```bash
mvn javafx:run
```

### Generer la documentation Javadoc

```bash
mvn javadoc:javadoc
```

La documentation sera generee dans `target/reports/apidocs/`.

## Utilisation

1. **Ecran d'accueil** : Cliquez sur le bouton pour selectionner une image a editer
2. **Editeur** :
   - Utilisez la molette de la souris avec Ctrl pour zoomer
   - Glissez-deposez pour deplacer la zone visible
   - Utilisez les boutons de l'interface pour les actions
   - Ctrl+Z pour annuler, Ctrl+Y pour retablir
3. **Sauvegarde** : Exportez l'image recadree ou sauvegardez la configuration

## Technologies utilisees

- Java 21
- JavaFX 21
- Maven
- FXML pour les interfaces

## Auteurs

Projet realise par :
- @vladildev
- @macanec
- @loucasbrl

Cours LOG121 - Conception orientee objet  
Ecole de technologie superieure (ETS)  
Session Automne 2025
