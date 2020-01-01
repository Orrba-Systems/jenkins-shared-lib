#!/usr/bin/groovy

def call() {
node {
   def mvnHome,VERSION
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      cleanWs()
   
       checkout scm        
   }


    stage ('Test') {


       sh "mvn clean -DskipTests=true"
  

    }
    // Running Build without skipping tests for PR builds
   if (env.BRANCH_NAME.startsWith('PR-')){
        stage ('Install') {
           sh "mvn clean install"
           cleanWs()
        }
      }
   
   //Running build with skipping tests and deploying artifacts
   else {
    stage ('Install') {
        //rtMaven.run pom: 'pom.xml', goals: 'install -DskipTests=true', buildInfo: buildInfo
        sh "mvn clean install -DskipTests=true"
       } 
     }
   }
}
