package jc.geecity.taihua.test;

import java.util.Map;

import jc.geecity.taihua.xgohttp.XgoHttpClient;
import jc.geecity.taihua.xgohttp.protocol.BaseObjProtocol;
import rx.Observable;

/**
 * 测试接口
 * Created by ljb on 2016/3/23.
 */
public class TestObjProtocol extends BaseObjProtocol {

    private static final String URL = "http://0.89892528.cn:8700/test/jsonData.php";

    /**
     * Delete请求
     */
    public Observable<TestResultBean> test8989Date(Map<String, Object> params) {
        return createObservable(URL, XgoHttpClient.METHOD_GET, params, TestResultBean.class);
    }
}
