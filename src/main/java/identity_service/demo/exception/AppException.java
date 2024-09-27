package identity_service.demo.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    public AppException(ErrorCode errorCode) {
        super(
                String.valueOf(errorCode.getMessage())
        );
        this.errorCode = errorCode;
    }

    private final ErrorCode errorCode;

}
