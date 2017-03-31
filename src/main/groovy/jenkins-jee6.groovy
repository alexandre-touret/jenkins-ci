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
 * Recherche un livrable et le deploie dans weblogic
 * Cette fonctionne s appuie sur le shell deploye sur le serveur
 * @param artifactSuffix Suffixe du livrable (ex. ear)
 * @return le statut de l execution
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
    def artifact = findFiles(glob: '**/*.' + artifactSuffix)
    if (fileExists(artifact[0].path)) {
        ansiColor('xterm') {
            echo "\u2622 \u001B[32m Livrable trouvé [" + artifact[0].path + "] \u001B[0m \u2622 "
            echo "\u27A1 \u001B[32m Deploiement du de l'application " + env.JOB_NAME + "  avec le livrable  " + artifact[0].path + ' sur ' + CONNECTION.url + "\u001B[0m"
        }
        def WLS_DEPLOYMENT_COMMAND = (CONNECTION.installerPath +
                '-u ' +
                CONNECTION.user +
                ' -p ' +
                CONNECTION.password +
                ' -t ' +
                CONNECTION.domain +
                ' -s ' +
                CONNECTION.url +
                ' -a ' +
                env.JOB_NAME +
                ' -z ' +
                pwd() +
                '/' +
                artifact[0].path)
        // execution de la commande SHELL
        def process = WLS_DEPLOYMENT_COMMAND.execute()
        println(process.text)
        process.waitFor()
        def isDeploymentOK = process.exitValue()
        echo "\u27A1 RETOUR WEBLOGIC : " + isDeploymentOK
        if (isDeploymentOK != 0) {
            ansiColor('xterm') {
                echo " \u274C  \u001B[31m Deploiement dans WEBLOGIC KO \u001B[0m"
                error("Deploiement dans WEBLOGIC KO ")
            }
        }
        status = isDeploymentOK
    } else {
        ansiColor('xterm') {
            error("\u274C \u001B[31m Aucun fichier n'est disponible pour le déploiement \u001B[0m \u274C ")
        }
    }

    return status
}

return this;