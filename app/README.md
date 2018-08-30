# YBroswer
YBrower是基于腾讯X5内核实现的浏览器DEMO

## 关于JS调用Andoird
这里不涉及这问题，我已经单独做了一个Demo：[simplebam/JSWidthAndroidNative: JS调用Android（Java）接口 ](https://github.com/simplebam/JSWidthAndroidNative)



## X5调用内核的方法
```
没有X5内核 setTbsListener  onInstallFinish: 600
下载X5内核 setTbsListener   onInstallFinish: 600 onDownloadProgress:1 -> 99 onDownloadFinish:100 -> onInstallFinish: 200 需要杀死进程再进入（完全退出应用）
有X5内核(完整的)  setTbsListener   onInstallFinish: 610 （正常情况）
正在下载X5内核就退出应用 setTbsListener ->  onInstallFinish: 610 -> onDownloadProgress: 接着之前的进度
下载完成X5内核之后，就加载网页，还是不能调用X5内核加载网页，需要完全退出

正在下载内核的时候，只要应用还没被杀死，就会一直下


首次安装应用：
1、x5VwebView没有loadUrl时候  QbSdk.PreInitCallback :onCoreInitFinished  -> onViewInitFinished is false
2、使用x5WebView加载loadUrl    onInstallFinish: 610 -> onDownloadProgress 一直到99 -> onDownloadFinish: 100 -> onInstallFinish: 200
3、下载X5内核完成的话，没有退出应用直接调用  onInstallFinish: 610
4、双杀退出应用再进来（此时下载内核完成）onInstallFinish: 610 -> onCoreInitFinished  -> onViewInitFinished is true
```


## 开发中遇到的问题
* 手机安装了微信、QQ正式版、QQ空间还是没有启动X5内核的解决办法：
  * 微信无法加载X5：
     * (1)如果发现无法下载，请杀掉微信所有进程、再将系统时间调整＋1天以上，再次打开网页触发下载；
     * (2)或者在聊天窗口输入// deletetbs 并发送，删除旧内核，再重启微信以下载 TBS 内核。
  * 手 Q 无法加载X5：
     * 如果发现无法下载，请请杀掉手Ｑ所有进程、再将系统时间调整＋1天以上，再次打开网页触发下载。
* 64位手机无法加载x5:"libmttwebview.so” is 32-bit instead of 64-bit
  * [64位手机无法加载x5 - 腾讯浏览服务]( https://x5.tencent.com/tbs/technical.html#/detail/sdk/1/34cf1488-7dc2-41ca-a77f-0014112bcab7)
  * jniLibs文件夹应该放在module/src/main/ 下
  * 不要~~在gradle.properties文件中加上Android.useDeprecatedNdk=true；~~ ，测试在华为M5上使用会报错
  * 不要添加下面代码:
  ```
  //android studio默认so文件加载目录为:src/main/jniLibs
                  //如在module的build.gradle按照如下方式,自定义了so文件加载目录请确保对应目录下只有armeabi目录
                  //    sourceSets {
                  //        main{
                  //            jniLibs.srcDirs = ['libs']
                  //        }
                  //    }~~

  ```
