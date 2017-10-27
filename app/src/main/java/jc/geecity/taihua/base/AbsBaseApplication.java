package jc.geecity.taihua.base;

import android.content.Context;

import com.jaydenxiao.common.base.BaseApplication;
import com.jaydenxiao.common.commonutils.XgoLog;

import org.litepal.crud.DataSupport;

import jc.geecity.taihua.app.AbsAppComponent;
import jc.geecity.taihua.app.DaggerAbsAppComponent;
import jc.geecity.taihua.config.AppConfig;
import jc.geecity.taihua.me.bean.UserBean;

@SuppressWarnings("ALL")
public class AbsBaseApplication extends BaseApplication {

    public static AbsBaseApplication sApp;

    public static AbsBaseApplication get(Context context) {
        return (AbsBaseApplication) context.getApplicationContext();
    }

    private UserBean usInfo;                    // 用户信息

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        XgoLog.logInit(AppConfig.LOG_DEBUG);
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
}
