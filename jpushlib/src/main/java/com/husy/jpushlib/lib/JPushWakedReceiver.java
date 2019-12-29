package com.husy.jpushlib.lib;

import android.content.Context;

import com.husy.jpushlib.JPushHelper;

import cn.jpush.android.service.WakedResultReceiver;

/**
 * @author husy
 * @date 2019/12/28
 */
public class JPushWakedReceiver extends WakedResultReceiver {

    private static final int START_SERVICE = 1;
    private static final int BIND_SERVICE = 2;
    private static final int CONTENTPROVIDER = 4;

    /**
     * 应用被拉起时回调 onWake(Context context, int wakeType) 方法，context 是上下文，
     *      wakeType 是拉起的类型，其取值对应的拉起方式如下：
     *
     * wakeType值	拉起方式
     * 1	START_SERVICE
     * 2	BIND_SERVICE
     * 4	CONTENTPROVIDER
     */
    @Override
    public void onWake(Context context, int i) {
        super.onWake(context, i);
        if (JPushHelper.mJPushListener != null) {
            JPushHelper.mJPushListener.onWake(context, i);
        }
    }
}
