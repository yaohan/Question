package com.ssdut411.app.questionanswer.activity.student;

import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.question.LookQuestionActivity;
import com.ssdut411.app.questionanswer.activity.question.QuestionFragment;
import com.ssdut411.app.questionanswer.activity.system.BaseExpandListActivity;
import com.ssdut411.app.questionanswer.activity.system.ViewHolder;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.GetErrorsReq;
import com.ssdut411.app.questionanswer.model.Resp.GetErrorsResp;
import com.ssdut411.app.questionanswer.model.model.ErrorQuestionModel;
import com.ssdut411.app.questionanswer.utils.L;

import java.util.List;
import java.util.Map;

/**
 * Created by yao_han on 2016/4/8.
 */
public class ErrorQuestionActivity extends BaseExpandListActivity {
    private List<String> pointList;
    private Map<String,List<ErrorQuestionModel>> errorQuestionMap;

    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    protected void initChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        startLookQuestionActivity(groupPosition, childPosition);
    }

    @Override
    protected void loadData() {
        getErrors();
    }
    public void getErrors() {
        AppAction action = new AppActionImpl(context);
        GetErrorsReq getErrorsReq = new GetErrorsReq();
        getErrorsReq.setId(MainApplication.getInstance().getUserId());
        action.getErrors(getErrorsReq, new ActionCallbackListener<GetErrorsResp>() {
            @Override
            public void onSuccess(GetErrorsResp data) {
                if (data.getResult() == GetErrorsResp.RESULT_SUCCESS) {
                    pointList = data.getPointList();
                    errorQuestionMap = data.getQuestionList();
                    L.i(errorQuestionMap.toString());
                    if (pointList.size() == 0) {
                        emptyLayout.showEmpty();
                    } else {
                        setList();
                    }
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

    private void setList() {
        MyAdapter<ErrorQuestionModel> adapter = new MyAdapter<ErrorQuestionModel>(context,pointList, R.layout.item_listview,errorQuestionMap,R.layout.item_second_listview) {
            @Override
            public void convertGroup(ViewHolder viewHolder, String s) {
                viewHolder.getTextView(R.id.item_center).setText(s);
            }

            @Override
            protected void convertChild(ViewHolder viewHolder, ErrorQuestionModel errorQuestionModel) {
                viewHolder.getTextView(R.id.item_center).setText(errorQuestionModel.getQuestionModel().getStem());
            }
        };
        lvList.setAdapter(adapter);

    }


    @Override
    protected String initTitle() {
        return getString(R.string.my_error);
    }

    private void startLookQuestionActivity(int groupPosition, int childPosition) {
        Intent intent = new Intent(context,LookQuestionActivity.class);
        intent.putExtra(getString(R.string.string_state), QuestionFragment.STATE_CHECK);
        intent.putExtra(getString(R.string.string_question),errorQuestionMap.get(pointList.get(groupPosition).toString()).get(childPosition).getQuestionModel());
        startActivity(intent);
    }
}
