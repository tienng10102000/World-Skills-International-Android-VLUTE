package com.example.module1.JDBCConnection;

import java.sql.Connection;

public class JDBCControl {
    JDBCModel Jdbcmodel = new JDBCModel();

    public Connection ConnectionData(){
        return Jdbcmodel.getConnectionOf();
    }
}
