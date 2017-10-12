package jc.geecity.taihua.test;

import jc.geecity.taihua.xgohttp.XgoHttpClient;
import jc.geecity.taihua.xgohttp.protocol.BaseJsonProtocol;
import rx.Observable;

/**
 * 测试接口
 * Created by ljb on 2016/3/23.
 */
public class TestJsonProtocol extends BaseJsonProtocol {

    private static final String URL = "http://0.89892528.cn:8700/test/jsonData.php";

    /**
     * Delete请求
     */
    public Observable<String> test8989Date() {
        return createObservable(URL, XgoHttpClient.METHOD_GET, null);
    }
}
