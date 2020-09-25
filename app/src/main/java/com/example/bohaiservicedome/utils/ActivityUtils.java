package com.example.bohaiservicedome.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: created by JiangNan
 * @Date: 2020/3/15 14
 */
public class ActivityUtils {
    public static List<Activity> activitys = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activitys.add(activity);//把传入的Activity添加到List中
    }

    public static void removeActivity(Activity activity) {
        activitys.remove(activity);//根据传入的Activity来删除
    }

    public static void removeAll() {
        for (Activity activity : activitys) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 除了传来的Activity其他的全部删除
     * 可以传多个Activity
     */
    public static void removeAll(Class<?>... clazz) {
        boolean isExist = false;
        for (Activity act : activitys) {
            for (Class c : clazz) {
                if (act.getClass().isAssignableFrom(c)) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                if (!act.isFinishing()) {
                    act.finish();
                }
            } else {
                isExist = false;
            }
        }
    }

    /**
     * 从Activity集合查询, 传入的Activity是否存在
     * 如果存在就返回该Activity,不存在就返回null
     */
    public static Activity getActivity(Class<?> activity) {
        for (int i = 0; i < activitys.size(); i++) {
            // 判断是否是自身或者子类
            if (activitys.get(i).getClass().isAssignableFrom(activity)) {
                return activitys.get(i);
            }
        }
        return null;
    }

    public static Activity getTopActivity() {
        return activitys.get(activitys.size() - 1);
    }

}
