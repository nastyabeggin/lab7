package common.util.response;

import java.io.Serializable;

public class ResponseBody implements Serializable {
    private final ResponseBodyTypes responseBodyTypes;
    private final String message;

    protected ResponseBody(ResponseBodyTypes responseBodyTypes, String message) {
        this.responseBodyTypes = responseBodyTypes;
        this.message = message;
    }

    public ResponseBody(String message) {
        this.responseBodyTypes = ResponseBodyTypes.SIMPLE;
        this.message = message;
    }

    public ResponseBodyTypes getResponseBodyTypes() {
        return responseBodyTypes;
    }

    public String getMessage() {
        return message;
    }
}
