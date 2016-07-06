package com.ssdut411.app.questionanswer.activity.question;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.activity.system.CommonAdapter;
import com.ssdut411.app.questionanswer.activity.system.ViewHolder;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.GetNewTestReq;
import com.ssdut411.app.questionanswer.model.Req.GetTestSitesReq;
import com.ssdut411.app.questionanswer.model.Resp.GetTestSitesResp;
import com.ssdut411.app.questionanswer.model.model.ModelConfig;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.widget.EmptyLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yao_han on 2015/11/28.
 */
public class SelectPointActivity extends BaseActivity implements View.OnClickListener {

    private EmptyLayout emptyLayout;
    private ListView lvList;
    private List<String> list;
    private LinearLayout llRight;
    private RelativeLayout rlRandom;
    private RelativeLayout rlError;
    private RelativeLayout rlPoint;
    private List<String> selectList;
    private List<Boolean> checkBoxState;
    private RadioGroup rgSelect;
    private RadioButton rbRandom;
    private RadioButton rbError;
    private RadioButton rbPoint;
    private boolean flagPoint = false;
    private List<Map<String, Object>> sourceList;
    CommonAdapter<Map<String, Object>> adapter;

    @Override
    protected String initTitle() {
        return "选择知识点";
    }

    @Override
    protected int initMenu() {
        return R.menu.menu_sure;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_select_point;
    }

    @Override
    protected void initViews() {
        lvList = getListView(R.id.lv_second_list);
//        llRight = getLinearLayout(R.id.ll_title_right);
        rlRandom = getRelativeLayout(R.id.rl_select_random);
        rlError = getRelativeLayout(R.id.rl_select_error);
        rlPoint = getRelativeLayout(R.id.rl_select_point);
        rgSelect = getRadioGroup(R.id.rg_select);
        rbRandom = getRadioButton(R.id.rb_select_random);
        rbError = getRadioButton(R.id.rb_select_error);
        rbPoint = getRadioButton(R.id.rb_select_point);

//        llRight.setOnClickListener(this);
        rlRandom.setOnClickListener(this);
        rlError.setOnClickListener(this);
        rlPoint.setOnClickListener(this);

        emptyLayout = new EmptyLayout(this, lvList);
        emptyLayout.setErrorButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        emptyLayout.showLoading();

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.actions_new:
                        result();
                }
                return true;
            }
        });
    }

    @Override
    protected void loadData() {
        AppAction action = new AppActionImpl(this);
        action.getTestSites(new GetTestSitesReq(), new ActionCallbackListener<GetTestSitesResp>() {

            @Override
            public void onSuccess(GetTestSitesResp data) {
                list = data.getTestSites();
                setList(list);
            }

            @Override
            public void onFailure(String message) {

            }
        });

    }

    private void setList(List<String> list) {

        checkBoxState = new ArrayList<Boolean>();
        sourceList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("point", list.get(i));
            map.put("state", false);
            sourceList.add(map);
        }
        adapter = new CommonAdapter<Map<String, Object>>(this, sourceList, R.layout.item_listview) {
            @Override
            public void convert(ViewHolder viewHolder, Map<String, Object> stringObjectMap, int position) {
                viewHolder.getTextView(R.id.item_center).setText(stringObjectMap.get("point").toString());
                viewHolder.getCheckBox(R.id.cb_left).setVisibility(View.VISIBLE);
                L.i((Boolean) stringObjectMap.get("state") + "");
                viewHolder.getCheckBox(R.id.cb_left).setChecked((Boolean) stringObjectMap.get("state"));
            }
        };
        lvList.setAdapter(adapter);
        lvList.setVisibility(View.GONE);
    }

    @Override
    protected void showView() {
        if (getIntent().getStringExtra("title") != null) {
            list.clear();
            emptyLayout.showEmpty();
            setTitle(getIntent().getStringExtra("title"));
        } else {
            setTitle("选择知识点");
        }
//        setRightText("确定");
        setCanBack();

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rbPoint.setChecked(true);
                Map<String, Object> map = sourceList.get(position);
                if ((Boolean) map.get("state")) {
                    sourceList.get(position).put("state", false);
                } else {
                    sourceList.get(position).put("state", true);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.ll_title_right:
//                result();
//                break;
            case R.id.rl_select_random:
                random();
                break;
            case R.id.rl_select_error:
                error();
                break;
            case R.id.rl_select_point:
                point();
                break;
        }
    }

    private void point() {
        rbPoint.setChecked(true);
        lvList.setVisibility(View.VISIBLE);
    }

    private void error() {
        for (int i = 0; i < sourceList.size(); i++) {
            sourceList.get(i).put("state", false);
        }
        adapter.notifyDataSetChanged();
        rbError.setChecked(true);
        lvList.setVisibility(View.GONE);
    }

    private void random() {
        for (int i = 0; i < sourceList.size(); i++) {
            sourceList.get(i).put("state", false);
        }
        adapter.notifyDataSetChanged();
        rbRandom.setChecked(true);
        lvList.setVisibility(View.GONE);
    }

    private void result() {
        L.i("result");
        Intent intent = new Intent();
        ArrayList<String> emptyList = new ArrayList<String>();
        if (rbRandom.isChecked()) {
            L.i("random");
            intent.putStringArrayListExtra("point", emptyList);
            intent.putExtra("select", ModelConfig.SELECT_RANDOM);
            intent.putExtra("selectString", getString(R.string.select_random));
        } else if (rbError.isChecked()) {
            L.i("error");
            intent.putStringArrayListExtra("point", emptyList);
            intent.putExtra("select", ModelConfig.SELECT_RANDOM);
            intent.putExtra("selectString", getString(R.string.select_error));
        } else {
            L.i("point");
            ArrayList<String> list = new ArrayList<String>();
            Boolean first = true;
            StringBuffer strSelect = new StringBuffer();
            for (int i = 0; i < sourceList.size(); i++) {
                Boolean state = (Boolean) sourceList.get(i).get("state");
                L.i("state:"+state);
                list.add(sourceList.get(i).get("point").toString());
                if (state) {
                    if (first) {
                        strSelect.append(sourceList.get(i).get("point").toString());
                        first = false;
                    } else {
                        strSelect.append(",");
                        strSelect.append(sourceList.get(i).get("point").toString());
                    }
                }
            }
            L.i("str:"+strSelect);
            intent.putStringArrayListExtra("point", list);
            intent.putExtra("select", ModelConfig.SELECT_RANDOM);
            intent.putExtra("selectString", strSelect.toString());
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
