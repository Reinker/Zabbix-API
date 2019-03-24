package zabbixApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;
import java.util.Date;


public class ZabbixApi extends Post {
    private JSONObject param = new JSONObject();

    private String auth;

    public static String url;

    public void getAuth(String auth){
        this.auth = auth;
    }

    public String getVersion(){
        return Post(new Json().Request(param, "apiinfo.version", null, 1)).get("result").toString();
    }

    public void login(String user, String password){
        param.put("user", user);
        param.put("password", password);
        getAuth(Post(new Json().Request(param, "user.login", null, 1)).get("result").toString());
    }

    public JSONObject getItemByName(String name, String key, String hostname){
        JSONObject filter = new JSONObject();
        param.put("output", "extend");
        param.put("hostids", getHostByName(hostname).get("hostid"));
        filter.put("name", name);
        filter.put("key_", key);
        param.put("filter", filter);

        return Post(new Json().Request(param, "item.get", auth, 1)).getJSONArray("result").getJSONObject(0);
    }


    public JSONArray getItems(JSONObject params){
        return Post(new Json().Request(param, "item.get", auth, 1)).getJSONArray("result");
    }

    public JSONObject getHostByName(String name){
        JSONObject filter = new JSONObject();
        filter.put("host", name);
        param.put("filter", filter);
        return Post(new Json().Request(param, "host.get", auth, 1)).getJSONArray("result").getJSONObject(0);
    }

    public JSONArray getHosts(JSONObject params){
        return Post(new Json().Request(param, "host.get", auth, 1)).getJSONArray("result");
    }

    public JSONObject getHistory(JSONObject param){
        return Post(new Json().Request(param, "history.get", auth, 1));
    }

    public JSONObject getHistoryByName(String itemName, String itemKey, String hostName, Timestamp time){
        String item = getItemByName(itemName, itemKey, hostName).toString();

        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime()/1000L);
        JSONObject param =  new JSONObject();
        param.put("output", "extend");
        param.put("history", JSON.parseObject(item).get("value_type"));
        param.put("itemids", JSON.parseObject(item).get("itemid"));
        param.put("time_from", time);
        param.put("time_till", now);
        param.put("sortorder", "DESC");
        param.put("sortfield", "clock");

        return Post(new Json().Request(param, "history.get", auth, 1));
    }

}
