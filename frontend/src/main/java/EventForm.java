import com.google.gson.Gson;

import java.util.Date;

public class EventForm extends Form{
    //Customer Service; Senior CS; Financial Manager; Administration Manager

    public String clientName;
    public String from;
    public String to;
    public String number;
    public String prefer; //DECORATION,PARTY,PHOTO,MEAL,DRINK;
    public String expectedBudget;
    public String status; //PEND, REJ, AD;


    public static EventForm getEventForm(String formId,String sessionId){
        //POST
        String msg ="{\"sessionId\":\"" + sessionId + "\",\"formId\":\"" + formId +"\"}";
        //System.out.println(msg);
        String url = "http://3.133.82.240:8080/reqForm1";
        Requester rster= new Requester(url, msg);
        String res = rster.getRes();
        //PARSE
        Gson gson = new Gson();
        EventForm eventForm= gson.fromJson(res, EventForm.class);
        //RETURN
        return eventForm;
    }

    public static EventForm createEventForm(String formType){
        //POST
        String msg ="{\"formType\":\"" + formType + "\"}";
        System.out.println(msg);
        String url = "http://3.133.82.240:8080/initForm";
        Requester rster= new Requester(url, msg);
        String res = rster.getRes();
        //PARSE
        Gson gson = new Gson();
        EventForm eventForm= gson.fromJson(res, EventForm.class);
        //RETURN
        return eventForm;
    }

}
