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
				  pwd
				  pid=$(ps x | grep "Practice8_InterfaceDevelop-1.0-SNAPSHOT.jar" | grep -v grep | awk '{print $1}')
				
				  if [ -n "$pid" ]; then
				  kill -9 $pid
				  fi
				
				  cd Practice8_InterfaceDevelop
				  pwd
				  mvn clean package
				  cd target
				  BUILD_ID=dontKillMe
					
				  nohup java -jar Practice8_InterfaceDevelop-1.0-SNAPSHOT.jar &
                		'''
			}
		}
		stage('Test'){
			steps{
				sh '''
				  source /etc/profile
				  pwd
				  cd Practice7_MysqlAndMybatisCase
				  mvn clean package
				'''
			}
		}
		stage('Generate-Report'){
			steps{
				sh '''
					source /etc/profile
					result=$(curl -s http://localhost:8081/job/myFirstTask/lastBuild/buildNumber --user test:123456)
					pwd
					mkdir -p /home/myReport/$result
					cp /var/jenkins_home/workspace/myFirstTask/Practice7_MysqlAndMybatisCase/test-output/index.html /home/myReport/$result/index.html
				'''
			}
		}
	}
	post{
		always {
            		echo 'This will always run'
        	}
		success {
            		echo 'This will run only if successful'
        	}
        	failure {
            		echo 'This will run only if failed'
        	}
        	unstable {
            		echo 'This will run only if the run was marked as unstable'
        	}
        	changed {
            		echo 'This will run only if the state of the Pipeline has changed'
            		echo 'For example, if the Pipeline was previously failing but is now successful'
        	}
	}
}
