package com.seek;

import android.app.Activity;

import androidx.annotation.Nullable;
import android.os.Process;
import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    //新建一个list 来暂存activity
    public static List<Activity> activities = new ArrayList<>();

    //向list内添加活动
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }
    //从list内移除活动
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }
    //将list内存储的活动移除
    public static void  finishAll(){
        for (Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
                //杀掉当前进程的代码，确保完全退出
                //killProcess用于杀掉一个进程 需要进程id，通过mypid方法获取 只能杀死当前进程
                Process.killProcess(Process.myPid());
            }
        }
    }
}
