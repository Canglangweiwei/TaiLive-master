package jc.geecity.taihua.base;

import android.app.Activity;
import android.content.Context;

import com.jaydenxiao.common.base.BaseApplication;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.config.Constants;

import org.litepal.crud.DataSupport;

import java.util.Stack;

import jc.geecity.taihua.app.AbsAppComponent;
import jc.geecity.taihua.app.DaggerAbsAppComponent;
import jc.geecity.taihua.me.bean.UserBean;

@SuppressWarnings("ALL")
public class AbsBaseApplication extends BaseApplication {

    public static AbsBaseApplication sApp;

    private Stack<Activity> activityStack;// activity栈

    public static AbsBaseApplication get(Context context) {
        return (AbsBaseApplication) context.getApplicationContext();
    }

    private UserBean usInfo;                    // 用户信息

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        LogUtils.logInit(Constants.LOG_DEBUG);
        setApplicationComponent();
    }

    private AbsAppComponent absAppComponent;

    private void setApplicationComponent() {
        // Dagger开头的注入类DaggerAppComponent
        absAppComponent = DaggerAbsAppComponent.builder()
                .build();
    }

    /**
     * 获取AppComponent
     */
    public AbsAppComponent component() {
        if (absAppComponent == null) {
            setApplicationComponent();
        }
        return absAppComponent;
    }

    /**
     * 获取用户信息
     **/
    public void setUserInfo(UserBean userInfo) {
        this.usInfo = userInfo;
        // 清空数据表
        DataSupport.deleteAll(UserBean.class);
        if (userInfo == null) {
            return;
        }
        // 保存用户信息
        userInfo.save();
    }

    /**
     * 保存用户信息
     **/
    public UserBean getUserInfo() {
        if (usInfo == null) {
            usInfo = DataSupport.findFirst(UserBean.class);
        }
        return usInfo;
    }

    /**
     * 是否已经登录
     */
    public boolean isLogin() {
        return sApp.getUserInfo() != null;
    }

    /**
     * 把一个activity压入栈列中
     */
    public void pushActivityToStack(Activity actvity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(actvity);
    }

    /**
     * 获取栈顶的activity，先进后出原则
     */
    private Activity getLastActivityFromStack() {
        return activityStack.lastElement();
    }

    /**
     * 从栈列中移除一个activity
     */
    private void popActivityFromStack(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
                activity.finish();
                activityStack.remove(activity);
                activity = null;
            }
        }
    }

    /**
     * 退出所有activity
     */
    public void finishAllActivity() {
        if (activityStack != null) {
            while (activityStack.size() > 0) {
                Activity activity = getLastActivityFromStack();
                if (activity == null) {
                    break;
                }
                popActivityFromStack(activity);
            }
        }
    }
}
