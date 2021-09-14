package com.example.module1.JDBCConnection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.module1.AssetInfomationActivity;
import com.example.module1.Class.Assets;
import com.example.module1.Module3;
import com.example.module1.R;
import com.example.module1.Overview;

import java.sql.SQLException;
import java.util.ArrayList;

public class AssetList extends BaseAdapter implements ListAdapter {
    Activity activity;
    ArrayList<Assets> assets = new ArrayList<>();

    String noff;
    public AssetList() {
    }

    public AssetList(Activity activity, ArrayList<Assets> assets) {
        this.activity = activity;
        this.assets = assets;
    }

    //CHANGE TO -> PUBLIC CLASS ASSET LIST EXTEND BASE ADAPTER IMPLEMENT LIST ADAPTER
    @Override
    public int getCount() {
        return assets.size();
    }

    @Override
    public Object getItem(int position) {
        return assets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.activity_asset_list , null);

        Assets asset = assets.get(position);
        ((TextView) convertView.findViewById(R.id.tv_asset_name)).setText(String.format("%s", asset.getAssetName()));
        try {
            ((TextView) convertView.findViewById(R.id.tv_department_name)).setText(String.format("%s", new Overview().
                    getDepartmentName(new Overview().getDepartmentID(asset.getDepartmentLocationID()))));
        } catch (SQLException throwables) {
            ((TextView) convertView.findViewById(R.id.tv_department_name)).setText(String.format("%s", ""));
        }
        ((TextView) convertView.findViewById(R.id.tv_asset_sn)).setText(String.format("%s", asset.getAssetSN()));
        byte[] img = null;
        try {
            img = new Overview().getPhoto(asset.getID(), 1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            noff = "--ASSET LIST-- HAVE SOME PROBLEM WHEN LOAD IMAGE";
            Toast.makeText(activity.getApplicationContext(), noff, Toast.LENGTH_LONG).show();
        }
        if (img != null){
            Bitmap bitmapImg = BitmapFactory.decodeByteArray(img, 0, img.length);
            Log.d("Demo", "Image loaded "+img);
            ((ImageView) convertView.findViewById(R.id.img_asset_photo)).setImageBitmap(bitmapImg);
        }

        Button btn_update = convertView.findViewById(R.id.btn_update);
        Button btn_delete = convertView.findViewById(R.id.btn_delete);
        Button btn_module3 = convertView.findViewById(R.id.btn_move);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), AssetInfomationActivity.class);
                intent.putExtra("status", "update");
                intent.putExtra("data", asset.getID());
                parent.getContext().startActivity(intent);
                notifyDataSetChanged();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new Overview().deleteAsset(asset);
                    assets.remove(position);
                    notifyDataSetChanged();
                } catch (SQLException throwables) {
                    noff = "-ASSET LIST- FAIL TO DELETE";
                    Toast.makeText(parent.getContext(), noff, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        btn_module3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(parent.getContext(), Module3.class);
                it.putExtra("data", asset.getID());
                parent.getContext().startActivity(it);
            }
        });
        return convertView;
    }
}
