package com.ssdut411.app.questionanswer.model.Resp;

import java.util.Date;
import java.util.List;

/**
 * Created by yao_han on 2016/4/13.
 */
public class GetTeacherHomeworkResp extends BaseResp{
    public static final String DESC_SUCCESS = "获取成功";
    private List<TeacherHomework> list;

    public GetTeacherHomeworkResp() {
    }

    public List<TeacherHomework> getList() {
        return list;
    }

    public void setList(List<TeacherHomework> list) {
        this.list = list;
    }

    public class TeacherHomework{
        private String id;
        private Date newDate;
        private String name;
        private int finishes;
        private int counts;
        private int state;

        public TeacherHomework() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Date getNewDate() {
            return newDate;
        }

        public void setNewDate(Date newDate) {
            this.newDate = newDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getFinishes() {
            return finishes;
        }

        public void setFinishes(int finishes) {
            this.finishes = finishes;
        }

        public int getCounts() {
            return counts;
        }

        public void setCounts(int counts) {
            this.counts = counts;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
