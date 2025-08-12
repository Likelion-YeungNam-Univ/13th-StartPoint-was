package startpointwas.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import startpointwas.domain.user.dto.LoginDto;
import startpointwas.domain.user.dto.SignUpReq;
import startpointwas.domain.user.dto.UserInfoDto;
import startpointwas.domain.user.dto.NameRoleRes;

@Tag(name = "User", description = "사용자 인증/조회 API")
public interface UserApi {

    String SESSION_KEY = "LOGIN_USER_ID";

    @Operation(
            summary = "회원가입",
            description = "사용자를 생성하고, 응답으로 이름과 역할만 반환합니다. "
                    + "컨트롤러 매핑: POST /api/users/signup"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NameRoleRes.class),
                            examples = @ExampleObject(name = "성공 예시", value = """
                    {
                      "name": "홍길동",
                      "role": "MENTOR"
                    }
                """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "요청 값 오류",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "유효하지 않은 요청입니다.")
                    )
            )
    })
    ResponseEntity<NameRoleRes> signUp(SignUpReq req);

    @Operation(
            summary = "로그인",
            description = "세션을 생성하고, 응답으로 이름과 역할만 반환합니다. "
                    + "컨트롤러 매핑: POST /api/users/login"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NameRoleRes.class),
                            examples = @ExampleObject(value = """
                    {
                      "name": "홍길동",
                      "role": "USER"
                    }
                """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "아이디/비밀번호 오류",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "아이디 또는 비밀번호가 올바르지 않습니다.")
                    )
            )
    })
    ResponseEntity<NameRoleRes> login(LoginDto req, HttpServletRequest request);

    @Operation(
            summary = "로그아웃",
            description = "세션을 무효화하고 JSESSIONID 쿠키를 만료시킵니다. "
                    + "컨트롤러 매핑: POST /api/users/logout"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "로그아웃에 성공하셨습니다.")
                    )
            )
    })
    ResponseEntity<String> logout(HttpSession session, HttpServletResponse response);

    @Operation(
            summary = "사용자 단건 조회",
            description = "ID로 사용자 상세 정보를 조회합니다. "
                    + "컨트롤러 매핑: GET /api/users/{id}"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserInfoDto.class),
                            examples = @ExampleObject(value = """
                    {
                      "id": 1,
                      "name": "홍길동",
                      "userId": "hong1234",
                      "email": "hong@example.com",
                      "role": "MENTOR",
                      "birth": "2000-01-13",
                      "phone": "010-1234-5678"
                    }
                """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 사용자",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "사용자를 찾을 수 없습니다. id=1")
                    )
            )
    })
    ResponseEntity<UserInfoDto> getUser(Long id);

    @Operation(
            summary = "내 정보 조회",
            description = "세션에 저장된 ID를 사용해 현재 로그인한 사용자의 정보를 조회합니다. "
                    + "컨트롤러 매핑: GET /api/users/me"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserInfoDto.class),
                            examples = @ExampleObject(value = """
                    {
                      "id": 1,
                      "name": "홍길동",
                      "userId": "hong1234",
                      "email": "hong@example.com",
                      "role": "USER",
                      "birth": "2000-01-13",
                      "phone": "010-1234-5678"
                    }
                """)
                    )
            ),
            @ApiResponse(responseCode = "401", description = "미로그인",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "로그인이 필요합니다.")
                    )
            )
    })
    ResponseEntity<?> me(HttpSession session);

    @Operation(
            summary = "사용자 삭제",
            description = "ID로 사용자를 삭제합니다. "
                    + "컨트롤러 매핑: DELETE /api/users/{id}"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 사용자",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "사용자를 찾을 수 없습니다. id=1")
                    )
            )
    })
    ResponseEntity<Void> delete(Long id);
}
