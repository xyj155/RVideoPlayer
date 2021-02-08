package com.dancechar.pilivideo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dancechar.pilivideo.controller.CompleteController;
import com.dancechar.pilivideo.sdk.LivePlayerObserver;
import com.dancechar.pilivideo.sdk.PlayerManager;
import com.dancechar.pilivideo.sdk.VodPlayer;
import com.dancechar.pilivideo.sdk.model.AutoRetryConfig;
import com.dancechar.pilivideo.sdk.model.CacheConfig;
import com.dancechar.pilivideo.sdk.model.DataSourceConfig;
import com.dancechar.pilivideo.sdk.model.MediaInfo;
import com.dancechar.pilivideo.sdk.model.StateInfo;
import com.dancechar.pilivideo.sdk.model.VideoBufferStrategy;
import com.dancechar.pilivideo.sdk.model.VideoOptions;
import com.dancechar.pilivideo.sdk.model.VideoScaleMode;
import com.dancechar.pilivideo.sdk.view.AdvanceTextureView;
import com.dancechar.pilivideo.weight.AVLoadingIndicatorView;
import com.dancechar.pilivideo.weight.ZzHorizontalProgressBar;
import com.netease.neliveplayer.sdk.NELivePlayer;
import com.netease.neliveplayer.sdk.model.NEAutoRetryConfig;

import java.util.Locale;
import java.util.Map;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;

public class RVideoView extends FrameLayout implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, LivePlayerObserver, NELivePlayer.OnCurrentRealTimeListener, NELivePlayer.OnCurrentPositionListener, NELivePlayer.OnCompletionListener, AudioManager.OnAudioFocusChangeListener {
    public final static int MEDIA_CODEC_SW_DECODE = 0;
    public final static int MEDIA_CODEC_HW_DECODE = 1;
    public final static int MEDIA_CODEC_AUTO = 2;
    public static final int ASPECT_RATIO_ORIGIN = 0;
    public static final int ASPECT_RATIO_FIT_PARENT = 1;
    public static final int ASPECT_RATIO_PAVED_PARENT = 2;
    public static final int ASPECT_RATIO_16_9 = 3;
    public static final int ASPECT_RATIO_4_3 = 4;
    private ImageView ivThumbs, ivPoster;
    private LinearLayout flContainer;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private FrameLayout flParent, flComplete;
    private ViewGroup.LayoutParams layoutParams1s;
    private CheckBox cbPlay;
    private SeekBar seekBar;
    private TextView tvCurrent, tvTotal;
    private ZzHorizontalProgressBar sbBottomBar;
    private String playUrl = "";
    private LinearLayout llBottomControl;
    private AdvanceTextureView surfaceView;
    private VideoOptions options;
    private AutoRetryConfig autoRetryConfig;
    private VodPlayer vodPlayer;
    private AudioManager mAudioManager;
    private boolean isPrepare = false;

    //    原始尺寸、适应屏幕、全屏铺满、16:9、4:3
    public RVideoView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public RVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        View.inflate(context, R.layout.layout_video_container, this);
        avLoadingIndicatorView = findViewById(R.id.progressBar);
        flParent = findViewById(R.id.parent);
        surfaceView = new AdvanceTextureView(context);

        llBottomControl = findViewById(R.id.ll_bottom_control);
        cbPlay = findViewById(R.id.cb_play);
        flComplete = findViewById(R.id.fl_complete);
        sbBottomBar = findViewById(R.id.sb_bottom_video);
        seekBar = findViewById(R.id.sb_video);
        tvCurrent = findViewById(R.id.tv_current);
        tvTotal = findViewById(R.id.tv_total);

