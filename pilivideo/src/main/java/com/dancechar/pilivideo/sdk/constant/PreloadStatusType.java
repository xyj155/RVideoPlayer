package com.dancechar.pilivideo.sdk.constant;

import com.netease.neliveplayer.sdk.constant.NEPreloadStatusType;

/**
 * 预调度结果状态.
 */
public class PreloadStatusType {
    /**
     * 等待预调度
     */
    int WAIT = NEPreloadStatusType.WAIT;
    /**
     * 正在预调度
     */
    int RUNNING = NEPreloadStatusType.RUNNING;
    /**
     * 已经完成预调度
     */
    int COMPLETE = NEPreloadStatusType.COMPLETE;
}
