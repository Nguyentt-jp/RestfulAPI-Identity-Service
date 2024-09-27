package identity_service.demo.exception;

import identity_service.demo.dto.request.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .error(true)
                .message(Collections.singletonList(String.valueOf(e.getMessage())))
                .build();

        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .success(false)
                        .result(exceptionResponse)
                        .build()
        );
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Object>> handleAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .error(true)
                .message(errorCode.getMessage()).build();

        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .success(false)
                        .result(exceptionResponse)
                        .build()
        );
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    ResponseEntity<ApiResponse<Object>> MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .error(true)
                .message(Collections.singletonList("Data type mismatch!"))
                .build();

        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .success(false)
                        .result(exceptionResponse)
                        .build()
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<ErrorCode> errorCodeList = new ArrayList<>();
        for (int i = 0; i < e.getFieldErrors().size(); i++) {
            errorCodeList.add(ErrorCode.valueOf(e.getFieldErrors().get(i).getDefaultMessage())); // ->
        }

        try {
            if (errorCodeList.isEmpty()){
                errorCodeList.add(ErrorCode.INVALID_KEY);
            }
            //errorCode = ErrorCode.valueOf(errorMessage);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        List<String> errorMessages = new ArrayList<>();
        for (ErrorCode value : errorCodeList) {
            errorMessages.add(value.getMessage().toString());
        }

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .error(true)
                .message(errorMessages).build();

        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .success(false)
                .result(exceptionResponse)
                .build());
    }
}
