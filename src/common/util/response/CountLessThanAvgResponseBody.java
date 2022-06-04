package common.util.response;

public class CountLessThanAvgResponseBody extends ResponseBody{

    private final Integer value;

    public CountLessThanAvgResponseBody(Integer value, String message) {
        super(ResponseBodyTypes.COUNT_LESS_THAN_AVG, message);
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
