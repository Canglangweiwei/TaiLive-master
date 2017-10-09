package jc.geecity.taihua.me.component;

import dagger.Component;
import jc.geecity.taihua.LoginActivity;
import jc.geecity.taihua.WelcomeActivity;
import jc.geecity.taihua.app.AbsAppComponent;
import jc.geecity.taihua.me.module.LoginModule;

/**
 * 简单说就是，可以通过Component访问到Module中提供的依赖注入对象。
 * 假设，如果有两个Module，AModule、BModule，
 * 如果Component只注册了AModule，而没有注册BModule，那么BModule中提供的对象，无法进行依赖注入！
 */
@Component(dependencies = AbsAppComponent.class, modules = {LoginModule.class})
public interface LoginPresenterComponent {

    /**
     * <p>
     * 可以注册多个页面
     * </p>
     * 登录页面
     */
    void inject(LoginActivity loginActivity);

    void inject(WelcomeActivity welcomeActivity);
}
