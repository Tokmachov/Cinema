#!/bin/sh
CinemaDir=$1
TomCatDir=$2
mvn -f $CinemaDir/pom.xml clean install
cp $CinemaDir/target/Cinema-1.0.war $TomCatDir/webapps
$TomCatDir/bin/shutdown.sh
$TomCatDir/bin/startup.sh
