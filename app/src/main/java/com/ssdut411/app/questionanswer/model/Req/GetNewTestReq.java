package com.ssdut411.app.questionanswer.model.Req;

import java.util.Date;
import java.util.List;

/**
 * Created by LENOVO on 2015/12/26.
 */
public class GetNewTestReq extends BaseReq{

    private List<String> testSites;
    private int select;
    private int number;
    private String psId;
    private String name;
    private Date newTime;

    public GetNewTestReq() {
    }

    public List<String> getTestSites() {
        return testSites;
    }

    public void setTestSites(List<String> testSites) {
        this.testSites = testSites;
    }

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPsId() {
        return psId;
    }

    public void setPsId(String psId) {
        this.psId = psId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getNewTime() {
        return newTime;
    }

    public void setNewTime(Date newTime) {
        this.newTime = newTime;
    }
}
