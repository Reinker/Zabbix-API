package zabbixApi;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashMap;
import java.util.Map;

public class Json{
    private Map<String, Object> data = new HashMap<String, Object>();

    private JSONObject json = new JSONObject();

    public JSONObject Request(JSONObject param ,String method, String auth, int id){
        data.put("jsonrpc", "2.0");
        data.put("method", method);
        data.put("params", param);
        data.put("id", id);
        data.put("auth", auth);
        json.putAll(data);
        return json;
    }

    public JSONObject param(Map<String, Object> data){
        json.putAll(data);
        return json;
    }
}
