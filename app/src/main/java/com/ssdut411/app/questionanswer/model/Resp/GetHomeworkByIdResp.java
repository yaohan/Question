package com.ssdut411.app.questionanswer.model.Resp;


import com.ssdut411.app.questionanswer.model.model.TeacherHomeworkModel;

/**
 * Created by yao_han on 2016/4/13.
 */
public class GetHomeworkByIdResp extends BaseResp{
    public static final String DESC_SUCCESS = "获取成功";
    public static final String DESC_NOT_FOUND = "未找到该作业";
    private TeacherHomeworkModel teacherHomeworkModel;

    public GetHomeworkByIdResp() {
    }

    public TeacherHomeworkModel getTeacherHomeworkModel() {
        return teacherHomeworkModel;
    }

    public void setTeacherHomeworkModel(TeacherHomeworkModel teacherHomeworkModel) {
        this.teacherHomeworkModel = teacherHomeworkModel;
    }
}
