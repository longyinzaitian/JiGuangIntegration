package com.husy.jiguang

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import cn.jpush.android.data.JPushLocalNotification
import com.husy.jpushlib.JPushHelper
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashSet

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.push_state ->
                log("当前Jpush连接状态：" + JPushHelper.getConnectionState())

            R.id.push_stop ->
                log("停止JPush推送：" + JPushHelper.stopPush())

            R.id.push_is_stop ->
                log("是否已经停止JPush推送：" + JPushHelper.isPushStoped())

            R.id.push_resume ->
                log("恢复Jpush推送：" + JPushHelper.resumePush())

            R.id.push_silence -> {
                log("设置JPush静默时间")
                JPushHelper.setSilenceTime(12, 0, 8, 0)
            }

            R.id.push_allow_time -> {
                val days = HashSet<Int>()
                days.add(1)
                days.add(2)
                days.add(3)
                days.add(4)
                days.add(5)
                log("设置JPush允许推送时间")
                JPushHelper.setPushTime(days, 5, 12)
            }

            R.id.push_channel -> {
                log("设置JPush Channel")
                JPushHelper.setChannel("Jpush_Integration")
            }

            R.id.push_set_alias -> {
                log("设置JPush 用户别名")
                JPushHelper.setAlias(0, "user_alias")
            }

            R.id.push_delete_alias -> {
                log("删除JPush 用户别名")
                JPushHelper.deleteAlias(0)
            }

            R.id.push_get_alias -> {
                log("获取JPush 用户别名")
                JPushHelper.getAlias(0)
            }

            R.id.push_get_register_id -> {
                log("获取JPush 注册ID：" + JPushHelper.getRegistrationID())
            }

            R.id.push_clear_all_push -> {
                log("清除JPush 所有通知：" + JPushHelper.clearAllNotifications())
            }

            R.id.push_set_allow_save_mode -> {
                log("设置JPush 省电模式：" + JPushHelper.setPowerSaveMode(this, true))
            }

            R.id.push_set_max_num -> {
                log("设置JPush 通知最大数量：" + JPushHelper.setLatestNotificationNumber(5))
            }

            R.id.push_send_local -> {
                val notification = JPushLocalNotification()
                notification.broadcastTime = System.currentTimeMillis() + 1000 * 5
                notification.builderId = 0
                notification.content = "本地通知内容"

                val map: MutableMap<String?, Any?> =
                    HashMap()
                map["name"] = "jpush"
                map["test"] = "111"
                val json = JSONObject(map)
                notification.extras = json.toString()

                notification.notificationId = 10
                notification.title = "本地通知标题"

                log("发送JPush 本地通知：${notification.toJSON()}")
                JPushHelper.addLocalNotification(notification)
            }

            R.id.push_clear_all_local -> {
                log("清除JPush 本地所有通知：" + JPushHelper.clearLocalNotifications())
            }

            R.id.push_clear_one_local -> {
                log("清除JPush 本地指定通知：" + JPushHelper.removeLocalNotification(10))
            }

            R.id.push_notice_state -> {
                log("获取手机通知状态开关：" + JPushHelper.isNotificationEnabled())
            }

            R.id.push_go_to_setting -> {
                log("跳转手机通知状态开关设置界面：" + JPushHelper.goToAppNotificationSettings())
            }

            R.id.push_init_crash -> {
                log("初始化JPush Crash上报：" + JPushHelper.initCrashHandler())
            }

            R.id.push_stop_crash -> {
                log("停止JPush Crash上报：" + JPushHelper.stopCrashHandler())
            }

            R.id.push_request_permission -> {
                log("请求JPush 所需要的权限：" + JPushHelper.requestPermission(this))
            }

            R.id.push_mobile -> {
                log("设置JPush 用户手机号：" + JPushHelper.setMobileNumber(0, "16623456789"))
            }

            R.id.push_set_tags -> {
                val tags = HashSet<String>()
                tags.add("年轻")
                tags.add("骚年")
                tags.add("宅")
                log("设置JPush 用户标签：" + JPushHelper.setTags(0, tags))
            }

            R.id.push_add_tags -> {
                val tags = HashSet<String>()
                tags.add("二次元")
                log("添加JPush 用户标签：" + JPushHelper.addTags(0, tags))
            }

            R.id.push_delete_tags -> {
                val tags = HashSet<String>()
                tags.add("二次元")
                log("删除JPush 用户标签：" + JPushHelper.deleteTags(0, tags))
            }

            R.id.push_clean_all_tags -> {
                log("清除JPush 用户所有标签：" + JPushHelper.cleanTags(0))
            }

            R.id.push_get_all_tags -> {
                log("获取JPush 用户所有标签：" + JPushHelper.getAllTags(0))
            }

            R.id.push_get_tag_state -> {
                log("获取JPush 用户与指定标签状态：" + JPushHelper.checkTagBindState(0, "二次元"))
            }

            R.id.push_get_valid_tags -> {
                val tags = HashSet<String>()
                tags.add("年轻")
                tags.add("骚年")
                tags.add("宅")
                tags.add("二货")
                log("获取JPush 用户有效的标签：" + Arrays.toString(JPushHelper.filterValidTags(tags).toTypedArray()))
            }
        }
    }

    private fun log(msg: String) {
        Log.i("MainActivity", msg)
    }

    override fun onPause() {
        super.onPause()
        JPushHelper.onPause(this)
    }

    override fun onResume() {
        super.onResume()
        JPushHelper.onResume(this)
    }
}
