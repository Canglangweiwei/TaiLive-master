package jc.geecity.taihua.me;

import android.text.TextUtils;

/**
 * 用户名和密码校验工具
 */
public class Validator {

    public Validator() {
        super();
    }

    public boolean validUsername(String username) {
        return !TextUtils.isEmpty(username);
    }

    public boolean validPassword(String password) {
        return !TextUtils.isEmpty(password);
    }
}
