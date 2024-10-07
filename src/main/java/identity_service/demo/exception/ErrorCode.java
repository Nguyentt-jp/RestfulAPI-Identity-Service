package identity_service.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Collections;
import java.util.List;

@Getter
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, HttpStatus.INTERNAL_SERVER_ERROR, Collections.singletonList("Uncategorized error")),
    INVALID_USER_EXISTED(1001, HttpStatus.BAD_REQUEST, Collections.singletonList("Username already exists!")),
    INVALID_USER_NOT_EXISTED(1001, HttpStatus.NOT_FOUND, Collections.singletonList("User not exists!")),
    INVALID_USER_NOT_FOUND(1002, HttpStatus.NOT_FOUND, Collections.singletonList("User not found!")),
    INVALID_KEY(1003, HttpStatus.BAD_REQUEST, Collections.singletonList("Uncategorized error")),
    INVALID_PASSWORD(1004, HttpStatus.BAD_REQUEST, Collections.singletonList("Password must be at least 8 characters")),
    INVALID_USERNAME(1005, HttpStatus.BAD_REQUEST, Collections.singletonList("username must be at least 5 characters")),
    UNAUTHENTICATED(1006, HttpStatus.UNAUTHORIZED, Collections.singletonList("Username or Password invalid!")),
    UNAUTHORIZED(1007, HttpStatus.FORBIDDEN, Collections.singletonList("User Not Authorization!")),
    INVALID_ROLE_EXISTED(1007, HttpStatus.BAD_REQUEST, Collections.singletonList("RoleName already exists!"));

    ErrorCode(int code, HttpStatusCode statusCode, List<String> message){
        this.code = code;
        this.statusCode = statusCode;
        this.message = message;
    }

    private final int code;
    private final HttpStatusCode statusCode;
    private final List<String> message;
}
