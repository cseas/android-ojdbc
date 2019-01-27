# android-ojdbc

This app connects to on-premise hosted Oracle Database 11g using OJDBC, queries the database and displays the query results on the app's home page.

## Configuration

Define database username and password in a file named `config.properties` inside directory [main > assets](https://github.com/cseas/android-ojdbc/tree/master/Query/app/src/main/assets).

Enter JDBC URL configuration in [MainActivity.java](https://github.com/cseas/android-ojdbc/blob/master/Query/app/src/main/java/com/absingh/query/MainActivity.java)