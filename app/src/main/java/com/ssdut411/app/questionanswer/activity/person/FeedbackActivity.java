package com.ssdut411.app.questionanswer.activity.person;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.utils.KeyBoardUtils;
import com.ssdut411.app.questionanswer.utils.T;

/**
 * Created by yao_han on 2016/6/1.
 */
public class FeedbackActivity extends BaseActivity {
    @Override
    protected String initTitle() {
        return "意见反馈";
    }

    @Override
    protected int initMenu() {
        return R.menu.menu_sure;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initViews() {
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(getEditText(R.id.et_feedback).getText()==null || getEditText(R.id.et_feedback).getText().length() == 0){
                    T.showShort(context,"意见不能为空");
                }else{
                    T.showShort(context,"谢谢您的意见！");
                    KeyBoardUtils.closeKeyboard(getEditText(R.id.et_feedback), context);
                    finish();
                }
                return true;
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void showView() {
        setCanBack();
        KeyBoardUtils.openKeyboard(getEditText(R.id.et_feedback), context);
    }

    protected void setCanBack() {
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.closeKeyboard(getEditText(R.id.et_feedback), context);
                finish();
            }
        });
    }
}
