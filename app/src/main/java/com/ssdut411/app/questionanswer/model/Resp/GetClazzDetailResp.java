package com.ssdut411.app.questionanswer.model.Resp;


import com.ssdut411.app.questionanswer.model.model.UserModel;

import java.util.List;

/**
 * Created by yao_han on 2016/4/7.
 */
public class GetClazzDetailResp extends BaseResp {
    public static final String DESC_SUCCESS = "获取成功";

    private List<UserModel> students;
    private List<UserModel> teachers;

    public GetClazzDetailResp() {
    }

    public List<UserModel> getStudents() {
        return students;
    }

    public void setStudents(List<UserModel> students) {
        this.students = students;
    }

    public List<UserModel> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<UserModel> teachers) {
        this.teachers = teachers;
    }
}
