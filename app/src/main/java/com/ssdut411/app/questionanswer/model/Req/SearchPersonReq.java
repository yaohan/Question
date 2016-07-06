package com.ssdut411.app.questionanswer.model.Req;

/**
 * Created by yao_han on 2016/4/5.
 */
public class SearchPersonReq extends BaseReq {
    private String name;
    private int role;

    public SearchPersonReq() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
