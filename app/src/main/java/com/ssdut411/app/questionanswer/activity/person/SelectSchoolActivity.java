package com.ssdut411.app.questionanswer.activity.person;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.activity.system.CommonAdapter;
import com.ssdut411.app.questionanswer.activity.system.ViewHolder;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.GetClazzReq;
import com.ssdut411.app.questionanswer.model.Req.GetSchoolReq;
import com.ssdut411.app.questionanswer.model.Resp.GetClazzResp;
import com.ssdut411.app.questionanswer.model.Resp.GetSchoolResp;
import com.ssdut411.app.questionanswer.model.model.Clazz;
import com.ssdut411.app.questionanswer.model.model.School;
import com.ssdut411.app.questionanswer.utils.GsonUtils;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.T;
import com.ssdut411.app.questionanswer.widget.EmptyLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yao_han on 2016/3/22.
 */
public class SelectSchoolActivity extends BaseActivity{
    private static String GRADE_PROVINCE = "province";
    private static String GRADE_CITY = "city";
    private static String GRADE_AREA = "area";
    private static String GRADE_SCHOOL = "school";

    private static String GRADE_GRADE = "grade";
    private static String GRADE_CLASS = "class";

    private RelativeLayout rlSearch;
    private boolean showSearch = false;
    private CommonAdapter<String> adapter;
    private List<Clazz> classList;
    private List<School> schoolList;
    private List<String> stringList;
    private List<String> schoolIdList = new ArrayList<String>();
    private ListView lvList;
    private PopupWindow pop;
    private String globalGrade;
    private String target;
    private String retClass = "";
    private EmptyLayout emptyLayout;
    private String province,city,area,grade,schoolId;

    @Override
    protected String initTitle() {
        if(target.equals("school")){
            return "选择学校";
        }else{
            return "选择班级";
        }
    }

    @Override
    protected int initMenu() {
        return R.menu.menu_new;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_select_school;
    }

