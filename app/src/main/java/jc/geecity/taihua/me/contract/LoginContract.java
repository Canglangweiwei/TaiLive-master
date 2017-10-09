package jc.geecity.taihua.me.contract;

import jc.geecity.taihua.base.BasePresenter;
import jc.geecity.taihua.base.BaseView;
import jc.geecity.taihua.me.bean.UserBean;

@SuppressWarnings("ALL")
public interface LoginContract {

    interface View extends BaseView {
        void canLogin(boolean allowedLogin);

        void getUserinfo(UserBean userBean);
    }

    interface Presenter extends BasePresenter<View> {
        void login(String username, String password);
    }
}