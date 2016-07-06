package com.ssdut411.app.questionanswer.activity.question;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.model.model.QuestionModel;
import com.ssdut411.app.questionanswer.model.model.SelfTestModel;
import com.ssdut411.app.questionanswer.utils.L;

import java.util.List;

/**
 * Created by yao_han on 2016/3/2.
 */
public class TopicDetailFragment extends Fragment {

    private GridView gvGrade;
    private SelfTestModel selfTestModel;
    public static TopicDetailFragment newInstance(SelfTestModel selfTestModel){
        TopicDetailFragment fragment = new TopicDetailFragment();
        fragment.selfTestModel = selfTestModel;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_detail,container,false);
        GridView gvGrade = (GridView) view.findViewById(R.id.gv_topic_gridview);
        gvGrade.setAdapter(new CardAdapter(getActivity(), selfTestModel.getResultList()));
        gvGrade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startQuestionActivity(position);
            }
        });
        return view;
    }
    private void startQuestionActivity(int position) {
        QuestionModel question = selfTestModel.getQuestionList().get(position);
        L.i("myAnswer:" + question.getMyAnswer());
        Intent intent = new Intent(getActivity(), LookQuestionActivity.class);
        intent.putExtra(getString(R.string.string_state),QuestionFragment.STATE_LOOK);
        intent.putExtra("question", question);
        startActivity(intent);
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
            if(question.get(position)){
                convertView = layoutInflater.inflate(R.layout.item_question_true, null);
            }else{
                convertView = layoutInflater.inflate(R.layout.item_question_false, null);
            }
            viewHolder.text = (TextView)convertView.findViewById(R.id.tv_answer_text);
            viewHolder.text.setText(position+1+"");
            return convertView;
        }
    }
    private class ViewHolder{
        private TextView text;
    }
}
