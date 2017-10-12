package jc.geecity.taihua.test;

@SuppressWarnings("ALL")
public class BaseResponseModel {


    /**
     * success : true
     * code : 0
     * message : success
     * data : [{"id":40,"name":"9:00-12:00"},{"id":41,"name":"13:00-15:00"},{"id":42,"name":"16:00-17:30"},{"id":43,"name":"随时上门"}]
     */

    private boolean success;
    private int code;
    private String message;

    public BaseResponseModel() {
        super();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
