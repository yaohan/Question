package com.ssdut411.app.questionanswer.application;

import com.ssdut411.app.questionanswer.api.volley.VolleyManager;
import com.ssdut411.app.questionanswer.exception.BaseExceptionHandler;
import com.ssdut411.app.questionanswer.model.Resp.LoginResp;
import com.ssdut411.app.questionanswer.model.model.ModelConfig;
import com.ssdut411.app.questionanswer.model.model.UserModel;
import com.ssdut411.app.questionanswer.utils.GsonUtils;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.utils.SPUtils;


/**
 * Created by yao_han on 2015/12/22.
 */
public class MainApplication extends BaseApplication {
    public static int ROLE_NULL = -1;
    public static int ROLE_PUPILS = 1;
    public static int ROLE_TEACHER = 2;
    public static int ROLE_PARENT = 3;

    // 单例一个MainApplication
    private static MainApplication instance;

    private String userId;
    private String childId;
    private boolean login;
    private int role;
    private UserModel user;
    private boolean theme;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        L.i("set role"+role);
        SPUtils.put(context,"role",role);
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        L.i("set userId"+userId);
        SPUtils.put(context,"userId",userId);
        this.userId = userId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        L.i("set childId:"+childId);
        SPUtils.put(context,"childId",childId);
        this.childId = childId;
    }

    public boolean getLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        L.i("set login" + login);
        SPUtils.put(context, "login", login);
        this.login = login;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        SPUtils.put(context, "user", GsonUtils.gsonToJsonString(user));
        setUserId(user.getId());
        if(user.getRole() == ROLE_PARENT){
            setChildId(user.getChildId());
        }
        this.role = user.getRole();
        this.user = user;
    }


    /**
     * 得到MainApplication实例
     *
     * @return
     */
    public static MainApplication getInstance() {
        return MainApplication.instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 打印日志信息
        L.isDebug = true;
        //初始化信息
        role = (Integer)SPUtils.get(context,"role",ROLE_NULL);
        userId = (String)SPUtils.get(context,"userId","");
        childId = (String)SPUtils.get(context, "childId","");
        login = (Boolean)SPUtils.get(context,"login",false);
        user = GsonUtils.gsonToObject(SPUtils.get(context,"user","").toString(),UserModel.class);
        L.i("role:"+role +" userId:"+userId+" childId:"+childId+" login: "+login+" user:"+GsonUtils.gsonToJsonString(user));
        // 初始化Volley
        VolleyManager.init(context);
        L.i("初始化Volley");
    }


    /**
     * 获取默认的未捕获异常处理器
     */
    @Override
    public BaseExceptionHandler getDefaultUncaughtExceptionHandler() {
//        return new CrashExceptionHandler(applicationContext);
        return null;
    }

    public void clear(){
        role = ModelConfig.ROLE_NULL;
        userId = null;
        login = false;
        user = null;
        childId = null;
    }

    public boolean getTheTheme() {
        return theme;
    }

    public void setTheme(Boolean theme) {
        this.theme = theme;
    }
}
