# Image Viewer

Application JavaFX de visualisation et d'édition d'images développée dans le cadre du cours LOG121 - Conception orientée objet à l'École de Technologie Supérieure (ÉTS) de Montréal, Canada.

## Description

Image Viewer est un logiciel permettant de visualiser, éditer et manipuler des images avec des fonctionnalités de zoom, translation et recadrage. L'application met en œuvre plusieurs patrons de conception (design patterns) étudiés dans le cours.

### Fonctionnalités

- **Importation d'images** : Support des formats PNG, JPG et GIF avec chargement dynamique des dimensions
- **Zoom** : Agrandissement et réduction de la zone visible via la molette de souris (+ Ctrl), gestes tactiles (pinch-to-zoom) ou boutons dédiés
- **Translation** : Déplacement de la zone visible par glisser-déposer avec la souris ou via les boutons directionnels
- **Sauvegarde d'image** : Export de la zone recadrée aux formats PNG, JPG ou BMP
- **Historique des commandes** : Annulation (Ctrl+Z) et rétablissement (Ctrl+Y) illimités des actions
- **Instantanés (Snapshots)** : Sauvegarde et restauration de configurations via le patron Memento avec horodatage
- **Miniature** : Prévisualisation de la zone qui sera sauvegardée

## Architecture

L'application suit le patron architectural **MVC** (Modèle-Vue-Contrôleur) et intègre les patrons de conception suivants :

| Patron        | Utilisation                                                                                                                                                                 |
| ------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Observer**  | Notification automatique des vues lors des changements d'état de l'image ou de la perspective. Les contrôleurs s'abonnent aux modèles pour recevoir les mises à jour.       |
| **Command**   | Encapsulation des actions (zoom, translation, import, etc.) en objets pour permettre l'annulation et le rétablissement. Chaque commande implémente `execute()` et `undo()`. |
| **Memento**   | Sauvegarde et restauration de l'état de l'éditeur sans violer l'encapsulation. Permet de créer des instantanés de la configuration courante.                                |
| **Iterator**  | Parcours bidirectionnel de l'historique des commandes pour les opérations Undo/Redo.                                                                                        |
| **Singleton** | Instance unique du gestionnaire (`Manager`) et de l'invocateur (`Invoker`) pour centraliser la logique métier.                                                              |

