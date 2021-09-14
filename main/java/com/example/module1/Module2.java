package com.example.module1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.module1.Class.AssetGroup;
import com.example.module1.Class.Assets;
import com.example.module1.Class.Department;
import com.example.module1.JDBCConnection.AssetList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;

public class Module2 extends AppCompatActivity {
    //KHAI BÁO
    EditText e_sdate, e_endate, e_search;
    Spinner sp_department, sp_assetgroup;
    Button btn_search;
    ListView lv_asset;
    FloatingActionButton fab;
    static TextView tv_amount;
    static long allAmount;
    Button btn_refresh;

    ArrayList<Assets> assets = new ArrayList<>();
    private int requestCode = 1030;
    String noff="THÔNG BÁO TOAST";

    private int lastSelectedYearS;
    private int lastSelectedMonthS;
    private int lastSelectedDayOfMonthS;
    private int lastSelectedYearE;
    private int lastSelectedMonthE;
    private int lastSelectedDayOfMonthE;

    //ÁNH XẠ
    private void Anhxa(){
        e_sdate = findViewById(R.id.e_sdate);
        e_sdate.setFocusable(false);
        e_sdate.setCursorVisible(false);
        e_endate = findViewById(R.id.e_edate);
        e_endate.setFocusable(false);
        e_endate.setCursorVisible(false);
        e_search = findViewById(R.id.e_search);
        sp_department = findViewById(R.id.s_department);
        sp_assetgroup = findViewById(R.id.s_asset);
        btn_search = findViewById(R.id.btn_sreach);
        lv_asset = findViewById(R.id.lv_asset);
        btn_refresh = findViewById(R.id.btn_refresh);
        tv_amount = findViewById(R.id.tv_amount);
        fab = findViewById(R.id.fab);
    }

    //LOAD DATA INTO LIST ASSET
    private void load() throws SQLException {
        assets = new Overview().getAssetsList();
        allAmount = assets.size();
        AssetList assetListAdapter = new AssetList(this, assets);
        lv_asset.setAdapter(assetListAdapter);
        String s_amount = allAmount + " assets from " + allAmount;
        tv_amount.setText(s_amount);
        sp_assetgroup.setSelection(0);
        sp_department.setSelection(0);
        e_sdate.setText("");
        e_endate.setText("");
        e_search.setText("");
        noff = "LOAD DATA COMPLETE";
        Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
    }

    //LOAD DATA WHEN CHOICE ONE SPINNER //10/09/2021
    private void refresh(int cases) throws SQLException{
        switch (cases){
            case 1:
                assets = new Overview().getAssetsListByDepartment(sp_department.getSelectedItemId());
                break;
            case 2:
                assets = new Overview().getAssetsListByAssetGroup(sp_assetgroup.getSelectedItemId());
                break;
            case 3:
                assets = new Overview().getAssetsListByS(e_sdate.getText().toString().trim());
                break;
            case 4:
                assets = new Overview().getAssetsListByE(e_endate.getText().toString().trim());
                break;
            default:
                load();
        }
        noff = "LOAD DATA COMPLETE";
        Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
        AssetList assetListAdapter = new AssetList(this, assets);
        lv_asset.setAdapter(assetListAdapter);
    }

