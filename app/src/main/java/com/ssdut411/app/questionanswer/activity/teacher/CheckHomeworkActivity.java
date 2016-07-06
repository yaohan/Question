package com.ssdut411.app.questionanswer.activity.teacher;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.activity.system.CommonAdapter;
import com.ssdut411.app.questionanswer.activity.system.ViewHolder;
import com.ssdut411.app.questionanswer.utils.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yao_han on 2015/11/29.
 */
public class CheckHomeworkActivity extends BaseActivity {
    private ListView lvList;
    private List<Map<String, String>> list;

    @Override
    protected String initTitle() {
        return getString(R.string.check_homework);
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
        List<Map<String, String>> mlist = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "11月16日作业");
        map.put("count", "1/30");
        map.put("finished", "false");
        mlist.add(map);
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("name", "四则运算");
        map1.put("count", "30/30");
        map1.put("finished", "true");
        mlist.add(map1);
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("name", "11月14日作业");
        map2.put("count", "29/30");
        map2.put("finished", "false");
        mlist.add(map2);
        list = mlist;
        L.i(list.toString());
    }

    @Override
    protected void showView() {
        setCanBack();
        CommonAdapter<Map<String,String>> adapter = new CommonAdapter<Map<String, String>>(this,list,R.layout.item_listview) {
            @Override
            public void convert(ViewHolder viewHolder, Map<String, String> stringStringMap, int position) {
                viewHolder.getTextView(R.id.item_left).setText(stringStringMap.get("name"));
                viewHolder.getTextView(R.id.item_center).setText(stringStringMap.get("count"));
                if(stringStringMap.get("finished").equals("true")){
                    viewHolder.getImageView(R.id.item_right_image).setImageResource(R.mipmap.ic_check);
                }else{
                    viewHolder.getImageView(R.id.item_right_image).setImageResource(R.mipmap.ic_reminder);
                }
            }
        };
        lvList.setAdapter(adapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                L.i(getString(R.string.function_not_finished));
            }
        });
    }
//
//    private void startClassRankActivity() {
//        Intent intent = new Intent(CheckHomeworkActivity.this)
//    }
}
