package com.example.module1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    //Nor
    String noff;
    EditText e_tk, e_mk;
    Button dn;

    private void Anhxa() {
        //ánh xạ
        e_tk = findViewById(R.id.e_user);
        e_mk = findViewById(R.id.e_password);
        dn = findViewById(R.id.btn_login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();

        dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk = e_tk.getText().toString();
                String mk = e_mk.getText().toString();
                if(tk.isEmpty() || mk.isEmpty()){
                    noff = "-LOGIN- PLEASE PROVIDE FULL INFORMATION";
                    Toast.makeText(getApplicationContext(),noff,Toast.LENGTH_LONG).show();
                    return;
                }else{
                    long ID = -1;
                    try{
                        ID = new Overview().login(tk,mk);
                    }catch(SQLException throwables){
                        noff= "-LOGIN- QUERY FAIL ! ! !";
                        Toast.makeText(getApplicationContext(),noff,Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(ID == -1){
                        noff="-LOGIN- YOUR USERNAME OR PASSWORD IS WRONG ! ! !";
                        Toast.makeText(getApplicationContext(),noff,Toast.LENGTH_LONG).show();
                    }else{
                        boolean isAdmin = false;
                        try {
                            isAdmin = new Overview().isAdmin(ID);
                        }catch(SQLException throwables){
                            throwables.printStackTrace();
                        }
                        if (isAdmin== true){
                            noff="LOGIN SUCCESSFUL WITH ADMINISTRATOR PERMISSION";
                        }else{
                            noff = "LOGIN SUCCESSFUL";
                        }
                        Toast.makeText(MainActivity.this, noff, Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(MainActivity.this,Module2.class);
                        startActivity(it);
                    }
                }
            }
        });
    }
}