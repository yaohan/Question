package com.ssdut411.app.questionanswer.activity.teacher;

import android.content.Intent;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.question.CheckQuestionActivity;
import com.ssdut411.app.questionanswer.activity.question.QuestionActivity;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.activity.system.CommonAdapter;
import com.ssdut411.app.questionanswer.activity.system.ViewHolder;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.GetNewTestReq;
import com.ssdut411.app.questionanswer.model.Req.GetTestSitesReq;
import com.ssdut411.app.questionanswer.model.Req.PostHomeworkReq;
import com.ssdut411.app.questionanswer.model.Resp.GetNewTestResp;
import com.ssdut411.app.questionanswer.model.Resp.GetTestSitesResp;
import com.ssdut411.app.questionanswer.model.Resp.PostHomeworkResp;
import com.ssdut411.app.questionanswer.model.model.ModelConfig;
import com.ssdut411.app.questionanswer.utils.AnimUtils;
import com.ssdut411.app.questionanswer.utils.KeyBoardUtils;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.T;
import com.ssdut411.app.questionanswer.widget.EmptyLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yao_han on 2015/11/28.
 */
public class PostHomeworkActivity extends BaseActivity {
    private RelativeLayout rlName;                      //自测名称layout
    private LinearLayout llSelect;                      //选择知识点layout
    private EditText etSelect, etName, etCount;       //输出框
    private ImageView ivArrow;                          //显示知识点的箭头
    private ListView lvList;                            //知识点ListView

    private EmptyLayout emptyLayout;
    private List<String> pointListString;               //获得的知识点list
    private boolean closeSelect = true;                //选择知识点layout的开关
    private Boolean closePoint = true;                  //知识点listView的开关
    private List<Map<String, Object>> sourceList;        //知识点与选中状态
    private CommonAdapter<Map<String, Object>> adapter;
    private double y1, y2;

    private int select = ModelConfig.SELECT_RANDOM;   //选择知识点类别
    private List<String> selectList = new ArrayList<String>();//选择的知识点list

    @Override
    protected void onResume() {
        super.onResume();
        KeyBoardUtils.openKeyboard(etName, context);
    }

