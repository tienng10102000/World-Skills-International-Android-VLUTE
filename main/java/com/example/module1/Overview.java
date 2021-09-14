package com.example.module1;

import android.widget.Toast;

import com.example.module1.Class.AssetGroup;
import com.example.module1.Class.Assets;
import com.example.module1.Class.Department;
import com.example.module1.Class.DepartmentLocation;
import com.example.module1.Class.Employees;
import com.example.module1.Class.Location;
import com.example.module1.JDBCConnection.JDBCControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Overview {
    private JDBCControl jdbcControl = new JDBCControl();
    private Connection connection;

    public Overview(){
        connection = jdbcControl.ConnectionData();
    }

    //MODULE 1 - CHECK LOGIN
    public long login(String username, String password) throws SQLException{
        long ID = -1;
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Employees WHERE Username = '" + username + "' AND Password = '" + password + "'";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            ID = resultSet.getLong("ID");
        }
        connection.close();
        return ID;
    }

    //MODULE 1 - CHECK ADMIN
    public boolean isAdmin(long ID) throws SQLException{
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Employees WHERE ID = " + ID;
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            if (resultSet.getBoolean("isAdmin")) {
                connection.close();
                return true;
            }
        }
        connection.close();
        return false;
    }

// MODULE 2 - ASSET LIST GET DEPARTMENT NAME BY DEPARTMENT ID -> FUNCTION BELOW
    public String getDepartmentName(long departmentID) throws SQLException {
        String name = "";
        Statement statement = connection.createStatement();
        String query = "select * from Departments where ID = " + departmentID;
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            name = resultSet.getString("Name");
        }
        connection.close();
        return name;
    }

// MODULE 2 - ASSET LIST - GET ID OF DEPARTMENT LOCATION ID TO GET NAME DEPARTMENT
    //MODULE 2 -> ASSET INFORMATION -> LOAD INFO, SET ON ITEM SELECTED (SP_DEP)
    public long getDepartmentID(long departmentLocationID) throws SQLException {
        long departmentID = -1;
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM DepartmentLocations WHERE ID = " + departmentLocationID;
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            departmentID = resultSet.getLong("DepartmentID");
        }
        connection.close();
        return departmentID;
    }

