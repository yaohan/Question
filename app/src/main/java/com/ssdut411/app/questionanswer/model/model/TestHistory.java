package com.ssdut411.app.questionanswer.model.model;

import java.util.Date;

/**
 * Created by yao_han on 2016/4/16.
 */
public class TestHistory {
    private String id;
    private String name;
    private int state;
    private Date newTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getNewTime() {
        return newTime;
    }

    public void setNewTime(Date newTime) {
        this.newTime = newTime;
    }
}
