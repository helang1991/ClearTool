package com.clean.service;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;

import com.clean.utils.PackageUtil;
import com.clean.utils.TaskInfo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 清理内存Service,一定要加上系统签名，才能真的达到效果
 * @author helang
 */
public class SpeedService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public SpeedService() {
        super("SpeedService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ArrayList<TaskInfo> infos = PackageUtil.getTaskInfos(getApplicationContext());
        if (infos != null){
            for (TaskInfo info:infos){
                killAppByPackage(getApplicationContext(),info.getPackageName());
            }
        }
    }

    /**
     * Kill掉某个正在运行的应用
     * @param context
     * @param packageToKill
     */
    private void killAppByPackage(Context context, String packageToKill) {

        List<ApplicationInfo> packages;
        PackageManager pm;
        pm = context.getPackageManager();
        //get a list of installed apps.
        packages = pm.getInstalledApplications(0);

        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        //利用killBackgroundProcesses方法(API > 8)
        for (ApplicationInfo packageInfo : packages) {

            if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                continue;
            }
            if (packageInfo.packageName.equals(packageToKill)
                    && mActivityManager != null) {
                mActivityManager.killBackgroundProcesses(packageInfo.packageName);
            }
        }

        //利用反射调用forceStopPackage方法
        //需要android.permission.FORCE_STOP_PACKAGES权限
        //需要系统签名
        try {
            Method method = Class.forName("android.app.ActivityManager").getMethod("forceStopPackage", String.class);
            method.invoke(mActivityManager, packageToKill);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
