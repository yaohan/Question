package com.ssdut411.app.questionanswer.activity.question;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.student.NewTestActivity;
import com.ssdut411.app.questionanswer.activity.system.CommonAdapter;
import com.ssdut411.app.questionanswer.activity.system.ViewHolder;
import com.ssdut411.app.questionanswer.model.model.CommentInfo;
import com.ssdut411.app.questionanswer.model.model.ModelConfig;
import com.ssdut411.app.questionanswer.model.model.SelfTestModel;
import com.ssdut411.app.questionanswer.model.model.StudentHomeworkModel;
import com.ssdut411.app.questionanswer.utils.AnimUtils;
import com.ssdut411.app.questionanswer.utils.BitmapUtils;
import com.ssdut411.app.questionanswer.utils.GsonUtils;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.widget.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yao_han on 2016/3/2.
 */
public class EvaluationFragment extends Fragment implements View.OnClickListener {

    private ScrollView svScroll;
    private LinearLayout llGrade;
    private TextView tvGrade;
    private LinearLayout llEvaluation;
    private LinearLayout llEvaluationGone;
    private ImageView ivEvaluationMenu;
    private ListViewForScrollView lvEvaluation;
    private LinearLayout llRank;
    private TextView tvRankMenu;
    private LinearLayout llPoint;
    private ImageView ivPointMenu;
    private ListViewForScrollView lvPoint;

    private boolean closeGrade = false;
    private boolean closeEvaluation = false;
    private boolean closePoint = false;

    private SelfTestModel selfTestModel;
    private String source;
    private static List<CommentInfo> commentInfos;
    CommonAdapter<CommentInfo> adapter;

    static EvaluationFragment newInstance(SelfTestModel selfTestModel, String source) {
        EvaluationFragment fragment = new EvaluationFragment();
        fragment.selfTestModel = selfTestModel;
        fragment.source = source;
        return fragment;
    }

    static EvaluationFragment newInstance1(StudentHomeworkModel studentHomeworkModel, String source) {
        L.i("studentHomeworkModel:"+GsonUtils.gsonToJsonString(studentHomeworkModel));
        EvaluationFragment fragment = new EvaluationFragment();
        fragment.selfTestModel = studentHomeworkModel;
        commentInfos = studentHomeworkModel.getCommentList();
        fragment.source = source;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.i("evaluation fragment oncreate");

        View view = inflater.inflate(R.layout.fragment_evaluation_situation, container, false);
        initView(view);
        showView();
        L.i("evaluation fragment oncreate return");
        return view;
    }


    private void initView(View view) {
        svScroll = (ScrollView) view.findViewById(R.id.sv_scroll);
        llGrade = (LinearLayout) view.findViewById(R.id.ll_report_grade);
        tvGrade = (TextView) view.findViewById(R.id.tv_report_grade);
        llEvaluation = (LinearLayout) view.findViewById(R.id.ll_report_evaluation);
        llEvaluationGone = (LinearLayout) view.findViewById(R.id.ll_report_evaluation_gone);
        ivEvaluationMenu = (ImageView) view.findViewById(R.id.iv_report_evaluation_menu);
        lvEvaluation = (ListViewForScrollView) view.findViewById(R.id.lv_report_evaluation);
        llRank = (LinearLayout) view.findViewById(R.id.ll_report_rank);
        tvRankMenu = (TextView) view.findViewById(R.id.tv_report_rank_menu);
        llPoint = (LinearLayout) view.findViewById(R.id.ll_report_point);
        ivPointMenu = (ImageView) view.findViewById(R.id.iv_report_point_menu);
        lvPoint = (ListViewForScrollView) view.findViewById(R.id.lv_report_point);

        llGrade.setOnClickListener(this);
        llEvaluation.setOnClickListener(this);
        llRank.setOnClickListener(this);
        llPoint.setOnClickListener(this);
    }

    protected void showView() {
        svScroll.smoothScrollTo(0, 0);

        if (source.equals("test")) {
            llEvaluation.setVisibility(View.GONE);
            lvEvaluation.setVisibility(View.GONE);
            llRank.setVisibility(View.GONE);
        } else {
//            setRightText(getString(R.string.reply));
            setEvaluation();
        }
        setGrade();
        setPoint();
    }

