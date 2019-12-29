package com.husy.jiguang

import android.app.Application
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.util.Log
import cn.jpush.android.api.CmdMessage
import cn.jpush.android.api.CustomMessage
import cn.jpush.android.api.JPushMessage
import cn.jpush.android.api.NotificationMessage
import com.husy.jpushlib.AbstractJPushListener
import com.husy.jpushlib.JPushHelper

/**
 * @author husy
 * @date 2019/12/26
 */
class JGApplication: Application() {
    private val TAG = "JGApplication"

    override fun onCreate() {
        super.onCreate()
        setJPushListener()
        JPushHelper.init(this, BuildConfig.DEBUG)
    }

    fun setJPushListener() {
        JPushHelper.setJPushListener(absJPushListener)
    }

    private val absJPushListener = object : AbstractJPushListener() {
        override fun getNotification(
            context: Context?,
            notificationMessage: NotificationMessage?,
            notification: Notification?
        ): Notification {
            Log.i(TAG, "获取到通知:" + notificationMessage.toString())
            return super.getNotification(context, notificationMessage, notification)
        }

        override fun onMessage(context: Context?, customMessage: CustomMessage?) {
            super.onMessage(context, customMessage)
            Log.i(TAG, "收到自定义消息回调:" + customMessage.toString())
        }

        override fun onNotifyMessageOpened(
            context: Context?,
            notificationMessage: NotificationMessage?
        ) {
            super.onNotifyMessageOpened(context, notificationMessage)
            Log.i(TAG, "点击通知打开:" + notificationMessage.toString())
        }

        override fun onNotifyMessageArrived(
            context: Context?,
            notificationMessage: NotificationMessage?
        ) {
            super.onNotifyMessageArrived(context, notificationMessage)
            Log.i(TAG, "收到通知回调:" + notificationMessage.toString())
        }

        override fun onNotifyMessageDismiss(
            context: Context?,
            notificationMessage: NotificationMessage?
        ) {
            super.onNotifyMessageDismiss(context, notificationMessage)
            Log.i(TAG, "清除通知回调:" + notificationMessage.toString())
        }

        override fun onRegister(context: Context?, s: String?) {
            super.onRegister(context, s)
            Log.i(TAG, "注册成功回调:$s")
        }

        override fun onConnected(context: Context?, b: Boolean) {
            super.onConnected(context, b)
            Log.i(TAG, "长连接状态回调:$b")
        }

        override fun onCommandResult(context: Context?, cmdMessage: CmdMessage?) {
            super.onCommandResult(context, cmdMessage)
            Log.i(TAG, "注册失败回调:$cmdMessage")
        }

        override fun onMultiActionClicked(context: Context?, intent: Intent?) {
            super.onMultiActionClicked(context, intent)
            Log.i(TAG, "通知的MultiAction点击回调:$intent")
        }

        override fun onNotificationSettingsCheck(context: Context?, isOn: Boolean, i: Int) {
            super.onNotificationSettingsCheck(context, isOn, i)
            Log.i(TAG, "手机APP通知开关的回调-》  isOn:$isOn, i:$i")
        }

        override fun onTagOperatorResult(context: Context?, jPushMessage: JPushMessage?) {
            super.onTagOperatorResult(context, jPushMessage)
            Log.i(TAG, "tag 增删查改的操作会在此方法中回调结果:$jPushMessage")
        }

        override fun onCheckTagOperatorResult(context: Context?, jPushMessage: JPushMessage?) {
            super.onCheckTagOperatorResult(context, jPushMessage)
            Log.i(TAG, "查询某个 tag 与当前用户的绑定状态的操作会在此方法中回调结果:$jPushMessage")
        }

        override fun onAliasOperatorResult(context: Context?, jPushMessage: JPushMessage?) {
            super.onAliasOperatorResult(context, jPushMessage)
            Log.i(TAG, "alias 相关的操作会在此方法中回调结果:$jPushMessage")
        }

        override fun onMobileNumberOperatorResult(context: Context?, jPushMessage: JPushMessage?) {
            super.onMobileNumberOperatorResult(context, jPushMessage)
            Log.i(TAG, "设置手机号码会在此方法中回调结果:$jPushMessage")
        }

        override fun onWake(context: Context?, wakeType: Int) {
            super.onWake(context, wakeType)
            Log.i(TAG, "APP Jpush唤醒回调:$wakeType")
        }
    }
}
