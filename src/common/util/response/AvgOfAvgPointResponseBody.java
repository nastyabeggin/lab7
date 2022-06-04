package common.util.response;

public class AvgOfAvgPointResponseBody extends ResponseBody {

    private final Long value;

    public AvgOfAvgPointResponseBody(Long value, String message) {
        super(ResponseBodyTypes.AVG_OF_AVG_POINT, message);
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
