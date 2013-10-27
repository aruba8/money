#sys variable for static files
export MONEY_HOME_DIR=${PWD}/statics
mvn compile exec:java -Dexec.mainClass=money.MoneyController