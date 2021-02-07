package com.dancechar.pilivideo.sdk.model;


import com.dancechar.pilivideo.sdk.LivePlayer;

/**
 * 状态信息类
 * @author netease
 */

public class StateInfo {

    private LivePlayer.STATE state;
    private int causeCode;

    public StateInfo(LivePlayer.STATE state, int causeCode) {
        this.state = state;
        this.causeCode = causeCode;
    }

    /**
     * 当前播放器状态
     *
     * @return 状态
     */
    public LivePlayer.STATE getState() {
        return state;
    }

    /**
     * 导致该状态的原因
     *
     * @return 错误码或者停止的原因，参考 {@link }
     */
    public int getCauseCode() {
        return causeCode;
    }
}
