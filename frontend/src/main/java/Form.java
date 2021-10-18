import com.google.gson.Gson;

import javax.swing.*;
import java.util.Map;

public class Form {

    public String formId;
    public String formType; //EVENT, TASK, HR, FINANCIAL;


    public static void updateForm(String id, String key, String value){
        //msg
        String msg ="{\"formId\":\"" + id + "\",\"" + key + "\":\"" + value +"\"}";
        System.out.println("msg:"+ msg);
        //url
        String url = "http://3.133.82.240:8080/modifyForm";
        Requester rster= new Requester(url, msg);
        String res = rster.getRes();
        Gson gson = new Gson();
        Check check = gson.fromJson(res, Check.class);
        if (check.status.equals("ok")){
            JOptionPane.showMessageDialog(null, "Modified successfully", "tips", JOptionPane.PLAIN_MESSAGE);
            System.out.println("Modify!");
        }

    }

    public static void confirmForm(String id){
        //msg
        String msg ="{\"formId\":\"" + id + "\"}";
        System.out.println("msg:"+ msg);
        //url
        String url = "http://3.133.82.240:8080/confirmForm";
        Requester rster= new Requester(url, msg);
        String res = rster.getRes();
        Gson gson = new Gson();
        Check check = gson.fromJson(res, Check.class);
        if (check.status.equals("ok")){
            System.out.println("Modify!");
        }
    }
    public class Check{
        String status;
    }
}



