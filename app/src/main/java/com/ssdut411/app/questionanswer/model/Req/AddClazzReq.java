package com.ssdut411.app.questionanswer.model.Req;

/**
 * Created by yao_han on 2015/12/25.
 */
public class AddClazzReq extends BaseReq {
    private String schoolId;
    private String grade;
    private String clazz;

    public AddClazzReq() {
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
}
