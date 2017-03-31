/**
 * Created by TOURET-A on 31/03/2017.
 */
def version = '1.0'

/**
 * Execute ma commande mvn resources:resources ainsi que la qualimetrie SONAR avec le sonar-scanner
 * @return
 */
def runQuality() {
    println("\u27A1 Extraction du soanr-project.properties... ")
    withMaven(maven: 'maven-3.2',jdk:'JDK8') {
        sh "mvn resources:resources -P ${params.PROFIL_JDK},int"
    }
    println("\u27A1 Lancement de SONARQUBE ... ")
    def sonarScannerHome = tool 'sonar-scanner-2.6.1'
    def sonarProjectProperties = findFiles(glob: 'target/**/sonar-project.properties')
    if (fileExists(sonarProjectProperties[0].path)) {
        echo "\u27A1 FICHIER SONAR-PROJECT.PROPERTIES trouvé [" + sonarProjectProperties[0].path + "] "
        withSonarQubeEnv {
            sh "${sonarScannerHome}/bin/sonar-scanner -Dproject.settings=${sonarProjectProperties[0].path}  -Dsonar.java.binaries=target/classes -e "
        }
    }

}

return this;