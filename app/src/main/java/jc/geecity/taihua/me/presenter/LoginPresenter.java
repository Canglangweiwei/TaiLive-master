package jc.geecity.taihua.me.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import jc.geecity.taihua.config.httpclient.UserInfoHttpPost;
import jc.geecity.taihua.me.Validator;
import jc.geecity.taihua.me.bean.LoginResultBean;
import jc.geecity.taihua.me.bean.UserBean;
import jc.geecity.taihua.me.contract.LoginContract;
import rx.Subscriber;

/**
 * 用户登录
 */
@SuppressWarnings("ALL")
public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
//    private Subscription mSubscription;
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
//        mSubscription = RetrofitFactory.get().getUserApiService().userLogin(username, password)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<BaseResponse>() {
//
//                    @Override
//                    public void call(BaseResponse baseResponse) {
//                        if (null == baseResponse) {
//                            view.onFailureCallback(1001, "用户信息获取失败");
//                            return;
//                        }
//                        if (!baseResponse.isSuccess()) {
//                            int code = baseResponse.getCode();
//                            String message = baseResponse.getMessage();
//                            view.onFailureCallback(code, message);
//                            return;
//                        }
//                        // 解析用户信息
//                        String dataJson = JsonUtils.toJson(baseResponse.getData());
//                        UserBean loginInfo = (UserBean) JsonUtils.fromJson(dataJson, UserBean.class);
//                        view.getUserinfo(loginInfo);
//                    }
//                }, new Action1<Throwable>() {
//
//                    @Override
//                    public void call(Throwable throwable) {
//                        view.onFailureCallback(throwable);
//                    }
//                });
        UserInfoHttpPost userInfoHttpPost = new UserInfoHttpPost();
        userInfoHttpPost.login(username, password, new Subscriber<LoginResultBean>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LoginResultBean loginResultBean) {
                        if (null == loginResultBean) {
                            view.onFailureCallback(1001, "用户信息获取失败");
                            return;
                        }
                        if (!loginResultBean.isSuccess()) {
                            int code = loginResultBean.getCode();
                            String message = loginResultBean.getMessage();
                            view.onFailureCallback(code, message);
                            return;
                        }
                        // 解析用户信息
                        UserBean loginInfo = loginResultBean.getData();
                        view.getUserinfo(loginInfo);
                    }
                }
        );
    }

    @Override
    public void attachView(@NonNull LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
//        if (mSubscription != null
//                && !mSubscription.isUnsubscribed()) {
//            mSubscription.unsubscribe();
//        }
        view = null;
    }
}
