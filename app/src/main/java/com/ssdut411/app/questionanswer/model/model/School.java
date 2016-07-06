package com.ssdut411.app.questionanswer.model.model;

/**
 * Created by yao_han on 2015/12/23.
 */
public class School {

    private String schoolId;
    private String province;
    private String city;
    private String area;
    private String school;

    public School() {};

    public School(String province, String city, String area, String school) {
        this.province = province;
        this.city = city;
        this.area = area;
        this.school = school;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }

    public String getSchool() {
        return school;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
