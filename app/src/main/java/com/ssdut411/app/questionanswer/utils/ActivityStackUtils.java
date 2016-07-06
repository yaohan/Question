package com.ssdut411.app.questionanswer.utils;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

/**
 * Activity堆栈管理工具
 *
 * Created by yao_han on 2015/11/25.
 */
public class ActivityStackUtils {
    private static ActivityStackUtils instance;
    private Stack<Activity> activityStack;

    private ActivityStackUtils(){
        activityStack = new Stack<Activity>();
    }

    /**
     * 获得Activity栈的单例
     *
     * @return
     */
    public static ActivityStackUtils getInstance(){
        if(instance == null){
            synchronized (ActivityStackUtils.class){
                if(instance == null){
                    instance = new ActivityStackUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 获取栈
     *
     * @return
     */
    public Stack<Activity> getStack(){
        return activityStack;
    }

    /**
     * 入栈
     *
     * @param activity
     */
    public void addActivity(Activity activity){
        activityStack.push(activity);
    }

    /**
     * 出栈
     *
     * @param activity
     */
    public void removeActivity(Activity activity){
        activityStack.remove(activity);
    }

    /**
     * finish所有activity
     */
    public void finishAllActivity(){
        Activity activity;
        while(!activityStack.empty()){
            activity = activityStack.pop();
            if(activity != null){
                activity.finish();
            }
        }
    }

    /**
     * 彻底退出
     */
    public void exit(){
        this.finishAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 根据class查找Activity
     *
     * @param actCls
     * @return
     */
    public Activity findActivityByClass(Class<? extends Activity> actCls){
        Activity activity  = null;
        Iterator<Activity> iterator = activityStack.iterator();
        while(iterator.hasNext()){
            activity = iterator.next();
            if(activity !=null && activity.getClass().getName().equals(actCls.getName()) && !activity.isFinishing()){
                break;
            }
            activity = null;
        }
        return activity;
    }

    /**
     * finish掉指定的activity
     *
     * @param actCls
     * @return
     */
    public boolean finishActivity(Class<? extends Activity> actCls){
        Activity activity = findActivityByClass(actCls);
        if(activity !=null && !activity.isFinishing()){
            activity.finish();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for(Activity activity:activityStack){
            sb.append(activity.toString());
        }
        return sb.toString();
    }
}
