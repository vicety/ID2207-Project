import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Test {
    OkHttpClient client = new OkHttpClient();
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式

    public String run(String url) throws IOException {
        String json2 = "{'name':'wyz','age':25}";
        RequestBody body = RequestBody.create(json2,JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public class Person {
        private String name;
        private int age;
}
    public class Bean {

        public String name;
        public int age;
        public String sex;
        public String address;
    }


    public static <Bean> void main(String[] args) {
//        Test t = new Test();
//        try {
//            String json1 = t.run("http://3.133.82.240:8080");
//
//            //解析JSON
//            String json2 = "{'name':'wyz','age':25}";
//            Gson gson = new Gson();
//            Person person = gson.fromJson(json2, Person.class);
//            System.out.println(person.age);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //如何将对象生成json
        //1
        Map<String, Object> map = new HashMap<>();
        map.put("name", "小明");
        map.put("age", 30);
        map.put("address", "中国");
        map.put("sex", "boy");
        //2
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "小明");
        map1.put("age", 30);
        map1.put("address", "中国");
        map1.put("sex", "boy");

        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map);
        list.add(map1);

        String s = new Gson().toJson(list);//生成jsonString
        System.out.println(s);

        //解析数组json
        String strJson = "[\n" +
                "    {\n" +
                "        \"address\": \"中国\",\n" +
                "        \"age\": 30,\n" +
                "        \"name\": \"小明\",\n" +
                "        \"sex\": \"boy\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"address\": \"加拿大\",\n" +
                "        \"age\": 20,\n" +
                "        \"name\": \"小雨\",\n" +
                "        \"sex\": \"girl\"\n" +
                "    }\n" +
                "]";
        String strJson2 = "[]";
        Gson gson = new Gson();//创建Gson对象
        //获取JsonArray对象
        JsonArray jsonElements = JsonParser.parseString(strJson2).getAsJsonArray();
        //新建一个ArrayList
        ArrayList<Test.Bean> beans = new ArrayList<>();
        //遍历JsonArray对象中的elements
        for (JsonElement bean : jsonElements) {
            Test.Bean bean1 = gson.fromJson(bean, Test.Bean.class);//解析
            beans.add(bean1);
        }
        System.out.println(beans.size());
        //System.out.println(beans.get(1).name);



    }
}
