# **LennonLib**使用帮助
### 概述
LennonLib是基于[XDroidMvp](https://github.com/limedroid/XDroidMvp)二次简单封装的项目框架,并集成了阿里[ARouter](https://github.com/alibaba/ARouter),使用AndroidX支持库
### 使用注意事项
###### 1、Application
食用时，请自定义Application，并继承BaseApplication
###### 2、Activity
如非特殊需求，请尽量继承BaseActivty
###### 3、Fragment
如非必要，建议继承BaseFragment
###### 4、Cache
本项目中的ACache基于XDroid修改而来，将DiskCache和MemoryCache进行了整合，可直接食用  
DataCache是为简单的页面快捷缓存而存在的，优先读取本地数据并同时发起网络请求，当网络请求成功后，再刷新本地数据并通知ui界面刷新
DataListCache是为简单的分页快捷加载而存在的，按照页数读取本地数据并同时发起网络请求，当请求成功后，再刷新本地数据
###### 5、LennonProvider(utill)
食用本项目时，请在Application中的onCreat()中调用Lennon.registProvider()  
注意，须在super.onCreat()前调用
###### 6、网络请求
retrofit实现网络请求的interface不能使用kotlin来写，部分情况下会导致报错
###### 7、语音播报（speech)
集成了讯飞在线语音播报，使用时请自行更换讯飞在线合成sdk（讯飞sdk与APP是绑定的）
###### 8、扫码和生成二维码([zxing](https://github.com/zxing/zxing))
集成zxing
###### 9、推送（push)
集成jpush和小米推送
###### 10、视频录制（record)待完善

###### 11、图片缩放([SubsamplingScaleImageView](https://github.com/davemorrissey/subsampling-scale-image-view))(AndroidX适配)

###### 12、[xrecycler](https://github.com/limedroid/ARecyclerView)(AndroidX适配)

###### 13、voicerecorder 语音录制

###### 14、nativelogger(日志工具,可生成日志文件)