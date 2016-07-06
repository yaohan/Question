package com.ssdut411.app.questionanswer.model.model;

import java.util.Date;
import java.util.List;

/**
 * Created by yao_han on 2016/4/16.
 */
public class StudentHomeworkModel extends SelfTestModel{
    //评论列表
    private List<CommentInfo> commentList;

    private String studentName;

    public StudentHomeworkModel() {

    }

    public List<CommentInfo> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentInfo> commentList) {
        this.commentList = commentList;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }


}
