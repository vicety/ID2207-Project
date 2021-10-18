import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListJsonGenerator {

    private Map<String, Object> map;
    private List<Map<String, Object>> list;

    public ListJsonGenerator(){
        list = new ArrayList<>();
    }

    public Map<String, Object> createMap(){
        map = new HashMap<>();
        return map;
    }

    public void addInList(Map<String, Object> map){
        list.add(map);
    }
    public String output(){
        String s = new Gson().toJson(list);
        return s;
    }
}
