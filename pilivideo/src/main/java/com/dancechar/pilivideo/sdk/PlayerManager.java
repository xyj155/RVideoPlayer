package com.dancechar.pilivideo.sdk;

import android.content.Context;

import com.dancechar.pilivideo.core.player.PlayerManagerImpl;
import com.dancechar.pilivideo.sdk.model.SDKInfo;
import com.dancechar.pilivideo.sdk.model.SDKOptions;
import com.dancechar.pilivideo.sdk.model.VideoOptions;
import com.xyj155.neliveplayer.sdk.NEMediaDataSource;

import java.util.ArrayList;
import java.util.Map;

public class PlayerManager {
    /**
     * 初始化SDK,使用播放器时必须先进行初始化才能进行后续操作。
     *
     * @param context 调用上下文
     * @param config sdk配置信息
     */
    public static void init(Context context, SDKOptions config) {
        PlayerManagerImpl.init(context,config);
    }

    /**
     * 获取是否已经准备好so库文件
     * 仅在初始化 init 接口中配置动态加载才能使用该接口查询
     * @return 是否准备好
     */
    public static boolean isDynamicLoadReady(){
        return PlayerManagerImpl.isDynamicLoadReady();
    }

    /**
     * 获取SDK信息
     * @return SDK信息实例
     */
    public static SDKInfo getSDKInfo(Context context){
        return PlayerManagerImpl.getSDKInfo(context);
    }


    /**
     * 构造播放器实例对象
     *
     * @param context   上下文
     * @param videoPath 视频资源路径
     * @param options   播放选项
     * @return 播放器实例对象
     */
    public static LivePlayer buildLivePlayer(Context context, String videoPath, VideoOptions options) {
        return PlayerManagerImpl.buildLivePlayer(context, videoPath, options);
    }

    /**
     * 构造播放器实例对象
     *
     * @param context   上下文
     * @param videoPath 视频资源路径
     * @param options   播放选项
     * @return 播放器实例对象
     */
    public static VodPlayer buildVodPlayer(Context context, String videoPath, VideoOptions options) {
        return PlayerManagerImpl.buildVodPlayer(context, videoPath, options);
    }

    /**
     * 构造播放器实例对象
     *
     * @param context   上下文
     * @param mediaDataSource 自定义视频资源
     * @param options   播放选项
     * @return 播放器实例对象
     */
    public static VodPlayer buildVodPlayer(Context context, NEMediaDataSource mediaDataSource, VideoOptions options) {
        return PlayerManagerImpl.buildVodPlayer(context, mediaDataSource, options);
    }

    /**
     * 添加预加载拉流链接地址
     * @param urls 拉流链接地址
     */
    public static void addPreloadUrls(ArrayList<String> urls){
        PlayerManagerImpl.addPreloadUrls(urls);
    }

    /**
     * 移除预加载拉流链接地址
     * @param urls 拉流链接地址
     */
    public static void removePreloadUrls(ArrayList<String> urls){
        PlayerManagerImpl.removePreloadUrls(urls);

    }

    /**
     * 查询预加载拉流链接地址的结果信息
     * @return Map<String,Integer> String是链接地址，Integer是状态,状态码参考 {@link com.xyj155.neliveplayer.sdk.constant.NEPreloadStatusType}
     */
    public static Map<String,Integer> queryPreloadUrls(){
        return  PlayerManagerImpl.queryPreloadUrls();
    }

    /**
     * 刷新全部拉流地址预加载信息
     */
    public static void refreshPreloadUrls(){
        PlayerManagerImpl.refreshPreloadUrls();
    }
}
