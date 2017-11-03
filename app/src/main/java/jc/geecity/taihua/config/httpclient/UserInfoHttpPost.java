package jc.geecity.taihua.config.httpclient;

import com.jaydenxiao.common.basebean.BaseResponse;

import jc.geecity.taihua.config.RetrofitFactory;
import jc.geecity.taihua.me.bean.UserBean;
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
    public void login(String username, String password, Subscriber<BaseResponse<UserBean>> subscriber) {
        httpRetrofit.httpSubscribe(httpRetrofit.getUserApiService().userLogin(username, password), subscriber);
    }

    /**
     * test
     */
    public void testGroup(Subscriber<TestResultBean> subscriber) {
        httpRetrofit.httpSubscribe(httpRetrofit.getUserApiService().test(), subscriber);
    }
}
