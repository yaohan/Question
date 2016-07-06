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
import com.ssdut411.app.questionanswer.application.MainApplication;
import com.ssdut411.app.questionanswer.core.ActionCallbackListener;
import com.ssdut411.app.questionanswer.core.AppAction;
import com.ssdut411.app.questionanswer.core.AppActionImpl;
import com.ssdut411.app.questionanswer.model.Req.CollectionReq;
import com.ssdut411.app.questionanswer.model.Req.SubmitStudentHomeworkReq;
import com.ssdut411.app.questionanswer.model.Req.SubmitTestReq;
import com.ssdut411.app.questionanswer.model.Req.UnCollectionReq;
import com.ssdut411.app.questionanswer.model.Resp.CollectionResp;
import com.ssdut411.app.questionanswer.model.Resp.SubmitStudentHomeworkResp;
import com.ssdut411.app.questionanswer.model.Resp.SubmitTestResp;
import com.ssdut411.app.questionanswer.model.Resp.UnCollectionResp;
import com.ssdut411.app.questionanswer.model.model.QuestionModel;
import com.ssdut411.app.questionanswer.model.model.SelfTestModel;
import com.ssdut411.app.questionanswer.model.model.ModelConfig;
import com.ssdut411.app.questionanswer.model.model.StudentHomeworkModel;
import com.ssdut411.app.questionanswer.utils.GsonUtils;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.SPUtils;
import com.ssdut411.app.questionanswer.utils.T;
import com.ssdut411.app.questionanswer.widget.dialog.BaseDialog;
import com.ssdut411.app.questionanswer.widget.dialog.SimpleDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yao_han on 2015/11/29.
 */
public class QuestionActivity extends BaseActivity {

    private static int REQUEST = 0;
    private int state;

    private ViewPager vpQuestions;
    private TextView tvOperation;
    private ImageView ivCollection;
    private TextView tvTime;

    final Handler handler = initHandler();

    private List<Fragment> fragmentList;

    private TimerTask task;
    private Timer timer;

    private int[] finishs;
    private int time = 0;
    private boolean isTime;

    private int totalCount = 20;
    private int currentIndex = 1;

    private BaseDialog leaveDialog;
    private BaseDialog assignmentDialog;
    private BaseDialog pauseDialog;
    private BaseDialog notFinishedDialog;

    private SelfTestModel selfTestModel;
    private String target;

    private MenuItem menuCard;
    private boolean isCollection = false;
    private int current;
    private double grade;

    public boolean getTheTheme(){
        return theme;
    }

    @Override
    public void onBackPressed() {
        leaveDialog.show();
        if (isTime) {
            pauseTime();
        } else {
            startTime();
        }
        pauseTime();
    }

    @Override
    protected String initTitle() {
        return selfTestModel.getName();
    }

