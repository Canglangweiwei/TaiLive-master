package jc.geecity.taihua;

import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jaydenxiao.common.commonutils.ToastUitl;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
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
import rx.functions.Action1;

/**
 * <p>
 * 用户登录
 * </p>
 * Created by Administrator on 2017/7/6 0006.
 */
@SuppressWarnings("ALL")
public class LoginActivity extends AbsBaseActivity implements LoginContract.View {

    @Bind(R.id.et_login_account)
    EditText mEditUsername;// 账号
    @Bind(R.id.et_login_pwd)
    EditText mEditPassword;// 密码

    @Bind(R.id.btn_login)
    Button btn_login;

    @Inject
    LoginPresenter mPresenter;

    private String username, password;

    @Override
    protected int initLayoutResID() {
        return R.layout.activity_login;
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
        UserBean userBean = AbsBaseApplication.get(this).getUserInfo();
        if (userBean != null) {
            mEditUsername.setText(userBean.getUserName());
            mEditPassword.setText(userBean.getPassword());
        }
        // 绑定页面
        mPresenter.attachView(this);
    }

    @Override
    protected void initListener() {
        /**
         * 检测用户名输入框
         */
        RxTextView.textChanges(mEditUsername).subscribe(new Action1<CharSequence>() {

            @Override
            public void call(CharSequence charSequence) {
                mPresenter.checkInput(charSequence.toString().trim(), mEditPassword.getText().toString().trim());
            }
        });

        /**
         * 检测密码输入框
         */
        RxTextView.textChanges(mEditPassword).subscribe(new Action1<CharSequence>() {

            @Override
            public void call(CharSequence charSequence) {
                mPresenter.checkInput(mEditUsername.getText().toString().trim(), charSequence.toString().trim());
            }
        });
    }

    /**
     * 开始登录
     */
    @OnClick({R.id.btn_login})
    void btnLogin() {
        username = mEditUsername.getText().toString().trim();
        password = mEditPassword.getText().toString().trim();
        startProgressDialog();
        mPresenter.login(username, "b505ffed0d024130");
    }

    /**
     * 返回登录按钮是否可点击
     *
     * @param allowedLogin 是否允许登录
     */
    @Override
    public void canLogin(boolean allowedLogin) {
        if (allowedLogin) {
            btn_login.setEnabled(true);
            btn_login.setBackground(getResources().getDrawable(R.drawable.selector_blue_maincolor));
        } else {
            btn_login.setEnabled(false);
            btn_login.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }

    /**
     * 获取用户信息
     *
     * @param userBean 用户信息
     */
    @Override
    public void getUserinfo(UserBean userBean) {
        stopProgressDialog();
        if (userBean == null)
            return;
        userBean.setUserName(username);
        userBean.setPassword("b505ffed0d024130");
        // 保存用户信息
        AbsBaseApplication.sApp.setUserInfo(userBean);
        // 跳转到主页面
        startNextActivity(null, HomeActivity.class, true);
    }

    /**
     * 返回登录失败信息
     *
     * @param throwable
     */
    @Override
    public void onFailureCallback(Throwable throwable) {
        stopProgressDialog();
        ToastUitl.showShort("请检查网络连接是否正常");
    }

    /**
     * 返回登录失败信息
     *
     * @param errorCode 错误代码
     * @param errorMsg  错误信息
     */
    @Override
    public void onFailureCallback(int errorCode, String errorMsg) {
        stopProgressDialog();
        ToastUitl.showShort(errorMsg);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
