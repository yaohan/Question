package com.ssdut411.app.questionanswer.activity.teacher;

import android.widget.ListView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.activity.system.CommonAdapter;
import com.ssdut411.app.questionanswer.activity.system.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yao_han on 2015/11/28.
 */
public class StudentListActivity extends BaseActivity {
    private ListView lvList;
    private List<String> list;

    @Override
    protected String initTitle() {
        return getString(R.string.student_list);
    }

    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_listview;
    }

    @Override
    protected void initViews() {
        lvList = getListView(R.id.lv_second_list);

    }

    @Override
    protected void loadData() {
        list = new ArrayList<String>();
        list.add("张三");
        list.add("李四");
        list.add("王五");
    }

    @Override
    protected void showView() {
        setCanBack();
//        setRightText(getString(R.string.sure));
        CommonAdapter<String> adapter = new CommonAdapter<String>(this,list,R.layout.item_listview) {
            @Override
            public void convert(ViewHolder viewHolder, String s, int position) {
                viewHolder.getTextView(R.id.item_center).setText(s);
            }
        };

        lvList.setAdapter(adapter);
    }
}
