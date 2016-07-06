package com.ssdut411.app.questionanswer.model.model;


/**
 * Created by zjm on 15-11-24.
 */

public class Clazz {
    private String cId;
    private String schoolId;
    private String grade;
    private String cls;

    //空构造函数
    public Clazz() {}

    public Clazz(String schoolId, String grade, String cls) {
        this.schoolId = schoolId;
        this.grade = grade;
        this.cls = cls;
    }

    public String getcId() {
        return cId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public String getGrade() {
        return grade;
    }

    public String getCls() {
        return cls;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }
}
