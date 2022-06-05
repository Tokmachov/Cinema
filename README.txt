# 1 cd to dir of app deployment

# 2 download tomcat using command

curl http://archive.apache.org/dist/tomcat/tomcat-10/v10.0.14/bin/apache-tomcat-10.0.14.zip --output tomcat.zip

# 3 unrchive tomcat.zip file

unzip tomcat.zip

# 4 open /Cinema/src/main/resources/application.properties and fill images.storage.path property with dir where app will store user avatars

# 5 to start server and app run script deploy_cinema.sh with 2 parameters: 1 - apache tomcat dir 2 - Cinema dir
# 6 to shut down server and app - run shutdown_cinema.sh with 1 parameter: apache tomcat dir




