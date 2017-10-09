package jc.geecity.taihua.me.module;

import dagger.Module;
import dagger.Provides;
import jc.geecity.taihua.me.Validator;
import jc.geecity.taihua.me.contract.LoginContract;

@Module
public class LoginModule {

    private final LoginContract.View view;
    private Validator validator;

    /**
     * 构造器
     *
     * @param view      页面
     * @param validator 账户、密码验证工具
     */
    public LoginModule(LoginContract.View view, Validator validator) {
        this.view = view;
        this.validator = validator;
    }

    /**
     * 在@Module注解的类中，使用@Provider注解，说明提供依赖注入的具体对象
     */
    @Provides
    public Validator provideValidator() {
        return validator;
    }
}
