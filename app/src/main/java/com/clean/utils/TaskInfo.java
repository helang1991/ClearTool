package com.clean.utils;

/**
 * 进程实体
 * @author helang
 */
public class TaskInfo {

    private String name;// 应用程序的名字
    private String packageName;// 应用程序的包名
    private boolean userTask;// true 用户进程 false 系统进程

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }


    public boolean isUserTask() {
        return userTask;
    }

    public void setUserTask(boolean userTask) {
        this.userTask = userTask;
    }
}