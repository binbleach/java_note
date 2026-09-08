package Multithreading;

public class ThreadExceptionVo {

    //是否异常
    private boolean isException;
    //异常信息
    private String message;

    public ThreadExceptionVo(boolean isException, String message) {
        this.isException = isException;
        this.message = message;
    }

    public boolean isException() {
        return isException;
    }

    public void setException(boolean exception) {
        isException = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
