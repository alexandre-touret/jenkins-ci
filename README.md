Description
===========

Ce projet centralise les fichiers pipeline décrivant les JOBS JENKINS.


Documentation
=============

* [Documentation officielle](https://jenkins.io/doc/)
* [Exemples](https://github.com/jenkinsci/pipeline-examples/tree/master/jenkinsfile-examples/)


###Plugins nécessaires
Afin de pouvoir exécuter ces scripts, il est nécessaire d'avoir installé au préalable les plugins suivants :

* Hidden Parameter Plugin
* Pipeline Utility Steps
* Workflow Remote Loader
* SonarQube Scanner for Jenkins
* Pipeline: Shared Groovy Libraries
* Pipeline NPM Integration Plugin
* Pipeline Maven Integration Plugin
* Pipeline Aggregator
* Blue Ocean *** ( suite de plugins )


Utilisation standard
====================
Il y a un seul fichier JENKINSFILE pour tous les projets d'un même type (ex. projet JEE6)


Workflow commun à tous les jobs
===============================
1. Checkout du projet
2. Build (compilation, déploiement dans Artifactory)
3. Test ( lancement des tests unitaires et d'intégration)
4. Quality ( lancement de sonarqube)
5. Deploy (Déploiement du binaire vers l'environnement d'exécution d'intégration)
6. Release ( Création de la release )
7. GCL (Déploiement en GCL)