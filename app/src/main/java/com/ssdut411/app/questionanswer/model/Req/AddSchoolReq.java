package com.ssdut411.app.questionanswer.model.Req;

/**
 * Created by yao_han on 2015/12/25.
 */
public class AddSchoolReq extends BaseReq{
    private String province;
    private String city;
    private String area;
    private String school;

    public AddSchoolReq() {
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
