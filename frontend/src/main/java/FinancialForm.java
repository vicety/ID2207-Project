import com.google.gson.Gson;

public class FinancialForm extends Form{
        public String department;
		public int amount;
		public String reason;

    public static FinancialForm getFinancialForm(String formId,String sessionId){
        //POST
        String msg ="{\"sessionId\":\"" + sessionId + "\",\"formId\":\"" + formId +"\"}";
        //System.out.println(msg);
        String url = "http://3.133.82.240:8080/reqForm3";
        Requester rster= new Requester(url, msg);
        String res = rster.getRes();
        //PARSE
        Gson gson = new Gson();
        FinancialForm fForm = gson.fromJson(res, FinancialForm.class);
        //RETURN
        return fForm;
    }

    public static FinancialForm createFinancialForm(String formType){
        //POST
        String msg ="{\"formType\":\"" + formType + "\"}";
        System.out.println(msg);
        String url = "http://3.133.82.240:8080/initForm";
        Requester rster= new Requester(url, msg);
        String res = rster.getRes();
        //PARSE
        Gson gson = new Gson();
        FinancialForm fForm = gson.fromJson(res, FinancialForm.class);
        //RETURN
        return fForm;
    }
}
