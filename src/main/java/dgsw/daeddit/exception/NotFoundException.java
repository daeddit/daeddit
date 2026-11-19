package dgsw.daeddit.exception;

public class NotFoundException extends BusinessException {
    public NotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }
}
