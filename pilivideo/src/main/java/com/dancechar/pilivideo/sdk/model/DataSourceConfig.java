package com.dancechar.pilivideo.sdk.model;

/**
 * 播放数据源配置项
 * 在设置播放地址和切换播放地址时如果有需要可以配置该信息
 * @author netease
 */
public class DataSourceConfig {

    /**
     * 点播本地缓存配置信息
     */
    public CacheConfig cacheConfig;

    /**
     * 点播视频解密配置
     */
    public DecryptionConfig decryptionConfig;
}

