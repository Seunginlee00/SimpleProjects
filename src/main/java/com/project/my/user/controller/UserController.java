package com.project.my.user.controller;


import com.project.my.auth.service.Auth;
import com.project.my.user.dto.UserRegisterRequest;
import com.project.my.common.dto.response.SearchDto;
import com.project.my.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원 API")
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@RestController
@Slf4j
public class UserController {
/*
https://ng-log.tistory.com/entry/SpringBoot-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%8B%9C%EC%9E%91%ED%95%98%EA%B8%B0-5-Security-%EC%84%A4%EC%A0%95%ED%95%98%EA%B3%A0-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%ED%99%95%EC%9D%B8%ED%95%98%EA%B8%B0
참고 사이트
 */

    private final UserService userService;

    /* 회원 가입*/
    @Operation(summary = "회원 가입하기")
    @PostMapping("/join")
    public ResponseEntity<?> userJoin(@RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(userService.userSignUp(request));
    }


    /* 회원 수정 */

    @PatchMapping("/update")
    public ResponseEntity<?> userUpdate(@Auth Long userId, @RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(userService.userUpdate(userId,request));
    }

    /* 회원 상세 조회 */

    @GetMapping("/detail")
    public ResponseEntity<?> userDetail(@Auth Long userId, @RequestBody UserRegisterRequest request)
    {
        return ResponseEntity.ok(userService.userSearch(request));
    }

    /* 회원 전체 목록 조회 */

    @GetMapping("/list")
    public ResponseEntity<?> userList(SearchDto dto, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(userService.userListSearch(dto,pageable));
    }


    /* 회원 삭제
    * 번호 만 입력시 1명 리스트로 입력시 여러명
    *  */

    @PatchMapping("/delete")
    public ResponseEntity<?> userDelete(@RequestBody List<Long> delIds)
    {
        userService.delete(delIds);
        return ResponseEntity.ok("게시판 삭제");
    }

    // 로그 아웃

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Auth Long userId) {

        // Redis 에서 해당 사용자의 accessToken/refreshToken 모두 삭제
//        jwtTokenStoreService.removeTokenForUser(userId);
//        jwtTokenStoreService.removeRefreshTokenForUser(userId);

        return ResponseEntity.ok(Map.of("result", "logout_success"));
    }


    // 회원 비밀번호 초기화
//    @PostMapping("/user/pw-reset")
//    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ADMIN')") // 관리자만 허용
//    public ResponseEntity<ResultDTO<Void>> resetMemberPassword(@RequestParam String memberId, Authentication authentication) {
//        // 현재 관리자 정보 및 권한 추출
//        String adminId = authentication.getName();
//        boolean isAdmin = memberService.isAdmin(adminId);
//
//        // 관리자 권한 체크
//        if (!isAdmin) {
//            // 관리자 권한이 없는 사용자가 접속하여 처리하려 했다는 로그를 남겨야 함.
//            ResultDTO<Void> fail = ResultDTO.<Void>builder()
//                    .success(false)
//                    .message("관리자 권한 없음")
//                    .build();
//            return ResponseEntity.ok(fail);
//        }
//
//        ResultDTO<Void> result = memberService.resetMemberPassword(memberId, adminId);
//        return ResponseEntity.ok(result);
//    }



}
