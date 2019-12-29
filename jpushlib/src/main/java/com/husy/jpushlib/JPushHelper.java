package com.husy.jpushlib;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.DefaultPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.data.JPushLocalNotification;

/**
 * @author husy
 * @date 2019/12/26
 */
public class JPushHelper {
    private static Context mContext;
    public static AbstractJPushListener mJPushListener;

// =================================初始化==========================================================

    /**
     * 初始化
     *
     * @param context Application
     * @param isDebug boolean
     */
    public static void init(Context context, boolean isDebug) {
        mContext = context.getApplicationContext();
        JPushInterface.setDebugMode(isDebug);
        JPushInterface.init(mContext);
    }

// ========================================推送相关API==============================================
// ================================================================================================

    /**
     * 停止推送服务。
     * <p>
     * 调用了本 API 后，JPush 推送服务完全被停止。具体表现为：
     * <p>
     * 收不到推送消息
     * 极光推送所有的其他 API 调用都无效，不能通过
     * JPushInterface.init 恢复，需要调用 resumePush 恢复。
     */
    public static void stopPush() {
        if (mContext == null) {
            return;
        }
        JPushInterface.stopPush(mContext);
    }

    /**
     * 恢复推送服务。
     * <p>
     * 调用了此 API 后，极光推送完全恢复正常工作。
     */
    public static void resumePush() {
        if (mContext == null) {
            return;
        }
        JPushInterface.resumePush(mContext);
    }

    /**
     * 用来检查 Push Service 是否已经被停止
     *
     * @return boolean
     */
    public static boolean isPushStoped() {
        if (mContext == null) {
            return false;
        }

        return JPushInterface.isPushStopped(mContext);
    }

    /**
     * 获取当前连接状态
     * 开发者可以使用此功能获取当前 Push 服务的连接状态
     * <p>
     * 当连接状态发生变化时（连接，断开），会发出一个广播，开发者可以在自定义的 Receiver 监听
     * cn.jpush.android.intent.CONNECTION 获取变化的状态，也可通过 API 主动获取。
     *
     * @return boolean
     */
    public static boolean getConnectionState() {
        if (mContext == null) {
            return false;
        }
        return JPushInterface.getConnectionState(mContext);
    }

    /**
     * 动态配置 channel，优先级比 AndroidManifest 里配置的高
     * channel 希望配置的 channel
     * 传 null 表示依然使用 AndroidManifest 里配置的 channel
     *
     * @param channel string
     */
    public static void setChannel(String channel) {
        if (mContext == null || TextUtils.isEmpty(channel)) {
            return;
        }
        JPushInterface.setChannel(mContext, channel);
    }

    /**
     * Set days
     * 0 表示星期天，
     * 1 表示星期一，
     * 以此类推。 （ 7 天制，Set 集合里面的 int 范围为 0 到 6 ）
     * <p>
     * set 的值为 null，则任何时间都可以收到通知，set 的 size 为 0，则表示任何时间都收不到通知。
     * <p>
     * int startHour 允许推送的开始时间 （ 24 小时制：startHour 的范围为 0 到 23 ）
     * <p>
     * int endHour 允许推送的结束时间 （ 24 小时制：endHour 的范围为 0 到 23 ）
     * <p>
     * <p>
     * Set<Integer> days = new HashSet<Integer>();
     * days.add(1);
     * days.add(2);
     * days.add(3);
     * days.add(4);
     * days.add(5);
     * JPushInterface.setPushTime(getApplicationContext(), days, 10, 23);
     * 此代码表示周一到周五、上午 10 点到晚上 23 点，都可以推送。
     *
     * @param weekDays  Set days
     * @param startHour int startHour
     * @param endHour   int endHour
     */
    public static void setPushTime(Set<Integer> weekDays, int startHour, int endHour) {
        if (mContext == null) {
            return;
        }
        JPushInterface.setPushTime(mContext, weekDays, startHour, endHour);
    }

