package com.ssdut411.app.questionanswer.activity.question;

import android.widget.ListView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.activity.system.CommonAdapter;
import com.ssdut411.app.questionanswer.activity.system.ViewHolder;
import com.ssdut411.app.questionanswer.utils.GsonUtils;
import com.ssdut411.app.questionanswer.utils.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yao_han on 2015/11/28.
 */
public class ClassRankActivity extends BaseActivity {


    private ListView lvList;
    private List<Map<String,String>> list;

    @Override
    protected String initTitle() {
        return getString(R.string.class_rank);
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
        List<Map<String,String>> mlist = new ArrayList<Map<String,String>>();
        Map<String,String> map = new HashMap<String,String>();
        map.put("name", "张三");
        map.put("score", "96分");
        mlist.add(map);
        Map<String,String> map1 = new HashMap<String,String>();
        map1.put("name", "李四");
        map1.put("score", "94分");
        mlist.add(map1);
        Map<String,String> map2 = new HashMap<String,String>();
        map2.put("name", "王五");
        map2.put("score", "88分");
        mlist.add(map2);
        list = GsonUtils.gsonToListMap(getIntent().getStringExtra("list"));
//        list = mlist;
        L.i(list.toString());
    }

    @Override
    protected void showView() {
        setCanBack();
        CommonAdapter<Map<String,String>> adapter = new CommonAdapter<Map<String, String>>(this,list,R.layout.item_listview) {
            @Override
            public void convert(ViewHolder viewHolder, Map<String, String> stringStringMap, int position) {
                viewHolder.getTextView(R.id.item_left).setText(stringStringMap.get("name"));
                viewHolder.getTextView(R.id.item_right).setText(stringStringMap.get("score"));
            }
        };

        lvList.setAdapter(adapter);
    }
}
