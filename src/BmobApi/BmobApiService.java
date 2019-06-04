package BmobApi;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.*;


public interface BmobApiService {


    /**
     * 查询数据
     *
     * @param tableName
     * @param objectId
     * @return
     */
    @GET("/1/classes/{tableName}/{objectId}")
    Call<JsonObject> get(@Path("tableName") String tableName, @Path("objectId") String objectId);

    /**
     * 通过条件查询数据
     *
     * @param tableName
     * @return
     */
    @GET("/1/classes/{tableName}")
    Call<JsonObject> getobjectid(@Path("tableName") String tableName, @Query(value = "where", encoded = true) String username);//打开自动转换

    /**
     * 更新数据
     *
     * @param tableName
     * @param objectId
     * @param jsonObject
     * @return
     */
    @PUT("/1/classes/{tableName}/{objectId}")
    Call<JsonObject> update(@Path("tableName") String tableName, @Path("objectId") String objectId, @Body JsonObject jsonObject);
    /**
     * 新增一行数据
     *
     * @param tableName
     * @param Object
     * @return
     */
    @POST("/1/classes/{tableName}")
    Call<JsonObject> insert(@Path("tableName") String tableName, @Body Object Object);

}

