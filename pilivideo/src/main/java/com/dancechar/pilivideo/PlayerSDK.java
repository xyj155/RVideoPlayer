package com.dancechar.pilivideo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.netease.neliveplayer.sdk.NELivePlayer;
import com.netease.neliveplayer.sdk.model.NEDynamicLoadingConfig;
import com.netease.neliveplayer.sdk.model.NESDKConfig;

import java.util.Map;

public class PlayerSDK {
    private static final String TAG = "PlayerSDK";
    public static void init(Context context){
        NESDKConfig config = new NESDKConfig();
        NELivePlayer.OnDataUploadListener mOnDataUploadListener = new NELivePlayer.OnDataUploadListener() {
            @Override
            public boolean onDataUpload(String url, String data) {
                Log.d(TAG, "onDataUpload url:" + url + ", data:" + data);
                return true;
            }

            @Override
            public boolean onDocumentUpload(String url, Map<String, String> params, Map<String, String> filepaths) {
                Log.d(TAG, "onDataUpload url:" + url + ", params:" + params+",filepaths:"+filepaths);
                return  true;
            }
        };
        config.dataUploadListener =  mOnDataUploadListener;
//        config.dynamicLoadingConfig.onDynamicLoadingListener = mOnDynamicLoadingListener;


        NELivePlayer.init(context, config);
    }
    private static NELivePlayer.OnDynamicLoadingListener mOnDynamicLoadingListener = new NELivePlayer.OnDynamicLoadingListener() {
        @Override
        public void onDynamicLoading(final NEDynamicLoadingConfig.ArchitectureType type, final boolean isCompleted) {

        }
    };
}
