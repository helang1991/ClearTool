package com.clean.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.util.ArrayList;
import java.util.List;

/**
 * @author helang
 */
public class PackageUtil {

    /**
     * 获取系统运行的进程信息
     * 并且排除系统和自身进程
     * @param context
     * @return
     */
    public static ArrayList<TaskInfo> getTaskInfos(Context context) {
        // 应用程序管理器
        ActivityManager am = (ActivityManager) context
                .getSystemService(context.ACTIVITY_SERVICE);
        // 应用程序包管理器
        PackageManager pm = context.getPackageManager();

        // 获取正在运行的程序信息, 就是以下粗体的这句代码,获取系统运行的进程     要使用这个方法，需要加载
        List<AndroidAppProcess> processInfos = AndroidProcesses.getRunningAppProcesses();

        ArrayList<TaskInfo> taskinfos = new ArrayList<>();
        // 遍历运行的程序,并且获取其中的信息
        for (AndroidAppProcess processInfo : processInfos) {
            TaskInfo taskinfo = new TaskInfo();
            // 应用程序的包名
            String packname = processInfo.name;
            taskinfo.setPackageName(processInfo.name);
            try {
                // 获取应用程序信息
                ApplicationInfo applicationInfo = pm.getApplicationInfo(
                        packname, 0);
                String name = applicationInfo.loadLabel(pm).toString();
                taskinfo.setName(name);
                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    // 用户进程
                    taskinfo.setUserTask(true);
                } else {
                    // 系统进程
                    taskinfo.setUserTask(false);
                }
            } catch (PackageManager.NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                // 系统内核进程 没有名称
                taskinfo.setName(packname);
            }
            if (!taskinfo.getPackageName().equals(context.getPackageName())){//排除自己的包名
                taskinfos.add(taskinfo);
            }

        }
        return taskinfos;
    }
}