    /**
     * 默认情况下用户在收到推送通知时，客户端可能会有震动，响铃等提示。
     * 但用户在睡觉、开会等时间点希望为“免打扰”模式，也是静音时段的概念。
     * <p>
     * 开发者可以调用此 API 来设置静音时段。如果在该时间段内收到消息，则：不会有铃声和震动。
     * <p>
     * int startHour 静音时段的开始时间 - 小时 （ 24 小时制，范围：0~23 ）
     * int startMinute 静音时段的开始时间 - 分钟（范围：0~59 ）
     * int endHour 静音时段的结束时间 - 小时 （ 24 小时制，范围：0~23 ）
     * int endMinute 静音时段的结束时间 - 分钟（范围：0~59 ）
     * <p>
     * setSilenceTime(getApplicationContext(), 22, 30, 8, 30);
     * 此代码表示晚上 10：30 点到第二天早上 8：30 点为静音时段。
     *
     * @param startHour   int startHour
     * @param startMinute int startMinute
     * @param endHour     int endHour
     * @param endMinute   int endMinute
     */
    public static void setSilenceTime(int startHour, int startMinute, int endHour, int endMinute) {
        if (mContext == null) {
            return;
        }
        JPushInterface.setSilenceTime(mContext, startHour, startMinute, endHour, endMinute);
    }

    /**
     * 在 Android 6.0 及以上的系统上，需要去请求一些用到的权限，JPush SDK 用到的一些需要请求如下权限，
     * 因为需要这些权限使统计更加精准，功能更加丰富，建议开发者调用。
     *
     * @param activity activity
     *                 <p>
     *                 "android.permission.READ_PHONE_STATE"
     *                 "android.permission.WRITE_EXTERNAL_STORAGE"
     *                 "android.permission.READ_EXTERNAL_STORAGE"
     *                 "android.permission.ACCESS_FINE_LOCATION"
     */
    public static void requestPermission(Activity activity) {
        if (activity == null) {
            return;
        }
        JPushInterface.requestPermission(activity);
    }

    /**
     * JPush SDK 开启和关闭省电模式，默认为关闭
     *
     * @param activity activity
     * @param enable   是否需要开启或关闭，true 为开启，false 为关闭
     */
    public static void setPowerSaveMode(Activity activity, boolean enable) {
        if (activity == null) {
            return;
        }
        JPushInterface.setPowerSaveMode(activity, enable);
    }

    /**
     * 调用此 API 设置手机号码。该接口会控制调用频率，频率为 10s 之内最多 3 次
     * 短信补充仅支持国内业务，号码格式为 11 位数字，有无 +86 前缀皆可。
     *
     * @param sequence     用户自定义的操作序列号，同操作结果一起返回，用来标识一次操作的唯一性
     * @param mobileNumber 手机号码。如果传 null 或空串则为解除号码绑定操作。
     *                     限制：只能以 “+” 或者 数字开头；后面的内容只能包含 “-” 和数字。
     */
    public static void setMobileNumber(int sequence, String mobileNumber) {
        if (mContext == null) {
            return;
        }
        JPushInterface.setMobileNumber(mContext, sequence, mobileNumber);
    }

    /**
     * 调用此 API 来取得应用程序对应的 RegistrationID。
     * 只有当应用程序成功注册到 JPush 的服务器时才返回对应的值，否则返回空字符串。
     * <p>
     * 通过 RegistrationID 进行点对点推送
     *
     * @return RegistrationID
     */
    public static String getRegistrationID() {
        if (mContext == null) {
            return "";
        }
        return JPushInterface.getRegistrationID(mContext);
    }

//===================================别名相关API====================================================
// ================================================================================================

    /**
     * 调用此 API 来设置别名。
     * <p>
     * 需要理解的是，这个接口是覆盖逻辑，而不是增量逻辑。即新的调用会覆盖之前的设置。
     * <p>
     * 参数定义
     * <p>
     * sequence
     * <p>
     * 用户自定义的操作序列号，同操作结果一起返回，用来标识一次操作的唯一性。
     * <p>
     * alias
     * <p>
     * 每次调用设置有效的别名，覆盖之前的设置。
     * 有效的别名组成：字母（区分大小写）、数字、下划线、汉字、特殊字符@!#$&*+=.|。
     * 限制：alias 命名长度限制为 40 字节。（判断长度需采用 UTF-8 编码）
     */
    public static void setAlias(int sequence, String alias) {
        if (mContext == null) {
            return;
        }
        JPushInterface.setAlias(mContext, sequence, alias);
    }

    /**
     * 调用此 API 来删除别名。
     *
     * @param sequence 用户自定义的操作序列号，同操作结果一起返回，用来标识一次操作的唯一性
     */
    public static void deleteAlias(int sequence) {
        if (mContext == null) {
            return;
        }
        JPushInterface.deleteAlias(mContext, sequence);
    }

    /**
     * 调用此 API 来查询别名。
     *
     * @param sequence 用户自定义的操作序列号，同操作结果一起返回，用来标识一次操作的唯一性
     */
    public static void getAlias(int sequence) {
        if (mContext == null) {
            return;
        }
        JPushInterface.getAlias(mContext, sequence);
    }


//====================================标签相关API===================================================
// ================================================================================================

