pipeline{
    agent any
    stages {
        stage('Checkout'){
            steps{
                 checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: 'eandx00', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: params.SVNURL]], workspaceUpdater: [$class: 'UpdateUpdater']])
            }
        }
        stage('Build'){
            steps{
                withMaven(maven:'maven'){
                    //sh "mvn clean install -Dmaven.test.skip=true -P jdk8,int"
                }
            }
        }
        stage('Test'){
            steps{
                withMaven(maven:'maven'){
                   // sh "mvn test -Dmaven.test.skip=true -P jdk8,int"
                }
                //junit()
            }
        }
        stage('Deploy'){
            steps{
                withMaven(maven:'maven'){
                    //sh "mvn deploy -Dmaven.test.skip=true -P jdk8,int"
                }

            }
        }

        stage('Release'){
            steps(){
            script{
                def release= input id: 'release', message: 'test', parameters: [string(defaultValue: '1.0.0-SNAPSHOT', description: 'New SNAPSHOT', name: 'developmentVersion'), string(defaultValue: '1.0.0_01', description: 'New release', name: 'releaseVersion')], submitterParameter: 'submitter'
                echo (release['developmentVersion'])
                }
            }
        }
    }
}
