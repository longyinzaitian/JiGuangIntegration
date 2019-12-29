package com.husy.jpushlib.lib;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;

import com.husy.jpushlib.JPushHelper;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * @author husy
 * @date 2019/12/26
 */
public class JPushReceiver extends JPushMessageReceiver {
    @Override
    public Notification getNotification(Context context, NotificationMessage notificationMessage) {
        Notification notification = super.getNotification(context, notificationMessage);
        if (JPushHelper.mJPushListener != null) {
            JPushHelper.mJPushListener.getNotification(context, notificationMessage, notification);
        }
        return notification;
    }

    /**
     * 收到自定义消息回调
     *
     * @param context Context
     * @param customMessage 接收自定义消息内容
     */
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);
        if (JPushHelper.mJPushListener != null) {
            JPushHelper.mJPushListener.onMessage(context, customMessage);
        }
    }

    /**
     * 点击通知回调
     *
     * @param context Context
     * @param notificationMessage 点击的通知内容
     */
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        JPushInterface.reportNotificationOpened(context, notificationMessage.msgId);
        super.onNotifyMessageOpened(context, notificationMessage);
        if (JPushHelper.mJPushListener != null) {
            JPushHelper.mJPushListener.onNotifyMessageOpened(context, notificationMessage);
        }
    }

    /**
     * 收到通知回调
     *
     * @param context Context
     * @param notificationMessage 接收到的通知内容
     */
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
        if (JPushHelper.mJPushListener != null) {
            JPushHelper.mJPushListener.onNotifyMessageArrived(context, notificationMessage);
        }
    }

    /**
     * 清除通知回调
     *
     * 说明:
     *
     * 1.同时删除多条通知，可能不会多次触发清除通知的回调
     *
     * 2.只有用户手动清除才有回调，调接口清除不会有回调
     *
     * @param context Context
     * @param notificationMessage 清除的通知内容
     */
    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageDismiss(context, notificationMessage);
        if (JPushHelper.mJPushListener != null) {
            JPushHelper.mJPushListener.onNotifyMessageDismiss(context, notificationMessage);
        }
    }

    /**
     * 注册成功回调
     *
     * @param context Context
     * @param s  注册id
     */
    @Override
    public void onRegister(Context context, String s) {
        super.onRegister(context, s);
        if (JPushHelper.mJPushListener != null) {
            JPushHelper.mJPushListener.onRegister(context, s);
        }
    }

    /**
     * 长连接状态回调
     *
     * @param context Context
     * @param b 长连接状态
     */
    @Override
    public void onConnected(Context context, boolean b) {
        super.onConnected(context, b);
        if (JPushHelper.mJPushListener != null) {
            JPushHelper.mJPushListener.onConnected(context, b);
        }
    }

    /**
     * 注册失败回调
     *
     * @param context Context
     * @param cmdMessage 错误信息
     */
    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        super.onCommandResult(context, cmdMessage);
        if (JPushHelper.mJPushListener != null) {
            JPushHelper.mJPushListener.onCommandResult(context, cmdMessage);
        }
    }

    /**
     * 通知的MultiAction回调
     *
     * @param context Context
     * @param intent 点击后触发的Intent
     *
     *  说明 注意这个方法里面禁止再调super.onMultiActionClicked,因为会导致逻辑混乱
     */
    @Override
    public void onMultiActionClicked(Context context, Intent intent) {
        //注意这个方法里面禁止再调super.onMultiActionClicked,因为会导致逻辑混乱
//        super.onMultiActionClicked(context, intent);
        if (JPushHelper.mJPushListener != null) {
            JPushHelper.mJPushListener.onMultiActionClicked(context, intent);
        }
    }

    /**
     * 通知开关的回调
     *
     * 该方法会在以下情况触发时回调。
     *
     * 1.sdk每次启动后都会检查通知开关状态并通过该方法回调给开发者。
     *
     * 2.当sdk检查到通知状态变更时会通过该方法回调给开发者。
     *
     * @param context Context
     * @param isOn 通知开关状态
     * @param i 触发场景，0为sdk启动，1为检测到通知开关状态变更
     */
    @Override
    public void onNotificationSettingsCheck(Context context, boolean isOn, int i) {
        //isOn  通知开关状态
        //触发场景，0为sdk启动，1为检测到通知开关状态变更
        super.onNotificationSettingsCheck(context, isOn, i);
        if (JPushHelper.mJPushListener != null) {
            JPushHelper.mJPushListener.onNotificationSettingsCheck(context, isOn, i);
        }
    }

    /**
     * tag 增删查改的操作会在此方法中回调结果
     *
     * @param context Context
     * @param jPushMessage tag 相关操作返回的消息结果体
     */
    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onTagOperatorResult(context, jPushMessage);
        if (JPushHelper.mJPushListener != null) {
            JPushHelper.mJPushListener.onTagOperatorResult(context, jPushMessage);
        }
    }

    /**
     * 查询某个 tag 与当前用户的绑定状态的操作会在此方法中回调结果
     *
     * @param context Context
     * @param jPushMessage check tag 与当前用户绑定状态的操作返回的消息结果体，具体参考 JPushMessage 类的说明
     */
    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onCheckTagOperatorResult(context, jPushMessage);
        if (JPushHelper.mJPushListener != null) {
            JPushHelper.mJPushListener.onCheckTagOperatorResult(context, jPushMessage);
        }
    }

    /**
     * alias 相关的操作会在此方法中回调结果
     *
     * @param context Context
     * @param jPushMessage alias 相关操作返回的消息结果体
     */
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage);
        if (JPushHelper.mJPushListener != null) {
            JPushHelper.mJPushListener.onAliasOperatorResult(context, jPushMessage);
        }
    }

    /**
     * 设置手机号码会在此方法中回调结果
     *
     * @param context Context
     * @param jPushMessage 设置手机号码返回的消息结果体
     */
    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onMobileNumberOperatorResult(context, jPushMessage);
        if (JPushHelper.mJPushListener != null) {
            JPushHelper.mJPushListener.onMobileNumberOperatorResult(context, jPushMessage);
        }
    }
}