    /**
     * 使用建议
     * 设置 tags 时，如果其中一个 tag 无效，则整个设置过程失败。
     * 如果 App 的 tags 会在运行过程中动态设置，并且存在对 JPush SDK tag 规定的无效字符，
     * <p>
     * 则有可能一个 tag 无效导致这次调用里所有的 tags 更新失败。
     * 这时你可以调用本方法 filterValidTags 来过滤掉无效的 tags，得到有效的 tags，
     * <p>
     * 再调用 JPush SDK 的 set tags / alias 方法。
     *
     * @param tags Set<String>
     * @return 有效的 tag 集合。
     */
    public static Set<String> filterValidTags(Set<String> tags) {
        if (mContext == null) {
            return null;
        }

        return JPushInterface.filterValidTags(tags);
    }

    /**
     * 调用此 API 来设置标签。
     * <p>
     * 需要理解的是，这个接口是覆盖逻辑，而不是增量逻辑。即新的调用会覆盖之前的设置
     *
     * @param sequence 用户自定义的操作序列号，同操作结果一起返回，用来标识一次操作的唯一性
     * @param tags     每次调用至少设置一个 tag，覆盖之前的设置，不是新增。
     *                 有效的标签组成：字母（区分大小写）、数字、下划线、汉字、特殊字符@!#$&*+=.|。
     *                 限制：每个 tag 命名长度限制为 40 字节，最多支持设置 1000 个 tag，且单次操作总长度不得超过 5000 字节。
     *                 （判断长度需采用 UTF-8 编码）
     *                 单个设备最多支持设置 1000 个 tag。App 全局 tag 数量无限制。
     */
    public static void setTags(int sequence, Set<String> tags) {
        if (mContext == null) {
            return;
        }
        JPushInterface.setTags(mContext, sequence, tags);
    }

    /**
     * 调用此 API 来新增标签
     *
     * @param sequence 用户自定义的操作序列号，同操作结果一起返回，用来标识一次操作的唯一性
     * @param tags     每次调用至少新增一个 tag。
     *                 有效的标签组成：字母（区分大小写）、数字、下划线、汉字、特殊字符@!#$&*+=.|。
     *                 限制：每个 tag 命名长度限制为 40 字节，最多支持设置 1000 个 tag，且单次操作总长度不得超过 5000 字节。
     *                 （判断长度需采用 UTF-8 编码）
     *                 单个设备最多支持设置 1000 个 tag。App 全局 tag 数量无限制。
     */
    public static void addTags(int sequence, Set<String> tags) {
        if (mContext == null) {
            return;
        }
        JPushInterface.addTags(mContext, sequence, tags);
    }

    /**
     * 调用此 API 来删除指定标签
     *
     * @param sequence 用户自定义的操作序列号, 同操作结果一起返回，用来标识一次操作的唯一性
     * @param tags     每次调用至少删除一个 tag。
     *                 有效的标签组成：字母（区分大小写）、数字、下划线、汉字、特殊字符@!#$&*+=.|。
     *                 限制：每个 tag 命名长度限制为 40 字节，最多支持设置 1000 个 tag，且单次操作总长度不得超过 5000 字节。
     *                 （判断长度需采用 UTF-8 编码）
     *                 单个设备最多支持设置 1000 个 tag。App 全局 tag 数量无限制
     */
    public static void deleteTags(int sequence, Set<String> tags) {
        if (mContext == null) {
            return;
        }
        JPushInterface.deleteTags(mContext, sequence, tags);
    }

    /**
     * 调用此 API 来清除所有标签
     *
     * @param sequence 用户自定义的操作序列号，同操作结果一起返回，用来标识一次操作的唯一性
     */
    public static void cleanTags(int sequence) {
        if (mContext == null) {
            return;
        }
        JPushInterface.cleanTags(mContext, sequence);
    }

    /**
     * 调用此 API 来查询所有标签
     *
     * @param sequence 用户自定义的操作序列号，同操作结果一起返回，用来标识一次操作的唯一性
     */
    public static void getAllTags(int sequence) {
        if (mContext == null) {
            return;
        }
        JPushInterface.getAllTags(mContext, sequence);
    }

    /**
     * 调用此 API 来查询指定 tag 与当前用户绑定的状态
     *
     * @param sequence 用户自定义的操作序列号，同操作结果一起返回，用来标识一次操作的唯一性
     * @param tag      被查询的 tag
     */
    public static void checkTagBindState(int sequence, String tag) {
        if (mContext == null) {
            return;
        }
        JPushInterface.checkTagBindState(mContext, sequence, tag);
    }


//===============================通知相关API=======================================================
// ===============================================================================================

