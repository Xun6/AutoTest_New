pipeline{
    agent{
	docker{
		image 'maven:3.6.1-alpine'
		args '-v /root/.m2:/root/.m2'
	}
    }
	stages{
		stage('Build'){
			steps{
				sh '''
                    		  source /etc/profile
				  pid=$(ps x | grep "Practice8_InterfaceDevelop-1.0-SNAPSHOT.jar" | grep -v grep | awk '{print $1}')
				
				  if [ -n "$pid" ]; then
				  kill -9 $pid
				  fi
				
				  cd Practice8_InterfaceDevelop
				  mvn clean package
				  cd target
				  BUILD_ID=dontKillMe
					
				  nohup java -jar Practice8_InterfaceDevelop-1.0-SNAPSHOT.jar &
                		'''
			}
		}
	}
}
