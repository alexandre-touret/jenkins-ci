/**
 * Created by touret-a on 21/03/2017.
 */
def version = '1.0'

class WeblogicConnection {
    static CONNECTION = [
            installerPath: "/logiciels/scripts/install-application-wls12c.sh ",
            user         : "weblogic ",
            password     : "weblogic-ic1",
            domain       : "INTRANETHM_IC1",
            url          : "http://wlsintegration1:7001"
    ]
}

/**
 * Deploie dans weblogic
 * @param artifactName
 * @param artifactPath
 * @return
 */
def deployInWeblogic( String artifactName, String artifactPath) {
    def parametersFile = new File(weblogicParameterFile)
    if (parametersFile.exists()) {
        println "weblogic deployment ..."
        def WLS_DEPLOYMENT_COMMAND = WeblogicConnection.CONNECTION.installerPath '-u ' + WeblogicConnection.CONNECTION.user + ' -p ' + WeblogicConnection.CONNECTION.password + ' -t ' + WeblogicConnection.CONNECTION.domain + ' -s ' + WeblogicConnection.CONNECTION.url + ' -a ' + artifactName + ' -z ' + artifactPath
        def process = WLS_DEPLOYMENT_COMMAND.execute()
        process.text.eachLine { println it }
        return process.exitValue()
    }
}

return this;