    /**
     * 推送通知到客户端时，由 JPush SDK 展现通知到通知栏上。
     * <p>
     * 此 API 提供清除通知的功能，包括：清除所有 JPush 展现的通知（不包括非 JPush SDK 展现的）；
     */
    public static void clearAllNotifications() {
        if (mContext == null) {
            return;
        }
        JPushInterface.clearAllNotifications(mContext);
    }

    /**
     * 清除指定某个通知。
     *
     * @param notificationId 通知id
     *                       <p>
     *                       此 notificationId 来源于 intent 参数 JPushInterface.EXTRA_NOTIFICATION_ID
     */
    public static void clearNotificationById(int notificationId) {
        if (mContext == null) {
            return;
        }
        JPushInterface.clearNotificationById(mContext, notificationId);
    }

    /**
     * 当用户需要定制默认的通知栏样式时，则可调用此方法。
     * <p>
     * 极光 Push SDK 提供了 3 个用于定制通知栏样式的构建类：
     * <p>
     * BasicPushNotificationBuilder
     * Basic 用于定制 Android Notification 里的 defaults / flags / icon 等基础样式（行为）
     * CustomPushNotificationBuilder
     * 继承 Basic 进一步让开发者定制 Notification Layout
     * MultiActionsNotificationBuilder
     * 继承 DefaultPushNotificationBuilder 进一步让开发者定制 Notification Layout
     * 如果不调用此方法定制，则极光 Push SDK 默认的通知栏样式是：Android 标准的通知栏提示
     *
     * @param builder DefaultPushNotificationBuilder
     */
    public static void setDefaultPushNotificationBuilder(DefaultPushNotificationBuilder builder) {
        if (builder == null) {
            return;
        }
        JPushInterface.setDefaultPushNotificationBuilder(builder);
    }

    /**
     * 当开发者需要为不同的通知，指定不同的通知栏样式（行为）时，则需要调用此方法设置多个通知栏构建类。
     * <p>
     * 设置时，开发者自己维护 notificationBuilderId 这个编号，下发通知时使用 builder_id 指定该编号，
     * 从而 Push SDK 会调用开发者应用程序里设置过的指定编号的通知栏构建类，来定制通知栏样式。
     *
     * @param notificationBuilderId int
     * @param builder               BasicPushNotificationBuilder
     */
    public static void setPushNotificationBuilder(Integer notificationBuilderId,
                                                  BasicPushNotificationBuilder builder) {
        if (builder == null) {
            return;
        }
        JPushInterface.setPushNotificationBuilder(notificationBuilderId, builder);
    }

    /**
     * 通过极光推送，推送了很多通知到客户端时，如果用户不去处理，就会有很多保留在那里。
     * <p>
     * 从 v 1.3.0 版本开始 SDK 增加此功能，限制保留的通知条数。默认为保留最近 5 条通知。
     * <p>
     * 开发者可通过调用此 API 来定义为不同的数量。
     * <p>
     * 仅对通知有效。所谓保留最近的，意思是，如果有新的通知到达，之前列表里最老的那条会被移除。
     * 例如，设置为保留最近 5 条通知。假设已经有 5 条显示在通知栏，当第 6 条到达时，第 1 条将会被移除。
     * <p>
     * 本接口可以在 JPushInterface.init 之后任何地方调用。可以调用多次。SDK 使用最后调用的数值。
     *
     * @param maxNum int
     */
    public static void setLatestNotificationNumber(int maxNum) {
        JPushInterface.setLatestNotificationNumber(mContext, maxNum);
    }


// ===========================崩溃API==============================================================
// ================================================================================================

    /**
     * SDK 通过 Thread.UncaughtExceptionHandler 捕获程序崩溃日志，并在程序奔溃时实时上报
     * 如果实时上报失败则会在程序下次启动时发送到服务器。如需要程序崩溃日志功能可调用此方法。
     * <p>
     * 关闭 CrashLog 上报
     */
    public static void stopCrashHandler() {
        if (mContext == null) {
            return;
        }
        JPushInterface.stopCrashHandler(mContext);
    }

    /**
     * 开启 CrashLog 上报
     */
    public static void initCrashHandler() {
        if (mContext == null) {
            return;
        }
        JPushInterface.initCrashHandler(mContext);
    }


// ===========================本地通知API==========================================================
// ================================================================================================

