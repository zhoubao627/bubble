@echo off

rem mvn -N clean install

rem cd ./bubble-common
rem mvn -Dmaven.test.skip=true clean install

rem cd ./bubble-persistence
rem mvn -Dmaven.test.skip=true clean install

rem cd ../bubble-remote
rem mvn -Dmaven.test.skip=true clean install 

rem cd ./bubble-application
rem mvn -f bubble-application/pom.xml -Dmaven.test.skip=true clean package

mvn -Dmaven.test.skip=true clean assembly:assembly