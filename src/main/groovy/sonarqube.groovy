/**
 * Created by TOURET-A on 31/03/2017.
 */
def version = '1.0'

/**
 * Execute ma commande mvn resources:resources ainsi que la qualimetrie SONAR avec le sonar-scanner
 * @return
 */
def runQuality(){
    println("Extraction du soanr-project.properties...")
    withEnv(["JAVA_HOME=${tool 'JDK8'}", "PATH+MAVEN=${tool 'maven-3.2'}/bin:${env.JAVA_HOME}/bin"]) {
        sh "mvn resources:resources -P ${params.PROFIL_JDK},int"
    }

    println("Running SONAR QUALITY...")
    def sonarScannerHome = tool 'sonar-scanner-2.6.1'
    def workspace = pwd()
    withSonarQubeEnv {
        sh "${sonarScannerHome}/bin/sonar-scanner -Dproject.settings=${workspace}/target/classes/sonar-project.properties  -Dsonar.java.binaries=target/classes -e "
    }

}

return this;