package com.example.module1.Class;

public class DepartmentLocation {
    long ID, DepartmentID, LocationID;
    String StartDate, EndDate;

    public DepartmentLocation() {
    }

    public DepartmentLocation(long ID, long departmentID, long locationID, String startDate, String endDate) {
        this.ID = ID;
        DepartmentID = departmentID;
        LocationID = locationID;
        StartDate = startDate;
        EndDate = endDate;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(long departmentID) {
        DepartmentID = departmentID;
    }

    public long getLocationID() {
        return LocationID;
    }

    public void setLocationID(long locationID) {
        LocationID = locationID;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }
}
