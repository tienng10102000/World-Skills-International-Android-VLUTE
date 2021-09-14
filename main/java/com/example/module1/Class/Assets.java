package com.example.module1.Class;

public class Assets {
    long ID;
    String AssetSN, AssetName;
    long DepartmentLocationID, EmployeeID, AssetGroupID;
    String Description, WarrantyDate;

    public Assets(long ID, String assetSN, String assetName, long departmentLocationID, long employeeID, long assetGroupID, String description, String warrantyDate) {
        this.ID = ID;
        AssetSN = assetSN;
        AssetName = assetName;
        DepartmentLocationID = departmentLocationID;
        EmployeeID = employeeID;
        AssetGroupID = assetGroupID;
        Description = description;
        WarrantyDate = warrantyDate;
    }

    public Assets(String assetSN, String assetName, long departmentLocationID, long employeeID, long assetGroupID, String description, String warrantyDate) {
        AssetSN = assetSN;
        AssetName = assetName;
        DepartmentLocationID = departmentLocationID;
        EmployeeID = employeeID;
        AssetGroupID = assetGroupID;
        Description = description;
        WarrantyDate = warrantyDate;
    }

    public Assets() {
        ID = 0;
        AssetSN = "NULL";
        AssetName = "NULL";
        DepartmentLocationID = 0;
        EmployeeID = 0;
        AssetGroupID = 0;
        Description = "NULL";
        WarrantyDate = "NULL";
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getAssetSN() {
        return AssetSN;
    }

    public void setAssetSN(String assetSN) {
        AssetSN = assetSN;
    }

    public String getAssetName() {
        return AssetName;
    }

    public void setAssetName(String assetName) {
        AssetName = assetName;
    }

    public long getDepartmentLocationID() {
        return DepartmentLocationID;
    }

    public void setDepartmentLocationID(long departmentLocationID) {
        DepartmentLocationID = departmentLocationID;
    }

    public long getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(long employeeID) {
        EmployeeID = employeeID;
    }

    public long getAssetGroupID() {
        return AssetGroupID;
    }

    public void setAssetGroupID(long assetGroupID) {
        AssetGroupID = assetGroupID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getWarrantyDate() {
        return WarrantyDate;
    }

    public void setWarrantyDate(String warrantyDate) {
        WarrantyDate = warrantyDate;
    }
}