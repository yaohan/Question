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
import com.ssdut411.app.questionanswer.model.Req.GetCollectionsReq;
import com.ssdut411.app.questionanswer.model.Resp.GetCollectionsResp;
import com.ssdut411.app.questionanswer.model.Resp.GetErrorsResp;
import com.ssdut411.app.questionanswer.model.model.QuestionModel;
import com.ssdut411.app.questionanswer.utils.GsonUtils;
import com.ssdut411.app.questionanswer.utils.L;

import java.util.List;
import java.util.Map;

/**
 * Created by yao_han on 2016/4/8.
 */
public class CollectQuestionActivity extends BaseExpandListActivity {
    private List<String> pointList;
    private Map<String,List<QuestionModel>> questionMap;
    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    protected void initChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        startLookQuestionActivity(groupPosition,childPosition);
    }

    @Override
    protected void loadData() {
        getCollection();
    }

    @Override
    protected String initTitle() {
        return "我的收藏";
    }

    public void getCollection() {
        AppAction action = new AppActionImpl(context);
        GetCollectionsReq getCollectionsReq= new GetCollectionsReq();
        getCollectionsReq.setId(MainApplication.getInstance().getUserId());
        action.getCollections(getCollectionsReq, new ActionCallbackListener<GetCollectionsResp>() {
            @Override
            public void onSuccess(GetCollectionsResp data) {
                if (data.getResult() == GetErrorsResp.RESULT_SUCCESS) {
                    pointList = data.getPointList();
                    questionMap = data.getQuestionList();
                    L.i(GsonUtils.gsonToJsonString(questionMap));
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
        MyAdapter<QuestionModel> adapter = new MyAdapter<QuestionModel>(context,pointList,R.layout.item_listview,questionMap,R.layout.item_second_listview){

            @Override
            public void convertGroup(ViewHolder viewHolder, String s) {
                viewHolder.getTextView(R.id.item_center).setText(s);
            }

            @Override
            protected void convertChild(ViewHolder viewHolder, QuestionModel questionModel) {
                viewHolder.getTextView(R.id.item_center).setText(questionModel.getStem());
            }
        };
        lvList.setAdapter(adapter);
    }
    private void startLookQuestionActivity(int groupPosition, int childPosition) {
        Intent intent = new Intent(context,LookQuestionActivity.class);
        intent.putExtra(getString(R.string.string_state), QuestionFragment.STATE_CHECK);
        intent.putExtra(getString(R.string.string_question),questionMap.get(pointList.get(groupPosition).toString()).get(childPosition));
        startActivity(intent);
    }
}
