package com.ssdut411.app.questionanswer.activity.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.question.LookQuestionActivity;
import com.ssdut411.app.questionanswer.activity.question.QuestionFragment;
import com.ssdut411.app.questionanswer.model.model.QuestionModel;
import com.ssdut411.app.questionanswer.model.model.SelfTestModel;
import com.ssdut411.app.questionanswer.model.model.StudentHomeworkModel;
import com.ssdut411.app.questionanswer.utils.GsonUtils;
import com.ssdut411.app.questionanswer.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yao_han on 2016/5/29.
 */
public class QuestionListFragment extends Fragment {

    private GridView gvGrade;
    private List<StudentHomeworkModel> studentHomeworkModels;
    private StudentHomeworkModel studentHomeworkModel;
    public static QuestionListFragment newInstance(List<StudentHomeworkModel> studentHomeworkModels){
        QuestionListFragment fragment = new QuestionListFragment();
        fragment.studentHomeworkModels = studentHomeworkModels;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_detail,container,false);
        GridView gvGrade = (GridView) view.findViewById(R.id.gv_topic_gridview);
        studentHomeworkModel = studentHomeworkModels.get(0);
        gvGrade.setAdapter(new CardAdapter(getActivity(), studentHomeworkModel.getResultList()));
        gvGrade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startQuestionActivity(position);
            }
        });
        return view;
    }
    private void startQuestionActivity(int position) {
        QuestionModel question = studentHomeworkModel.getQuestionList().get(position);
        L.i("myAnswer:" + question.getMyAnswer());
        Intent intent = new Intent(getActivity(), QuestionDetailActivity.class);
        intent.putExtra(getString(R.string.string_state), QuestionFragment.STATE_TEACHER);
        intent.putExtra("detail",getDetail(position));
        intent.putExtra("question", question);
        startActivity(intent);
    }

    public String getDetail(int position) {
        int optionsA = 0,optionsB = 0,optionsC = 0,optionsD = 0,sum = 0;
        for(StudentHomeworkModel studentHomeworkModel:studentHomeworkModels){
            if(studentHomeworkModel !=null){
                sum++;
                String option = studentHomeworkModel.getAnswerList().get(position);
                if(option.equals("A")){
                    optionsA++;
                }else if(option.equals("B")){
                    optionsB++;
                }else if(option.equals("C")){
                    optionsC++;
                }else if(option.equals("D")){
                    optionsD++;
                }
            }
        }
        List<Integer> list = new ArrayList<>();
        list.add(sum);
        list.add(optionsA);
        list.add(optionsB);
        list.add(optionsC);
        list.add(optionsD);
        return GsonUtils.gsonToJsonString(list);
    }

    private class CardAdapter extends BaseAdapter {
        private Context context;
        private List<Boolean> question;

        public CardAdapter(Context context, List<Boolean> question) {
            this.context = context;
            this.question = question;
        }

        @Override
        public int getCount() {
            return question.size();
        }

        @Override
        public Object getItem(int position) {
            return question.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
//            if(question.get(position)){
//                convertView = layoutInflater.inflate(R.layout.item_question_true, null);
//            }else{
                convertView = layoutInflater.inflate(R.layout.item_question_true, null);
//            }
            viewHolder.text = (TextView)convertView.findViewById(R.id.tv_answer_text);
            viewHolder.text.setText(position+1+"");
            return convertView;
        }
    }
    private class ViewHolder{
        private TextView text;
    }
}
