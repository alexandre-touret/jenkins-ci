/**
 * Created by TOURET-A on 31/03/2017.
 */

def version = '1.0'

/**
 * Extraection du projet . Pour l'instant SVN
 * Cette fonction utilise le parametre SVN_URL
 */
def checkout() {
    catchError {
        ansiColor('xterm') {
            echo ' \u001B[36m ➡ Checkout du repository ' + params.SVN_URL + ' \u001B[0m'
        }
        checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: 'eandx00atsvn', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: params.SVN_URL]], workspaceUpdater: [$class: 'UpdateUpdater']])
    }

}


return this