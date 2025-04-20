package com.project.my.common.exception;


import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionData {

    // System Exception
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E0002"),
    INTERNAL_SERVER_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "E0003"),
    ILLEGAL_ARGUMENT_ERROR(HttpStatus.BAD_REQUEST, "E0004", "필수 파라미터가 없습니다"),
    ID_PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "E0005",
        "아이디 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요."),


    // Custom Exception
    SECURITY_EXCEPTION(HttpStatus.UNAUTHORIZED, "CE0001", "인가받지 않은 회원입니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "CE0002", "회원을 조회할 수 없습니다."),
    NOT_FOUND_COMMON_CODE(HttpStatus.NOT_FOUND, "CE0003", "공통코드를 조회할 수 없습니다."),
    NOT_FOUND_LEVEL(HttpStatus.NOT_FOUND, "CE0004", "급수를 조회할 수 없습니다."),
    NOT_FOUND_STUDENT(HttpStatus.NOT_FOUND, "CE0005", "수련생을 조회할 수 없습니다."),
    NOT_FOUND_CLASS(HttpStatus.NOT_FOUND, "CE0006", "수업을 조회할 수 없습니다."),
    NOT_FOUND_CLASS_FILE(HttpStatus.NOT_FOUND, "CE0007", "클래스 파일을 조회할 수 없습니다."),
    NOT_FOUND_CLASS_GROUP(HttpStatus.NOT_FOUND, "CE0008", "수업 반을 조회할 수 없습니다."),
    NOT_FOUND_POLICY(HttpStatus.NOT_FOUND, "CE0009", "이용약관을 조회할 수 없습니다."),
    NOT_FOUND_CLASS_PLACE(HttpStatus.NOT_FOUND, "CE0010", "수업 장소를 조회할 수 없습니다."),
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "CE0011", "카테고리를 조회할 수 없습니다."),
    NOT_FOUND_CONTENT_FILE(HttpStatus.NOT_FOUND, "CE0012", "콘텐츠 파일을 조회할 수 없습니다."),
    NOT_FOUND_FILE(HttpStatus.NOT_FOUND, "CE0013", "파일을 조회할 수 없습니다."),
    NOT_FOUND_COMPANY(HttpStatus.NOT_FOUND, "CE0014", "회사/도장을 조회할 수 없습니다."),
    NOT_FOUND_CONTENT(HttpStatus.NOT_FOUND, "CE0015", "콘텐츠를 조회할 수 없습니다."),
    NOT_FOUND_SCHEDULE(HttpStatus.NOT_FOUND, "CE0016", "스케쥴을 조회할 수 없습니다."),
    DUPLICATE_CONTENT(HttpStatus.NOT_FOUND, "CE0017", "콘텐츠가 이미 포함되어 있습니다."),

    FILE_NAME_INVALID(HttpStatus.BAD_REQUEST, "CE0018", "파일 확장자를 확인해주세요."),
    NOT_FOUND_COMPANY_LOGO(HttpStatus.NOT_FOUND, "CE0019", "회사/태권도장의 로고를 조회할 수 없습니다."),
    NOT_FOUND_HISTORY(HttpStatus.NOT_FOUND,"CE0020", "관리자 접속 이후 이력을 조회 할 수 없습니다."),
    NOT_FOUND_COMMISION(HttpStatus.NOT_FOUND,"CE0021", "정산 정보를 조회할 수 없습니다. ")
    ;


    private final HttpStatus status;
    private final String code;
    private String message;

    ExceptionData(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionData(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
