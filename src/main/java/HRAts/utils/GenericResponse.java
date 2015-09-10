package HRAts.utils;

import java.io.Serializable;

public class GenericResponse implements Serializable {

    private int status;
    private Object data;

    public GenericResponse(){
        super();
    }

    public GenericResponse(int status, Object data) {
        super();
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
