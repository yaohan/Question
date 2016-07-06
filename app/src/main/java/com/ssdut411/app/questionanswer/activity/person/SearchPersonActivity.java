package com.ssdut411.app.questionanswer.activity.person;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.activity.system.CommonAdapter;
import com.ssdut411.app.questionanswer.activity.system.ViewHolder;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.SearchPersonReq;
import com.ssdut411.app.questionanswer.model.Resp.SearchPersonResp;
import com.ssdut411.app.questionanswer.model.model.UserModel;
import com.ssdut411.app.questionanswer.utils.BitmapUtils;
import com.ssdut411.app.questionanswer.utils.GsonUtils;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.T;
import com.ssdut411.app.questionanswer.widget.EmptyLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yao_han on 2016/4/7.
 */
public class SearchPersonActivity extends BaseActivity {

    private int REQUEST_CODE = 1;

    private int role;
    private List<UserModel> userModelList;
    private CommonAdapter<UserModel> adapter;
    private ListView listView;
    private UserModel userModel;

    @Override
    protected String initTitle() {
        return "搜索";
    }

    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_search_person;
    }

    @Override
    protected void initViews() {
        listView = getListView(R.id.lv_search_list);
        final EmptyLayout emptyLayout = new EmptyLayout(context,listView);
        getTextView(R.id.tv_search_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getEditText(R.id.et_search_name).getText().toString().equals("")){
                    T.showShort(context, "姓名不能为空");
                }else{
                    emptyLayout.showLoading();
                    AppAction action = new AppActionImpl(context);
                    SearchPersonReq searchPersonReq = new SearchPersonReq();
                    searchPersonReq.setName(getEditText(R.id.et_search_name).getText().toString());
                    searchPersonReq.setRole(role);
                    action.searchPerson(searchPersonReq, new ActionCallbackListener<SearchPersonResp>() {
                        @Override
                        public void onSuccess(SearchPersonResp data) {
                            if(data.getResult() == SearchPersonResp.RESULT_SUCCESS){
                                if(data.getList().size() == 0){
                                    emptyLayout.showEmpty();
                                }else{
                                    userModelList.removeAll(userModelList);
                                    userModelList.addAll(data.getList());
                                    adapter.notifyDataSetChanged();
                                }
                            }else{
                                T.showShort(context,data.getDesc());
                            }
                        }

                        @Override
                        public void onFailure(String message) {
                            emptyLayout.showError();
                        }
                    });
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userModel = userModelList.get(position);
                Intent intent = new Intent(context,SearchPersonInfoActivity.class);
                intent.putExtra("target",getIntent().getStringExtra("target"));
                intent.putExtra("userModel",userModelList.get(position));
                intent.putExtra("role",role);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void loadData() {
        role = getIntent().getIntExtra("role",-1);
        if(role == -1){
            T.showShort(context,"role error!");
        }
    }

    @Override
    protected void showView() {
        setCanBack();
        userModelList = new ArrayList<UserModel>();
        adapter = new CommonAdapter<UserModel>(context,userModelList,R.layout.item_person) {
            @Override
            public void convert(ViewHolder viewHolder, UserModel userModel, int position) {
                if(userModel.getRealName() != null){
                    String head = userModel.getHeadPortrait();
                    if(head == null || head.length() == 0){
                        viewHolder.getImageView(R.id.iv_person_head).setImageResource(R.mipmap.role_student);
                    }else{
                        viewHolder.getImageView(R.id.iv_person_head).setImageBitmap(BitmapUtils.convertStringToIcon(head));
                    }
                    viewHolder.getTextView(R.id.tv_person_name).setText(userModel.getRealName());
                    if(userModel.getGender().equals("male")){
                        viewHolder.getTextView(R.id.tv_person_gender).setText("男");
                    }else if(userModel.getGender().equals("female")){
                        viewHolder.getTextView(R.id.tv_person_gender).setText("女");
                    }
                    if(userModel.getAge() !=null){
                        viewHolder.getTextView(R.id.tv_person_age).setText(userModel.getAge()+"岁");
                    }
                    if(userModel.getAddress() !=null){
                        viewHolder.getTextView(R.id.tv_person_address).setText(userModel.getAddress());
                    }
                }
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data.getBooleanExtra("set",false)){
                Intent intent = getIntent();
                L.i("user"+ GsonUtils.gsonToJsonString(userModel));
                intent.putExtra("id",userModel.getId());
                intent.putExtra("name",userModel.getRealName());
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
