package com.ssdut411.app.questionanswer.model.Req;

import java.util.Date;
import java.util.List;

/**
 * Created by aass on 2016/4/7.
 */
public class PostHomeworkReq extends BaseReq {
    private String id ;
    private String homeworkName;
    private int pointType ;
    private List<String> pointList;
    private int number ;
    private Date newTime;

    public PostHomeworkReq() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomeworkName() {
        return homeworkName;
    }

    public void setHomeworkName(String homeworkName) {
        this.homeworkName = homeworkName;
    }

    public int getPointType() {
        return pointType;
    }

    public void setPointType(int pointType) {
        this.pointType = pointType;
    }

    public List<String> getPointList() {
        return pointList;
    }

    public void setPointList(List<String> pointList) {
        this.pointList = pointList;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getNewTime() {
        return newTime;
    }

    public void setNewTime(Date newTime) {
        this.newTime = newTime;
    }
}
