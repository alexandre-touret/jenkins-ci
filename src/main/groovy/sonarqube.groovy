/**
 * Created by TOURET-A on 31/03/2017.
 */
def version = '1.0'

def runQuality(){
    println("Running SONAR QUALITY...")
    def sonarScannerHome = tool 'sonar-scanner-2.6.1'
    def workspace = pwd()
    withSonarQubeEnv {
        sh "${sonarScannerHome}/bin/sonar-scanner -Dproject.settings=${workspace}/target/classes/sonar-project.properties  -Dsonar.java.binaries=target/classes -e "
    }

}

return this;