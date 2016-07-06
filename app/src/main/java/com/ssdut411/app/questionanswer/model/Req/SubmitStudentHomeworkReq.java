package com.ssdut411.app.questionanswer.model.Req;

import com.ssdut411.app.questionanswer.model.model.StudentHomeworkModel;

/**
 * Created by yao_han on 2016/4/18.
 */
public class SubmitStudentHomeworkReq extends BaseReq {
    private StudentHomeworkModel studentHomeworkModel;

    public SubmitStudentHomeworkReq() {
    }

    public StudentHomeworkModel getStudentHomeworkModel() {
        return studentHomeworkModel;
    }

    public void setStudentHomeworkModel(StudentHomeworkModel studentHomeworkModel) {
        this.studentHomeworkModel = studentHomeworkModel;
    }
}
