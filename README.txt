
***************************************
*************** TOOLBOX ***************
***************************************
 - chaque tool doit être utilisable en tant que thread pour ne pas être bloquant
 - configuration et gestion des logs avec log4j


UTILISATION DU GENERATOR DE BEAN
 -> ce programme permet de generer des bean
 
1) Configurer les differentes variables dans le fichier config.properties
2) Configurer les attributs du bean voulu dans le fichier bean.att
3) REGLE : si le nom du template contient (bean.name), un fichier par bean est généré, sinon, un seul pour le plugin
4) 2 cas de bean ceux avec un id généré automatiquement (id de type entier, utilisation d'une sequence pour hibernate) et ceux avec un string

Génération : clic droit sur GenApp.java, Run as java application	 

UTILISATION DU MARKER DE TEMPLATE
 -> ce programme ajoute le nom d'un template à la fin de son code source afin de le retrouver dans l'environnement
1) configurer le chemin du repertoire racine des templates (variable pathTemplate dans Application.java)

TODO :
corriger les templates pour prendre en compte le checkstyle de la mairie de paris
pouvoir injecter les attributs sans modifier le code d'un bean (mis à part les set/get particulier) : 
	utiliser deux map : valeur et type des attributs ?
reduire la duplication
finir les extends : créer des classes abstraites pour l'ensemble des fichiers (dao, service, filter, converter..) afin de prévenir la duplication
 idée 1 : un getIdNormalize pour avoir quoi qu'il arrive la meme method d'obtention de l'id (avec cast vers string dans tous les cas)
 			et généraliser ainsi les traitements
 idée 2 : getField ? attention à ne pas forcement avoir les memes nom d'attribut entre filter et bean (problemes avec le populate)
 	
creation d'une regle pour définir les fichiers cibles => 
	plugin.name.xml.ftl 
	=> remplacement des MARKER prévu (plugin.name & bean.name par exemple) puis suppression des .ftl
	=> lel.xml 
Externaliser la method de filtre
poser sur papier le processus et trouver des nom de methodes / variables plus parlant
generation harnai de test ? plugin maven pour la compilation ? cf plugin ods
a quoi sert et comment est utilisé le contenu de la balise <class> dans le plugin.xml ?

 X prendre en compte ce qui a été fait pour ODS pour les attributs dynamiques
 X rechir si le system d'annotation ne pourrait pas servir pour la génération plutôt que du naming ?
 X pour les mark, pouvoir spécifier un type de balise (commentaire) par pattern de fichier
 X définir des xpages et les contexte pour les affichers dans un fichier
 X définir les beans => dnas le xml ou avec des annot
 X supprimer génération d'un daemon et corriger les chemins vers les jsp de l'admin feature
 X générer le message.properties avec les clé utilisé (adminfeature...)
 X préfixier les admin right avec le nom du plugin suivit du référentiel
 X corriger génération des tables du plugin
 X corriger generation jsp avec module stock billetterie
 X générer contenu messages adminfeature
 X ajouter fichier macro pour convertir type variable java => SQL et utiliser la macro dans la génération des scripts SQL
 X corriger generation jsp avec module stock billetterie
 X 2 modes => 1) génération des fichiers (java, jsp, html, xml) par defaut s'ils n'existent pas (en fonction d'un fichier de config), 2) génération des fichiers manquants et reprise du contenu des fichiers existants (adaptation selon les parametres existants ?)
 X mappingplugin => remplacer les maven properties pour les versions 
 
refactorer le marker pour utiliser le fichier de conf
permettre de marquer les jsp au niveau du contenu injecté par les jsp bean 
 X creer une methode pour reprendre les anciens fichiers (remplace *~ => *)
 
 
 