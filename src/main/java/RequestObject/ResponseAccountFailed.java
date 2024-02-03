package RequestObject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseAccountFailed {


    // asta preia codul si mesajul din response
    @JsonProperty("code")
    private Integer code;

    @JsonProperty("message")
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
