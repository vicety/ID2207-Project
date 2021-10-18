import com.google.gson.Gson;

public class RecruiForm extends Form{

    public String contractType;
    public String department;
    public String yearOfExperience;
    public String jobTitle;
    public String jobDescription;

    public static RecruiForm getRecruiForm(String formId,String sessionId){
        //POST
        String msg ="{\"sessionId\":\"" + sessionId + "\",\"formId\":\"" + formId +"\"}";
        //System.out.println(msg);
        String url = "http://3.133.82.240:8080/reqForm2";
        Requester rster= new Requester(url, msg);
        String res = rster.getRes();
        //PARSE
        Gson gson = new Gson();
        RecruiForm reForm= gson.fromJson(res, RecruiForm.class);
        //RETURN
        return reForm;
    }

    public static RecruiForm createRecruiForm(String formType){
        //POST
        String msg ="{\"formType\":\"" + formType + "\"}";
        System.out.println(msg);
        String url = "http://3.133.82.240:8080/initForm";
        Requester rster= new Requester(url, msg);
        String res = rster.getRes();
        //PARSE
        Gson gson = new Gson();
        RecruiForm reForm= gson.fromJson(res, RecruiForm.class);
        //RETURN
        return reForm;
    }

}
