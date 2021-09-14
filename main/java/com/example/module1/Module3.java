package com.example.module1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.module1.Class.Assets;
import com.example.module1.Class.Department;
import com.example.module1.Class.Location;

import java.sql.SQLException;
import java.util.ArrayList;

public class Module3 extends AppCompatActivity {

    TextView tv_ass_name,
            tv_cur_dep,
            tv_ass_sn,
            tv_ass_sn_new,
            tv_move_submit,
            tv_move_cancel;
    Spinner sp_des_dep,
            sp_des_loc;

    Assets asset;
    String gg, oldAssetSN;
    long cur_dep;
    String noff= "THONG BAO TOAST";

    public void Anhxa(){
        tv_ass_name = findViewById(R.id.tv_ass_name);
        tv_cur_dep = findViewById(R.id.tv_cur_dep);
        tv_ass_sn = findViewById(R.id.tv_ass_sn);
        tv_move_submit = findViewById(R.id.tv_move_submit);
        tv_move_cancel = findViewById(R.id.tv_move_cancel);
        tv_ass_sn_new = findViewById(R.id.tv_ass_sn_new);
        sp_des_dep = findViewById(R.id.sp_des_dep);
        sp_des_loc = findViewById(R.id.sp_des_loc);
    }

    private void loadInfo(long AssetID)throws SQLException {
        asset = new Overview().getAsset(AssetID);
        long dep_loc_id = asset.getDepartmentLocationID();
        cur_dep = new Overview().getDepartmentID(dep_loc_id);
        tv_ass_name.setText(asset.getAssetName());
        tv_cur_dep.setText(new Overview().getDepartmentName(cur_dep));
        tv_ass_sn.setText(asset.getAssetSN());
        gg = getIDString(asset.getAssetGroupID());
        oldAssetSN = "??/"+ gg +"/????";
        tv_ass_sn_new.setText(oldAssetSN);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_back:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private String getIDString(long id){
        if (id < 10){
            return "0"+id;
        }
        else return id+"";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module3);

        Anhxa();

        ArrayList<String> department = new ArrayList<>();
        department.add("- Department -");
        try {
            ArrayList<Department> departments = new Overview().getDepartmentList();
            for (Department d : departments){
                department.add(d.getName());
            }
            ArrayAdapter<String> departmentsArrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, department);
            departmentsArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            sp_des_dep.setAdapter(departmentsArrayAdapter);

        }catch (SQLException throwables) {
            noff = "-MODULE 3- HAVE SOME PROBLEM WHEN LOAD DATA TO DEPARTMENT SPINNER";
            Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
            throwables.printStackTrace();
        }

        ArrayList<String> location = new ArrayList<>();
        location.add("- Location -");
        try {
            ArrayList<Location> locations = new Overview().getLocationsList();
            for (Location l : locations){
                location.add(l.getName());
            }
            ArrayAdapter<String> locationsArrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, location);
            locationsArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            sp_des_loc.setAdapter(locationsArrayAdapter);

        } catch (SQLException throwables){
            noff = "-MODULE 3- HAVE SOME PROBLEM WHEN LOAD DATA TO LOCATION SPINNER";
            Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
            throwables.printStackTrace();
        }

        Intent intent = getIntent();
        try {
            long assID = 0;
            assID = intent.getLongExtra("data", 0);
            if (assID > 0){
                loadInfo(assID);
            } else {
                Toast.makeText(Module3.this, assID+"", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException throwables) {
            noff = "-MODULE 3- HAVE SOME PROBLEM WHEN LOAD DATA INFORMATION";
            Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
            throwables.printStackTrace();
        }

        sp_des_dep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0 && position != cur_dep){
                    boolean ASNIE = true;
                    int i = 0;
                    String nnnn;
                    String dd = getIDString(position);
                    do{
                        i++;
                        if (i < 10)
                            nnnn = "000"+i;
                        else if (i < 100)
                            nnnn = "00"+i;
                        else if (i < 1000)
                            nnnn = "0"+i;
                        else
                            nnnn = i+"";
                        try {
                            ASNIE = new Overview().AssetSNisExists(dd+"/"+gg+"/"+nnnn);
                        } catch (SQLException throwables) {
                            noff = "-MODULE 3- HAVE SOME PROBLEM WHEN LOAD ASSET SN";
                            Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
                            throwables.printStackTrace();
                        }
                    } while (ASNIE);
                    String newSN = getIDString(position)+"/"+ gg +"/"+nnnn;
                    tv_ass_sn_new.setText(newSN);
                }
                else {
                    if (position == cur_dep) tv_ass_sn_new.setText(asset.getAssetSN());
                    else tv_ass_sn_new.setText(oldAssetSN);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tv_move_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_move_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long dep_loc_ID = 0;
                long depID = sp_des_dep.getSelectedItemId();
                long locID = sp_des_loc.getSelectedItemId();

                //DEPARTMENT >0 && LOCATION >0
                if (depID > 0) {
                    if (locID > 0){
                        try {
                            dep_loc_ID = new Overview().getDepartmentLocationID(depID, locID);
                            new Overview().moveAssetDepartmentTo(dep_loc_ID, tv_ass_sn_new.getText().toString(), asset.getID());
                            finish();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            noff= "-- MODULE 3 -- Department Location ID NOT EXISTS | DEPARTMENT >0 && LOCATION >0";
                            Toast.makeText(Module3.this, noff, Toast.LENGTH_SHORT).show();
                        }
                    }

                    //DEPARTMENT >0 && LOCATION <=0
                    else {
                        try {
                            dep_loc_ID = new Overview().getDepartmentLocationID(depID, new Overview().getLocationID(asset.getDepartmentLocationID()));
                            new Overview().moveAssetDepartmentTo(dep_loc_ID, tv_ass_sn_new.getText().toString(), asset.getID());
                            finish();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            noff= "-- MODULE 3 -- Department Location ID NOT EXISTS | DEPARTMENT >0 && LOCATION <=0";
                            Toast.makeText(Module3.this, noff, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                //DEPARTMENT <=0 && LOCATION >0
                else {
                    if (locID > 0){
                        try {
                            dep_loc_ID = new Overview().getDepartmentLocationID(new Overview().getDepartmentID(asset.getDepartmentLocationID()), locID);
                            new Overview().moveAssetDepartmentTo(dep_loc_ID, asset.getAssetSN(), asset.getID());
                            finish();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            noff= "-- MODULE 3 -- Department Location ID NOT EXISTS | DEPARTMENT <=0 && LOCATION >0";
                            Toast.makeText(Module3.this, noff, Toast.LENGTH_SHORT).show();
                        }
                    }
                    //DEPARTMENT <=0 && LOCATION <=0
                    else {
                        finish();
                    }
                }
            }
        });
    }
}