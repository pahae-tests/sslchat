# Chat SSL — projet organisé frontend / backend

```text
chat_ssl_fullstack/
├── frontend/   # Application desktop Java Swing (démo, données locales)
└── backend/    # API REST Spring Boot (aucun socket applicatif, aucun temps réel)
```

## 1. Backend Spring Boot

Prérequis : JDK 17+ et Maven 3.8+.

```bash
cd backend
mvn spring-boot:run
```

L'API démarre sur `http://localhost:8080`. H2 est utilisé par défaut en mémoire.
Console H2 : `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:chatdb`, user `sa`, mot de passe vide).

### Activer SQL Server
Créer au préalable une base `chat_ssl`, puis modifier `backend/src/main/resources/application-sqlserver.properties`
avec les identifiants de votre serveur SQL Server. Lancer :

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=sqlserver
```

Le driver SQL Server est inclus. Vérifier la compatibilité de la version du serveur SQL Server et du pilote JDBC
dans votre environnement ; SQL Server 2008 est ancien et peut nécessiter un pilote/configuration adaptés.

## 2. API REST

Toutes les routes sont sous `/api`. Les corps sont JSON.

| Méthode | Route | Rôle |
|---|---|---|
| POST | `/auth/register` | Créer un compte (image en tableau d'octets JSON facultative) |
| POST | `/auth/login` | Vérifier email/mot de passe |
| GET | `/users/{id}` | Lire un utilisateur |
| POST | `/groups` | Créer un groupe, créateur ADMIN |
| GET | `/groups?userId=1` | Lister tous les groupes et statut d'adhésion |
| POST | `/groups/{id}/join-requests?userId=2` | Demander à rejoindre |
| GET | `/groups/{id}/members` | Membres du groupe |
| GET | `/groups/{id}/requests?adminId=1` | Demandes en attente (admin seulement) |
| POST | `/groups/{id}/requests/{requestId}` | Accepter/refuser (`adminId`, `accept`) |
| POST | `/messages` | Envoyer message privé ou de groupe |
| GET | `/groups/{id}/messages?userId=1` | Historique du groupe (membre seulement) |
| GET | `/messages/private?user1=1&user2=2` | Historique privé entre deux utilisateurs |

Exemples JSON :

Inscription :
```json
{"name":"Alex Morgan","email":"alex@example.com","password":"pass1234","image":null}
```

Connexion :
```json
{"email":"alex@example.com","password":"pass1234"}
```

Création de groupe :
```json
{"name":"Développeurs Java","image":null,"userId":1}
```

Demande d'adhésion : `POST /api/groups/1/join-requests?userId=2`

Envoyer un message de groupe :
```json
{"senderId":1,"groupId":1,"content":"Bonjour tout le monde"}
```

Envoyer un message privé :
```json
{"senderId":1,"recipientId":2,"content":"Salut"}
```

Décision d'un administrateur :
```json
{"adminId":1,"accept":true}
```

## 3. Frontend

Le dossier `frontend/` contient le projet Swing précédent. Pour le lancer :

```bash
cd frontend
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out chat.Main
```

**Important :** le frontend est conservé en version autonome de démonstration et n'appelle pas encore l'API.
Le backend est fonctionnel indépendamment via HTTP REST. Il n'y a ni WebSocket, ni socket TCP personnalisée,
ni mécanisme de temps réel : le client devra appeler périodiquement ou à l'ouverture des conversations les
routes REST pour actualiser les données.

## Limites de cette base minimale
- Authentification simple : pas encore de JWT/session ni de gestion de rôles côté sécurité Spring.
- Les endpoints admin vérifient le rôle dans la base, mais l'identité (`userId`/`adminId`) est fournie par le client ;
  à sécuriser avant toute mise en production.
- CORS est ouvert pour faciliter les tests locaux.
- H2 est éphémère : les données disparaissent au redémarrage.
- Les images sont reçues comme `byte[]` et stockées en colonnes BLOB via `@Lob`.
- Prévoir HTTPS/TLS au niveau du serveur web ou du reverse proxy pour chiffrer les échanges HTTP.
