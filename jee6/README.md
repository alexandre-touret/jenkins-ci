BUILD DES PROJETS JEE6
======================

Ce fichier centralise la gestion des projets JEE6 dans JENKINS


Création d'un JOB
=================

Créer un job de type pipeline

Déclarer que ce job nécessite des paramètres

Paramètres nécessaires
----------------------
###URL SVN
* Name : SVN_URL
* Type de paramètre : Hidden Parameter
* Default Value : renseigner l'url du repository

###Test de Release
* Name : IS_RELEASE
* Type de paramètre : Boolean
* Default Value : false


Suppression des anciens builds
------------------------------
Indiquer la stratégie de suppression des anciens builds


Pipeline
--------
Voici les propriétés à renseigner

* Definition: "Pipeline script from SCM"
* SCM : https://touret-a@gitlab.com/hm-eand/seed-jenkins-jee6.git
* Credentials : Indiquer l'utilisateur renseigné dans JENKINS
* Script Path : jee6/Jenkinsfile.gdsl
 
 
