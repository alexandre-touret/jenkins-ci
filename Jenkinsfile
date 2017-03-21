pipeline{
    agent any
     parameters {
            booleanParam(name: 'IS_RELEASE', defaultValue: false, description: 'Voulez vous faire une release')
            string(name: 'SVN_URL', defaultValue: 'http://svnhm/svn/eand_src/attitudes/attitudes/trunk/', description: 'URL REPOSITORY SVN')
        }

    stages {
     stage ('Init'){
        steps{
        script{
        timeout(time:5, unit:'DAYS') {
          if( params.IS_RELEASE == true){
                def release= input id: 'release', message: 'test', parameters: [string(defaultValue: '1.0.0-SNAPSHOT', description: 'New SNAPSHOT', name: 'developmentVersion'), string(defaultValue: '1.0.0_01', description: 'New release', name: 'releaseVersion')], submitterParameter: 'submitter'
                echo (release['developmentVersion'])
            }
            }
         }
        }
        }
        stage('Checkout'){
            steps{
                 checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: 'eandx00', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: params.SVNURL]], workspaceUpdater: [$class: 'UpdateUpdater']])
            }
        }
        stage('Build'){
            steps{
                withMaven(maven:'maven'){
                    sh "mvn clean install -Dmaven.test.skip=true -P jdk8,int"
                }
            }
        }
        stage('Test'){
            steps{
                catchError{
                withMaven(maven:'maven'){
                    sh "mvn test -P jdk8,int -Daggregate=true -DtestFailureIgnore=true"
                }
                junit "target/test-reports/TEST*.xml"
                }
                step([$class: 'Mailer', recipients: 'admin@somewhere'])
            }

        }
        stage('Deploy'){
            steps{
                echo "Deployment"
                withMaven(maven:'maven'){
                    //sh "mvn deploy -Dmaven.test.skip=true -P jdk8,int"
                }

            }
        }

        stage('Release'){
            steps(){
            script{
                if( params.IS_RELEASE == true){
                    withMaven(maven:'maven'){
                        sh "mvn release:prepare -Dmaven.test.skip=true -P jdk8,int"
                    }
                }
                }


            }
        }
    }
}
