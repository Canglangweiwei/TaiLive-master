package jc.geecity.taihua.app;

import dagger.Component;
import jc.geecity.taihua.base.AbsBaseApplication;

@Component()
public interface AbsAppComponent {
    void inject(AbsBaseApplication absBaseApplication);
}
