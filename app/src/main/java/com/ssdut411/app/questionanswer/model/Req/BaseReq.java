package com.ssdut411.app.questionanswer.model.Req;

import java.lang.reflect.Field;

/**
 * Created by yao_han on 2015/12/23.
 */
public class BaseReq {

    public BaseReq() {
    }

    /**
     * 判断当前类的属性是否有null值
     * @return 返回值为null的属性名，null表示没有空值。
     */
    public String hasNull(){
        try{
            for(Field f : getClass().getDeclaredFields()){
                f.setAccessible(true);
                    if(f.get(this) == null){
                        return f.getName();
                    }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
