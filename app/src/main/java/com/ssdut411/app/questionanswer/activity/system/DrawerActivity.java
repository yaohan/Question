package com.ssdut411.app.questionanswer.activity.system;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.ImageView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.person.FeedbackActivity;
import com.ssdut411.app.questionanswer.activity.person.InfoActivity;
import com.ssdut411.app.questionanswer.activity.person.LoginActivity;
import com.ssdut411.app.questionanswer.activity.person.SelectRoleActivity;
import com.ssdut411.app.questionanswer.activity.person.SetActivity;
import com.ssdut411.app.questionanswer.activity.person.ShowInfoActivity;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.model.model.ModelConfig;
import com.ssdut411.app.questionanswer.model.model.UserModel;
import com.ssdut411.app.questionanswer.utils.BitmapUtils;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.SPUtils;
import com.ssdut411.app.questionanswer.utils.T;


/**
 * 带侧边栏的基类
 * <p/>
 * Created by yao_han on 2016/3/29.
 */
public abstract class DrawerActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    /**
     * 修改info信息
     */
    @Override
    protected void onResume() {
        super.onResume();
        UserModel userModel = (UserModel)getIntent().getSerializableExtra("model");
        if(userModel == null){
            userModel = MainApplication.getInstance().getUser();
        }else{
            L.i("get from login");
        }
        L.i("userModel = null?"+userModel);
        if (userModel != null) {
            getTextView(R.id.tv_drawer_name).setText(MainApplication.getInstance().getUser().getRealName());
            getTextView(R.id.tv_drawer_class).setText(MainApplication.getInstance().getUser().getClassName());
            BitmapUtils.setSelfHead(context, getImageView(R.id.iv_head));
            if(userModel.getRole() == ModelConfig.ROLE_PARENT){
                getTextView(R.id.tv_drawer_class).setText("孩子："+MainApplication.getInstance().getUser().getChildName());
            }
        }

        if (MainApplication.getInstance().getLogin()) {
            getLinearLayout(R.id.ll_menu_info).setVisibility(View.VISIBLE);
            getTextView(R.id.tv_menu_register_login).setVisibility(View.GONE);
        } else {
            getLinearLayout(R.id.ll_menu_info).setVisibility(View.GONE);
            getTextView(R.id.tv_menu_register_login).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected abstract int initMenu();

    @Override
    protected abstract int initContentView();

    @Override
    protected void initViews() {
        //个人信息
        getLinearLayout(R.id.ll_menu_person).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartInfoActivity();
            }
        });
        //切换角色
        getLinearLayout(R.id.ll_menu_role).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartChangeRoleActivity();
            }
        });
        //意见反馈
        getLinearLayout(R.id.ll_menu_feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, FeedbackActivity.class));
            }
        });
        //关于我们
        getLinearLayout(R.id.ll_menu_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(context, getString(R.string.about_us));
            }
        });
        getLinearLayout(R.id.ll_menu_about).setVisibility(View.GONE);
        //个人设置
        getLinearLayout(R.id.ll_menu_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSetActivity();
            }
        });
        //切换主题
        getLinearLayout(R.id.ll_menu_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheme();
            }
        });
        getLinearLayout(R.id.ll_menu_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCache();
            }
        });
    }

    @Override
    protected abstract void loadData();

    @Override
    protected void showView() {
        getLinearLayout(R.id.ll_menu_role).setVisibility(View.GONE);
        setDrawer();
    }

    private void setDrawer() {
        //设置左上角图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //设置侧边栏
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void changeTheme() {
        SPUtils.put(context, "theme", !theme);
        Intent intent = getIntent();
        /**
         * http://www.cnblogs.com/lwbqqyumidi/p/3775479.html
         * FLAG_ACTIVITY_NEW_TASK：根据Activity Affinity判断是否需要创建新的Task，然后再创建新的Activit实例放进去。
         * FLAG_ACTIVITY_CLEAR_TOP：任何用来放置该activity的已经存在的task里面的已经存在的activity先清空，然后该activity再在该task中启动，也就是说，这个新启动的activity变为了这个空tas的根activity.所有老的activity都结束掉。该标志必须和FLAG_ACTIVITY_NEW_TASK一起使用。
         */
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void startSetActivity() {
        if(!MainApplication.getInstance().getLogin()){
            T.showShort(context,"尚未登录");
        }else{
            Intent intent = new Intent(context, SetActivity.class);
            startActivity(intent);
        }
    }

    private void StartInfoActivity() {
        if (MainApplication.getInstance().getLogin()) {
            Intent intent = new Intent(context, ShowInfoActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void StartChangeRoleActivity() {
        Intent intent = new Intent(context, SelectRoleActivity.class);
        startActivity(intent);
    }

    private void clearCache() {
        SPUtils.clear(context);
        MainApplication.getInstance().clear();
        startActivity(new Intent(context, MainActivity.class));
        finish();
    }
}
