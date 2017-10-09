package jc.geecity.taihua.config.api;

import com.jaydenxiao.common.basebean.BaseResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　魏巍 版权所有(c)2016<br>
 * <b>作者</b>：　　  weiwei<br>
 * <b>创建日期</b>：　17-11-13<br>
 * <b>描述</b>：　　　用户信息<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public interface IUserApiService {

    /**
     * 获取用户信息
     */
    @FormUrlEncoded
    @POST("common/login.php")
    Observable<BaseResponse> userLogin(@Field("username") String username, @Field("password") String password);
}
