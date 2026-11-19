package dgsw.daeddit.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String code;
    private String message;
    private List<FieldError> errors;

    @Getter
    @AllArgsConstructor
    public static class FieldError {
        private String field;
        private String message;
    }

    public static ErrorResponse of(ErrorCode errorCode, List<FieldError> errors) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(errorCode.getStatus().value())
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .errors(errors)
                .build();
    }
}
