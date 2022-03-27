# 20503-adv-java-workshop-jdbc

This is a small project demonstrating jdbc driver for course 20503 in the OpenU.

Use maven to compile and get dependencies:
mvn compile

This example works with sqlite, but without using RowSet due to it being unsupported in the sqlite JDBC driver.

To work with mysql, you need a mysql database with a db called hero and the root password set as in the file main java file.
You can use docker to set up a mysql db locally without hassles.

You then need to uncomment the line "USE shield;" in the create_db script and replace every occurance of autoincrement with AUTO_INCREMENT.
This is actually a good demonstration of how the JDBC and SQL standards are not really implemented consistently among different vendors.

You need to uncomment the connection initialization for MYSQL_URL and comment the SQLITE in that case.
When using mysql you can use checkHeroPowersWithRowSet() instread of checkHeroPowersWithOutRowSet(conn, statement).