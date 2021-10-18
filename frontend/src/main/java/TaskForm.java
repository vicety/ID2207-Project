import com.google.gson.Gson;

public class TaskForm extends Form{
    public String assignto;
    public String priority;
    public String taskDescription;
    public String sender;
    public String comment;

    public static TaskForm getTaskForm(String formId,String sessionId){
        //POST
        String msg ="{\"sessionId\":\"" + sessionId + "\",\"formId\":\"" + formId +"\"}";
        //System.out.println(msg);
        String url = "http://3.133.82.240:8080/reqForm4";
        Requester rster= new Requester(url, msg);
        String res = rster.getRes();
        //PARSE
        Gson gson = new Gson();
        TaskForm taskForm= gson.fromJson(res, TaskForm.class);
        //RETURN
        return taskForm;
    }

    public static TaskForm createTaskForm(String formType){
        //POST
        String msg ="{\"formType\":\"" + formType + "\"}";
        System.out.println(msg);
        String url = "http://3.133.82.240:8080/initForm";
        Requester rster= new Requester(url, msg);
        String res = rster.getRes();
        //PARSE
        Gson gson = new Gson();
        TaskForm taskForm= gson.fromJson(res, TaskForm.class);
        //RETURN
        return taskForm;
    }
}