    /**
     * 通过极光推送的 SDK，开发者只需要简单调用几个接口，便可以在应用中定时发送本地通知
     * <p>
     * 本地通知 API 不依赖于网络，无网条件下依旧可以触发
     * 本地通知与网络推送的通知是相互独立的，不受保留最近通知条数上限的限制
     * 本地通知的定时时间是自发送时算起的，不受中间关机等操作的影响
     *
     * @param notification 是本地通知对象；建议notificationId设置为正整数，为0或者负数时会导致本地通知无法清除
     */
    public static void addLocalNotification(JPushLocalNotification notification) {
        if (mContext == null) {
            return;
        }
        JPushInterface.addLocalNotification(mContext, notification);
    }

    /**
     * 移除指定的本地通知
     *
     * @param notificationId 是要移除的本地通知的 ID，注意notificationId为0或者负数的通知无法移除
     */
    public static void removeLocalNotification(long notificationId) {
        if (mContext == null) {
            return;
        }
        JPushInterface.removeLocalNotification(mContext, notificationId);
    }

    /**
     * 移除所有的本地通知，注意notificationId为0或者负数时通知无法移除
     */
    public static void clearLocalNotifications() {
        if (mContext == null) {
            return;
        }

        JPushInterface.clearLocalNotifications(mContext);
    }

// ==================================地理围栏功能API================================================
    // JPush SDK 提供地理围栏功能，当设备进入或离开相应的地理区域才触发通知或自定义消息。
    // 开发者可以通过此功能对SDK提供的地理围栏功能进行设置。
// ===============================================================================================

    /**
     * 设置地理围栏监控周期，最小3分钟，最大1天。默认为15分钟，
     * 当距离地理围栏边界小于1000米周期自动调整为3分钟。设置成功后一直使用设置周期，不会进行调整。
     *
     * @param interval 监控周期，单位是毫秒。
     */
    public static void setGeofenceInterval(long interval) {
        if (mContext == null) {
            return;
        }
        JPushInterface.setGeofenceInterval(mContext, interval);
    }

    /**
     * 设置最多允许保存的地理围栏数量，超过最大限制后，
     * 如果继续创建先删除最早创建的地理围栏。默认数量为10个，允许设置最小1个，最大100个。
     *
     * @param maxNumber 最多允许保存的地理围栏个数
     */
    public static void setMaxGeofenceNumber(int maxNumber) {
        if (mContext == null) {
            return;
        }
        JPushInterface.setMaxGeofenceNumber(mContext, maxNumber);
    }

    /**
     * 删除指定id的地理围栏
     *
     * @param geofenceid 地理围栏的id
     */
    public static void deleteGeofence(String geofenceid) {
        if (mContext == null) {
            return;
        }
        JPushInterface.deleteGeofence(mContext, geofenceid);
    }

    /**
     * 设置角标数字(目前仅支持华为手机)
     * 如果需要调用这个接口，还需要在AndroidManifest.xml里配置华为指定的权限
     * <uses-permission
     * android:name="com.huawei.android.launcher.permission.CHANGE_BADGE "/>
     *
     * @param num 新的角标数字，传入负数将会修正为0
     */
    public static void setBadgeNumber(int num) {
        if (mContext == null) {
            return;
        }
        JPushInterface.setBadgeNumber(mContext, num);
    }

// ===========================手机APP通知开关API====================================================
// ================================================================================================

    /**
     * 检查当前应用的通知开关是否开启
     * <p>
     * 返回结果：1表示开启，0表示关闭，-1表示检测失败
     */
    public static int isNotificationEnabled() {
        if (mContext == null) {
            return -1;
        }
        return JPushInterface.isNotificationEnabled(mContext);
    }

    /**
     * 跳转手机的应用通知设置页，可由用户操作开启通知开关
     */
    public static void goToAppNotificationSettings() {
        if (mContext == null) {
            return;
        }
        JPushInterface.goToAppNotificationSettings(mContext);
    }

// ===========================统计分析API===========================================================
// ================================================================================================

    public static void onResume(final Activity activity) {
        if (activity == null) {
            return;
        }
        JPushInterface.onResume(activity);
    }

    public static void onPause(final Activity activity) {
        if (activity == null) {
            return;
        }
        JPushInterface.onPause(activity);
    }

// ===========================设置监听器============================================================
// ================================================================================================
    /**
     * 设置回调监听
     * @param listener JPushListener
     */
    public static void setJPushListener(AbstractJPushListener listener) {
        mJPushListener = listener;
    }

    public static AbstractJPushListener getJPushListener() {
        return mJPushListener;
    }

    public static void cleanJPushListener() {
        mJPushListener = null;
    }
}
