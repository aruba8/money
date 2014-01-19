## HOW TO INSTALL PROJECT:

to install this project firstly you have to download and install next tools:

 1. apache maven 3 or above
 2. java sdk 1.7 or above
 3. mongodb

Install MongoDB as described here: http://docs.mongodb.org/manual/installation/

 pull last code changes from repository
 ensure that mongod process is running


 execute from project directory:
 ```
 mongo < scripts/indexes.ss
 ```


Define as system environment variables next variables:

 **JAVA_HOME** - path to folder with java sdk

 **M2_HOME** - path to folder with maven



 update your PATH variable
 ```
 export PATH=$JAVA_HOME/bin:$M2_HOME/bin:$PATH
 ```


## HOW TO DEPLOY AND RUN:

From project directory in terminal execute following commands:
```
./money3.sh start
```
for this script also available "stop", "restart", "status" options.


It will take several minutes on first start because maven have to download all dependencies.

Application available on url: http://localhost:8082

Last version (updates every day) always available here: http://money3.zapto.org
