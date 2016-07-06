package com.ssdut411.app.questionanswer.activity.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.question.ReportCardActivity;
import com.ssdut411.app.questionanswer.activity.system.CommonAdapter;
import com.ssdut411.app.questionanswer.activity.system.ViewHolder;
import com.ssdut411.app.questionanswer.model.model.StudentHomeworkModel;
import com.ssdut411.app.questionanswer.utils.GsonUtils;
import com.ssdut411.app.questionanswer.utils.L;

import java.util.List;

/**
 * Created by yao_han on 2016/5/29.
 */
public class ClassListFragment extends Fragment {
    private List<StudentHomeworkModel> studentHomeworkModels;

    public static ClassListFragment newInstance(List<StudentHomeworkModel> studentHomeworkModels){
        L.i("class new instance"+ GsonUtils.gsonToJsonString(studentHomeworkModels));
        ClassListFragment classListFragment = new ClassListFragment();
        classListFragment.studentHomeworkModels = studentHomeworkModels;
        return classListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_classlist,null);
        TextView textView = (TextView)view.findViewById(R.id.tv_text);
        ListView list = (ListView)view.findViewById(R.id.lv_list);
        double sum = 0;
        int count = 0;
        for(StudentHomeworkModel studentHomeworkModel:studentHomeworkModels){
            if(studentHomeworkModel !=null){
                sum+=studentHomeworkModel.getGrade();
                count++;
            }
        }
        textView.setText("班级平均分"+sum/count);
        CommonAdapter<StudentHomeworkModel> adapter = new CommonAdapter<StudentHomeworkModel>(getActivity(),studentHomeworkModels,R.layout.item_listview) {
            @Override
            public void convert(ViewHolder viewHolder, StudentHomeworkModel studentHomeworkModel, int position) {
                viewHolder.getTextView(R.id.item_left).setText(studentHomeworkModel.getStudentName());
                L.i("grade："+studentHomeworkModel.getGrade());
                viewHolder.getTextView(R.id.item_right).setText(studentHomeworkModel.getGrade()+"");
            }
        };
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ReportCardActivity.class);
                intent.putExtra("source","homework");
                intent.putExtra("data",studentHomeworkModels.get(position));
                startActivity(intent);
            }
        });
        return view;
    }
}