    @Override
    protected void initViews() {
        rlSearch = getRelativeLayout(R.id.rl_school_search);
        lvList = getListView(R.id.lv_school_list);
        emptyLayout = new EmptyLayout(context,lvList);
        emptyLayout.showLoading();
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String click = stringList.get(position);
                if(target.equals("school")){
                    if(globalGrade.equals(GRADE_SCHOOL)){//学校
                        Intent intent = getIntent().putExtra("schoolName",click);
                        L.i("schoolIdList:"+schoolIdList);
                        intent.putExtra("schoolId",schoolIdList.get(position));
                        setResult(RESULT_OK, intent);
                        finish();
                    }else{
                        stringList.removeAll(stringList);
                        stringList.addAll(getSubGrade(globalGrade,click));
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    retClass+=click;
                    if(globalGrade.equals(GRADE_CLASS)){
                        Intent intent = getIntent().putExtra("className",retClass);
                        intent.putExtra("classId",schoolIdList.get(position));
                        L.i("retClass:"+retClass);
                        setResult(RESULT_OK,intent);
                        finish();
                    }else{
                        grade = click;
                        stringList.removeAll(stringList);
                        stringList.addAll(getClazz());
                        adapter.notifyDataSetChanged();
                    }
                }

            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actions_new:
                        Intent intent = new Intent(context,AddSchoolActivity.class);
                        intent.putExtra("target",target);
                        if(target.equals("school")){
                            intent.putExtra("province",province);
                            intent.putExtra("city",city);
                            intent.putExtra("area",area);
                        }else{
                            intent.putExtra("schoolId",schoolId);
                            intent.putExtra("grade",grade);
                        }
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }



    @Override
    protected void loadData() {
        target = getIntent().getStringExtra("target");
        L.i("target:"+target);
        if(target.equals("school")){
            setSchoolList();
            setTitle("选择学校");
        }else{
            schoolId = getIntent().getStringExtra("schoolId");
            setClassList();
            setTitle("选择班级");
        }
    }

    private void setClassList() {
        AppAction action = new AppActionImpl(context);
        GetClazzReq classReq = new GetClazzReq();
        classReq.setSchoolId(schoolId);
        action.getClazzList(classReq, new ActionCallbackListener<GetClazzResp>() {
            @Override
            public void onSuccess(GetClazzResp data) {
                if (data.getResult() == GetClazzResp.RESULT_SUCCESS) {
                    L.i("classList:"+data.getDesc()+"  "+data.getResult()+" "+data.getClassList());
                    classList = data.getClassList();
                    if(classList == null ||classList.size() == 0){
                        emptyLayout.showEmpty();
                    }else{
                        stringList.removeAll(stringList);
                        stringList.addAll(getGrade());
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    emptyLayout.showError();
                    T.showShort(context, data.getDesc());
                }
            }

            @Override
            public void onFailure(String message) {
                emptyLayout.showError();
                T.showShort(context, message);
            }
        });
    }

    private void setSchoolList() {
        AppAction action = new AppActionImpl(context);
        GetSchoolReq schoolReq = new GetSchoolReq();
        action.getSchoolList(schoolReq, new ActionCallbackListener<GetSchoolResp>() {
            @Override
            public void onSuccess(GetSchoolResp data) {
                if (data.getResult() == GetSchoolResp.RESULT_SUCCESS) {
                    schoolList = data.getSchoolList();
                    if (schoolList.size() == 0) {
                        emptyLayout.showEmpty();
                    } else {
                        stringList.removeAll(stringList);
                        stringList.addAll(getProvince());
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    T.showShort(context, data.getDesc());
                    emptyLayout.showError();
                }
            }

            @Override
            public void onFailure(String message) {
                T.showShort(context, message);
                emptyLayout.showError();
            }
        });
    }

    @Override
    protected void showView() {
        setCanBack();
        stringList = new ArrayList<String>();
        adapter = new CommonAdapter<String>(context,stringList,R.layout.item_listview) {
            @Override
            public void convert(ViewHolder viewHolder, String s, int position) {
                viewHolder.getTextView(R.id.item_center).setText(s);
            }
        };
        lvList.setAdapter(adapter);
    }

    public List getListFromMap(Map<String,Object> map) {
        List<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            list.add(entry.getKey());
        }
        return list;
    }

    public List<String> getProvince() {
        globalGrade = GRADE_PROVINCE;
        List<String> list = new ArrayList<String>();
        for(School school:schoolList){
            if(!list.contains(school.getProvince())){
                list.add(school.getProvince());
            }
        }
        return list;
    }

    private List<String> getSubGrade(String grade, String click) {
        schoolIdList.removeAll(schoolIdList);
        L.i("grade:"+grade+" click"+click);
        List<String> list = new ArrayList<String>();
        for(School school:schoolList){
            if(grade.equals(GRADE_PROVINCE)){
                province = click;
                globalGrade = GRADE_CITY;
                if(school.getProvince().equals(click)){
                    if(!list.contains(school.getCity())){
                        list.add(school.getCity());
                    }
                }
            }else if(grade.equals(GRADE_CITY)){
                city = click;
                globalGrade = GRADE_AREA;
                if(school.getCity().equals(click)){
                    if(!list.contains(school.getArea())){
                        list.add(school.getArea());
                    }
                }
            }else if(grade.equals(GRADE_AREA)){
                area = click;
                globalGrade = GRADE_SCHOOL;
                if(school.getArea().equals(click)){
//                    L.i("school:"+school);
                    list.add(school.getSchool());
                    schoolIdList.add(school.getSchoolId());
                    L.i("schoolId:" + schoolIdList);
                }
            }
        }
        L.i("list:"+list);
        return list;
    }

    private List<String> getGrade(){
        globalGrade = GRADE_GRADE;
        List<String> list = new ArrayList<String>();
        for(Clazz clazz:classList){
            if(!list.contains(clazz.getGrade())){
                list.add(clazz.getGrade());
            }
        }
        return list;
    }

    private List<String> getClazz(){
        schoolIdList = new ArrayList<String>();
        globalGrade = GRADE_CLASS;
        List<String> list = new ArrayList<String>();
        for(Clazz clazz:classList){
            if(clazz.getGrade().equals(grade)){
                list.add(clazz.getCls());
            }
            schoolIdList.add(clazz.getcId());
        }
        return list;
    }
}