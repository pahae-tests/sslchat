# Backend — Spring Boot

Backend REST organisé en couches : `models`, `repositories`, `services`, `controllers` et `dto`. Aucun socket, WebSocket ou mécanisme temps réel n'est utilisé.

## Démarrage
- Java 17+ et Maven requis.
- Depuis ce dossier : `mvn clean spring-boot:run`
- API : `http://localhost:8080`
- Console H2 : `http://localhost:8080/h2-console` (base en mémoire, données perdues à l'arrêt).

## SQL Server
Configurer les identifiants dans `src/main/resources/application-sqlserver.properties`, puis lancer :
`mvn spring-boot:run -Dspring-boot.run.profiles=sqlserver`

## Routes REST
- `POST /api/auth/register`, `POST /api/auth/login`
- `GET /api/users/{id}`
- `POST /api/groups`, `GET /api/groups?userId=...`
- `GET /api/groups/{groupId}/members`
- `POST /api/groups/{groupId}/join-requests?userId=...`
- `GET /api/groups/{groupId}/requests?adminId=...`
- `POST /api/groups/{groupId}/requests/{requestId}`
- `POST /api/messages`
- `GET /api/groups/{groupId}/messages?userId=...`
- `GET /api/messages/private?user1=...&user2=...`

Les objets échangés avec l'API sont regroupés dans `dto/ApiDtos.java`. Les mots de passe sont hachés avec BCrypt.

**Note sécurité :** cette version est une base pédagogique. Elle ne fournit pas encore de JWT/session : les identifiants d'utilisateur transmis dans les requêtes ne constituent pas une authentification robuste. Ajouter une vraie gestion de session et limiter CORS avant tout déploiement. HTTPS doit être configuré pour un usage réel.
