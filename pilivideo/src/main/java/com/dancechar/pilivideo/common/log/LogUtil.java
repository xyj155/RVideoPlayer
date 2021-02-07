package com.dancechar.pilivideo.common.log;

import android.content.Context;
import android.util.Log;

import com.dancechar.pilivideo.common.utils.StorageUtil;


/**
 * @author netease
 */

public class LogUtil {

    private static boolean inited = false;

    public static void init(Context context) {
        if (context == null) {
            return;
        }

        if (inited) {
            return;
        }

        final String logDir = StorageUtil.getAppCacheDir(context.getApplicationContext()) + "/app/playerkit/log";
        LogImpl.init(logDir, Log.DEBUG);
        inited = true;
        Log.i("player_log", "log init done, log dir=" + logDir );
    }

    public static void info(String msg) {
        if (!inited) {
            return;
        }

        i("player_info", msg);
    }

    public static void debug(String msg) {
        if (!inited) {
            return;
        }

        d("player_info", msg);
    }


    public static void ui(String msg) {
        if (!inited) {
            return;
        }

        i("player_ui", msg);
    }

    public static void app(String msg) {
        if (!inited) {
            return;
        }

        i("player_app", msg);
    }

    public static void error(String msg) {
        if (!inited) {
            return;
        }

        e("player_error", msg);
    }

    public static void error(String msg, Throwable e) {
        if (!inited) {
            return;
        }

        e("player_error", msg, e);
    }


    public static final void v(String tag, String msg) {
        LogImpl.v(tag, buildMessage(msg));
    }

    public static final void v(String tag, String msg, Throwable thr) {
        LogImpl.v(tag, buildMessage(msg), thr);
    }

    public static final void d(String tag, String msg) {
        LogImpl.d(tag, buildMessage(msg));
    }

    public static final void d(String tag, String msg, Throwable thr) {
        LogImpl.d(tag, buildMessage(msg), thr);
    }

    public static final void i(String tag, String msg) {
        LogImpl.i(tag, buildMessage(msg));
    }

    public static final void i(String tag, String msg, Throwable thr) {
        LogImpl.i(tag, buildMessage(msg), thr);
    }

    public static final void w(String tag, String msg) {
        LogImpl.w(tag, buildMessage(msg));
    }

    public static final void w(String tag, String msg, Throwable thr) {
        LogImpl.w(tag, buildMessage(msg), thr);
    }

    public static final void w(String tag, Throwable thr) {
        LogImpl.w(tag, buildMessage(""), thr);
    }

    public static final void e(String tag, String msg) {
        LogImpl.e(tag, buildMessage(msg));
    }

    public static final void e(String tag, String msg, Throwable thr) {
        LogImpl.e(tag, buildMessage(msg), thr);
    }
    private static String buildMessage(String msg) {
        return msg;
    }

}