    //LOAD DATA WHEN CHOICE SPINNER START DATE -> GOTO FUNCTION REFRESH()
    private void SelectDateS(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                e_sdate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                lastSelectedYearS = year;
                lastSelectedMonthS = month+1;
                lastSelectedDayOfMonthS = dayOfMonth;
                try {
                    refresh(3);
                } catch (SQLException throwables) {
                    noff ="- MODULE 2 - SOME PROBLEM WHEN LOADING DATA SEARCH. PLEASE TRY AGAIN";
                    Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
                    throwables.printStackTrace();
                }
            }
        };
        DatePickerDialog datePickerDialog = null;
        datePickerDialog = new DatePickerDialog(this,dateSetListener, lastSelectedYearS, lastSelectedMonthS, lastSelectedDayOfMonthS);
        datePickerDialog.show();
    }

    //LOAD DATA WHEN CHOICE SPINNER END DATE -> GOTO FUNCTION REFRESH()
    private void SelectDateE(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                e_endate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                lastSelectedYearE = year;
                lastSelectedMonthE = month+1;
                lastSelectedDayOfMonthE = dayOfMonth;
                try {
                    refresh(4);
                } catch (SQLException throwables) {
                    noff ="- MODULE 2 - SOME PROBLEM WHEN LOADING DATA SEARCH. PLEASE TRY AGAIN";
                    Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
                    throwables.printStackTrace();
                }
            }
        };
        DatePickerDialog datePickerDialog = null;
        datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK, dateSetListener, lastSelectedYearE, lastSelectedMonthE, lastSelectedDayOfMonthE);
        datePickerDialog.show();
    }

    // LOAD DATA WHEN SEARCH ELEMENT INTO EDIT TEXT SEARCH.
    private void search() throws SQLException {
        String search = e_search.getText().toString();
        int count =0;
        if(!search.isEmpty()){
            ArrayList<Assets> t = new ArrayList<>();
            for (Assets a : assets){
                count++;
                if(a.getAssetName().contains(search) || a.getAssetSN().contains(search))
                    t.add(a);
            }
            AssetList assetListAdapter = new AssetList(this, t);
            lv_asset.setAdapter(assetListAdapter);
            if(count != 0){
                noff = "HAVE "+ count + " Assets";
            }else{
                noff = "NO ASSETS FINDED BY SEARCH CRITERIA";
            }
            Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
        }
        else refresh(0);
    }

    //THROUGHOUT THE OPERATION OF FORM MODULE 2 -> FUNCTION LOAD()
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            load();
        } catch (SQLException throwables) {
            noff ="- MODULE 2 - SOME PROBLEM WHEN LOADING DATA. PLEASE TRY AGAIN";
            Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
            throwables.printStackTrace();
        }
    }

    //WHEN THE MODULE WORKS FOR THE FIRST AND ONLY TIME
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module2);

        Anhxa();

        final Calendar c = Calendar.getInstance();
        this.lastSelectedYearS = c.get(Calendar.YEAR);
        this.lastSelectedMonthS = c.get(Calendar.MONTH);
        this.lastSelectedDayOfMonthS = c.get(Calendar.DAY_OF_MONTH);

        this.lastSelectedYearE = this.lastSelectedYearS;
        this.lastSelectedMonthE = this.lastSelectedMonthS;
        this.lastSelectedDayOfMonthE = this.lastSelectedDayOfMonthS;

        //RUN WHEN START TO END
        try {
            load();
        } catch (SQLException throwables) {
            noff = "SOME PROBLEM WHEN LOAD DATA. PLEASE TRY AGAIN LATER. THANKS";
            Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
            throwables.printStackTrace();
        }

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    load();
                } catch (SQLException throwables) {
                    noff = "SOME PROBLEM WHEN LOAD DATA. PLEASE TRY AGAIN LATER";
                    Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
                    throwables.printStackTrace();
                }
            }
        });

        //INITIALIZING AND TAKING DEPARTMENT ARRAY
        ArrayList<String> department = new ArrayList<>();
        department.add("- Department -");
        try {
            ArrayList<Department> departments = new Overview().getDepartmentList();
            for (Department d : departments) {
                department.add(d.getName());
            }
            ArrayAdapter<String> departmentsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, department);
            departmentsArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            sp_department.setAdapter(departmentsArrayAdapter);
        } catch (SQLException throwables) {
            noff = "- MODULE 2- HAVE SOME PROBLEM WHEN LOADING LIST DEPARTMENT. PLEASE TRY AGAIN";
            Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
            throwables.printStackTrace();
        }

        //INITIALIZING AND TAKING ASSET GROUP ARRAY
        ArrayList<String> assetGroup = new ArrayList<>();
        assetGroup.add("- Asset Group -");
        try {
            ArrayList<AssetGroup> assetGroups = new Overview().getAssetGroupsList();
            for (AssetGroup a : assetGroups) {
                assetGroup.add(a.getName());
            }

            ArrayAdapter<String> assetGroupsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, assetGroup);
            assetGroupsArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            sp_assetgroup.setAdapter(assetGroupsArrayAdapter);
        }catch (SQLException throwables){
            noff = "- MODULE 2- HAVE SOME PROBLEM WHEN LOADING LIST ASSET GROUP. PLEASE TRY AGAIN";
            Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
            throwables.printStackTrace();
        }

        //SEARCH DATA BY SPINNER DEPARTMENT -> REFRESH(1)
        sp_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    try {
                        refresh(1);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    sp_assetgroup.setSelection(0);
                    e_sdate.setText("");
                    e_endate.setText("");
                    e_search.setText("");
                }else if (position == 0){
                    try {
                        load();
                    }catch(SQLException throwables){
                        throwables.printStackTrace();
                        noff ="- MODULE 2 - SOME PROBLEM WHEN LOADING DATA SEARCH. PLEASE TRY AGAIN";
                        Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                noff = "SELECT ONE UNIT";
                Toast.makeText(getApplicationContext(),noff,Toast.LENGTH_SHORT).show();
            }
        });

        //SEARCH DATA BY SPINNER ASSET GROUP -> REFRESH(2)
        sp_assetgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    try {
                        refresh(2);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    sp_department.setSelection(0);
                    e_sdate.setText("");
                    e_endate.setText("");
                    e_search.setText("");
                }else if (position == 0){
                    try {
                        load();
                    }catch(SQLException throwables){
                        noff ="- MODULE 2 - SOME PROBLEM WHEN LOADING DATA SEARCH. PLEASE TRY AGAIN";
                        Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
                        throwables.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                noff = "SELECT ONE UNIT";
                Toast.makeText(getApplicationContext(),noff,Toast.LENGTH_SHORT).show();
            }
        });

        //LOAD DATA WHEN CHOICE START DATE -> SELECTDATES -> REFRESH(3)
        e_sdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDateS();
                sp_assetgroup.setSelection(0);
                sp_department.setSelection(0);
                e_endate.setText("");
                e_search.setText("");
            }
        });

        //LOAD DATA WHEN CHOICE START DATE -> SELECTDATES -> REFRESH(4)
        e_endate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDateE();
                sp_assetgroup.setSelection(0);
                sp_department.setSelection(0);
                e_sdate.setText("");
                e_search.setText("");
            }
        });

        //REFRESH ALL DATA TO SHOW OVER WHEN CLICK LONG TIME
        e_sdate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                e_sdate.setText("");
                try {
                    refresh(0);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    noff ="- MODULE 2 - SOME PROBLEM WHEN LOADING DATA. PLEASE TRY AGAIN";
                    Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        //REFRESH ALL DATA TO SHOW OVER WHEN CLICK LONG TIME
        e_endate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                e_endate.setText("");
                try {
                    refresh(0);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    noff ="- MODULE 2 - SOME PROBLEM WHEN LOADING DATA. PLEASE TRY AGAIN";
                    Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        //SEARCH DATA BY KEYWORD -> SEARCH()
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    search();
                } catch (SQLException throwables) {
                    noff ="SOME PROBLEM WHEN LOADING DATA SEARCH. PLEASE TRY AGAIN";
                    Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
                    throwables.printStackTrace();
                }
            }
        });

        //ADD NEW ASSET -> INTENT TO NEW ACTIVITY
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Module2.this, AssetInfomationActivity.class);
                intent.putExtra("status", "add");
                startActivityForResult(intent, requestCode);
            }
        });
    }
}