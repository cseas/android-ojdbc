package com.absingh.query;

/**
 * MainActivity.java
 * Created by Abhijeet Singh
 * www.absingh.com
 */

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.absingh.query.helper.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private static final String DEFAULT_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DEFAULT_URL = "jdbc:oracle:thin:@192.168.43.47:1521:XE";
    private static String DEFAULT_USERNAME;
    private static String DEFAULT_PASSWORD;

    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        TextView tv = (TextView) findViewById(R.id.hello);
        try {
            this.connection = createConnection();
            Toast.makeText(MainActivity.this, "Connected",
                    Toast.LENGTH_SHORT).show();
            Statement stmt=connection.createStatement();
            StringBuffer stringBuffer = new StringBuffer();
            ResultSet rs = stmt.executeQuery("select * from student");

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            // print column names
            for(int i=1; i<=columnsNumber; i++) {
                stringBuffer.append(rsmd.getColumnName(i) + "\t\t\t\t");
            }
            stringBuffer.append("\n");

            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1)
                        stringBuffer.append(",\t\t\t");
                    String columnValue = rs.getString(i);
                    stringBuffer.append(columnValue);
                }
                stringBuffer.append("\n");
            }

            tv.setText(stringBuffer.toString());
            connection.close();
        }
        catch (Exception e) {

            Toast.makeText(MainActivity.this, ""+e,
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static Connection createConnection(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException {

        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public Connection createConnection() throws ClassNotFoundException, SQLException {
        try {
            DEFAULT_USERNAME = Util.getProperty("username",getApplicationContext());
            DEFAULT_PASSWORD = Util.getProperty("password",getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createConnection(DEFAULT_DRIVER, DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }
}