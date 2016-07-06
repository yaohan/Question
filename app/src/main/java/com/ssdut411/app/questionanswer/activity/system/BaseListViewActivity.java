package com.ssdut411.app.questionanswer.activity.system;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.widget.EmptyLayout;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * listView的基类
 * 定义了2个ListView和EmptyLayout
 * initMenu()           返回menu布局
 * clickFirstList       第一个listView点击事件
 * clickSecondList      第二个ListView点击事件
 * initTitle            Activity标题
 * initFirstText        第一个标题
 * initSecondText       第二个标题
 * initFirstAdapter     第一个List的Adapter
 * initSecondAdapter    第二个List的Adapter
 * Created by yao_han on 2016/4/7.
 */
public abstract class BaseListViewActivity extends BaseActivity {

    protected ListView lvFirstList,lvSecondList;
    protected EmptyLayout emptyLayoutFirst,emptyLayoutSecond;

    protected abstract int initMenu();

    @Override
    protected int initContentView() {
        return R.layout.activity_listview;
    }

    @Override
    protected void initViews() {
        lvFirstList = getListView(R.id.lv_first_list);
        lvFirstList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickFirstList(parent, view, position, id);
            }
        });

        lvSecondList = getListView(R.id.lv_second_list);
        lvSecondList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickSecondList(parent, view, position, id);
            }
        });

        emptyLayoutFirst = new EmptyLayout(context, lvFirstList);
        emptyLayoutFirst.showLoading();

        emptyLayoutSecond = new EmptyLayout(context,lvSecondList);
        emptyLayoutSecond.showLoading();
    }

    protected abstract void clickSecondList(AdapterView<?> parent, View view, int position, long id);

    protected abstract void clickFirstList(AdapterView<?> parent, View view, int position, long id);


    protected abstract void loadData();


    @Override
    protected void showView() {
        getTextView(R.id.tv_list_first).setText(initFirstText());
        getTextView(R.id.tv_list_second).setText(initSecondText());
        setTitle(initTitle());
        setCanBack();
    }


    protected abstract String initSecondText();

    protected abstract String initFirstText();

    protected abstract String initTitle();

    protected Date getDayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return calendar.getTime();
    }
    protected String getTime(Date date){
        DecimalFormat df = new DecimalFormat("00");
        L.i("minute:" + date.getMinutes());
        L.i("format:" + df.format(date.getMinutes()));
        return df.format(date.getHours())+":"+df.format(date.getMinutes());
    }
}
