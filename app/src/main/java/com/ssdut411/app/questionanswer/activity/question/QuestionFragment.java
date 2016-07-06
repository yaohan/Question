package com.ssdut411.app.questionanswer.activity.question;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.system.CommonAdapter;
import com.ssdut411.app.questionanswer.activity.system.ViewHolder;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.model.model.QuestionModel;
import com.ssdut411.app.questionanswer.utils.AnimUtils;
import com.ssdut411.app.questionanswer.utils.GsonUtils;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.widget.ListViewForScrollView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yao_han on 2015/11/29.
 */
public class QuestionFragment extends Fragment {

    public static int STATE_TEST = 0; //做题模式
    public static int STATE_LOOK = 1; //查看模式
    public static int STATE_CHECK = 2; //选题模式
    public static int STATE_TEACHER = 3;//老师批阅

    private QuestionModel question;
    private TextView tvStem;
    private ListViewForScrollView lvOptions;
    private LinearLayout llAnalytical;
    private TextView tvStandAnswer;
    private TextView tvPoint;
    private TextView tvAnalytical;

    private boolean isShow = false;

    private int fragmentIndex;
    private Handler handler;
    private int state;
    private Boolean[] options;
    private CommonAdapter<String> adapter;
    private String detail;

    public QuestionFragment() {
    }


    static QuestionFragment newInstance(QuestionModel question, int fragmentIndex, Handler handler, int state){
        QuestionFragment fragment = new QuestionFragment();
        fragment.question = question;
        fragment.fragmentIndex = fragmentIndex;
        fragment.handler = handler;
        fragment.state = state;
        return fragment;
    }
    static QuestionFragment newInstance(QuestionModel question, int state){
        QuestionFragment fragment = new QuestionFragment();
        fragment.question = question;
        fragment.state = state;
        return fragment;
    }

    public static QuestionFragment newInstance(QuestionModel question,String detail, int state){
        QuestionFragment fragment = new QuestionFragment();
        fragment.question = question;
        fragment.detail = detail;
        fragment.state = state;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question,container,false);
        initView(view);
        showView();
        L.i("state:" + state);
        if(state == STATE_TEST){
            test();
        }else if(state == STATE_LOOK){
            look();
        }else if(state == STATE_CHECK){
            check();
        }else{
            teacher();
        }
        return view;
    }



    private void showView() {
        tvStem.setText((fragmentIndex + 1) + ". " + question.getStem());
        int size = question.getOptions().size();
        options = new Boolean[size];
        resetOptions();
        if(question.getMyAnswer()!=null && !question.getMyAnswer().equals("")){
            options[question.getMyAnswer().hashCode()-65] = true;
        }else{
            L.i("myAnswer is null");
        }
        adapter = new CommonAdapter<String>(getActivity(), question.getOptions(), R.layout.item_options) {
            @Override
            public void convert(ViewHolder viewHolder, String s, int position) {
                viewHolder.getTextView(R.id.tv_options_left).setText((char) (position + 65) + "");
                viewHolder.getTextView(R.id.tv_options_center).setText(s);
                viewHolder.getRadioButton(R.id.rb_options_right).setChecked(options[position]);
            }
        };
        lvOptions.setAdapter(adapter);
        tvStandAnswer.setText(question.getAnswer());
        tvPoint.setText(ListToString(question.getTestSites()));
        tvAnalytical.setText(question.getResolve());
    }

    private void resetOptions() {
        int size = question.getOptions().size();
        for(int i=0;i<size;i++){
            options[i] = false;
        }
    }

    private String ListToString(List<String> stringList){
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }
    private void initView(View view) {
        tvStem = (TextView)view.findViewById(R.id.tv_fragment_stem);
        lvOptions = (ListViewForScrollView)view.findViewById(R.id.lv_fragment_options);
        llAnalytical = (LinearLayout)view.findViewById(R.id.ll_fragment_analytical);
        tvStandAnswer = (TextView)view.findViewById(R.id.tv_fragment_stand_answer);
        tvPoint = (TextView)view.findViewById(R.id.tv_fragment_point);
        tvAnalytical = (TextView)view.findViewById(R.id.tv_fragment_analytical);
//        if(MainApplication.getInstance().getTheTheme()){
////            lvOptions.setBackgroundResource(R.mipmap.options_box);
//        }else{
//            lvOptions.setBackgroundResource(R.color.background_night);
//        }
    }

    private void check() {
        AnimUtils.translateOpenUp(getActivity(), llAnalytical);
        adapter = new CommonAdapter<String>(getActivity(), question.getOptions(), R.layout.item_options) {
            @Override
            public void convert(ViewHolder viewHolder, String s, int position) {
                viewHolder.getTextView(R.id.tv_options_left).setText((char) (position + 65) + "");
                viewHolder.getTextView(R.id.tv_options_center).setText(s);
                viewHolder.getRadioButton(R.id.rb_options_right).setVisibility(View.INVISIBLE);
            }
        };
        lvOptions.setAdapter(adapter);
    }
    private void teacher() {
        AnimUtils.translateOpenUp(getActivity(), llAnalytical);
        final List<Integer> list = GsonUtils.gsonToList(detail, Integer.class);
        resetOptions();
        adapter = new CommonAdapter<String>(getActivity(), question.getOptions(), R.layout.item_options) {
            @Override
            public void convert(ViewHolder viewHolder, String s, int position) {
                viewHolder.getTextView(R.id.tv_options_left).setText((char) (position + 65) + "");
                viewHolder.getTextView(R.id.tv_options_center).setText(s);
                double sum = list.get(0).intValue();
                viewHolder.getTextView(R.id.tv_options_right).setText(new DecimalFormat("#%").format(list.get(position+1)/sum).toString());
                viewHolder.getRadioButton(R.id.rb_options_right).setVisibility(View.INVISIBLE);
            }
        };
        lvOptions.setAdapter(adapter);
    }


    private void look() {
        tvStem.setText(question.getStem());
        AnimUtils.translateOpenUp(getActivity(), llAnalytical);
    }

    private void test() {
        llAnalytical.setVisibility(View.GONE);
        lvOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resetOptions();
                options[position] = true;
                adapter.notifyDataSetChanged();
                Message message = new Message();
                message.what = 2;
                message.arg1 = fragmentIndex;
                String options = "";
                switch (position){
                    case 0: options = "A"; break;
                    case 1: options = "B"; break;
                    case 2: options = "C"; break;
                    case 3: options = "D"; break;
                }
                message.obj = options;
                handler.sendMessage(message);
            }
        });
    }
}
