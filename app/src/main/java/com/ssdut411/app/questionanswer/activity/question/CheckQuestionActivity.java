package com.ssdut411.app.questionanswer.activity.question;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.activity.system.BaseActivity;
import com.ssdut411.app.questionanswer.activity.teacher.SelectStudentActivity;
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.CollectionReq;
import com.ssdut411.app.questionanswer.model.Req.UnCollectionReq;
import com.ssdut411.app.questionanswer.model.Resp.CollectionResp;
import com.ssdut411.app.questionanswer.model.Resp.UnCollectionResp;
import com.ssdut411.app.questionanswer.model.model.TeacherHomeworkModel;
import com.ssdut411.app.questionanswer.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yao_han on 2016/4/13.
 */
public class CheckQuestionActivity extends BaseActivity {
    private static int REQUEST = 0;
    private ViewPager vpQuestions;
    private TextView tvOperation;
    private ImageView ivCollection;
    private List<Fragment> fragmentList;
    private int totalCount;
    private int currentIndex;
    private TeacherHomeworkModel teacherHomeworkModel;
    private MenuItem menuCard;
    private boolean isCollection = false;
    private int state;
    private Handler handler = initHandler();
    private int[] finishs;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        T.showShort(context, "该作业已经存为草稿");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuCard = menu.add(0,1,1,currentIndex+"/"+totalCount);
        menuCard.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuCard.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(context, AnswerCardActivity.class);
                intent.putExtra("finishs", finishs);
                startActivityForResult(intent, REQUEST);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected String initTitle() {
        return "查阅题目";
    }

    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_question;
    }

    @Override
    protected void initViews() {
        vpQuestions = getViewPager(R.id.vp_question_viewpager);
        tvOperation = getTextView(R.id.tv_question_operation);
        ivCollection = getImageView(R.id.iv_question_collection);
        ivCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCollection){
                    //TODO unCollection
                    setUnCollection();
                }else{
                    //TODO collection
                    setCollection();
                }
            }
        });
        tvOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postHomework();
            }
        });
        vpQuestions.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position + 1;
                setIndex();
            }

            boolean misScrolled;

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING://滑行中
                        misScrolled = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING://滑行完毕
                        misScrolled = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE://空闲
                        if (currentIndex == totalCount && !misScrolled) {
                            postHomework();
                        }
                        break;
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            currentIndex = data.getIntExtra("index", 0) + 1;
            vpQuestions.setCurrentItem(currentIndex - 1);
            setIndex();
        }
    }
    private void postHomework() {
        Intent intent = new Intent(context, SelectStudentActivity.class);
        intent.putExtra("model",teacherHomeworkModel);
        startActivity(intent);
    }


    @Override
    protected void loadData() {
        teacherHomeworkModel = (TeacherHomeworkModel)getIntent().getSerializableExtra("data");
        if(teacherHomeworkModel == null){
            T.showShort(context,"teacherHomeworkModel is null");
            finish();
        }
    }

    @Override
    protected void showView() {
        setCanBack();
        getRelativeLayout(R.id.rl_operation).setVisibility(View.GONE);
        state = QuestionFragment.STATE_CHECK;
        totalCount = teacherHomeworkModel.getQuestionList().size();
        currentIndex = getCurrent();
        fragmentList = initFragmentList(handler);
        tvOperation.setText("发布");
        vpQuestions.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        vpQuestions.setCurrentItem(currentIndex - 1);
    }

    public int getCurrent() {
        finishs = new int[totalCount];
        return 1;
    }

    private List<Fragment> initFragmentList(final Handler handler) {
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        for (int i = 0; i < teacherHomeworkModel.getQuestionList().size(); i++) {
            QuestionFragment fragment = QuestionFragment.newInstance(teacherHomeworkModel.getQuestionList().get(i), i, handler, state);
            fragmentList.add(fragment);
        }
        return fragmentList;
    }

    private void setUnCollection() {
        setUnCollectionIcon();
        AppAction appAction = new AppActionImpl(context);
        UnCollectionReq unCollectionReq = new UnCollectionReq();
        unCollectionReq.setPsId(MainApplication.getInstance().getUserId());
        unCollectionReq.setqId(teacherHomeworkModel.getQuestionList().get(currentIndex - 1).getqId());
        appAction.unCollection(unCollectionReq, new ActionCallbackListener<UnCollectionResp>() {
            @Override
            public void onSuccess(UnCollectionResp data) {
                T.showShort(context, data.getDesc());
            }

            @Override
            public void onFailure(String message) {
                T.showShort(context, getString(R.string.error_message));
            }
        });
    }

    private void setUnCollectionIcon() {
        isCollection = false;
        ivCollection.setImageResource(R.mipmap.icon_question_uncollection);
    }

    private void setCollection() {
        setCollectionIcon();
        AppAction appAction = new AppActionImpl(context);
        CollectionReq collectionReq = new CollectionReq();
        collectionReq.setPsId(MainApplication.getInstance().getUserId());
        collectionReq.setqId(teacherHomeworkModel.getQuestionList().get(currentIndex - 1).getqId());
        appAction.collection(collectionReq, new ActionCallbackListener<CollectionResp>() {
            @Override
            public void onSuccess(CollectionResp data) {
                T.showShort(context, data.getDesc());
            }

            @Override
            public void onFailure(String message) {
                T.showShort(context, getString(R.string.error_message));
            }
        });
    }

    private void setCollectionIcon() {
        isCollection = true;
        ivCollection.setImageResource(R.mipmap.icon_question_collection);
    }

    private void setIndex() {
        //TODO collection
//        if(teacherHomeworkModel.getQuestionList().get(currentIndex-1).getCollection()){
//            setCollectionIcon();
//        }else{
//            setUnCollectionIcon();
//        }
        menuCard.setTitle(currentIndex + "/" + totalCount);
    }

    private Handler initHandler() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {

                }
            }


        };
        return handler;
    }class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int i) {
            return list.get(i);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

}
