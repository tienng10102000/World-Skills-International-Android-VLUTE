package com.example.module1.JDBCConnection;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCModel {
    public Connection getConnectionOf() {
        Connection objConn = null;

        //Strict Mode
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        JDBCObject objEntity = new JDBCObject(
                "172.116.0.113",
                "sa",
                "123456",
                "Session2",
                "1433");

        String sConnURL = "jdbc:jtds:sqlserver://"
                + objEntity.getsServerName() + ":" + objEntity.getsPrort() + ";"
                + "databaseName=" + objEntity.getsDatabase()
                + ";user=" + objEntity.getsUserId()
                + ";password=" + objEntity.getsPwd() + ";";
        //jdbc:jtds:sqlserver://172.20.200.10:1433;databaseName= Session2; user=sa; password = 123456;

        try {
            Class.forName(objEntity.getsClass());
        } catch (ClassNotFoundException e) {
            Log.e("ERROR", e.getMessage());
        }

        try{
            objConn = DriverManager.getConnection(sConnURL);
        } catch (SQLException se) {
            Log.e("ERROR", se.getMessage());
        }
        return objConn;
    }
}