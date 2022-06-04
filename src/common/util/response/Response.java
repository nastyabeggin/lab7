package common.util.response;

import java.io.Serializable;

public class Response implements Serializable {
    private final ResponseCode responseCode;
    private final ResponseBody responseBody;

    public Response(ResponseCode responseCode, ResponseBody responseBody) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public ResponseBody getResponseBody() {
        return responseBody;
    }
}