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
 * @TODO : Faire reference a un fichier sur le serveur
 * Deploie dans weblogic
 * @param artifactName
 * @param artifactPath
 * @return
 */
def deployInWeblogic(String artifactSuffix) {
    final CONNECTION = [
            installerPath: "/logiciels/scripts/install-application-wls12c.sh ",
            user         : "weblogic ",
            password     : "weblogic-ic1",
            domain       : "INTRANETHM_IC1",
            url          : "http://wlsintegration1:7001"
    ]
    def status = 0
    def artifact = findFiles(glob: '**/*.'+artifactSuffix)
    if (fileExists(artifact[0].path)) {
        echo "\u2622 Livrable trouvé [" + artifact[0].path + "] \u2622 <<<"
        def WLS_DEPLOYMENT_COMMAND = CONNECTION.installerPath + '-u ' + CONNECTION.user + ' -p ' + CONNECTION.password + ' -t ' + CONNECTION.domain + ' -s ' + CONNECTION.url + ' -a ' + $ {
            env.JOB_NAME
        } + ' -z ' + artifact[0].path
        println "\u27A1 Deploiement du livrable " + artifact[0].path + ' sur ' + CONNECTION.url
        def process = WLS_DEPLOYMENT_COMMAND.execute()
        println(process.text)
        def isDeploymentOK = process.exitValue()
        echo "\u27A1 RETOUR WEBLOGIC : " + isDeploymentOK
        if (isDeploymentOK != 0) {
            error(" \u274C  \u001B[31m Deploiement dans WEBLOGIC KO \u001B[0m")
        }
        status = isDeploymentOK
    }else{
        error("\u274C \\u001B[31m Aucun fichier n'est disponible pour le déploiement \u001B[0m \u274C <<<")
    }

    return status
}

return this;