    @Override
    protected int initMenu() {
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        L.i("menu start");
        menuCard = menu.add(0, 1, 1, currentIndex + "/" + totalCount);
        menuCard.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuCard.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startAnswerCardActivity();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
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
        tvTime = getTextView(R.id.tv_question_time);
        leaveDialog = initLeaveDialog();
        assignmentDialog = initAssignmentDialog();
        pauseDialog = initPauseDialog();
        notFinishedDialog = initNotFinishedDialog();

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseDialog.show();
            }
        });
        ivCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCollection) {
                    selfTestModel.getQuestionList().get(currentIndex - 1).setCollection(false);
                    setUnCollection();
                } else {
                    selfTestModel.getQuestionList().get(currentIndex - 1).setCollection(true);
                    setCollection();
                }
            }
        });
        tvOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignmentDialog.show();
            }
        });
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveDialog.show();
                pauseTime();
            }
        });
        vpQuestions.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                currentIndex = i + 1;
                L.i("onPageSelected");
                setIndex();
            }

            boolean misScrolled;

            @Override
            public void onPageScrollStateChanged(int i) {
                switch (i) {
                    case ViewPager.SCROLL_STATE_DRAGGING://滑行中
                        misScrolled = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING://滑行完毕
                        misScrolled = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE://空闲
                        if (currentIndex == totalCount && !misScrolled) {
                            assignment();
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void loadData() {
        selfTestModel = (SelfTestModel) getIntent().getSerializableExtra("data");
        if (selfTestModel == null) {
            finish();
            T.showShort(context, "selfTestModel is null");
        }
        target = getIntent().getStringExtra("source");
    }

    @Override
    protected void showView() {
        totalCount = selfTestModel.getQuestionList().size();

        currentIndex = getCurrent();
        startTime();

        fragmentList = initFragmentList(handler);

        vpQuestions.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        L.i("vp.setCurrent");
        vpQuestions.setCurrentItem(currentIndex - 1);
    }

    private void setUnCollection() {
        setUnCollectionIcon();
        AppAction appAction = new AppActionImpl(context);
        UnCollectionReq unCollectionReq = new UnCollectionReq();
        unCollectionReq.setPsId(MainApplication.getInstance().getUserId());
        unCollectionReq.setqId(selfTestModel.getQuestionList().get(currentIndex - 1).getqId());
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
        collectionReq.setqId(selfTestModel.getQuestionList().get(currentIndex - 1).getqId());
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

    private void saveOrSubmit(int state) {
        AppAction action = new AppActionImpl(this);
        selfTestModel.setState(state);
        selfTestModel.setGrade(getGrade());

        if (target != null && target.equals("homework")) {
            SubmitStudentHomeworkReq submitStudentHomeworkReq = new SubmitStudentHomeworkReq();
            submitStudentHomeworkReq.setStudentHomeworkModel((StudentHomeworkModel) selfTestModel);
            action.submitStudentHomework(submitStudentHomeworkReq, new ActionCallbackListener<SubmitStudentHomeworkResp>() {
                @Override
                public void onSuccess(SubmitStudentHomeworkResp data) {
                    T.showShort(context, data.getDesc());
                }

                @Override
                public void onFailure(String message) {
                    T.showShort(QuestionActivity.this, getString(R.string.error_message));
                }
            });
        } else {
            SubmitTestReq submitTestReq = new SubmitTestReq();
            submitTestReq.setSelfTestModel(selfTestModel);
            action.submitTest(submitTestReq, new ActionCallbackListener<SubmitTestResp>() {
                @Override
                public void onSuccess(SubmitTestResp data) {
                    T.showShort(QuestionActivity.this, data.getDesc());
                    L.i("data:" + GsonUtils.gsonToJsonString(data));
                }

                @Override
                public void onFailure(String message) {
                    T.showShort(QuestionActivity.this, getString(R.string.error_message));
                    L.i("message:" + message);
                }
            });
        }


        SPUtils.put(QuestionActivity.this, selfTestModel.getId(), GsonUtils.gsonToJsonString(selfTestModel));
    }

    private void assignment() {
        pauseTime();
        if (hasFinishes(finishs)) {
            assignmentDialog.show();
        } else {
            notFinishedDialog.show();
        }

    }

    private boolean hasFinishes(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                return false;
            }
        }
        return true;
    }

    private BaseDialog initLeaveDialog() {
        BaseDialog dialog = new SimpleDialog.Builder(context, R.layout.dialog_easy)
                .setTitle(getString(R.string.exit))
                .setDesc(getString(R.string.are_you_sure_to_exit))
                .setPositiveButton(getString(R.string.sure), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveOrSubmit(ModelConfig.STATE_NOT_FINISH);
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        leaveDialog.dismiss();
                        startTime();
                    }
                }).getDialog();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private BaseDialog initAssignmentDialog() {
        BaseDialog dialog = new SimpleDialog.Builder(context, R.layout.dialog_easy)
                .setDesc(getString(R.string.are_you_sure_to_assigment))
                .setPositiveButton(getString(R.string.sure), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        saveOrSubmit(ModelConfig.STATE_FINISH);
                        startReportCardActivity();
                        T.showShort(QuestionActivity.this, "交卷");
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        assignmentDialog.dismiss();
                        startTime();
                    }
                }).getDialog();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private void startReportCardActivity() {
        Intent intent = new Intent(QuestionActivity.this, ReportCardActivity.class);
        intent.putExtra("data", selfTestModel);
        intent.putExtra("source", "test");
        finish();
        startActivity(intent);
    }

    private BaseDialog initPauseDialog() {
        BaseDialog dialog = new SimpleDialog.Builder(context, R.layout.dialog_easy).
                setDesc(getString(R.string.pause_time)).
                setPositiveButton(getString(R.string.empty), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).
                setNegativeButton(getString(R.string.start_time), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pauseDialog.dismiss();
                        startTime();
                    }
                }).getDialog();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private BaseDialog initNotFinishedDialog() {
        BaseDialog dialog = new SimpleDialog.Builder(context, R.layout.dialog_easy)
                .setDesc(getString(R.string.you_have_not_finish_are_you_sure_to_assigment))
                .setPositiveButton(getResources().getString(R.string.sure), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        saveOrSubmit(ModelConfig.STATE_FINISH);
                        startReportCardActivity();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startAnswerCardActivity();
                        notFinishedDialog.dismiss();
                        startTime();
                    }
                }).getDialog();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private List<Fragment> initFragmentList(final Handler handler) {
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        for (int i = 0; i < selfTestModel.getQuestionList().size(); i++) {
            QuestionFragment fragment = QuestionFragment.newInstance(selfTestModel.getQuestionList().get(i), i, handler, state);
            fragmentList.add(fragment);
        }
        return fragmentList;
    }

    private TimerTask initTask(final Handler handler) {
        return new TimerTask() {
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
    }

    private Handler initHandler() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case 1: //计时器返回
                        showTime();
                        break;
                    case 2: //点击选项
                        int finished = msg.arg1;
                        currentIndex = msg.arg1 + 2;
                        finishs[finished] = 1;
                        if (currentIndex > totalCount) {
                            assignment();
                        } else {
                            vpQuestions.setCurrentItem(currentIndex - 1);
                            L.i("handler");
                            setIndex();
                        }
                        saveOption(msg.arg1, (String) msg.obj);
                        break;
                }
            }


        };
        return handler;
    }

    private void saveOption(int index, String options) {
        selfTestModel.getAnswerList().set(index, options);
        QuestionModel questionModel = selfTestModel.getQuestionList().get(index);
        questionModel.setMyAnswer(options);
        if (options.equals(questionModel.getAnswer())) {
            selfTestModel.getResultList().set(index, true);
        }
    }

    private void setIndex() {
        L.i("menuCard == null?" + (menuCard == null));
        L.i("currentIndex = " + currentIndex);
        L.i("totalCount = " + totalCount);
        if (selfTestModel.getQuestionList().get(currentIndex - 1).getCollection()) {
            setCollectionIcon();
        } else {
            setUnCollectionIcon();
        }
        if (menuCard != null) {
            menuCard.setTitle(currentIndex + "/" + totalCount);
        }
    }

    private void showTime() {
        time++;
        String second = String.format("%02d", time % 60);
        String minute = String.format("%02d", (time % 3600) / 60);
        if (time < 60 * 60) {
            tvTime.setText(minute + ":" + second);
        } else {
            tvTime.setText(time / 3600 + ":" + minute + ":" + second);
        }
    }

    private void pauseTime() {
        isTime = false;
        timer.cancel();
    }

    private void startTime() {
        isTime = true;
        timer = new Timer(true);
        task = initTask(handler);
        timer.schedule(task, time, 1000);
    }

    private void startAnswerCardActivity() {
        Intent intent = new Intent(QuestionActivity.this, AnswerCardActivity.class);
        intent.putExtra("finishs", finishs);
        startActivityForResult(intent, REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            currentIndex = data.getIntExtra("index", 0) + 1;
            vpQuestions.setCurrentItem(currentIndex - 1);
            L.i("onActivityResult");
            setIndex();
        }
    }

    public int getCurrent() {
        finishs = new int[totalCount];
        List<String> answers = selfTestModel.getAnswerList();
        for (int i = 0; i < totalCount; i++) {
            if (!answers.get(i).equals("")) {
                finishs[i] = 1;
            } else {
                finishs[i] = 0;
            }
        }

        for (int i = 0; i < finishs.length; i++) {
            if (finishs[i] == 0) {
                return i + 1;
            }
        }
        return 1;
    }

    public double getGrade() {
            List<Boolean> list = selfTestModel.getResultList();
            int count = 0;
            for (Boolean b : list) {
                if (b) {
                    count++;
                }
            }
        return (double) count / list.size() * 100;
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
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
