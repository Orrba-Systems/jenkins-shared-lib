node {

	def lib = library (
		identifier: 'simple-lib@temp', 
		retriever: 
			modernSCM([
				$class: 'GitSCMSource', 
				remote: 'git@github.com:Orrba-Systems/jenkins-shared-lib.git', 
				traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]
			])
	)

        Stage("FOR CD"){
         org.netskope.cd("FOR CD STAGE")
        }
        Stage("FOR SP"){
         org.netskope.sp("FOR SP STAGE")
        }
        
}
