package com.ekrishibazaar.model;

public class DistrictModel {

    private String district_name;
    private String district_id;

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }
    @Override
    public String toString() {
        return district_name;
    }
}
