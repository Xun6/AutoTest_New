pipeline{
	agent{
		docker{
			image 'maven:3-alpine'
			label 'my-defined-label'
			args '-v /root/.m2:/root/.m2'
		}
	}
	stages{
		stage('Build'){
			steps{
				sh 'source /etc/profile'
				sh 'pid=$(ps x | grep "Practice8_InterfaceDevelop-1.0-SNAPSHOT.jar" | grep -v grep | awk '{print $1}')'
				sh 'if[ -n "$pid" ]; then kill -9 $pid; fi'
				sh 'cd Practice8_InterfaceDevelop'
				sh 'mvn clean package'
				sh 'cd target'
				sh 'BUILD_ID=dontKillMe'
				sh 'nohup java -jar Practice8_InterfaceDevelop-1.0-SNAPSHOT.jar &'		
			}
		}
	}
}
