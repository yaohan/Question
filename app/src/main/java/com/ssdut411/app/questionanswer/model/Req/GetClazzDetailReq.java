package com.ssdut411.app.questionanswer.model.Req;

/**
 * Created by yao_han on 2016/4/7.
 */
public class GetClazzDetailReq extends BaseReq{
    private String schoolId;
    private String classId;

    public GetClazzDetailReq() {
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
