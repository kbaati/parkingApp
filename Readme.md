# Versions:
- ParkingApp V1
- Java 17

# Choix technique

- J'ai adopté la technologie webClient de Spring Reactive (vs RestTemplate qui va etre depcrecated)
- voir https://www.baeldung.com/spring-webclient-resttemplate

- J'ai séparé la logique de traitement d'appel des deux services (liste des parking et la disponibilité des places)
- Dans cette version j'utilise le fichier properties pour passé l'url en tant que parametr, une version ameliorée qui utilise une base de donnée dans la version parkingAppV2
- Utilisation de Lombok
- Imports maven réduits au minimum requis.
- Architecture qui se base bien sur la séparation des differents couches
- Gestion des exceptions (avec un ControllerAdvice de Spring)
- Test unitaire (la structure est la il faut l'enrichir)
- Documenter la majorité des méthodes
- fournir une collection postman dans le dossier resources (il suffit de l'importer)


# Fonctionnalitées
- Se baser sur le site https://www.data.gouv.fr/fr/datasets/ pour comprendre les differents data/schema exposer par les villes
- Attention la seule ville que j'ai trouvé et propose des info en temps reel c'est grandpoitiers
- supporter le cas si une ville ne propose ds informations temps réel et afficher N/A dans ce cas

- Plusieurs point d'entrés :
- GET http://localhost:8081/api/v1/parking => fonctionnalité principale demander par l'exercice (liste des parking avec leurs diponibilite)
- GET http://localhost:8081/api/v1/parking/pagination?page=1&pageSize=3 => meme service avec pagination
- GET http://localhost:8081/api/v1/parking/list => Liste  du service data parking (je liste l'id et le nom)
- GET http://localhost:8081/api/v1/parking/disponibility => liste du service temps reel parking ((je liste nom, places, capacite))


- Java Doc consultable sur: parkingApp\site\apidocs\index.html

# Lancement de l'application
- sous le dossier
- ouvrir la ligne de commande 
>mvn spring-boot:run

- importer le fichier de postman et executer les requette predefinies 

# TODO
- test d'integration
- améliorer les TU
- Il y a une exception dans les TU qui mérite plus d'investigation
- J'ai mis des TODOs là ou il manque des ameliorations ou bien là ou j'ai fais des raccourci pour gagner du temps
  (comme l'utilisation d'une entité dans les retours du controller au lieu d'un DTO c'est exprès)
