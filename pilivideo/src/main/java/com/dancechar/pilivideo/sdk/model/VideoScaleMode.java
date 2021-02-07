package com.dancechar.pilivideo.sdk.model;

/**
 * 视频渲染时的缩放/裁减模式
 * <p>
 * @author netease
 */
public enum VideoScaleMode {
    NONE, // 原始大小
    FIT,  // 按比例拉伸，有一边会贴黑边
    FILL, // 全屏，画面可能会变形
    FULL  // 按比例拉伸至全屏，有一边会被裁剪
}
