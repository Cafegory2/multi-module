package exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionType {

    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT 토큰이 만료되었습니다."),
    JWT_INVALID(HttpStatus.UNAUTHORIZED, "JWT 토큰이 유효하지 않습니다."),
    JWT_CLAIM_INVALID(HttpStatus.UNAUTHORIZED, "JWT의 클레임이 유효하지 않습니다."),
    JWT_SUBJECT_NOT_FOUND(HttpStatus.UNAUTHORIZED, "JWT 토큰의 사용자 ID(sub)가 DB에 존재하지 않습니다."),
    JWT_ACCESS_SUB_AND_REFRESH_SUB_NOT_MATCHED(HttpStatus.UNAUTHORIZED, "JWT 액세스 토큰과 리프레시 토큰의 사용자 ID(sub)가 일치하지 않습니다."),
    JWT_REFRESH_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "JWT 리프레시 토큰의 memberId는 존재하나, DB에 해당 회원이 존재하지 않습니다."),
    JWT_REFRESH_MEMBER_ID_MISMATCH(HttpStatus.UNAUTHORIZED, "JWT 리프레시 토큰의 memberId는 존재하나 DB의 memberId와 일치하지 않음"),
    JWT_ACCESS_TOKEN_MISSING(HttpStatus.BAD_REQUEST, "액세스 토큰이 누락되었습니다."),
    JWT_REFRESH_TOKEN_MISSING(HttpStatus.BAD_REQUEST, "리프레시 토큰이 누락되었습니다."),
    JWT_INVALID_FORMAT(HttpStatus.UNAUTHORIZED, "JWT 토큰 포맷이 유효하지 않습니다."),

    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰이 없습니다."),
    TOKEN_REFRESH_REJECT(HttpStatus.UNAUTHORIZED, "토큰을 재발행할 수 없습니다."),

    CAFE_STUDY_INVALID_NAME(HttpStatus.BAD_REQUEST, "카공 이름은 20자 이하로만 작성할 수 있습니다"),
    STUDY_ONCE_CREATE_BETWEEN_CAFE_BUSINESS_HOURS(HttpStatus.BAD_REQUEST, "카공 생성시 시작시간과 종료시간은 카페 영업시간내에 포함되어야 합니다."),
    STUDY_ONCE_WRONG_START_TIME(HttpStatus.BAD_REQUEST, "카공 시작 시간은 현재 시간보다 최소 1시간 이후여야 합니다."),
    CAFE_STUDY_WRONG_START_DATE(HttpStatus.BAD_REQUEST, "카공 시작 날짜는 현재 날짜에서 1달 이내여야 합니다."),
    STUDY_ONCE_SHORT_DURATION(HttpStatus.BAD_REQUEST, "카공 시간은 1시간 이상이어야 합니다."),
    STUDY_ONCE_LONG_DURATION(HttpStatus.BAD_REQUEST, "카공 시간은 5시간 미만이어야 합니다."),
    STUDY_ONCE_TOO_MUCH_STUDY_IN_CAFE(HttpStatus.BAD_REQUEST, "이 카페에 이 인원의 카공을 더이상 생성할 수 없습니다."),
    STUDY_ONCE_LIMIT_MEMBER_CAPACITY(HttpStatus.BAD_REQUEST, "카공 참여 인원 수는 2명 이상 6명 이하여야 합니다."),
    STUDY_ONCE_CANNOT_REDUCE_BELOW_CURRENT(HttpStatus.BAD_REQUEST, "카공 최대 참여 인원 수는 현재 참여 신청중인 인원보다 적을 수 없습니다."),
    STUDY_ONCE_CONFLICT_TIME(HttpStatus.CONFLICT, "해당 시간에 참여중인 카공이 이미 있습니다."),
    STUDY_ONCE_DUPLICATE(HttpStatus.CONFLICT, "이미 참여중인 카공입니다."),
    STUDY_ONCE_FULL(HttpStatus.CONFLICT, "카공 신청 가능 인원을 초과하였습니다."),
    CAFE_STUDY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 카공을 찾을 수 없습니다."),
    STUDY_ONCE_TOO_LATE_JOIN(HttpStatus.CONFLICT, "카공 인원 모집이 확정된 이후 참여 신청을 할 수 없습니다."),
    STUDY_ONCE_TOO_LATE_QUIT(HttpStatus.CONFLICT, "카공 인원 모집이 확정된 이후 참여 취소를 할 수 없습니다."),
    STUDY_ONCE_TRY_QUIT_NOT_JOIN(HttpStatus.CONFLICT, "참여중인 카공이 아닙니다."),
    CAFE_STUDY_LEADER_QUIT_FAIL(HttpStatus.CONFLICT, "카공장은 다른 참여자가 있는 경우 참여 취소를 할 수 없습니다."),
    CAFE_STUDY_DELETE_FAIL_MEMBERS_PRESENT(HttpStatus.CONFLICT, "카공장은 다른 참여자가 있는 경우 스터디를 삭제 할 수 없습니다."),
    CAFE_STUDY_INVALID_LEADER(HttpStatus.BAD_REQUEST, "스터디 리더가 아닙니다."),
    STUDY_ONCE_LEADER_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "스터디 리더만 권한이 있습니다."),
    STUDY_ONCE_LOCATION_CHANGE_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "스터디 리더만 장소 변경을 할 권한이 있습니다."),
    STUDY_ONCE_REPLY_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "스터디 리더만 답변을 할 권한이 있습니다."),
    STUDY_ONCE_SINGLE_REPLY_PER_QUESTION(HttpStatus.CONFLICT, "하나의 카공 질문에는 하나의 답변만 할 수 있습니다."),
    STUDY_ONCE_EARLY_TAKE_ATTENDANCE(HttpStatus.BAD_REQUEST, "스터디 출석체크는 스터디 시작 10분 이후여야 합니다."),
    STUDY_ONCE_LATE_TAKE_ATTENDANCE(HttpStatus.BAD_REQUEST, "스터디 출석체크는 스터디 진행시간 절반이 지나기전에만 변경할 수 있습니다. "),
    STUDY_ONCE_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "없는 카공 댓글입니다."),
    STUDY_ONCE_COMMENT_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "댓글을 작성한 회원 본인만 수정 할 권한이 있습니다."),
    STUDY_ONCE_PARENT_COMMENT_MODIFICATION_BLOCKED(HttpStatus.FORBIDDEN, "답변이 존재하는 질문은 수정 할 수 없습니다."),
    STUDY_ONCE_PARENT_COMMENT_REMOVAL_BLOCKED(HttpStatus.FORBIDDEN, "답변이 존재하는 질문은 삭제 할 수 없습니다."),
    STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE(HttpStatus.BAD_REQUEST, "스터디 이름은 null, 빈 값, 혹은 공백만으로 이루어질 수 없습니다."),
    STUDY_ONCE_OPEN_CHAT_URL_EMPTY_OR_WHITESPACE(HttpStatus.BAD_REQUEST, "스터디의 오픈채팅방 URL은 null, 빈 값, 혹은 공백만으로 이루어질 수 없습니다."),

    CAFE_STUDY_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "없는 카공 댓글입니다."),
    CAFE_STUDY_COMMENT_CONTENT_NOT_BLANK(HttpStatus.BAD_REQUEST, "댓글은 빈 문자열 또는 공백일 수 없습니다."),
    CAFE_STUDY_COMMENT_HAS_REPLY(HttpStatus.BAD_REQUEST, "답변이 달린 댓글은 수정할 수 없습니다."),
    CAFE_STUDY_COMMENT_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "댓글을 작성한 회원 본인만 수정 할 권한이 있습니다."),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "없는 회원입니다."),
    MEMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "해당 이메일로 이미 회원가입이 되어 있습니다."),

    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "없는 리뷰입니다."),
    REVIEW_OVER_CONTENT_SIZE(HttpStatus.BAD_REQUEST, "리뷰 글자수가 최대 글자수 이하여야 합니다."),
    REVIEW_INVALID_MEMBER(HttpStatus.FORBIDDEN, "자신이 작성한 리뷰만 수정할 수 있습니다."),
    REVIEW_CONTENT_EMPTY_OR_WHITESPACE(HttpStatus.BAD_REQUEST, "리뷰 내용은 null, 빈 값, 혹은 공백만으로 이루어질 수 없습니다."),
    REVIEW_INVALID_RATE_RANGE(HttpStatus.BAD_REQUEST, "평점이 허용된 범위를 벗어났습니다."),

    CAFE_NOT_FOUND(HttpStatus.NOT_FOUND, "없는 카페입니다."),
    CAFES_NOT_FOUND(HttpStatus.NOT_FOUND, "일치하는 카페가 존재하지 않습니다."),
    CAFE_BUSINESS_HOUR_NOT_FOUND(HttpStatus.NOT_FOUND, "영업시간이 존재하지 않습니다."),
    CAFE_INVALID_BUSINESS_TIME_RANGE(HttpStatus.BAD_REQUEST, "영업시간이 허용된 범위를 벗어났습니다."),
    CAFE_NOT_FOUND_DAY_OF_WEEK(HttpStatus.INTERNAL_SERVER_ERROR, "현재 요일과 일치하는 요일을 찾을 수 없습니다."),
    CAFE_SEARCH_INVALID_TIME_RANGE(HttpStatus.BAD_REQUEST, "종료 시간이 오픈 시간보다 빠릅니다."),

    STUDY_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "없는 스터디멤버입니다."),

    PROFILE_GET_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "프로필을 조회할 권한이 없는 상대입니다."),
    PROFILE_UPDATE_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "자신의 프로필만 조회할 수 있습니다."),
    PROFILE_UPDATE_INVALID_INTRODUCTION(HttpStatus.FORBIDDEN, "자기 소개글은 300자 이하로만 작성할 수 있습니다.");

    private final int errStatus;
    private final String errorMessage;

    private static class HttpStatus {
        private static final int UNAUTHORIZED = 401;
        private static final int NOT_FOUND = 404;
        private static final int FORBIDDEN = 403;
        private static final int BAD_REQUEST = 400;
        private static final int CONFLICT = 409;

        private static final int INTERNAL_SERVER_ERROR = 500;
    }
}
