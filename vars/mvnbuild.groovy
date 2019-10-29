#!/usr/bin/groovy

def call() {
node {
   def mvnHome,VERSION
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      cleanWs()
   
       checkout scm

      // Get the Maven tool.
      // ** NOTE: This 'M2' Maven tool must be configured         

      VERSION = readMavenPom().getVersion()
      VERSION = VERSION.split("-")[0]
      echo "Building version ${VERSION}b${BUILD_NUMBER}"
      if (!env.BRANCH_NAME.startsWith('PR-')){
         currentBuild.displayName="${VERSION}b${BUILD_NUMBER}"}
   }


    stage ('Test') {

       VERSION = readMavenPom().getVersion()
       VERSION = VERSION.split("-")[0]
       VERSION = VERSION + "b" + "${BUILD_NUMBER}"
       //rtMaven.run pom: 'pom.xml', goals: 'versions:set -DnewVersion=${VERSION}'
       sh "mvn versions:set -DnewVersion=${VERSION}"
       rtMaven.run pom: 'pom.xml', goals: 'clean -DskipTests=true'

    }
    // Running Build without skipping tests for PR builds
   if (env.BRANCH_NAME.startsWith('PR-')){
        stage ('Install') {
           rtMaven.run pom: 'pom.xml', goals: 'clean install', buildInfo: buildInfo
           cleanWs()
        }
      }
   
   //Running build with skipping tests and deploying artifacts
   else {
    stage ('Install') {
        rtMaven.run pom: 'pom.xml', goals: 'install -DskipTests=true', buildInfo: buildInfo
    }


      
      }

}
}

