package com.zpc.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * activity 与 Fragment管理
 */
public class AppAFManager {
    private static List<Activity> activityStack;
    private static List<Fragment> fragmentList;

    public static AppAFManager getInstance() {
        return ViewManagerHolder.sInstance;
    }

    private static class ViewManagerHolder {
        private static final AppAFManager sInstance = new AppAFManager();
    }

    private AppAFManager() {
    }

    public void addFragment(int index, Fragment fragment) {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        fragmentList.add(index, fragment);
    }


    public Fragment getFragment(int index) {
        if (fragmentList != null) {
            return fragmentList.get(index);
        }
        return null;
    }


    public List<Fragment> getAllFragment() {
        if (fragmentList != null) {
            return fragmentList;
        }
        return null;
    }


    /**
     * 添加指定Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new CopyOnWriteArrayList<Activity>();
        }
        activityStack.add(activity);
    }


    /**
     * 获取当前Activity
     */
    public Activity currentActivity() {
        Activity activity = activityStack.get(0);
        return activity;
    }


    /**
     * 结束当前Activity
     */
    public void finishActivity() {
        Activity activity = activityStack.get(0);
        finishActivity(activity);
    }


    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }


    /**
     * 结束activity
     *
     * @param cls      指定
     * @param isCrrent 仅仅结束指定的
     */
    public void finishActivity(Class<?> cls, boolean isCrrent) {
        for (Activity activity : activityStack) {
            if (isCrrent) {
                //仅仅结束指定
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                    return;
                }
            } else {
                //摒弃指定
                if (activity.getClass().equals(cls)) {
                    continue;
                }
                finishActivity(activity);
            }
        }
    }


    /**
     * 结束activity
     *
     * @param list      指定
     * @param isCrrent 仅仅结束指定的
     */
    public void finishActivity(List<Class<?>> list, boolean isCrrent) {
        for (Activity activity : activityStack) {
            if (isCrrent) {
                //仅仅结束指定
                for (Class<?> cls : list) {
                    if (activity.getClass().equals(cls)) {
                        finishActivity(activity);
                        break;
                    }
                }
            } else {
                int cousor = 0;
                for (Class<?> cls : list) {
                    //摒弃指定
                    if (activity.getClass().equals(cls)) {
                        cousor++;
                        break;
                    }
                }
                if (cousor == 0) {
                    finishActivity(activity);
                }
            }
        }
    }

    /**
     * 结束全部的Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }


    /**
     * 退出应用程序
     */
    public void exitApp(Context context) {
        try {
            finishAllActivity();
            //杀死后台进程需要在AndroidManifest中声明android.permission.KILL_BACKGROUND_PROCESSES；
            android.app.ActivityManager activityManager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses(context.getPackageName());
            //System.exit(0);
        } catch (Exception e) {
            Log.e("ActivityManager", "app exit" + e.getMessage());
        }
    }

    /**
     * 得到当前的活动的 实体
     */
    public Activity getCurrentActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)){
                return activity;
            }
        }
        return null;
    }
}
