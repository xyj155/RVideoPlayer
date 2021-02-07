package com.dancechar.pilivideo.common.utils;

import android.content.Context;
import android.util.Log;

import com.dancechar.pilivideo.common.log.LogUtil;


/**
 * @author netease
 */

public class CrashHandler {

    private static boolean installed = false;

    public static void installCrashHandler(final Context context) {
        if (installed) {
            return;
        }

        final Thread.UncaughtExceptionHandler def = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                try {
                    String stack = Log.getStackTraceString(ex);
                    if (stack.contains("com.netease")) {
                        // 只记录我们自己的崩溃
                        LogUtil.error("************* crash *************\n** Thread: " + context.getPackageName() + "/" + thread.getName() + " **", ex);
                    }
                } catch (Throwable ignored) {
                    // 这里面可不能崩
                }
                def.uncaughtException(thread, ex);
            }
        });

        installed = true;
    }
}
