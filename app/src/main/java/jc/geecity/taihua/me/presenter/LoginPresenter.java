package jc.geecity.taihua.me.presenter;

import android.support.annotation.NonNull;

import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.commonutils.JsonUtils;

import javax.inject.Inject;

import jc.geecity.taihua.config.RetrofitFactory;
import jc.geecity.taihua.me.Validator;
import jc.geecity.taihua.me.bean.UserBean;
import jc.geecity.taihua.me.contract.LoginContract;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 用户登录
 */
@SuppressWarnings("ALL")
public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private Subscription mSubscription;
    private UserBean loginBean;
    private Validator validator;

    public void checkInput(String username, String password) {
        if (validator == null) {
            return;
        }
        view.canLogin(validator.validUsername(username) && validator.validPassword(password));
    }

    @Inject
    public LoginPresenter(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void login(String username, String password) {
        mSubscription = RetrofitFactory.get().getUserApiService().userLogin(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseResponse>() {

                    @Override
                    public void call(BaseResponse baseResponse) {
                        if (null == baseResponse) {
                            view.onFailureCallback(1001, "用户信息获取失败");
                            return;
                        }
                        if (baseResponse.isSuccess()) {
                            String dataJson = JsonUtils.toJson(baseResponse.getData());
                            view.getUserinfo((UserBean) JsonUtils.fromJson(dataJson, UserBean.class));
                            return;
                        } else {
                            view.onFailureCallback(baseResponse.getCode(), baseResponse.getMessage());
                            return;
                        }
                    }
                }, new Action1<Throwable>() {

                    @Override
                    public void call(Throwable throwable) {
                        view.onFailureCallback(throwable);
                    }
                });
    }

    @Override
    public void attachView(@NonNull LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        if (mSubscription != null
                && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        view = null;
    }
}
