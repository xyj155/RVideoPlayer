
## 基于[网易云视频播放器内核](http://dev.yunxin.163.com/docs/product/)，实现了可以满足日常开发的种种功能需求。 (请仔细阅读下方各项说明，大多数问题可在下方找到解答)。


类型 | 功能
-------- | ---
**缓存**|**边播边缓存，使用了VideoCache。**
**协议**|**h263\4\5、Https、concat、rtsp、hls、rtmp、crypto、mpeg等等。**
**倍速播放**|**可以实现自定义的倍速播放**
**帧图**|**视频第一帧、视频帧截图功能。**
**播放**|**列表播放、列表连续播放、视频本身rotation旋转属性、快播和慢播、网络视频加载速度。**
**画面**|**调整显示比例:默认、16:9、4:3、填充；播放时旋转画面角度（0,90,180,270）；镜像旋转。**
**播放**|**单例播放、多个同时播放、视频列表滑动自动播放、列表切换详情页面无缝播放。**
**广告**|**片头广告、跳过广告支持、中间插入广告功能。**
**完成播放自定义**|**可以在视频播放完成后弹出自定义的布局或者广告内容**
**更多**|**暂停前后台切换不黑屏；**

## 使用方法

### 1、添加module到项目中；
    implementation project(":pilivideo")
### 2、创建RVideoView到布局当中；
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      xmlns:tools="http://schemas.android.com/tools"
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      tools:context=".MainActivity">

      <com.dancechar.pilivideo.RVideoView
        android:layout_width="match_parent"
        android:id="@+id/videoView"
        android:layout_height="450dp" />

    </LinearLayout>
### 3、设置播放地址以及封面图片、宽高比
    播放地址：#.setPlayUrl("http://dy-video-upload/f0c5be07c3ff61e30c0f37b033d07d91.mp4");
    视频封面：#.setThumbs("https://f0c5be07c3ff61e30c0f37b033d07d91.mp4?x-oss-process=video/snapshot,t_1000,f_png,w_500,m_fast");
### 4、开始播放
    #.start()
 
 
 ## 其他设置：
 #### 1、设置播放完成布局【类似播放完成弹出分享面板】：
    #.setCompleteController(new Test(this));
 #### 2、设置倍速：
    #.setSpeed(int)
 #### 3、暂停播放：
    #.pause();
 #### 4、销毁播放器：
    #.release();


## 有什么问题可以直接微信加我 要什么功能我直接push代码 本人最近要考研 没什么工夫去维护这个框架 等我什么时候腾出手再说 本人微信:x1789780841 
