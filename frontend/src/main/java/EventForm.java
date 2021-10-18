import com.google.gson.Gson;

import java.util.Date;

public class EventForm extends Form{
    //Customer Service; Senior CS; Financial Manager; Administration Manager

    private  String clientName;
    private Date from;
    private Date to;
    private int number;
    private String prefer; //DECORATION,PARTY,PHOTO,MEAL,DRINK;
    private String expectedBudget;
    private String status; //PEND, REJ, AD;

    public EventForm getEventForm(String sessionId, String formId){
        //POST
        String msg ="{\"sessionId\":\"" + sessionId + "\",\"formId\":\"" + formId +"\"}";
        String url = "http://3.133.82.240:8080/reqForm1";
        Requester rster= new Requester(url, msg);
        String res = rster.getRes();
        //PARSE
        Gson gson = new Gson();
        EventForm eventForm= gson.fromJson(res, EventForm.class);
        //RETURN
        return eventForm;
    }

}
