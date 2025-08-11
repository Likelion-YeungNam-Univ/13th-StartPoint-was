package com.example.yu13thstartpointer.domain.user.controller;

import com.example.yu13thstartpointer.domain.user.dto.LoginDto;
import com.example.yu13thstartpointer.domain.user.dto.SignUpReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User API", description = "유저 관련 API")
public interface UserApi {

    @Operation(summary = "회원가입", description = "회원가입 시도")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "입력 누락 및 형식 비일치",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "필드 누락", value = """
                                    { "<field>" : "<field>는 필수 입력입니다." }
                                    """),
                            @ExampleObject(name = "이메일 형식 비일치", value = """
                                    { "email": "이메일 형식을 맞춰주세요." }
                                    """)
                    })),
            @ApiResponse(responseCode = "409", description = "중복으로 인한 회원가입 실패",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "이메일 중복", value = """
                                    { "status" : 409, "message" : "해당 이메일은 이미 존재합니다." }
                                    """),
                            @ExampleObject(name = "아이디 중복", value = """
                                    { "status" : 409, "message" : "해당 아이디는 이미 존재합니다." }
                                    """)
                    }))
    })
    ResponseEntity<?> signUp(
            @Parameter(description = "회원가입 정보")
            @Valid @RequestBody SignUpReq dto
    );

    @Operation(summary = "회원 상세조회", description = "조회 시도")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "ID가 존재하지 않음",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    { "status": 404, "message": "해당 유저를 찾을 수 없습니다." }
                                    """)
                    }))
    })
    ResponseEntity<?> getUser(
            @Parameter(description = "유저 고유 ID")
            @PathVariable Long user_id
    );

    @Operation(summary = "회원탈퇴(삭제)", description = "탈퇴(삭제) 시도")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "ID가 존재하지 않음",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    { "status": 404, "message": "해당 유저를 찾을 수 없습니다." }
                                    """)
                    }))
    })
    ResponseEntity<?> deleteUser(
            @Parameter(description = "유저 고유 ID")
            @PathVariable Long user_id
    );

    @Operation(summary = "로그인 (세션)", description = "userId와 비밀번호로 로그인하여 세션에 사용자 정보를 저장")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    { "id": 1, "name": "홍길동", "userId": "likelion", "email": "user@example.com" }
                                    """)
                    })),
            @ApiResponse(responseCode = "401", description = "로그인 실패",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    { "status": 401, "message": "아이디 또는 비밀번호가 올바르지 않습니다." }
                                    """)
                    }))
    })
    ResponseEntity<?> login(
            @Parameter(description = "로그인 정보(userId, password)")
            @Valid @RequestBody LoginDto dto,
            HttpSession session
    );

    @Operation(summary = "로그아웃 (세션)", description = "세션 무효화 및 JSESSIONID 쿠키 만료")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    })
    ResponseEntity<?> logout(HttpSession session, HttpServletResponse res);
}