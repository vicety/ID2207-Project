import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

/**
 * Get the String of JSON
 */
public class Requester {
    private String res;

    public Requester(String url, String msg){
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//json

        RequestBody body = RequestBody.create(msg,JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try(Response response = client.newCall(request).execute()){
            res = response.body().string();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public String getRes() {
        return res;
    }
}
