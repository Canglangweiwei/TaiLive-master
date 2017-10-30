package jc.geecity.taihua.config.httpclient;

import jc.geecity.taihua.config.RetrofitFactory;
import jc.geecity.taihua.me.bean.LoginResultBean;
import jc.geecity.taihua.test.TestResultBean;
import rx.Subscriber;

public class UserInfoHttpPost {

    private RetrofitFactory httpRetrofit;

    public UserInfoHttpPost() {
        httpRetrofit = RetrofitFactory.get();
    }

    /**
     * 登录
     */
    public void login(String username, String password, Subscriber<LoginResultBean> subscriber) {
        httpRetrofit.httpSubscribe(httpRetrofit.getUserApiService().userLogin(username, password), subscriber);
    }

    /**
     * test
     */
    public void testGroup(Subscriber<TestResultBean> subscriber) {
        httpRetrofit.httpSubscribe(httpRetrofit.getUserApiService().test(), subscriber);
    }
}
