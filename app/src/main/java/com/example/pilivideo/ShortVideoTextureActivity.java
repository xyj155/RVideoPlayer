package com.example.pilivideo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dancechar.pilivideo.observe.ShortPlayerObserver;

import com.dancechar.pilivideo.sdk.PlayerManager;
import com.dancechar.pilivideo.sdk.VodPlayer;
import com.dancechar.pilivideo.sdk.VodPlayerObserver;
import com.dancechar.pilivideo.sdk.model.AutoRetryConfig;
import com.dancechar.pilivideo.sdk.model.CacheConfig;
import com.dancechar.pilivideo.sdk.model.DataSourceConfig;
import com.dancechar.pilivideo.sdk.model.VideoBufferStrategy;
import com.dancechar.pilivideo.sdk.model.VideoOptions;
import com.dancechar.pilivideo.sdk.model.VideoScaleMode;
import com.dancechar.pilivideo.sdk.view.AdvanceTextureView;
import com.netease.neliveplayer.sdk.model.NEAutoRetryConfig;

import java.util.ArrayList;
import java.util.List;

public class ShortVideoTextureActivity extends Activity {
    private String TAG = "ShortVideoTextureActivity";

    private VerticalViewPager mViewPager;
    private VodPagerAdapter mPagerAdapter;
    private int mCurrentPosition = 0;
    private PlayerInfo mPlayerInfo;
    private List<String> mLiveUrlList;
    private boolean isErrorShow = false;
    private boolean isHardWare = false;
    private AutoRetryConfig autoRetryConfig;
    private int bufferPageCount = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_video_texture);
        parseIntent();
        initData();
        initView();
    }


    @Override
    public void onPause() {
        super.onPause();

        Log.i(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPagerAdapter.destroy();
    }

    private void parseIntent() {
        //接收MainActivity传过来的参数
        String mDecodeType = getIntent().getStringExtra("decode_type");

        if (mDecodeType != null && mDecodeType.equals("hardware")) {
            isHardWare = true;
        } else {
            isHardWare = false;
        }

    }

    private void initData() {
        mLiveUrlList = new ArrayList<>();
        mLiveUrlList.add("http://jdvodzopqjhvv.vod.126.net/jdvodzopqjhvv/a5977679-4bd6-4c32-a32d-b444fc2c3283.mp4");
        mLiveUrlList.add("http://jdvodzopqjhvv.vod.126.net/jdvodzopqjhvv/2f65ddc9-c46a-43fb-a8ef-41e9bc5fa688.mp4");
        mLiveUrlList.add("https://vodegkofxdv.vod.126.net/vodegkofxdv/pquGvpyd_1677633965_shd.mp4");
    }

    private void initView() {

        autoRetryConfig = new AutoRetryConfig();
        autoRetryConfig.count = 0;
        autoRetryConfig.delayDefault = 3000;
        autoRetryConfig.retryListener = onRetryListener;


        mViewPager = findViewById(R.id.view_pager);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "mVerticalViewPager, onPageSelected position = " + position);
                // 滑动界面，首先让之前的播放器暂停，并seek到0
                Log.i(TAG, "滑动后，让之前的播放器暂停，old position = " + (mPlayerInfo == null ? null : mPlayerInfo.position));
                if (mPlayerInfo != null) {
                    mPlayerInfo.vodPlayer.setBufferSize(20 * 1024 * 1024);
                    mPlayerInfo.vodPlayer.seekTo(0);
                    mPlayerInfo.vodPlayer.setMute(true);
                    mPlayerInfo.vodPlayer.pause();
                    mPlayerInfo.isPaused = true;
                }
                mCurrentPosition = position;
                mPlayerInfo = mPagerAdapter.findPlayerInfo(mCurrentPosition);
                if (mPlayerInfo != null && mPlayerInfo.isPaused) {
                    Log.i(TAG, "play start,transformPage position = " + mCurrentPosition);
                    //当前页面视频缓存50m
                    mPlayerInfo.vodPlayer.setBufferSize(50 * 1024 * 1024);
                    mPlayerInfo.vodPlayer.setMute(false);
                    mPlayerInfo.vodPlayer.start();
                    mPlayerInfo.isPaused = false;
                } else {
                    Log.i(TAG, "mPlayerInfo = " + mPlayerInfo);

                }
            }
        });

        mPagerAdapter = new VodPagerAdapter(mLiveUrlList);
        mViewPager.setOffscreenPageLimit(bufferPageCount);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2);
    }


    public class VodPagerAdapter extends PagerAdapter {
        private ArrayList<PlayerInfo> playerInfoList = new ArrayList<>();
        private List<String> liveUrlList;


        public VodPagerAdapter(List<String> liveUrlList) {
            this.liveUrlList = liveUrlList;
        }

        @Override
        public int getCount() {
            if (liveUrlList == null || liveUrlList.size() < 1) {
                return 0;
            }
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.i(TAG, "instantiateItem " + position);

            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.texture_item_layout, null);
            view.setId(position);

            // 获取此player
            PlayerInfo playerInfo = instantiatePlayerInfo(position);
            AdvanceTextureView advanceSingleTextureView = view.findViewById(R.id.live_texture);
            LinearLayout bufferLayout = view.findViewById(R.id.buffering_prompt);
            ImageView coverImage = view.findViewById(R.id.render_cover);
            bufferLayout.setVisibility(View.VISIBLE);
            coverImage.setVisibility(View.VISIBLE);
            playerInfo.advanceSingleTextureView = advanceSingleTextureView;
            playerInfo.bufferLayout = bufferLayout;
            playerInfo.coverImage = coverImage;
            playerInfo.vodPlayer.setupRenderView(advanceSingleTextureView, VideoScaleMode.FIT);

            playerInfo.vodPlayer.setMute(true);
            if (mCurrentPosition == position) {
                mPlayerInfo = playerInfo;
            }
            container.addView(view);

            return view;
        }

        protected PlayerInfo instantiatePlayerInfo(final int position) {
            Log.i(TAG, "instantiatePlayerInfo " + position);
            PlayerInfo playerInfo = new PlayerInfo();
            VideoOptions options = new VideoOptions();
            options.bufferSize = 50 * 1024 * 1024;
            options.hardwareDecode = isHardWare;
            options.bufferStrategy = VideoBufferStrategy.ANTI_JITTER;
            options.loopCount = -1;
            options.isAccurateSeek = true;
            DataSourceConfig dataSourceConfig = new DataSourceConfig();
            dataSourceConfig.cacheConfig= new CacheConfig(true,null);
            options.dataSourceConfig = dataSourceConfig;
            VodPlayer vodPlayer = PlayerManager.buildVodPlayer(ShortVideoTextureActivity.this, liveUrlList.get(position % liveUrlList.size()), options);
            VodPlayerObserver playerObserver = new ShortPlayerObserver(position) {


                @Override
                public void onError(int code, int extra) {
                    Log.i(TAG, "player mCurrentPosition:" + mCurrentPosition + " ,onError code:" + code + " extra:" + extra);
                    pauseOtherPlayerInfo(mCurrentPosition);
                    if (!isErrorShow && !findPlayerInfo(mCurrentPosition).vodPlayer.isPlaying()) {
                        isErrorShow = true;
                        //发生错误时，这里Demo会弹出对话框提示错误，用户可以在此根据错误码进行重试或者其他操作
                        AlertDialog.Builder build = new AlertDialog.Builder(ShortVideoTextureActivity.this);
                        build.setTitle("播放错误").setMessage("错误码：" + code)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        isErrorShow = false;
                                        PlayerInfo curPlayInfo = findPlayerInfo(mCurrentPosition);
                                        curPlayInfo.coverImage.setVisibility(View.GONE);
                                        curPlayInfo.bufferLayout.setVisibility(View.GONE);
                                    }
                                })
                                .setCancelable(false)
                                .show();
                    }
                }

                @Override
                public void onFirstVideoRendered() {
                    Log.i(TAG, "onFirstVideoRendered，mCurrentPosition->" + mCurrentPosition + ",getPosition()->" + getPosition());
                    PlayerInfo curPlayInfo = findPlayerInfo(getPosition());
                    curPlayInfo.coverImage.setVisibility(View.GONE);
                    curPlayInfo.bufferLayout.setVisibility(View.GONE);
                    curPlayInfo.vodPlayer.setupRenderView(curPlayInfo.advanceSingleTextureView, VideoScaleMode.FIT);
                    if (mCurrentPosition == getPosition()) {
                        curPlayInfo.vodPlayer.setMute(false);
                        return;
                    }
                    pausePlayerInfo(getPosition());
                }

            };
            vodPlayer.setAutoRetryConfig(autoRetryConfig);
            vodPlayer.registerPlayerObserver(playerObserver, true);
            Log.i(TAG, "play start,instantiatePlayerInfo position" + position + "，vodPlayer" + vodPlayer);
            vodPlayer.start();
            playerInfo.playURL = liveUrlList.get(position % liveUrlList.size());
            playerInfo.vodPlayer = vodPlayer;
            playerInfo.playerObserver = playerObserver;
            playerInfo.position = position;
            playerInfoList.add(playerInfo);
            return playerInfo;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.i(TAG, "destroyItem" + position);
            destroyPlayerInfo(position);
            container.removeView((View) object);
        }

        public void destroy() {
            for (PlayerInfo playerInfo : playerInfoList) {
                if (playerInfo == null || playerInfo.vodPlayer == null) {
                    return;
                }
                playerInfo.vodPlayer.registerPlayerObserver(playerInfo.playerObserver, false);
                playerInfo.vodPlayer.setMute(true);
                playerInfo.vodPlayer.stop();
                playerInfo.vodPlayer = null;
            }
            playerInfoList.clear();
            playerInfoList = null;
            autoRetryConfig = null;
        }


        public void destroyPlayerInfo(int position) {
            PlayerInfo playerInfo = findPlayerInfo(position);
            if (playerInfo == null || playerInfo.vodPlayer == null) {
                return;
            }
            playerInfo.vodPlayer.stop();
            playerInfo.vodPlayer = null;
            playerInfoList.remove(playerInfo);

            Log.i(TAG, "play stop,destroyPlayerInfo position " + position);
        }

        public void pausePlayerInfo(int position) {
            PlayerInfo playerInfo = findPlayerInfo(position);
            if (playerInfo == null || playerInfo.vodPlayer == null) {
                return;
            }
            if (!playerInfo.isPaused) {
                playerInfo.vodPlayer.setMute(true);
                playerInfo.vodPlayer.pause();
                playerInfo.isPaused = true;
                Log.i(TAG, "play pause,pausePlayerInfo position " + position);
            }

        }

        public void pauseOtherPlayerInfo(int position) {
            for (PlayerInfo playerInfo : playerInfoList) {
                if (playerInfo.position != position && playerInfo.vodPlayer.isPlaying()) {
                    playerInfo.vodPlayer.setMute(true);
                    //其他页面视频缓存20m
                    playerInfo.vodPlayer.setBufferSize(20 * 1024 * 1024);
                    playerInfo.vodPlayer.pause();
                    playerInfo.isPaused = true;
                    Log.i(TAG, "play pause,pausePlayerInfo position " + playerInfo.position);
                }
            }

        }


        public PlayerInfo findPlayerInfo(int position) {
            for (int i = 0; i < playerInfoList.size(); i++) {
                PlayerInfo playerInfo = playerInfoList.get(i);
                if (playerInfo.position == position) {
                    return playerInfo;
                }
            }
            return null;
        }

    }

    private NEAutoRetryConfig.OnRetryListener onRetryListener = new NEAutoRetryConfig.OnRetryListener() {

        @Override
        public void onRetry(int what, int extra) {
            showToast("开始重试，错误类型："+what+ "，附加信息："+extra);
        }
    };

    private class PlayerInfo {
        public VodPlayer vodPlayer;
        public VodPlayerObserver playerObserver;
        public String playURL;
        public AdvanceTextureView advanceSingleTextureView;
        public LinearLayout bufferLayout;
        public ImageView coverImage;
        public int position;
        public boolean isPaused;
    }

    private void showToast(String msg) {
        Log.d(TAG, "showToast" + msg);
        try {
            Toast.makeText(ShortVideoTextureActivity.this, msg, Toast.LENGTH_SHORT).show();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

}
