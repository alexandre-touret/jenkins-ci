/**
 * Created by TOURET-A on 31/03/2017.
 */
def version = '1.0'

/**
 * Execute ma commande mvn resources:resources ainsi que la qualimetrie SONAR avec le sonar-scanner
 * @return
 */
def runQuality(String mavenInstallationId,String jdkInstallationId) {
   runQuality('target/**/sonar-project.properties',mavenInstallationId,jdkInstallationId)
}
def runQuality(String sonarProjectPropsPath,String mavenInstallationId,String jdkInstallationId) {
    println("\u27A1 Extraction du soanr-project.properties... ")
    withMaven(maven: mavenInstallationId,jdk:jdkInstallationId) {
        sh "mvn resources:resources -P ${params.PROFIL_JDK},int"
    }
    println("\u27A1 Lancement de SONARQUBE ... ")
    def sonarScannerHome = tool 'sonar-scanner-2.6.1'
    def sonarProjectProperties = findFiles(glob: sonarProjectPropsPath)
    if (fileExists(sonarProjectProperties[0].path)) {
        echo "\u27A1 FICHIER SONAR-PROJECT.PROPERTIES trouvé [" + sonarProjectProperties[0].path + "] "
        withSonarQubeEnv {
            sh "${sonarScannerHome}/bin/sonar-scanner -Dproject.settings=${sonarProjectProperties[0].path}  -Dsonar.java.binaries=target/classes -e "
        }
    }
}


return this;