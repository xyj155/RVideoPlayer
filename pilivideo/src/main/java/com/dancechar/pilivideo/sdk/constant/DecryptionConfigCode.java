package com.dancechar.pilivideo.sdk.constant;

import com.xyj155.neliveplayer.sdk.constant.NEDecryptionConfigCode;

/**
 * 解密选项
 * 只适用于点播
 */
public interface DecryptionConfigCode {

    /**
     * 不需要对视频进行解密
     */
    int CODE_DECRYPTION_NONE = NEDecryptionConfigCode.CODE_DECRYPTION_NONE;

    /**
     * 使用解密信息对视频进行解密
     */
    int CODE_DECRYPTION_INFO = NEDecryptionConfigCode.CODE_DECRYPTION_INFO;

    /**
     * 解密秘钥对视频进行解密
     */
    int CODE_DECRYPTION_KEY = NEDecryptionConfigCode.CODE_DECRYPTION_KEY;

}
