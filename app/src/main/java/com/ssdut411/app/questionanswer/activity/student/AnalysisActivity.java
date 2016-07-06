package com.ssdut411.app.questionanswer.activity.student;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.activity.system.BaseListViewActivity;
import com.ssdut411.app.questionanswer.activity.system.CommonAdapter;
import com.ssdut411.app.questionanswer.activity.system.ViewHolder;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.GetStudentAnalysisReq;
import com.ssdut411.app.questionanswer.model.Resp.GetStudentAnalysisResp;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.T;
import com.ssdut411.app.questionanswer.widget.EmptyLayout;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yao_han on 2016/5/26.
 */
public class AnalysisActivity extends BaseActivity {
    private ListView listView;
    private CommonAdapter<GetStudentAnalysisResp.AnalysisInfo> adapter;
    String id;
    private EmptyLayout emptyLayout;
    private boolean click = false;

    @Override
    protected String initTitle() {
        return "我的分析";
    }

    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_analysis;
    }

    @Override
    protected void initViews() {
        listView = getListView(R.id.lv_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(click){
                    Intent intent = new Intent(context,NewTestActivity.class);
                    intent.putExtra("point", ((TextView) view.findViewById(R.id.item_left)).getText());
                    startActivity(intent);
                    finish();
                }
            }
        });
        emptyLayout = new EmptyLayout(context,listView);
        emptyLayout.showLoading();
    }

    @Override
    protected void loadData() {
        id = getIntent().getStringExtra("id");
        L.i("id："+id);
        if(id == null){
            click = true;
            id = MainApplication.getInstance().getUserId();
        }
        AppAction action = new AppActionImpl(context);
        GetStudentAnalysisReq getStudentAnalysisReq = new GetStudentAnalysisReq();
        getStudentAnalysisReq.setUserId(id);
        action.getStudentAnalysis(getStudentAnalysisReq, new ActionCallbackListener<GetStudentAnalysisResp>() {
            @Override
            public void onSuccess(GetStudentAnalysisResp data) {
                if (data.getResult() == GetStudentAnalysisResp.RESULT_SUCCESS) {
                    setView(data.getAnalysisInfos());
                } else {
                    T.showShort(context, data.getDesc());
                    emptyLayout.showError();
                }
            }

            @Override
            public void onFailure(String message) {
                T.showShort(context, getString(R.string.error_message));
                emptyLayout.showError();
            }
        });
    }

    @Override
    protected void showView() {
        setCanBack();
    }

    public void setView(List<GetStudentAnalysisResp.AnalysisInfo> list) {
        Collections.sort(list, new Comparator<GetStudentAnalysisResp.AnalysisInfo>() {
            @Override
            public int compare(GetStudentAnalysisResp.AnalysisInfo lhs, GetStudentAnalysisResp.AnalysisInfo rhs) {
                double rate1 = (double) lhs.getRight() / (lhs.getRight() + lhs.getWrong());
                double rate2 = (double) rhs.getRight() / (rhs.getRight() + rhs.getWrong());
                return (int) (rate1 * 100 - rate2 * 100);
            }
        });
        if(list.size() == 0){
            emptyLayout.showEmpty();
        }else{
            adapter = new CommonAdapter<GetStudentAnalysisResp.AnalysisInfo>(context,list,R.layout.item_listview) {
                @Override
                public void convert(ViewHolder viewHolder, GetStudentAnalysisResp.AnalysisInfo analysisInfos, int position) {
                    viewHolder.getTextView(R.id.item_left).setText(analysisInfos.getTestSite());
                    double right = analysisInfos.getRight();
                    double all = analysisInfos.getRight()+analysisInfos.getWrong();
                    viewHolder.getTextView(R.id.item_right).setText(String.format("%.2f",right/all*100)+"%");
                }
            };
            listView.setAdapter(adapter);
        }
    }
}
