package HRAts.model;

public class ActivityStatusContext {

    private Activity activity;
    private Status status;

    public ActivityStatusContext(){
        super();
    }

    public ActivityStatusContext(Activity activity, Status status) {
        super();
        this.activity = activity;
        this.status = status;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
