package by.bsuir.app.util.constants;

public enum Status {
    OK(200),
    SERVER_ERROR(502),
    CLOSE_CONNECTION(100),
    ACCOUNT_NOT_EXISTS(400),
    FAILED_PASSWORD_RECOVERY(401),
    MAIL_SENDING_ERROR(402),
    INCORRECT_EMAIL(403),
    REQUEST_ERROR(404),
    DUPLICATE_LOGIN(405);

    private final int code;

    Status(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}