package identity_service.demo.exception;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, Collections.singletonList("Uncategorized error")),
    INVALID_USER_EXISTED(1001, Collections.singletonList("Username already exists!")),
    INVALID_USER_NOT_FOUND(1002, Collections.singletonList("User not found!")),
    INVALID_KEY(1003, Collections.singletonList("Uncategorized error")),
    INVALID_PASSWORD(1004, Collections.singletonList("Password must be at least 8 characters")),
    INVALID_USERNAME(1005, Collections.singletonList("username must be at least 5 characters"));

    ErrorCode(int code, List<String> message){
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final List<String> message;
}