    private void setEvaluation() {
        L.i("setEvaluation start");
        commentInfos = ((StudentHomeworkModel)selfTestModel).getCommentList();
        L.i("commentInfos:"+GsonUtils.gsonToJsonString(commentInfos));
        if(commentInfos == null){
            commentInfos = new ArrayList<>();
        }
        adapter = new CommonAdapter<CommentInfo>(getActivity(),commentInfos,R.layout.item_question_evaluation) {
            @Override
            public void convert(ViewHolder viewHolder, CommentInfo commentInfo, int position) {
                if(commentInfo.getRole() == ModelConfig.ROLE_PARENT){
                    viewHolder.getImageView(R.id.iv_report_item_image).setImageResource(R.mipmap.icon_parent);
                }else if(commentInfo.getRole() == ModelConfig.ROLE_TEACHER){
                    viewHolder.getImageView(R.id.iv_report_item_image).setImageResource(R.mipmap.icon_student);
                }
                viewHolder.getTextView(R.id.tv_report_item_role).setText(commentInfo.getName());
                viewHolder.getTextView(R.id.tv_report_item_evaluation).setText(commentInfo.getComment());
            }
        };
        lvEvaluation.setAdapter(adapter);

        L.i("setEvaluation end");
    }

    private void setPoint() {
        final List<String> pointList = selfTestModel.getTestSitesList();
        lvPoint.setAdapter(new CommonAdapter<String>(getActivity(), pointList, R.layout.item_listview) {
            @Override
            public void convert(com.ssdut411.app.questionanswer.activity.system.ViewHolder viewHolder, String s, int position) {
                viewHolder.getTextView(R.id.item_center).setText(s);
            }
        });
        lvPoint.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NewTestActivity.class);
                intent.putExtra("point", pointList.get(position));
                startActivity(intent);
            }
        });
    }

    public void updateEvaluation(List<CommentInfo> list) {
//        L.i("updateEvaluation start");
//        L.i("list:"+GsonUtils.gsonToJsonString(list));
//        L.i("commentInfos = null?" + commentInfos);
//        if(commentInfos == null){
//            commentInfos = new ArrayList<>();
//        }
//        L.i("commonInfo1:"+GsonUtils.gsonToJsonString(commentInfos));
//        commentInfos.removeAll(commentInfos);
//        L.i("commonInfo2:" + GsonUtils.gsonToJsonString(commentInfos));
//        for(CommentInfo commentInfo:list){
//            commentInfos.add(commentInfo);
//        }
////        commentInfos.addAll(list);
//        L.i("commonInfo3:"+GsonUtils.gsonToJsonString(commentInfos));
        if(adapter == null){
            adapter = new CommonAdapter<CommentInfo>(getActivity(),commentInfos,R.layout.item_question_evaluation) {
                @Override
                public void convert(ViewHolder viewHolder, CommentInfo commentInfo, int position) {
                    if(commentInfo.getRole() == ModelConfig.ROLE_PARENT){
                        viewHolder.getImageView(R.id.iv_report_item_image).setImageResource(R.mipmap.icon_parent);
                    }else if(commentInfo.getRole() == ModelConfig.ROLE_TEACHER){
                        viewHolder.getImageView(R.id.iv_report_item_image).setImageResource(R.mipmap.icon_student);
                    }
                    viewHolder.getTextView(R.id.tv_report_item_role).setText(commentInfo.getName());
                    viewHolder.getTextView(R.id.tv_report_item_evaluation).setText(commentInfo.getComment());
                }
            };
            lvEvaluation.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }

        L.i("updateEvaluation end");
    }

    private void setGrade() {
        List<Boolean> list = selfTestModel.getResultList();
        int count = 0;
        for (Boolean b : list) {
            if (b) {
                count++;
            }
        }
        tvGrade.setText((int) ((double) count / list.size() * 100) + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_report_evaluation:
                changeEvaluationState();
                break;
            case R.id.ll_report_rank:
                startClassRankActivity();
                break;
            case R.id.ll_report_point:
                changePointState();
                break;
        }
    }

    private void startClassRankActivity() {
//        Intent intent = new Intent(ReportCardActivity.this, ClassRankActivity.class);
//        intent.putExtra("list", GsonUtils.gsonToJsonString(reportCard.getRank()));
//        startActivity(intent);
    }

    private void changeEvaluationState() {
        if (closeEvaluation) {
            closeEvaluation = false;
            lvEvaluation.setVisibility(View.VISIBLE);
        } else {
            closeEvaluation = true;
            lvEvaluation.setVisibility(View.GONE);
        }
    }


    private void changePointState() {
        if (closePoint) {
            closePoint = false;
            AnimUtils.translateOpenDown(getActivity(), lvPoint);
            AnimUtils.rotateLeft(getActivity(), ivPointMenu);
        } else {
            closePoint = true;
            AnimUtils.translateCloseUp(getActivity(), lvPoint);
            AnimUtils.rotateRight(getActivity(), ivPointMenu);
        }
    }
}
