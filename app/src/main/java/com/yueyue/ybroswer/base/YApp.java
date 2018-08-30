package com.yueyue.ybroswer.base;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

/**
 * author : yueyue on 2018/8/29
 * desc   :
 * quote  ：
 */
public class YApp extends Application {
    private static final String TAG=YApp.class.getSimpleName();
    public static boolean mPreinstallStaticTbs=false;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                Log.i(TAG, " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
                Log.i(TAG, " onCoreInitFinished ");
            }
        };

        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.i(TAG, "onDownloadFinish: "+i);
            }

            @Override
            public void onInstallFinish(int i) {
                Log.i(TAG, "onInstallFinish: "+i);
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.i(TAG, "onDownloadProgress: "+i);
            }
        });

        //x5内核初始化接口，调用preInit接口会导致initX5Enrironment中传入的callback无效。不要调用其他无关接口。
        QbSdk.setDownloadWithoutWifi(true);//允许在非WIFI条件下下载X5内核
        QbSdk.initX5Environment(getApplicationContext(), cb);

    }

}