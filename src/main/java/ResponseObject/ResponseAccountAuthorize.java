package ResponseObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponseAccountAuthorize {

    @JsonProperty("userId")
    private String userId;
    //maparea unei prime variabile pe care respone-ul o oare
    @JsonProperty("username")
    private String username;

    @JsonProperty("books")
    private List<BookObject> books; // am construit un obiect care sa contina toate informatiile partii books

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public List<BookObject> getBooks() {
        return books;
    }
}
