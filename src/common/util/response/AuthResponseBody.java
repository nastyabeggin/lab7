package common.util.response;

public class AuthResponseBody extends ResponseBody {
    private final boolean isSuccess;
    public AuthResponseBody(boolean isSuccess, String message) {
        super(ResponseBodyTypes.AUTH_BODY, message);
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
