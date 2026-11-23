# Chat SSL — Frontend Java Swing

Frontend uniquement : aucune socket, aucun serveur, aucune base de données et aucun appel réseau.
Les données affichées sont des exemples en mémoire.

## Prérequis
- JDK 17 ou supérieur
- Aucun framework ni dépendance externe

## Lancer
Depuis la racine du projet :

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out chat.Main
```

Sous Windows PowerShell :

```powershell
Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName } | Set-Content sources.txt
javac -encoding UTF-8 -d out @sources.txt
java -cp out chat.Main
```

## Parcours de démonstration
- Login : n'importe quel email et mot de passe permettent d'entrer.
- Register : nom, email, mot de passe (4 caractères minimum) et image facultative.
- Chats : sélection d'une conversation privée et affichage de messages fictifs.
- Groupes : tous les groupes de démonstration sont visibles.
  - Un groupe rejoint s'ouvre normalement.
  - Un groupe non rejoint déclenche une demande simulée et son état devient « Demande envoyée ».
  - Le bouton « Créer un groupe » ouvre un formulaire (nom + image). Le créateur devient admin.
  - Dans les détails d'un groupe dont l'utilisateur est admin, les demandes peuvent être acceptées ou refusées.

## Structure
- `chat.Main` : point d'entrée et navigation.
- `chat.LoginPanel`, `chat.RegisterPanel` : écrans d'authentification.
- `chat.MainPanel` : interface principale et interactions de démonstration.
- `chat.components.MessageBubble` : bulle de message (texte, expéditeur, date).
- `chat.components.ConversationItem` : conversation cliquable (image, nom, dernier message, lu/non lu).
- `chat.model` : petits modèles en mémoire.

## Passage au backend plus tard
Les méthodes de démonstration sont regroupées dans `MainPanel` et les données dans `chat.model`.
Elles pourront être remplacées par des services API/socket et une couche de persistance.
Les images sont conservées ici sous forme de `ImageIcon` en mémoire ; le futur backend pourra convertir
les octets de l'image en BLOB. Aucun stockage BLOB n'est implémenté dans cette version frontend.
