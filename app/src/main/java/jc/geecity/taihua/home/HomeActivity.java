package jc.geecity.taihua.home;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import jc.geecity.taihua.R;
import jc.geecity.taihua.app.AbsAppComponent;
import jc.geecity.taihua.base.AbsBaseActivity;
import jc.geecity.taihua.base.AbsBaseApplication;
import jc.geecity.taihua.business.BusinessFragment;
import jc.geecity.taihua.community.CommunityFragment;
import jc.geecity.taihua.me.MeFragment;
import jc.geecity.taihua.service.ServiceFragment;

/**
 * 首页
 */
@SuppressWarnings("ALL")
public class HomeActivity extends AbsBaseActivity {

    @Bind(R.id.drawer_layout)
    LinearLayout drawerLayout;
    @Bind(R.id.bottom_navigation)
    AHBottomNavigation mAhBottomNavigation;

    private List<Fragment> fragments = new ArrayList<>();

    private int currentTabIndex;

    @Override
    protected int initLayoutResID() {
        return R.layout.activity_home;
    }

    @Override
    protected void parseIntent() {

    }

    @Override
    protected void setupComponent(AbsAppComponent component) {

    }

    @Override
    protected void initUi() {
        initBottomNav();
    }

    /**
     * 初始化底部导航栏
     */
    private void initBottomNav() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("首页", R.drawable.home_bottom_tab_icon_contact_highlight, R.color.main_color);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("服务", R.drawable.home_bottom_tab_icon_ding_highlight, R.color.main_color);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("社区", R.drawable.home_bottom_tab_icon_message_highlight, R.color.main_color);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("商家", R.drawable.home_bottom_tab_icon_work_highlight, R.color.main_color);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("我的", R.drawable.home_bottom_tab_icon_mine_highlight, R.color.main_color);

        mAhBottomNavigation.addItem(item1);
        mAhBottomNavigation.addItem(item2);
        mAhBottomNavigation.addItem(item3);
        mAhBottomNavigation.addItem(item4);
        mAhBottomNavigation.addItem(item5);

        // Set background color
        mAhBottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        // Disable the translation inside the CoordinatorLayout
        mAhBottomNavigation.setBehaviorTranslationEnabled(false);
        // Change colors
        mAhBottomNavigation.setAccentColor(getResources().getColor(R.color.main_color));// 选中的字体颜色和图片颜色
        mAhBottomNavigation.setInactiveColor(getResources().getColor(R.color.gray));     // 未选中的字体颜色和图片颜色
        // Force to tint the drawable (useful for font with icon for example)
        mAhBottomNavigation.setForceTint(true);
        // Force the titles to be displayed (against Material Design guidelines!)
        mAhBottomNavigation.setForceTitlesDisplay(true);
        // Set current item programmatically
        mAhBottomNavigation.setCurrentItem(0);
    }

    @Override
    protected void initDatas() {
        fragments.add(HomeFragment.newInstance("首页"));
        fragments.add(ServiceFragment.newInstance("服务"));
        fragments.add(CommunityFragment.newInstance("社区"));
        fragments.add(BusinessFragment.newInstance("商户"));
        fragments.add(MeFragment.newInstance("我的"));
        showFragment(fragments.get(0));
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }

    @Override
    protected void initListener() {
        mAhBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {

            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                if (currentTabIndex != position) {
                    FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                    trx.hide(fragments.get(currentTabIndex));
                    if (!fragments.get(position).isAdded()) {
                        trx.add(R.id.content, fragments.get(position));
                    }
                    trx.show(fragments.get(position)).commit();
                }
                currentTabIndex = position;
            }
        });
    }

    private long newTime;

    /**
     * 监听返回键
     */
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - newTime > 2000) {
            newTime = System.currentTimeMillis();
            Snackbar snackbar = Snackbar.make(drawerLayout, getString(R.string.press_twice_exit), Snackbar.LENGTH_SHORT);
            snackbar.setAction(R.string.exit_directly, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AbsBaseApplication.get(getApplicationContext()).finishAllActivity();
                }
            });
            snackbar.show();
        } else {
            moveTaskToBack(true);
        }
    }
}
