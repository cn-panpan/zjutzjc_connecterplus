package BmobApi;


import Bean.State;
import Data.LoginDataSave;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class BmobDemo {
    public static void UpdatePCstate(String flag,Boolean mboolean) {
        BmobApiService bmobApiService;
        bmobApiService = Bmob.getInstance().api();
        Call<JsonObject> call;
        String mTableName = "State";
        String objectId = LoginDataSave.prNode.get("zjutzjc_connecterplus_objectId","").toString();
        JsonObject object = new JsonObject();
        object.addProperty(flag,mboolean);
        call = bmobApiService.update(mTableName, objectId, object);
        JsonObject ret = commit(call);
    }

    public static JsonElement Query(String item) {
        JsonElement jsonElement;
        Call<JsonObject> call;
        BmobApiService bmobApiService;
        bmobApiService = Bmob.getInstance().api();
        String mTableName = "State";
        String objectId = LoginDataSave.prNode.get("zjutzjc_connecterplus_objectId","").toString();
        call = bmobApiService.get(mTableName, objectId);
        JsonObject ret = commit(call);
        jsonElement = ret.get(item);
        return jsonElement;
    }

    public static String GetObjectIdQuery(String username) {
        Call<JsonObject> call;
        String objectId;
        BmobApiService bmobApiService;
        bmobApiService = Bmob.getInstance().api();
        String mTableName = "State";
        call = bmobApiService.getobjectid(mTableName, "%7B%22username%22:%22" + username + "%22%7D");
        JsonObject ret = commit(call);
        JsonElement jsonElement = ret.get("results");
        String data = jsonElement.toString().replace("[", "").replace("]", "");
        if (data.equals("") == false) {
            JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
            objectId = jsonObject.get("objectId").toString().replace("\"","").replace("\"","");
            return objectId;
        } else return null;
    }

    public static void Insert(String username) {
        String objectId;
        Call<JsonObject> call;
        BmobApiService bmobApiService;
        bmobApiService = Bmob.getInstance().api();
        State s = new State();
        s.setFlag(false);
        s.setPCstate(false);
        s.setUsername(username);
        String mTableName = "State";
        call = bmobApiService.insert(mTableName, s);
        JsonObject ret = commit(call);
        objectId = ret.get("objectId").getAsString();
        System.out.println(objectId);
    }

    private static JsonObject commit(Call<JsonObject> call) {
        try {
            Response<JsonObject> ret = call.execute();
            return ret.body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
