# ClearTool及其清理动画
android 6.0 clean memory and cache</br>
仅针对Android 6.0以上系统，其他低版本，请百度或者google自行解决（真的有很多方案）
## 使用说明
由于6.0系统对某些敏感目录的限制，因此，需要利用系统签名
## 关键方法
### 需要利用到aidl和反射
IPackageStatsObserver.aidl,IPackageDataObserver.aidl等aidl文件</br>
PackageManager.java等</br>
## 动画效果展示
![image](https://github.com/helang1991/ClearTool/blob/master/gif/11.gif)</br>