        ivThumbs = findViewById(R.id.iv_thumbs);
        flContainer = findViewById(R.id.ll_container);
        ivPoster = findViewById(R.id.iv_poster);
        initConfig();
        initVideo();

    }

    public void initConfig() {

        autoRetryConfig = new AutoRetryConfig();
        autoRetryConfig.count = 3;
        autoRetryConfig.delayDefault = 100;
        autoRetryConfig.delayArray = new long[]{100, 500, 3000};
        autoRetryConfig.retryListener = onRetryListener;


    }

    private void initVideo() {


        options = new VideoOptions();
        options.bufferSize = 20 * 1024 * 1024;
        options.hardwareDecode = true;
        options.bufferStrategy = VideoBufferStrategy.ANTI_JITTER;
        options.loopCount = 0;
        options.isAccurateSeek = true;
        options.isSyncOpen = true;
        options.isPlayLongTimeBackground = true;

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.cacheConfig = new CacheConfig(true, null);
        options.dataSourceConfig = dataSourceConfig;

    }

    private NEAutoRetryConfig.OnRetryListener onRetryListener = new NEAutoRetryConfig.OnRetryListener() {

        @Override
        public void onRetry(int what, int extra) {
            Toast.makeText(getContext(), "开始重试，错误类型：" + what + "，附加信息：" + extra, Toast.LENGTH_SHORT).show();
        }
    };

    private static String generateTime(long position) {
        int totalSeconds = (int) (position / 1000);

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes,
                    seconds).toString();
        } else {
            return String.format(Locale.US, "%02d:%02d", minutes, seconds)
                    .toString();
        }
    }

    private void setBackColor(int color) {
        setBackgroundColor(color == 0 ? Color.BLACK : color);
    }


    private static final String TAG = "RVideoView";

    /**
     * 设置宽高比
     *
     * @param w
     * @param h
     */
    public void setRatio(int w, int h) {
        layoutParams1s = (ViewGroup.LayoutParams) flParent.getLayoutParams();
        int screenWidth = ScreenUtil.getScreenWidth(getContext());
        layoutParams1s.height = (int) ((screenWidth * 1f / w * 1f) * h * 1f);
        flParent.setLayoutParams(layoutParams1s);
    }

    /***
     * 设置播放速度
     * @param speed
     */
    public void setSpeed(int speed) {
        vodPlayer.setPlaybackSpeed(speed);
    }


    /***
     *
     *设置播放源
     * @param playUrl
     */
    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
        vodPlayer = PlayerManager.buildVodPlayer(getContext(), playUrl, options);
        vodPlayer.setAutoRetryConfig(autoRetryConfig);
        vodPlayer.setupRenderView(surfaceView, VideoScaleMode.FIT);
        vodPlayer.setMute(false);

        initListener();
    }


    public void setPlayUrl(String playUrl, Map<String, String> headers) {

//        try {
//            NEDataSourceConfig config = new NEDataSourceConfig();
//            config.headers = headers;
//            vodPlayer.setDataSource(playUrl, config);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void reset() {
        if (vodPlayer != null) {
            vodPlayer.reset();
            isPrepare = false;
            vodPlayer.seekTo(0);
            flParent.post(new Runnable() {
                @Override
                public void run() {
                    sbBottomBar.setProgress(0);
                    seekBar.setProgress(0);
                    cbPlay.setChecked(false);
                    cbPlay.setVisibility(VISIBLE);
                }
            });
        }
    }

    /**
     * 停止播放
     */
    public void stop() {
        if (vodPlayer != null) {
            cbPlay.setChecked(false);
            vodPlayer.stop();
        }

    }

    /***
     *暂停播放
     */
    public void pause() {
        if (vodPlayer != null && vodPlayer.isPlaying()) {
            vodPlayer.pause();
            cbPlay.setChecked(false);
        }

    }

    public static void setCurrentVideo(RVideoView mVideo) {
//        if (instance != null) instance.release();
//        instance = mVideo;
    }


    /**
     * 开始播放
     */
    public void start() {
        setCurrentVideo(this);
        if (flContainer.getChildCount() == 0) {
            flContainer.removeAllViews();
            flContainer.addView(surfaceView);
            Log.i(TAG, "start: ==================");
        }
        mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (vodPlayer != null) {
            showControl();
            isSeekBarShow = true;
            cbPlay.setChecked(true);
            vodPlayer.start();
            flComplete.setVisibility(GONE);
            hideControl();
        }

    }

    public Context getApplicationContext() {
        Context context = getContext();
        if (context != null) {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext != null) {
                return applicationContext;
            }
        }
        return context;
    }

    /**
     * 释放播放器
     */
    public void release() {
        if (vodPlayer != null) {
            vodPlayer.stop();
            isPrepare = false;
            vodPlayer.seekTo(0);
            flParent.post(new Runnable() {
                @Override
                public void run() {
                    sbBottomBar.setProgress(0);
                    seekBar.setProgress(0);
                    cbPlay.setChecked(false);
                    cbPlay.setVisibility(VISIBLE);
                }
            });

            vodPlayer = null;
        }

    }

    public void setThumbs(String thumbs) {
        Glide.with(this).asBitmap().load(thumbs)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transition(withCrossFade())
                .into(new CustomTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ivPoster.setImageBitmap(resource);
                        Bitmap blurBitmap = ImageFilter.blurBitmap(getContext(), resource, 25f);
                        ivThumbs.setImageBitmap(blurBitmap);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }


    private void hideShowView(View view, int loadingVisible) {
        view.setVisibility(loadingVisible);
    }


    public void setCompleteController(CompleteController completeController) {
        flComplete.removeAllViews();
        flComplete.addView(completeController);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cb_play) {
            if (vodPlayer != null && vodPlayer.isPlaying())
                pause();
            else
                start();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser && vodPlayer != null) {
            vodPlayer.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        release();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.i(TAG, "onTouchEvent: =====");
            if (isPrepare) {
                if (isSeekBarShow) {
                    hideControlNoTime();
                } else {
                    showControl();
                    hideControl();
                }
            } else {
                start();
            }


        }
        return true;
    }

    private boolean isSeekBarShow = false;

    private void hideControl() {
        flParent.postDelayed(new Runnable() {
            @Override
            public void run() {
                isSeekBarShow = false;
                cbPlay.setVisibility(GONE);
                llBottomControl.setVisibility(GONE);
                sbBottomBar.setVisibility(VISIBLE);
            }
        }, 3000);

    }

    private void hideControlNoTime() {
        isSeekBarShow = false;
        cbPlay.setVisibility(GONE);
        llBottomControl.setVisibility(GONE);
        sbBottomBar.setVisibility(VISIBLE);
    }

    private void showControl() {
        isSeekBarShow = true;
        cbPlay.setVisibility(VISIBLE);
        llBottomControl.setVisibility(VISIBLE);
        sbBottomBar.setVisibility(GONE);
    }


    @Override
    public void onPreparing() {
        Log.i(TAG, "onPreparing: ");
        isPrepare = true;

    }

    @Override
    public void onPrepared(MediaInfo mediaInfo) {
        isPrepare = true;
        long duration = vodPlayer.getDuration();
        tvTotal.setText(generateTime(duration));
        sbBottomBar.setMax((int) duration);
        seekBar.setMax((int) duration);
        flComplete.setVisibility(GONE);
    }

    @Override
    public void onError(int code, int extra) {
        Log.d(TAG, "onError() called with: code = [" + code + "], extra = [" + extra + "]");

    }

    @Override
    public void onFirstVideoRendered() {
        Log.i(TAG, "onFirstVideoRendered: ");
    }

    @Override
    public void onFirstAudioRendered() {
        Log.d(TAG, "onFirstAudioRendered() called");
    }

    @Override
    public void onBufferingStart() {

    }

    @Override
    public void onBufferingEnd() {
        Log.d(TAG, "onBufferingEnd() called");
    }

    @Override
    public void onBuffering(int percent) {
        Log.d(TAG, "onBuffering() called with: percent = [" + percent + "]");
    }

    @Override
    public void onVideoDecoderOpen(int value) {

    }

    @Override
    public void onStateChanged(StateInfo stateInfo) {

    }

    @Override
    public void onHttpResponseInfo(int code, String header) {
        Log.d(TAG, "onHttpResponseInfo() called with: code = [" + code + "], header = [" + header + "]");
    }

    @Override
    public void onCompletion(NELivePlayer neLivePlayer) {
        cbPlay.setChecked(false);
        if (flComplete.getChildCount() != 0) {
            flComplete.setVisibility(VISIBLE);
        }

    }

    private void initListener() {
        vodPlayer.registerPlayerCurrentRealTimestampListener(100, this, true);
        vodPlayer.registerPlayerObserver(this, true);
        vodPlayer.registerPlayerCurrentPositionListener(100, this, true);
        vodPlayer.registerCompleteListener(this, true);
        cbPlay.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onCurrentRealTime(long l) {
        tvCurrent.setText(generateTime(l));
        seekBar.setProgress((int) l);
        isPrepare = true;
        sbBottomBar.setProgress((int) l);
    }

    @Override
    public void onCurrentPosition(long l) {

    }


    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
//                reset();
                release();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                try {
                    start();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                break;
        }
    }
}
