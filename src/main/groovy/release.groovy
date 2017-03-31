/**
 * Created by TOURET-A on 31/03/2017.
 */

def version = '1.0'
/**
 * Execute la release
 * Je fais passer en parametre les versions de maven et de JDK au cas ou les outils seraient incompatibles
 * @param mavenInstallationId
 * @param jdkInstallationId
 * @return
 */
def runRelease(String mavenInstallationId, String jdkInstallationId) {
    timeout(time: 1, unit: 'HOURS') {

        def workspace = pwd()
        def release = input id: 'release', message: 'Informations de la releaser à créer', parameters: [string(defaultValue: '1.0.0-SNAPSHOT', description: 'New SNAPSHOT', name: 'developmentVersion'), string(defaultValue: '1.0.0_01', description: 'New release', name: 'releaseVersion')], submitterParameter: 'submitter'
        ansiColor('xterm') {
            echo "➡ \u2622 \u001B[32m Creation de la release [" + release['developmentVersion'] + "/" + release['releaseVersion'] + "] \u2622 \u001B[0m"
        }
        currentBuild.displayName = release['releaseVersion']
        currentBuild.description = "Creation de la release ${release['releaseVersion']}"

        withMaven(maven: mavenInstallationId, jdk: jdkInstallationId) {
            sh "mvn --batch-mode release:prepare release:perform -P${params.PROFIL_JDK},dev"
        }
        // test presence fichier rollback
        if (fileExists('pom.xml.releaseBackup')) {
            ansiColor('xterm') {
                echo "\u273F \u001B[35m ROLLBACK de la précédente release \u001B[0m"
            }
            withMaven(maven: mavenInstallationId, jdk: jdkInstallationId) {
                sh "mvn release:rollback"
            }
        }
        if (fileExists('${workspace}/target/checkout/pom.xml')) {
            def sonarqube = fileLoader.fromGit('src/main/groovy/sonarqube', 'https://gitlab.com/hm-eand/jenkins-ci.git', 'master', 'TOURET-AATGITLAB')
            sonarqube.runQuality('target/checkout/**/sonar-project.properties')

            withMaven(maven: mavenInstallationId, jdk: jdkInstallationId) {
                sh "mvn clean deploy -Pint,${params.PROFIL_JDK} -f ${workspace}/target/checkout/pom.xml"
                sh "mvn clean deploy -Prec,${params.PROFIL_JDK} -f ${workspace}/target/checkout/pom.xml"
                sh "mvn clean deploy -Pppd,${params.PROFIL_JDK} -f ${workspace}/target/checkout/pom.xml"
                sh "mvn clean deploy -Pprd,${params.PROFIL_JDK} -f ${workspace}/target/checkout/pom.xml"
            }
        } else {
            ansiColor('xterm') {
                echo "\u274C \u001B[31m Le fichier [ ${workspace}/target/checkout/pom.xml ] n'existe pas ! \u001B[0m"
                error("Le fichier [ ${workspace}/target/checkout/pom.xml ] n'existe pas ! ")
            }
        }
    }
}



return this;