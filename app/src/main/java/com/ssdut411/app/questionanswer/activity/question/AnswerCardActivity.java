package com.ssdut411.app.questionanswer.activity.question;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;


/**
 * Created by yao_han on 2015/11/28.
 */
public class AnswerCardActivity extends BaseActivity {

    private GridView gvCard;
    private int[] finishs;
    public AnswerCardActivity() {
    }

    @Override
    protected String initTitle() {
        return getString(R.string.answer_card);
    }

    @Override
    protected int initMenu() {
        return R.menu.menu_null;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_answer_card;
    }

    @Override
    protected void initViews() {
        gvCard = getGridView(R.id.gv_answer_gridview);
    }

    @Override
    protected void loadData() {
       finishs = getIntent().getIntArrayExtra("finishs");
    }

    @Override
    protected void showView() {
        setCanBack();
        CardAdapter adapter = new CardAdapter(this,finishs);
        gvCard.setAdapter(adapter);
        gvCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("index", position);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private class CardAdapter extends BaseAdapter{
        private Context context;
        private int[] finishs;

        public CardAdapter(Context context, int[] finishs) {
            this.context = context;
            this.finishs = finishs;
        }

        @Override
        public int getCount() {
            return finishs.length;
        }

        @Override
        public Object getItem(int position) {
            return finishs[position];
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
            if(finishs[position] == 0){
                convertView = layoutInflater.inflate(R.layout.item_answer_card, null);
            }else{
                convertView = layoutInflater.inflate(R.layout.item_answer_card_finish, null);
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
