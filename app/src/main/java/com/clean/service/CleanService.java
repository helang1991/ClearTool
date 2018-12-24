package com.clean.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.clean.utils.CleanCacheUtil;
import com.clean.utils.PackageUtil;
import com.clean.utils.TaskInfo;

import java.util.ArrayList;

/**
 * 清理缓存Service
 * 加上系统签名，才能真的达到效果
 * @author helang
 */
public class CleanService extends IntentService {
    private static final String TAG = "CleanService";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public CleanService() {
        super("CleanService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ArrayList<TaskInfo> infos = PackageUtil.getTaskInfos(getApplicationContext());
        if (infos != null){
            for (TaskInfo info:infos){
                CleanCacheUtil.delAppCache(getApplicationContext(),info.getPackageName(),
                        new CleanCacheUtil.ClearCacheObserver(){
                    @Override
                    public void onRemoveCompleted(String packageName, boolean succeeded) {
                        super.onRemoveCompleted(packageName, succeeded);
                        Log.d(TAG,"Clean:packageName="+packageName+";succeeded="+succeeded);
                    }
                });
            }
        }
    }

}
