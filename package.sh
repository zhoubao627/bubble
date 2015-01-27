#mvn -N clean install

#cd ./bubble-common
#mvn -Dmaven.test.skip=true clean install

#cd ./bubble-persistence
#mvn -Dmaven.test.skip=true clean install

#cd ../bubble-remote
#mvn -Dmaven.test.skip=true clean install 

#cd ./bubble-application
#mvn -f bubble-application/pom.xml -Dmaven.test.skip=true clean package

mvn -Dmaven.test.skip=true clean assembly:assembly