    @Override
    protected String initTitle() {
        return getString(R.string.post_homework);
    }

    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_post_homework;
    }

    @Override
    protected void initViews() {
        rlName = getRelativeLayout(R.id.rl_post_name);
        etSelect = getEditText(R.id.et_post_select);
        etName = getEditText(R.id.et_post_name);
        etCount = getEditText(R.id.et_post_count);
        lvList = getListView(R.id.lv_second_list);
        ivArrow = getImageView(R.id.iv_arrow_down);
        llSelect = getLinearLayout(R.id.ll_select_point);

        getRelativeLayout(R.id.rl_post_point).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (closeSelect) {
                    openSelectLayout();
                } else {
                    closeSelectLayout();
                }
            }
        });

        etSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (closeSelect) {
                    openSelectLayout();
                } else {
                    closeSelectLayout();
                }
            }
        });
        getButton(R.id.bt_post_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkMessage()){
                    startQuestionActivity();
                }
            }
        });

        getRelativeLayout(R.id.rl_select_random).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                random();
            }
        });
        getRelativeLayout(R.id.rl_select_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error();
            }
        });;
        getRelativeLayout(R.id.rl_select_point).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point();
            }
        });;
        getRelativeLayout(R.id.rl_select_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelect()) {
                    closeSelectLayout();
                }
            }
        });
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = sourceList.get(position);
                if ((Boolean) map.get(getString(R.string.string_state))) {
                    sourceList.get(position).put(getString(R.string.string_state), false);
                } else {
                    sourceList.get(position).put(getString(R.string.string_state), true);
                }
                adapter.notifyDataSetChanged();
                etSelect.setText(getSelectPoint());
            }
        });

        emptyLayout = new EmptyLayout(this, lvList);
        emptyLayout.setErrorButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        emptyLayout.showLoading();
    }


    @Override
    protected void loadData() {
        String point = getIntent().getStringExtra(getString(R.string.select_point));
        if (point != null) {
            select = ModelConfig.SELECT_NULL;
            selectList.add(point);
            etSelect.setText(point);
        }else{
            etSelect.setText(getString(R.string.select_random));
        }
        AppAction action = new AppActionImpl(this);
        action.getTestSites(new GetTestSitesReq(), new ActionCallbackListener<GetTestSitesResp>() {

            @Override
            public void onSuccess(GetTestSitesResp data) {
                pointListString = data.getTestSites();
                if (pointListString != null && pointListString.size() != 0) {
                    setList(pointListString);
                } else {
                    emptyLayout.showError();
                }
            }

            @Override
            public void onFailure(String message) {
                emptyLayout.showError();
            }
        });
    }

    @Override
    protected void showView() {
        KeyBoardUtils.openKeyboard(etName, context);
        setBack();
        setDefaultName();
        setDefaultAnim();
    }

    private void setDefaultAnim() {
        Animation translateAnimation = AnimationUtils.loadAnimation(context, R.anim.translate_close_up);
        translateAnimation.setDuration(0);
        translateAnimation.setFillAfter(true);
        lvList.startAnimation(translateAnimation);
    }

    private void setDefaultName() {
        etName.setText(new SimpleDateFormat(getString(R.string.data_format)).format(new Date()) + getString(R.string.homework));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!closeSelect) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                y1 = event.getY();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                y2 = event.getY();
                if (y2 - y1 > 50) {
                    closeSelectLayout();
                }
            }
        }

        return false;
    }

    private boolean checkSelect() {
        if (etSelect.getText().toString().equals(getString(R.string.empty))) {
            T.showShort(context, getString(R.string.please_select_point));
            return false;
        }
        return true;
    }

    private boolean checkMessage() {
        if (etCount.getText().toString().equals(getString(R.string.empty))) {
            T.showShort(context, getString(R.string.please_input_question_number));
            return false;
        } else if (etName.getText().toString().equals(getString(R.string.empty))) {
            T.showShort(context, getString(R.string.question_name_cannot_be_empty));
            return false;
        }
        return true;
    }
    private void setList(List<String> list) {
        sourceList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(getString(R.string.string_point), list.get(i));
            map.put(getString(R.string.string_state), false);
            sourceList.add(map);
        }
        adapter = new CommonAdapter<Map<String, Object>>(this, sourceList, R.layout.item_listview) {
            @Override
            public void convert(ViewHolder viewHolder, Map<String, Object> stringObjectMap, int position) {
                viewHolder.getTextView(R.id.item_center).setText(stringObjectMap.get(getString(R.string.string_point)).toString());
                viewHolder.getCheckBox(R.id.cb_left).setVisibility(View.VISIBLE);
                viewHolder.getCheckBox(R.id.cb_left).setChecked((Boolean) stringObjectMap.get(getString(R.string.string_state)));
            }
        };
        lvList.setAdapter(adapter);
    }

    protected void setBack() {
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                KeyBoardUtils.closeKeyboard(etName, context);
            }
        });

    }

    private void closeSelectLayout() {
        closeSelect = true;
        rlName.setVisibility(View.VISIBLE);
        AnimUtils.translateCloseDown(context, llSelect);
        KeyBoardUtils.openKeyboard(etName, context);
    }

    private void openSelectLayout() {
        closeSelect = false;
        rlName.setVisibility(View.GONE);
        AnimUtils.translateOpenUp(context, llSelect);
        KeyBoardUtils.closeKeyboard(etName, context);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                closePoint = false;
                AnimUtils.translateOpenDown(context, lvList);
                AnimUtils.rotateLeft(context, ivArrow);
            }
        }, 300);
    }

    private void startQuestionActivity() {
        AppAction action = new AppActionImpl(this);

        final PostHomeworkReq postHomeworkReq = new PostHomeworkReq();
        postHomeworkReq.setId(MainApplication.getInstance().getUserId());
        postHomeworkReq.setHomeworkName(etName.getText().toString());
        postHomeworkReq.setPointType(select);
        postHomeworkReq.setPointList(selectList);
        postHomeworkReq.setNumber(Integer.parseInt(etCount.getText().toString()));
        postHomeworkReq.setNewTime(new Date());

        action.postHomework(postHomeworkReq, new ActionCallbackListener<PostHomeworkResp>() {

            @Override
            public void onSuccess(PostHomeworkResp data) {
                if (data.getResult() == GetNewTestResp.RESULT_SUCCESS) {
                    KeyBoardUtils.closeKeyboard(etName, context);
                    Intent intent = new Intent(context, CheckQuestionActivity.class);
                    intent.putExtra(getString(R.string.string_data), data.getHomeworkModel());
                    intent.putExtra(getString(R.string.string_title), postHomeworkReq.getHomeworkName());
                    startActivity(intent);
                } else {
                    T.showShort(context, data.getDesc());
                }
            }

            @Override
            public void onFailure(String message) {
                T.showShort(context, getString(R.string.error_message));
                L.i("message = " + message);
            }
        });
    }

    private void point() {
        etSelect.setText(getSelectPoint());
        if (closePoint) {
            closePoint = false;
            AnimUtils.translateOpenDown(context, lvList);
            AnimUtils.rotateLeft(context, ivArrow);
        }
    }

    private void error() {
        etSelect.setText(getString(R.string.select_error));
        resetSelect();
        if (!closePoint) {
            closePoint = true;
            AnimUtils.translateCloseUp(context, lvList);
            AnimUtils.rotateRight(context, ivArrow);
        }
    }


    private void random() {
        etSelect.setText(getString(R.string.select_random));
        resetSelect();
        if (!closePoint) {
            closePoint = true;
            AnimUtils.translateCloseUp(context, lvList);
            AnimUtils.rotateRight(context, ivArrow);
        }
    }

    private void resetSelect() {
        for (int i = 0; i < sourceList.size(); i++) {
            sourceList.get(i).put(getString(R.string.string_state), false);
        }
        adapter.notifyDataSetChanged();
    }

    public String getSelectPoint() {
        Boolean first = true;
        StringBuffer strSelect = new StringBuffer();
        for (int i = 0; i < sourceList.size(); i++) {
            Boolean state = (Boolean) sourceList.get(i).get(getString(R.string.string_state));
            pointListString.add(sourceList.get(i).get(getString(R.string.string_point)).toString());
            if (state) {
                if (first) {
                    strSelect.append(sourceList.get(i).get(getString(R.string.string_point)).toString());
                    first = false;
                } else {
                    strSelect.append(getString(R.string.comma));
                    strSelect.append(sourceList.get(i).get(getString(R.string.string_point)).toString());
                }
            }
        }
        return strSelect.toString();
    }
}
