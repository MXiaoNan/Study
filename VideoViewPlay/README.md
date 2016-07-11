
# VideoView

## 知识点

1. VideoView

2. 自定义View继承VideoView，重写其中的onMeasure方法来设定视频的宽高

### 功能：

* 播放手机里面的指定视频

* 播放资源文件中的指定视频

* 播放网络视频

* 到达指定位置暂停、继续、停止播放

* 得到当前播放的时间

* 得到视频总时长

* 对视频播放进行监听，提示播放完毕或者播放出错

* 添加可以点击横竖屏的按钮

* 在配置清单中添加了如下代码，保证切换横屏时不会重新走生命周期

		<activity
            android:name=".MainActivity"
            android:configChanges="orientation|keyboardHidden|screenSize">

