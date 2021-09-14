package com.example.module1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.module1.Class.AssetGroup;
import com.example.module1.Class.Assets;
import com.example.module1.Class.Department;
import com.example.module1.Class.Employees;
import com.example.module1.Class.Location;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class AssetInfomationActivity extends AppCompatActivity {

    //Khoi tao
    EditText e_asset_name, e_asset_description, e_exp_warranty, e_ass_SN;
    Spinner sp_dep, sp_loc, sp_asset_gr, sp_acc_party;
    Button btn_browse, btn_cap;
    ImageView pic1, pic2, pic3;
    TextView tv_pic1, tv_pic2, tv_pic3, tv_submit, tv_cancel;
    RadioGroup rad_gr_pic;
    RadioButton rad_pic1, rad_pic2, rad_pic3;

    String status = "";
    String noff = "";
    private int lastSelectedYear;
    private int lastSelectedMonth;
    private int lastSelectedDayOfMonth;
    private long dd = 0, gg = 0;
    byte[] img1;
    byte[] img2;
    byte[] img3;
    Assets asset;
    long img1ID = 0, img2ID = 0, img3ID = 0;

    //Capture Image
    private static final int CAMERA_REQUEST = 1010;
    private static final int BROWSER_REQUEST = 3006;

    public void Anhxa(){
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        e_asset_name = findViewById(R.id.e_asset_name);
        e_asset_description = findViewById(R.id.e_asset_description);
        sp_dep = findViewById(R.id.sp_dep);
        sp_loc = findViewById(R.id.sp_loc);
        sp_asset_gr = findViewById(R.id.sp_asset_gr);
        sp_acc_party = findViewById(R.id.sp_acc_party);
        btn_cap = findViewById(R.id.btn_cap);
        btn_browse = findViewById(R.id.btn_browse);
        pic1 = findViewById(R.id.pic1);
        pic2 = findViewById(R.id.pic2);
        pic3 = findViewById(R.id.pic3);
        tv_pic1 = findViewById(R.id.tv_pic1);
        tv_pic2 = findViewById(R.id.tv_pic2);
        tv_pic3 = findViewById(R.id.tv_pic3);
        tv_submit = findViewById(R.id.tv_submit);
        tv_cancel = findViewById(R.id.tv_cancel);
        rad_gr_pic = findViewById(R.id.rad_gr_pic);
        rad_pic1 = findViewById(R.id.rad_pic1);
        rad_pic2 = findViewById(R.id.rad_pic2);
        rad_pic3 = findViewById(R.id.rad_pic3);

        e_ass_SN = findViewById(R.id.e_ass_SN);
        e_ass_SN.setFocusable(false);
        e_ass_SN.setCursorVisible(false);

        e_exp_warranty = findViewById(R.id.e_exp_warranty);
        e_exp_warranty.setFocusable(false);
        e_exp_warranty.setCursorVisible(false);

        final Calendar c = Calendar.getInstance();
        this.lastSelectedYear = c.get(Calendar.YEAR);
        this.lastSelectedMonth = c.get(Calendar.MONTH);
        this.lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
    }

    //CREATE MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_back:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //LOAD DATA OF OBJECT - NEW OR UPDATE
    public void loadInfo(long assetID) throws SQLException{
        int photoAmount = new Overview().getCountPhoto(assetID);
        asset = new Overview().getAsset(assetID);
        long dep_loc_id = asset.getDepartmentLocationID();
        long dep_id = 0, loc_id = 0;
        dep_id = new Overview().getDepartmentID(dep_loc_id);
        loc_id = new Overview().getLocationID(dep_loc_id);
        e_asset_name.setText(asset.getAssetName());
        sp_dep.setSelection(Integer.parseInt(dep_id+""));
        sp_loc.setSelection(Integer.parseInt(loc_id+""));
        sp_acc_party.setSelection(Integer.parseInt(asset.getEmployeeID()+""));
        sp_asset_gr.setSelection(Integer.parseInt(asset.getAssetGroupID()+""));
        e_asset_description.setText(asset.getDescription());
        e_exp_warranty.setText(asset.getWarrantyDate());
        e_ass_SN.setText(asset.getAssetSN());
        switch (photoAmount){
            case 1:
                img1 = new Overview().getPhoto(asset.getID(), 1);
                Bitmap bitmapImg = BitmapFactory.decodeByteArray(img1, 0, img1.length);
                pic1.setImageBitmap(bitmapImg);
                img1ID = new Overview().getPhotoID(img1, assetID);
                break;
            case 2:
                img1 = new Overview().getPhoto(asset.getID(), 1);
                Bitmap bitmapImg1 = BitmapFactory.decodeByteArray(img1, 0, img1.length);
                pic1.setImageBitmap(bitmapImg1);
                img1ID = new Overview().getPhotoID(img1, assetID);

                img2 = new Overview().getPhoto(asset.getID(), 2);
                Bitmap bitmapImg2 = BitmapFactory.decodeByteArray(img2, 0, img2.length);
                pic2.setImageBitmap(bitmapImg2);
                img2ID = new Overview().getPhotoID(img2, assetID);
                break;
            default:
                img1 = new Overview().getPhoto(asset.getID(), 1);
                Bitmap bitmapImg01 = BitmapFactory.decodeByteArray(img1, 0, img1.length);
                pic1.setImageBitmap(bitmapImg01);
                img1ID = new Overview().getPhotoID(img1, assetID);

                img2 = new Overview().getPhoto(asset.getID(), 2);
                Bitmap bitmapImg02 = BitmapFactory.decodeByteArray(img2, 0, img2.length);
                pic2.setImageBitmap(bitmapImg02);
                img2ID = new Overview().getPhotoID(img2, assetID);

                img3 = new Overview().getPhoto(asset.getID(), 3);
                Bitmap bitmapImg03 = BitmapFactory.decodeByteArray(img3, 0, img3.length);
                pic3.setImageBitmap(bitmapImg03);
                img3ID = new Overview().getPhotoID(img3, assetID);
                break;
        }
        noff = "LOAD DATA SUCCESS !";
        Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
    }

    private void SelectDateE(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                e_exp_warranty.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                lastSelectedYear = year;
                lastSelectedMonth = month+1;
                lastSelectedDayOfMonth = dayOfMonth;
            }
        };
        DatePickerDialog datePickerDialog = null;
        datePickerDialog = new DatePickerDialog(this, dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
        datePickerDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_infomation);

        Anhxa();
        Intent intent = getIntent();
        status = intent.getStringExtra("status");

        ArrayList<String> department = new ArrayList<>();
        department.add("- Department -");
        //SPINNER DEPARTMENT
        try {
            ArrayList<Department> departments = new Overview().getDepartmentList();
            for (Department d : departments){
                department.add(d.getName());
            }
            ArrayAdapter<String> departmentsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, department);
            departmentsArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            sp_dep.setAdapter(departmentsArrayAdapter);
        }catch (SQLException throwables){
            noff = "- ASSET INFORMATION - HAVE SOME PROBLEM WHEN LOADING DATA OF DEPARTMENT TO SPINNER";
            Toast.makeText(AssetInfomationActivity.this, noff, Toast.LENGTH_SHORT).show();
            throwables.printStackTrace();
        }

        ArrayList<String> assetGroup = new ArrayList<>();
        assetGroup.add("- Asset Group -");
        //SPINNER ASSET GROUP
        try {
            ArrayList<AssetGroup> assetGroups = new Overview().getAssetGroupsList();
            for (AssetGroup a : assetGroups) {
                assetGroup.add(a.getName());
            }
            ArrayAdapter<String> assetGroupsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, assetGroup);
            assetGroupsArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            sp_asset_gr.setAdapter(assetGroupsArrayAdapter);
        }catch (SQLException throwables){
            noff = "- ASSET INFORMATION - HAVE SOME PROBLEM WHEN LOADING DATA OF ASSET GROUP TO SPINNER";
            Toast.makeText(AssetInfomationActivity.this, noff, Toast.LENGTH_SHORT).show();
            throwables.printStackTrace();
        }

        ArrayList<String> location = new ArrayList<>();
        location.add("- Location -");
        //SPINNER LOCATION
        try {
            ArrayList<Location> locations = new Overview().getLocationsList();
            for (Location l : locations){
                location.add(l.getName());
            }
            ArrayAdapter<String> locationsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, location);
            locationsArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            sp_loc.setAdapter(locationsArrayAdapter);
        }catch (SQLException throwables){
            noff = "- ASSET INFORMATION - HAVE SOME PROBLEM WHEN LOADING DATA OF LOCATION TO SPINNER";
            Toast.makeText(AssetInfomationActivity.this, noff, Toast.LENGTH_SHORT).show();
            throwables.printStackTrace();
        }
        ArrayList<String> employee = new ArrayList<>();
        employee.add("- Accountable Party -");

        //SPINNER EMPLOYEES
        try {
            ArrayList<Employees> employees = new Overview().getEmployeesList();
            for (Employees e : employees) {
                employee.add(e.getName());
            }
            ArrayAdapter<String> employeesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, employee);
            employeesArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            sp_acc_party.setAdapter(employeesArrayAdapter);
        }catch (SQLException throwables){
            noff = "- ASSET INFORMATION - HAVE SOME PROBLEM WHEN LOADING DATA OF EMPLOYEES TO SPINNER";
            Toast.makeText(AssetInfomationActivity.this, noff, Toast.LENGTH_SHORT).show();
            throwables.printStackTrace();
        }

        //CHECK STATUS UPDATE OR ADD
        if(status.equals("update")) {
            noff = "THIS IS UPDATE MODE";
            Toast.makeText(AssetInfomationActivity.this,noff, Toast.LENGTH_SHORT).show();
            try {
                loadInfo(intent.getLongExtra("data", 0));
                sp_loc.setEnabled(false);
                sp_dep.setEnabled(false);
                sp_asset_gr.setEnabled(false);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        btn_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int RadIsChecked = rad_gr_pic.getCheckedRadioButtonId();
                if(RadIsChecked < 0) {
                    noff = "- ASSET INFORMATION - CHECK ONE UNIT TO ADD IMAGE";
                    Toast.makeText(getApplicationContext(),noff ,Toast.LENGTH_SHORT).show();
                    return;
                }else{
//                    Intent photoIntent = new Intent(Intent.ACTION_PICK);
//                    photoIntent.setType("image/*");
//                    startActivityForResult(photoIntent, BROWSER_REQUEST);
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, BROWSER_REQUEST);
                }
            }
        });

        btn_cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                }catch (Exception e){
                    noff = "- ASSET INFORMATION - HAVE PROBLEM WHEN OPEN CAMERA. PLEASE TRY AGAIN";
                    Toast.makeText(getApplicationContext(),noff,Toast.LENGTH_SHORT).show();
                }
            }
        });

        sp_dep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    dd = new Overview().getDepartmentID(department.get(position));
                } catch (SQLException throwables) {
                    noff = "- ASSET INFORMATION - SOMETHING WRONG IN PROCESS GET DEPARTMENT ID INTO ASSET SN";
                    Toast.makeText(getApplicationContext(),noff ,Toast.LENGTH_SHORT).show();
                    throwables.printStackTrace();
                }
                if (gg > 0){
                    String sdd, sgg;
                    if(dd < 10)
                        sdd = "0"+dd;
                    else
                        sdd = ""+dd;
                    if(gg < 10 )
                        sgg = "0"+gg;
                    else
                        sgg = ""+gg;
                    boolean ASNIE = true;
                    int i = 0;
                    String nnnn;
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
                            ASNIE = new Overview().AssetSNisExists(sdd+"/"+sgg+"/"+nnnn);
                        } catch (SQLException throwables) {
                            noff = "- ASSET INFORMATION - SOMETHING WRONG IN PROCESS GET ASSET SN";
                            Toast.makeText(getApplicationContext(),noff ,Toast.LENGTH_SHORT).show();
                            throwables.printStackTrace();
                        }
                    } while (ASNIE);
                    e_ass_SN.setText(sdd+"/"+sgg+"/"+nnnn);
                }
                else {
                    e_ass_SN.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_asset_gr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    gg = new Overview().getAssetGroupID(assetGroup.get(position));
                } catch (SQLException throwables) {
                    noff = "- ASSET INFORMATION - SOMETHING WRONG IN PROCESS GET ASSET GROUP ID INTO ASSET SN";
                    Toast.makeText(getApplicationContext(),noff ,Toast.LENGTH_SHORT).show();
                    throwables.printStackTrace();
                }
                if (dd > 0){
                    String sdd, sgg;
                    if(dd < 10)
                        sdd = "0"+dd;
                    else
                        sdd = ""+dd;
                    if(gg < 10 )
                        sgg = "0"+gg;
                    else
                        sgg = ""+gg;
                    boolean ASNIE = true;
                    int i = 0;
                    String nnnn;
                    do{
                        i++;
                        if (i < 10) nnnn = "000"+i;
                        else if (i < 100) nnnn = "00"+i;
                        else if (i < 1000) nnnn = "0"+i;
                        else nnnn = i+"";
                        try {
                            ASNIE = new Overview().AssetSNisExists(sdd+"/"+sgg+"/"+nnnn);
                        } catch (SQLException throwables) {
                            noff = "- ASSET INFORMATION - SOMETHING WRONG IN PROCESS GET ASSET SN";
                            Toast.makeText(getApplicationContext(),noff ,Toast.LENGTH_SHORT).show();
                            throwables.printStackTrace();
                        }
                    } while (ASNIE);
                    e_ass_SN.setText(sdd+"/"+sgg+"/"+nnnn);
                }
                else {
                    e_ass_SN.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        e_exp_warranty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDateE();
            }
        });
        e_exp_warranty.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                e_exp_warranty.setText("");
                return false;
            }
        });

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noff = "DATA IS BEING PROCESSED";
                Toast.makeText(AssetInfomationActivity.this,noff, Toast.LENGTH_SHORT).show();
                String sn = e_ass_SN.getText().toString();
                String name = e_asset_name.getText().toString();
                String warranty = e_exp_warranty.getText().toString();
                long locid = sp_loc.getSelectedItemId();
                long depid = sp_dep.getSelectedItemId();
                long accountID = sp_acc_party.getSelectedItemId();
                long ass_gr_id = sp_asset_gr.getSelectedItemId();
                if (!name.isEmpty() &&
                        !sn.isEmpty() &&
                        locid > 0 &&
                        accountID > 0 &&
                        !warranty.isEmpty()){
                    long dep_loc_id = 0;
                    try {
                        dep_loc_id = new Overview().getDepartmentLocationID(depid, locid);
                    } catch (SQLException throwables) {
                        noff = "-ASSET INFORMATION- GET DEPARTMENT LOCATION ID FAIL";
                        Toast.makeText(AssetInfomationActivity.this, noff, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(dep_loc_id > 0){
                        if(status.equals("add")){
                            try {
                                new Overview().insertAssets(new Assets(sn, name, dep_loc_id, accountID, ass_gr_id, e_asset_description.getText().toString()+" ", warranty));
                            } catch (SQLException throwables) {
                                noff = "-ASSET INFORMATION- INSERT NEW ASSET FAIL";
                                Toast.makeText(AssetInfomationActivity.this, noff, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        else if(status.equals("update")
                                || !asset.getAssetName().equals(name)
                                || asset.getDepartmentLocationID() != dep_loc_id
                                || asset.getEmployeeID() != accountID
                                || asset.getAssetGroupID() != ass_gr_id
                                || !asset.getDescription().equals(e_asset_description.getText().toString()+" ")
                                || !asset.getWarrantyDate().equals(warranty)) {
                            try {
                                long id = intent.getLongExtra("data", 0);
                                new Overview().updateAssets(new Assets(id, sn, name, dep_loc_id, accountID, ass_gr_id, e_asset_description.getText().toString()+" ", warranty));
                            } catch (SQLException throwables) {
                                noff = "-ASSET INFORMATION- UPDATE ASSET FAIL";
                                Toast.makeText(AssetInfomationActivity.this, noff, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                    long assetID = 0;
                    try {
                        assetID = new Overview().getAssetID(sn);
                    } catch (SQLException throwables) {
                        noff = "-ASSET INFORMATION- GET ASSET ID FAIL";
                        Toast.makeText(AssetInfomationActivity.this, noff, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(img1 != null && assetID > 0){
                        if (status.equals("update") && img1ID > 0){
                            try {
                                new Overview().updatePhoto(img1ID, img1);
                            } catch (SQLException throwables) {
                                noff ="-ASSET INFORMATION- UPDATE IMAGE 1 FAIL";
                                Toast.makeText(AssetInfomationActivity.this, noff, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            try {
                                new Overview().insertPhoto(img1, assetID);
                            } catch (SQLException throwables) {
                                noff ="-ASSET INFORMATION- INSERT IMAGE 1 FAIL";
                                Toast.makeText(AssetInfomationActivity.this, noff, Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                    if(img2 != null && assetID > 0){
                        if (status.equals("update") && img2ID > 0){
                            try {
                                new Overview().updatePhoto(img2ID, img2);
                            } catch (SQLException throwables) {
                                noff ="-ASSET INFORMATION- INSERT IMAGE 2 FAIL";
                                Toast.makeText(AssetInfomationActivity.this, noff, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            try {
                                new Overview().insertPhoto(img2, assetID);
                            } catch (SQLException throwables) {
                                noff ="-ASSET INFORMATION- INSERT IMAGE 2 FAIL";
                                Toast.makeText(AssetInfomationActivity.this, noff, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    if(img3 != null && assetID > 0){
                        if (status.equals("update") && img3ID > 0){
                            try {
                                new Overview().updatePhoto(img3ID, img3);
                            } catch (SQLException throwables) {
                                noff = "-ASSET INFORMATION- INSERT IMAGE 3 FAIL";
                                Toast.makeText(AssetInfomationActivity.this, noff, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            try {
                                new Overview().insertPhoto(img3, assetID);
                            } catch (SQLException throwables) {
                                noff = "-ASSET INFORMATION- INSERT IMAGE 3 FAIL";
                                Toast.makeText(AssetInfomationActivity.this, noff, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    noff = "ADD ASSET COMPLETE. CHECK AGAIN INFORMATION";
                    Toast.makeText(getApplicationContext(), noff, Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    noff="-ASSET INFORMATION- CAN'T HAVE EMPTY FIELD ! ! !";
                    Toast.makeText(AssetInfomationActivity.this, noff, Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == BROWSER_REQUEST) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    int radChecked = rad_gr_pic.getCheckedRadioButtonId();
                    switch (radChecked) {
                        case R.id.rad_pic1:
                            pic1.setImageBitmap(selectedImage);
                            img1 = Img2Byte(selectedImage);
                            break;
                        case R.id.rad_pic2:
                            pic2.setImageBitmap(selectedImage);
                            img2 = Img2Byte(selectedImage);
                            break;
                        case R.id.rad_pic3:
                            pic3.setImageBitmap(selectedImage);
                            img3 = Img2Byte(selectedImage);
                            break;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    noff= "-ASSET INFORMATION- SOMETHING WENT WRONG WHEN LOAD IMAGE LIBRARY";
                    Toast.makeText(AssetInfomationActivity.this, noff, Toast.LENGTH_SHORT).show();
                }
            }else if (requestCode == CAMERA_REQUEST){
                Bitmap captured = (Bitmap) data.getExtras().get("data");
                int radChecked = rad_gr_pic.getCheckedRadioButtonId();
                switch (radChecked) {
                    case R.id.rad_pic1:
                        pic1.setImageBitmap(captured);
                        img1 = Img2Byte(captured);
                        break;
                    case R.id.rad_pic2:
                        pic2.setImageBitmap(captured);
                        img2 = Img2Byte(captured);
                        break;
                    case R.id.rad_pic3:
                        pic3.setImageBitmap(captured);
                        img3 = Img2Byte(captured);
                        break;
                }
            }
        }else {
            Toast.makeText(AssetInfomationActivity.this, "You haven't picked Image.",Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] Img2Byte(Bitmap img){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        byte[] bytesImage = byteArrayOutputStream.toByteArray();
        return bytesImage;
    }
}