// MODULE 2 - ASSET LIST - GET IMAGE
    //MODULE 2 - ASSET INFORMATION -> LOAD INFO() GET IMAGE
    public byte[] getPhoto(long AssetID, int row) throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM AssetPhotos WHERE AssetID = "+AssetID);
        for (int i = 0; i < row; i++) {
            resultSet.next();
        }
        byte[] bytesImageDB = resultSet.getBytes(3);
        connection.close();
        return bytesImageDB;
    }

    // MODULE 2 - ASSET LIST - DELETE BUTTON
    public boolean deleteAsset(Assets assets) throws SQLException{
        Statement statement = connection.createStatement();
        String query = "DELETE FROM Assets WHERE ID = " + assets.getID();
        String queryPhotos = "DELETE FROM AssetPhotos WHERE AssetID = " + assets.getID();
        try {
            statement.execute(queryPhotos);
            statement.execute(query);
        }catch (SQLException e){
            connection.close();
            return false;
        }
        connection.close();
        return true;
    }

    //MODULE 2 -> LOAD() - TO INSERT INTO THE LIST VIEW AT MODULE2.XML
    public ArrayList<Assets> getAssetsList() throws SQLException {
        ArrayList<Assets> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Assets";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            list.add(new Assets(resultSet.getLong("ID"),
                    resultSet.getString("AssetSN"),
                    resultSet.getString("AssetName"),
                    resultSet.getLong("DepartmentLocationID"),
                    resultSet.getLong("EmployeeID"),
                    resultSet.getLong("AssetGroupID"),
                    resultSet.getString("Description"),
                    resultSet.getString("WarrantyDate")));
        }
        connection.close();
        return list;
    }

    //MODULE 2 -> REFRESH(1) - TO RELOAD DATA BY DEPARTMENT
    public ArrayList<Assets> getAssetsListByDepartment(long departmentID) throws SQLException {
        ArrayList<Assets> list = new ArrayList<>();
        String query = "SELECT * FROM Assets WHERE DepartmentLocationID in " +
                "(SELECT ID FROM DepartmentLocations WHERE DepartmentID = " + departmentID + ")";
        list = getAssetsList(query);
        return list;
    }

    //MODULE 2 -> REFRESH(2) - TO RELOAD DATA BY ASSET GROUP
    public ArrayList<Assets> getAssetsListByAssetGroup(long assetGroupID) throws SQLException {
        ArrayList<Assets> list = new ArrayList<>();
        String query = "SELECT * FROM Assets WHERE AssetGroupID = " + assetGroupID;
        list = getAssetsList(query);
        return list;
    }

    //MODULE 2 -> REFRESH(3) - TO RELOAD DATA BY START DAY
    public ArrayList<Assets> getAssetsListByS(String startDate) throws SQLException {
        ArrayList<Assets> list = new ArrayList<>();
        String query = "SELECT * FROM Assets WHERE DepartmentLocationID IN " +
                "(SELECT ID FROM DepartmentLocations WHERE StartDate >= '" + startDate + "')";
        list = getAssetsList(query);
        return list;
    }

    //MODULE 2 -> REFRESH(4) - TO RELOAD DATA BY END DAY
    public ArrayList<Assets> getAssetsListByE(String endDate) throws SQLException {
        ArrayList<Assets> list = new ArrayList<>();
        String query = "SELECT * FROM Assets WHERE DepartmentLocationID IN " +
                "(SELECT ID FROM DepartmentLocations WHERE EndDate <= '" + endDate + "')";
        list = getAssetsList(query);
        return list;
    }

    //MODULE 2 -> TO IMPORT DEPARTMENT LIST TO SPINNER DEPARTMENT
    //MODULE 2 -> ASSET INFORMATION - TO IMPORT DEPARTMENT LIST TO SPINNER DEPARTMENT
    public ArrayList<Department> getDepartmentList() throws SQLException {
        ArrayList<Department> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Departments";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            list.add(new Department(resultSet.getLong("ID"),
                    resultSet.getString("Name")));
        }
        connection.close();
        return list;
    }

    //MODULE 2 -> TO IMPORT ASSET GROUP LIST TO SPINNER ASSET GROUP
    //MODULE 2 -> ASSET INFORMATION -> TO IMPORT ASSET GROUP LIST TO SPINNER ASSET GROUP
    public ArrayList<AssetGroup> getAssetGroupsList() throws SQLException {
        ArrayList<AssetGroup> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM AssetGroups";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            list.add(new AssetGroup(resultSet.getLong("ID"),
                    resultSet.getString("Name")));
        }
        connection.close();
        return list;
    }

    //MODULE 2 -> ASSET INFORMATION -> LOAD INFO() - COUNT PHOTOS
    public int getCountPhoto(long AssetID) throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(ID) AS COUNT_COLUMN FROM AssetPhotos WHERE AssetID = "+AssetID);
        resultSet.next();
        int count = resultSet.getInt("COUNT_COLUMN");
        connection.close();
        return count;
    }

    //MODULE 2 -> ASSET INFORMATION -> LOAD INFO() - IDENTIFY OBJECT
    public Assets getAsset(long ID) throws SQLException{
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Assets WHERE ID = " + ID;
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next()){
            Assets assets = new Assets(
                    resultSet.getLong("ID"),
                    resultSet.getString("AssetSN"),
                    resultSet.getString("AssetName"),
                    resultSet.getLong("DepartmentLocationID"),
                    resultSet.getLong("EmployeeID"),
                    resultSet.getLong("AssetGroupID"),
                    resultSet.getString("Description")+"",
                    resultSet.getString("WarrantyDate"));
            connection.close();
            return assets;
        }
        connection.close();
        return new Assets();
    }

    //getDepartmentName(LONG DEPARTMENT LOCATION ID)

    //MODULE 2 -> ASSET INFORMATION -> LOAD INFO() - IDENTIFY LOCATION ID OF ASSET
    public long getLocationID(long departmentLocationID) throws SQLException{
        long locationID = -1;
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM DepartmentLocations WHERE ID = " + departmentLocationID;
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            locationID = resultSet.getLong("LocationID");
        }
        connection.close();
        return locationID;
    }

    //getPhoto(asset.getID(),1);

    //MODULE 2 - ASSET INFORMATION - LOAD INFO() -> GET VALUE ID'S PHOTO
    public long getPhotoID(byte[] img, long assetID) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT ID FROM AssetPhotos WHERE AssetPhoto = ? AND AssetID = ?");
        preparedStatement.setBytes(1, img);
        preparedStatement.setLong(2, assetID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            long id = resultSet.getLong("ID");
            connection.close();
            return id;
        }
        connection.close();
        return 0;
    }

    //getDepartmentList()
    //getAssetGroupsList()

    //MODULE 2 - ASSET INFORMATION - LOAD INFO() -> GET LIST LOCATION
    public ArrayList<Location> getLocationsList() throws SQLException {
        ArrayList<Location> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Locations";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            list.add(new Location(resultSet.getLong("ID"),
                    resultSet.getString("Name")));
        }
        connection.close();
        return list;
    }

    //MODULE 2 - ASSET INFORMATION - LOAD INFO() -> GET LIST EMPLOYEES
    public ArrayList<Employees> getEmployeesList() throws SQLException {
        ArrayList<Employees> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Employees";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            list.add(new Employees(resultSet.getLong("ID"),
                    resultSet.getString("FirstName"),
                    resultSet.getString("LastName"),
                    resultSet.getString("Phone"),
                    resultSet.getBoolean("isAdmin"),
                    resultSet.getString("Username"),
                    resultSet.getString("Password")));
        }
        connection.close();
        return list;
    }

    //MODULE 2 - ASSET INFORMATION - SP_DEP.ON ITEM SELECTED LISTENER
    //MODULE 2 - ASSET INFORMATION - SP_ASSET_GR.ON ITEM SELECTED LISTENER
    public boolean AssetSNisExists(String sn) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Assets WHERE AssetSN = '" + sn + "'";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            long id = 0;
            id = resultSet.getLong("ID");
            if (id > 0) {
                connection.close();
                return true;
            }
        }
        connection.close();
        return false;
    }

    //MODULE 2 - ASSET INFORMATION - SP_ASSET_GR.ON ITEM SELECTED LISTENER
    public long getAssetGroupID(String name) throws SQLException {
        long ID = -1;
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM AssetGroups WHERE Name = '" + name + "'";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            ID = resultSet.getLong("ID");
        }
        connection.close();
        return ID;
    }

    //MODULE 2 - ASSET INFORMATION - tv_submit.setOnClickListener
    public long getDepartmentLocationID(long dep_id, long loc_id) throws SQLException{
        long ID = 0;
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM DepartmentLocations WHERE DepartmentID = " + dep_id + " and LocationID = " + loc_id;
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            ID = resultSet.getLong("ID");
        }
        connection.close();
        return ID;
    }

    //MODULE 2 - ASSET INFORMATION - tv_submit.setOnClickListener - STATUS = ADD
    public void insertAssets(Assets assets) throws SQLException{
        Statement statement = connection.createStatement();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Assets (" +
                        "AssetSN, " +
                        "AssetName, " +
                        "DepartmentLocationID, " +
                        "EmployeeID, " +
                        "AssetGroupID, " +
                        "Description, " +
                        "WarrantyDate) " +
                        "VALUES (?,?,?,?,?,?,?)");
        preparedStatement.setString(1, assets.getAssetSN());
        preparedStatement.setString(2, assets.getAssetName());
        preparedStatement.setLong(3, assets.getDepartmentLocationID());
        preparedStatement.setLong(4, assets.getEmployeeID());
        preparedStatement.setLong(5, assets.getAssetGroupID());
        preparedStatement.setString(6, assets.getDescription());
        preparedStatement.setString(7, assets.getWarrantyDate());
        preparedStatement.execute();
        connection.close();
    }

    //MODULE 2 - ASSET INFORMATION - tv_submit.setOnClickListener - STATUS = UPDATE
    public void updateAssets(Assets assets) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "UPDATE Assets SET " +
                "AssetSN = ?, " +
                "AssetName = ?, " +
                "DepartmentLocationID = ?, " +
                "EmployeeID = ?," +
                "AssetGroupID = ?, " +
                "Description = ?, " +
                "WarrantyDate = ? WHERE ID = ?");
        preparedStatement.setString(1, assets.getAssetSN());
        preparedStatement.setString(2, assets.getAssetName());
        preparedStatement.setLong(3, assets.getDepartmentLocationID());
        preparedStatement.setLong(4, assets.getEmployeeID());
        preparedStatement.setLong(5, assets.getAssetGroupID());
        preparedStatement.setString(6, assets.getDescription());
        preparedStatement.setString(7, assets.getWarrantyDate());
        preparedStatement.setLong(8, assets.getID());
        preparedStatement.execute();
        connection.close();
    }

    //MODULE 2 - ASSET INFORMATION - tv_submit.setOnClickListener - STATUS = ADD
    public void insertPhoto(byte[] img, long AssetID) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO AssetPhotos (AssetID, AssetPhoto) VALUES (?,?)");
        preparedStatement.setLong(1, AssetID);
        preparedStatement.setBytes(2, img);
        preparedStatement.execute();
        connection.close();
    }

    //MODULE 2 - ASSET INFORMATION - tv_submit.setOnClickListener - STATUS = UPDATE
    public void updatePhoto(long ID, byte[] img) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE AssetPhotos SET AssetPhoto = ? WHERE ID = ?");
        preparedStatement.setBytes(1, img);
        preparedStatement.setLong(2, ID);
        preparedStatement.execute();
        connection.close();
    }

    public ArrayList<DepartmentLocation> getDepartmentLocationsList() throws SQLException {
        ArrayList<DepartmentLocation> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM DepartmentLocations";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            list.add(new DepartmentLocation(
                    resultSet.getLong("ID"),
                    resultSet.getLong("DepartmentID"),
                    resultSet.getLong("LocationID"),
                    resultSet.getString("StartDate"),
                    resultSet.getString("EndDate")));
        }
        connection.close();
        return list;
    }

    public long getDepartmentID(String name) throws SQLException {
        long ID = -1;
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Departments WHERE Name = '" + name + "'";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            ID = resultSet.getLong("ID");
        }
        connection.close();
        return ID;
    }

    public ArrayList<Assets> getAssetsList(String query) throws SQLException {
        ArrayList<Assets> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            list.add(new Assets(resultSet.getLong("ID"),
                    resultSet.getString("AssetSN"),
                    resultSet.getString("AssetName"),
                    resultSet.getLong("DepartmentLocationID"),
                    resultSet.getLong("EmployeeID"),
                    resultSet.getLong("AssetGroupID"),
                    resultSet.getString("Description"),
                    resultSet.getString("WarrantyDate")));
        }
        connection.close();
        return list;
    }

    public long getAssetID(String SN) throws SQLException{
        long id = 0;
        Statement statement = connection.createStatement();
        String query = "SELECT ID FROM Assets WHERE AssetSN = '" + SN + "'";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            id = resultSet.getLong("ID");
            if (id > 0){
                connection.close();
                return id;
            }
        }
        connection.close();
        return id;
    }

    public void moveAssetDepartmentTo(long dep_loc_ID, String AssetSN, long assID) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "UPDATE Assets SET " +
                "AssetSN = ?, " +
                "DepartmentLocationID = ? where ID = ?");
        preparedStatement.setString(1, AssetSN);
        preparedStatement.setLong(2, dep_loc_ID);
        preparedStatement.setLong(3, assID);
        preparedStatement.execute();
        connection.close();
    }
}
