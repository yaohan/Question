package com.ssdut411.app.questionanswer.activity.system;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.utils.ActivityStackUtils;
import com.ssdut411.app.questionanswer.utils.KeyBoardUtils;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.SPUtils;
import com.ssdut411.app.questionanswer.utils.T;
import com.ssdut411.app.questionanswer.widget.GridViewForScrollView;
import com.ssdut411.app.questionanswer.widget.ListViewForScrollView;

/**
 * Activity基类   保持竖屏，默认关闭软键盘
 * 所有布局都要include layout_toolbar.xml
 * context 上下文
 * Toolbar 顶部toolbar栏
 * theme true:dayTheme false:nightTheme
 * <p/>
 * initMenu()  返回Menu的xml文件
 * initContentView 返回布局文件
 * initView()  初始化View,绑定listene
 * loadData()  获取数据，从Intent或者从后台
 * showView()  显示View
 * 简化绑定View
 * Created by yao_han on 2015/11/24.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Context context = BaseActivity.this;
    protected Toolbar mToolbar;
    protected Boolean theme;

    private SparseArray<View> views = new SparseArray<View>();
    private long firstTime = 0, intercal = 2000;
    private boolean hasTips = false;

    /**
     * 实体返回键 提示
     */
    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (hasTips) {
            if (secondTime - firstTime > intercal) {
                T.showShort(context, getString(R.string.press_again_exit));
                firstTime = secondTime;
            } else {
                ActivityStackUtils.getInstance().exit();
            }
        } else {
            finish();
        }
    }

    /**
     * 设置竖屏
     */
    @Override
    protected void onResume() {
        super.onResume();
        KeyBoardUtils.closeKeyboard(new EditText(this), this);  //关闭软键盘
        /**
         * 设置为竖屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStackUtils.getInstance().addActivity(this);
        setTheme(); //设置theme
        setContentView(this.initContentView()); //初始化布局文件
        initToolbar();  //初始化顶部Toolbar
        initViews();//初始化View
        loadData(); //加载数据
        showView(); //显示View
        setTitle(initTitle());
    }

    protected abstract String initTitle();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackUtils.getInstance().removeActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (initMenu() == 0) {
            getMenuInflater().inflate(R.menu.menu_null, menu);
        } else {
            getMenuInflater().inflate(this.initMenu(), menu);
        }
        return true;
    }

    /**
     * 初始化Menu
     *
     * @return
     */
    protected abstract int initMenu();

    /**
     * 初始化ContentView
     *
     * @return
     */
    protected abstract int initContentView();


    /**
     * 初始化view组件
     */
    protected abstract void initViews();

    /**
     * 加载数据
     */
    protected abstract void loadData();

    /**
     * 显示数据
     */
    protected abstract void showView();


    /**
     * 设置主题
     * theme true:DayTheme,false:NightTheme
     */
    private void setTheme() {
        theme = (Boolean) SPUtils.get(context, "theme", true);
        if (theme) {
            setTheme(R.style.DayTheme);
        } else {
            setTheme(R.style.NightTheme);
        }
        MainApplication.getInstance().setTheme(theme);
    }

    /**
     * 初始化Toolbar,所有布局都要include layout_toolbar.xml
     */
    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    /**
     * 设置左上角返回图标
     */
    protected void setCanBack() {
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置按实体返回键是否提示
     */
    protected void setBackTips() {
        hasTips = true;
    }


    /**
     * 简化绑定View
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public TextView getTextView(int viewId) {
        return getView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return getView(viewId);
    }

    public Button getButton(int viewId) {
        return getView(viewId);
    }

    public EditText getEditText(int viewId) {
        return getView(viewId);
    }

    public ListView getListView(int viewId) {
        return getView(viewId);
    }

    public ExpandableListView getExpandableListView(int viewId) {
        return getView(viewId);
    }

    public ListViewForScrollView getListViewForScrollView(int viewId) {
        return getView(viewId);
    }

    public GridView getGridView(int viewId) {
        return getView(viewId);
    }

    public GridViewForScrollView getGridViewForScrollView(int viewId) {
        return getView(viewId);
    }

    public LinearLayout getLinearLayout(int viewId) {
        return getView(viewId);
    }

    public RelativeLayout getRelativeLayout(int viewId) {
        return getView(viewId);
    }

    public ViewPager getViewPager(int viewId) {
        return getView(viewId);
    }

    public RadioButton getRadioButton(int viewId) {
        return getView(viewId);
    }

    public RadioGroup getRadioGroup(int viewId) {
        return getView(viewId);
    }

    public ScrollView getScrollView(int viewId) {
        return getView(viewId);
    }
}
