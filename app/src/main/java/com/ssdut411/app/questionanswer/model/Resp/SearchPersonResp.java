package com.ssdut411.app.questionanswer.model.Resp;

import com.ssdut411.app.questionanswer.model.model.UserModel;

import java.util.List;

/**
 * Created by yao_han on 2016/4/5.
 */
public class SearchPersonResp extends BaseResp{
    public static final String DESC_SUCCESS = "获取成功";
    private List<UserModel> list;

    public SearchPersonResp() {
    }

    public List<UserModel> getList() {
        return list;
    }

    public void setList(List<UserModel> list) {
        this.list = list;
    }
}
