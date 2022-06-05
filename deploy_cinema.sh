mvn clean -f $1/pom.xml
mvn package -f $1/pom.xml
mv $1/target/cinema.war $2/webapps/
chmod 777 $2/bin/startup.sh
chmod 777 $2/bin/catalina.sh
./$2/bin/startup.sh