### Diagramme de classes simplifié

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                    App                                      │
│                            (Point d'entrée JavaFX)                          │
└─────────────────────────────────────────────────────────────────────────────┘
                                      │
          ┌───────────────────────────┼───────────────────────────┐
          ▼                           ▼                           ▼
   ┌─────────────┐           ┌─────────────────┐          ┌──────────────┐
   │  HomeView   │           │   EditorView    │          │ ThumbnailView│
   └─────────────┘           └─────────────────┘          └──────────────┘
          │                           │                           │
          ▼                           ▼                           ▼
   ┌─────────────┐           ┌─────────────────┐          ┌──────────────┐
   │HomeController│          │EditorController │          │ThumbnailCtrl │
   └─────────────┘           └─────────────────┘          └──────────────┘
                                      │
                                      ▼
                              ┌───────────────┐
                              │    Invoker    │◄──────── Command Pattern
                              └───────────────┘
                                      │
                                      ▼
                              ┌───────────────┐
                              │    Manager    │◄──────── Singleton
                              └───────────────┘
                                      │
                    ┌─────────────────┼─────────────────┐
                    ▼                 ▼                 ▼
             ┌───────────┐    ┌──────────────┐   ┌──────────────┐
             │  Editor   │    │CommandHistory│   │MementoHistory│
             └───────────┘    └──────────────┘   └──────────────┘
                    │
          ┌─────────┴─────────┐
          ▼                   ▼
   ┌─────────────┐     ┌─────────────┐
   │    Image    │     │ Perspective │◄──────── Observer Pattern
   │ (Observable)│     │ (Observable)│
   └─────────────┘     └─────────────┘
```

### Structure du projet

```
image-viewer/
├── src/main/java/
│   ├── module-info.java
│   └── ca/ets/log121/labo5/imageviewer/
│       ├── App.java                    # Application JavaFX principale
│       ├── Launcher.java               # Point d'entrée (résout les problèmes de modules)
│       ├── controller/                 # Contrôleurs MVC
│       │   ├── EditorController.java   # Gestion des actions d'édition
│       │   ├── HomeController.java     # Gestion de l'écran d'accueil
│       │   ├── ThumbnailController.java# Gestion de la miniature
│       │   └── Invoker.java            # Exécution des commandes (Singleton)
│       ├── model/                      # Modèles de données
│       │   ├── Manager.java            # Gestionnaire principal (Singleton)
│       │   ├── Editor.java             # État de l'éditeur
│       │   ├── CommandHistory.java     # Historique pour Undo/Redo
│       │   ├── memento/                # Patron Memento
│       │   │   ├── Memento.java        # Interface marqueur
│       │   │   ├── EditorMemento.java  # Sauvegarde de l'état
│       │   │   └── MementoHistory.java # Stockage des instantanés
│       │   └── observer/               # Patron Observer
│       │       ├── Observable.java     # Interface sujet
│       │       ├── Observer.java       # Interface observateur
│       │       ├── Image.java          # Données de l'image
│       │       └── Perspective.java    # Zoom et position
│       ├── tools/
│       │   ├── command/                # Commandes (Patron Command)
│       │   │   ├── Command.java        # Interface commune
│       │   │   ├── ZoomCommand.java    # Commande de zoom
│       │   │   ├── TranslateCommand.java# Commande de translation
│       │   │   ├── UndoCommand.java    # Annulation
│       │   │   ├── RedoCommand.java    # Rétablissement
│       │   │   ├── ImportImageCommand.java
│       │   │   ├── SaveImageCommand.java
│       │   │   ├── SaveConfigFileCommand.java
│       │   │   └── LoadConfigFileCommand.java
│       │   └── iterator/               # Patron Iterator
│       │       ├── Iterator.java       # Interface bidirectionnelle
│       │       └── CommandHistoryIterator.java
│       └── view/                       # Vues JavaFX
│           ├── HomeView.java           # Écran d'accueil
│           ├── EditorView.java         # Éditeur principal
│           └── ThumbnailView.java      # Miniature de prévisualisation
└── src/main/resources/                 # Fichiers FXML
    └── ca/ets/log121/labo5/imageviewer/
        ├── home-view.fxml
        ├── editor-view.fxml
        └── thumbnail-view.fxml
```

## Prérequis

- **Java** 21 ou supérieur (JDK)
- **Maven** 3.6 ou supérieur
- **JavaFX** 21 (géré automatiquement par Maven)

## Installation et exécution

### Cloner le dépôt

```bash
git clone https://github.com/vladildev/log121-labo5.git
cd log121-labo5/image-viewer
```

### Compiler le projet

```bash
mvn clean compile
```

### Exécuter l'application

```bash
mvn javafx:run
```

### Générer la documentation Javadoc

```bash
mvn javadoc:javadoc
```

La documentation sera générée dans `target/reports/apidocs/index.html`.

### Créer un JAR exécutable

```bash
mvn clean package
```

## Utilisation

### 1. Écran d'accueil

Cliquez sur le bouton pour sélectionner une image à éditer depuis votre système de fichiers.

### 2. Éditeur d'images

- **Zoom** :
  - Molette de souris + touche Ctrl
  - Geste de pincement (écrans tactiles)
  - Boutons + et - de l'interface
- **Déplacement** :
  - Glisser-déposer avec la souris sur le cadre
  - Boutons directionnels de l'interface
- **Raccourcis clavier** :
  - `Ctrl+Z` : Annuler la dernière action
  - `Ctrl+Y` : Rétablir l'action annulée

### 3. Gestion des fichiers

- **Importer une image** : Charger une nouvelle image (PNG, JPG, GIF)
- **Sauvegarder l'image** : Exporter la zone recadrée
- **Sauvegarder la configuration** : Enregistrer l'état actuel (zoom, position) en JSON
- **Charger une configuration** : Restaurer un état précédemment sauvegardé

### 4. Instantanés (Snapshots)

- Créez des instantanés de votre configuration actuelle
- Sélectionnez un instantané dans la liste déroulante pour le restaurer
- Chaque instantané est horodaté pour faciliter l'identification

## Technologies utilisées

| Technologie | Version | Description                           |
| ----------- | ------- | ------------------------------------- |
| Java        | 21      | Langage de programmation principal    |
| JavaFX      | 21      | Framework d'interface graphique       |
| Maven       | 3.6+    | Gestion des dépendances et build      |
| FXML        | -       | Définition déclarative des interfaces |

## Auteurs

Projet réalisé par :

- [@vladildev](https://github.com/vladildev)
- [@macanec](https://github.com/macanec)
- [@loucasbrl](https://github.com/loucasbrl)

---

**Cours LOG121** - Conception orientée objet  
École de technologie supérieure (ÉTS)  
Session Automne 2025
