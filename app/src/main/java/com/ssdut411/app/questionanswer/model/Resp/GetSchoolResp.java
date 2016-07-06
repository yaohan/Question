package com.ssdut411.app.questionanswer.model.Resp;

import com.ssdut411.app.questionanswer.model.model.School;

import java.util.List;
import java.util.Map;

/**
 * Created by yao_han on 2015/12/23.
 */
public class GetSchoolResp extends BaseResp{
    private List<School> schoolList;

    public static String DESC_SUCCESS = "获取成功";

    public GetSchoolResp() {
    }

    public List<School> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<School> schoolList) {
        this.schoolList = schoolList;
    }
}
