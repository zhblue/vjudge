#!/bin/bash
apt-get update
apt-get -y install tomcat8 mysql-server
wget https://github.com/zhblue/vjudge/raw/master/vjudge.war
cp vjudge.war /var/lib/tomcat8/webapps/
echo "waiting for tomcat8 deploying vjudge.war ... 8s count down"
sleep 8
DBUSER=`cat /etc/mysql/debian.cnf | grep user|awk '{print $3}'|head -1`
DBPASS=`cat /etc/mysql/debian.cnf | grep password|awk '{print $3}'|head -1`
SQL=`find /var/lib/tomcat8/webapps/vjudge -name "*.sql"`
cat $SQL | mysql -u$DBUSER -p$DBPASS
cd /var/lib/tomcat8/webapps/vjudge/WEB-INF/classes
sed -i "s/jdbc.username=                 root/jdbc.username=$DBUSER/g" config.properties
sed -i "s/jdbc.password= /jdbc.password=$DBPASS/g" config.properties

service tomcat8 restart
