package onions.resultApi;

import com.alibaba.fastjson.JSONArray;

public class ResultApi {

    public static JSONArray resultApi(Object... o){
        JSONArray array = new JSONArray();
        for(int i = 0 ; i < o.length; i++){
            array.add(o[i]);
        }
        return array;
    }




}
