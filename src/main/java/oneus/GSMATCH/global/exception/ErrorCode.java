package oneus.GSMATCH.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

// 예외 케이스 관리
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_MATCH_ADMIN_TOKEN(HttpStatus.BAD_REQUEST, "관리자 암호가 일치하지 않습니다."),

    DUPLICATED_USERNAME(HttpStatus.BAD_REQUEST, "중복된 사용자 입니다"),

    NOT_MATCH_INFORMATION(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다."),

    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}