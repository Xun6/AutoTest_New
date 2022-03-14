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
					result=$(curl -s http://172.18.31.58:8081/job/deploy-pipeline/lastBuild/buildNumber --user root:root)
					mkdir /opt/Report/$result
					cp /var/lib/docker/volumes/jenkins-data/_data/workspace/deploy-pipeline/Practice7_MysqlAndMybatisCase/test-output/index.html /opt/Report/$result/index.html
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
