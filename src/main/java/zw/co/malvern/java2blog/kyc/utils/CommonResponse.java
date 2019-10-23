package zw.co.malvern.java2blog.kyc.utils;

public class CommonResponse {
    private String narrative;
    private boolean success;

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
