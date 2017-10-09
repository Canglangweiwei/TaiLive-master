package jc.geecity.taihua;

import android.text.TextUtils;
import android.widget.ImageView;

import com.jaydenxiao.common.commonutils.ToastUitl;

import javax.inject.Inject;

import butterknife.Bind;
import jc.geecity.taihua.app.AbsAppComponent;
import jc.geecity.taihua.base.AbsBaseActivity;
import jc.geecity.taihua.base.AbsBaseApplication;
import jc.geecity.taihua.home.HomeActivity;
import jc.geecity.taihua.me.Validator;
import jc.geecity.taihua.me.bean.UserBean;
import jc.geecity.taihua.me.component.DaggerLoginPresenterComponent;
import jc.geecity.taihua.me.contract.LoginContract;
import jc.geecity.taihua.me.module.LoginModule;
import jc.geecity.taihua.me.presenter.LoginPresenter;

/**
 * 欢迎页面
 */
@SuppressWarnings("ALL")
public class WelcomeActivity extends AbsBaseActivity implements LoginContract.View {

    // 用户名
    private String username;
    // 密码
    private String password;

    @Bind(R.id.image_launcherBg)
    ImageView image_launcherBg;// 背景图片

    @Inject
    LoginPresenter loginPresenter;

    @Override
    protected int initLayoutResID() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void parseIntent() {

    }

    @Override
    protected void setupComponent(AbsAppComponent component) {
        DaggerLoginPresenterComponent.builder()
                .absAppComponent(component)
                .loginModule(new LoginModule(this, new Validator()))
                .build()
                .inject(this);
    }

    @Override
    protected void initUi() {

    }

    @Override
    protected void initDatas() {
        loginPresenter.attachView(this);

        UserBean userBean = AbsBaseApplication.get(this).getUserInfo();
        if (userBean == null) {
            startNextActivity(null, LoginActivity.class, true);
            return;
        }
        username = userBean.getUserName();
        password = userBean.getPassword();
        if (TextUtils.isEmpty(username)
                || TextUtils.isEmpty(password)) {
            startNextActivity(null, LoginActivity.class, true);
            return;
        }
        loginPresenter.login(username, password);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void canLogin(boolean allowedLogin) {

    }

    @Override
    public void getUserinfo(UserBean userBean) {
        if (userBean == null)
            return;
        userBean.setUserName(username);
        userBean.setPassword("b505ffed0d024130");
        // 保存用户信息
        AbsBaseApplication.sApp.setUserInfo(userBean);
        // 跳转到主页面
        startNextActivity(null, HomeActivity.class, true);
    }

    @Override
    public void onFailureCallback(Throwable throwable) {
        ToastUitl.showShort("请检查网络连接是否正常");
        startNextActivity(null, LoginActivity.class, true);
    }

    @Override
    public void onFailureCallback(int errorCode, String errorMsg) {
        ToastUitl.showShort(errorMsg);
        startNextActivity(null, LoginActivity.class, true);
    }

    @Override
    protected void onDestroy() {
        loginPresenter.detachView();
        super.onDestroy();
    }
}
