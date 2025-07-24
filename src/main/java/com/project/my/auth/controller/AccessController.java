//package com.project.my.auth.controller;
//
//import com.mansa.smartx.api.mall.auth.service.AccessLogService;
//import com.mansa.smartx.api.mall.auth.service.IpWhoisService;
//import com.mansa.smartx.api.mall.auth.service.LoginFailService;
//import com.mansa.smartx.api.mall.common.dto.ResultDTO;
//import com.mansa.smartx.api.mall.common.util.ClientInfo;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Set;
//
//@RestController
//@RequiredArgsConstructor
//@Log4j2
//public class AccessController {
//    private final LoginFailService loginFailService;
//    private final IpWhoisService ipWhoisService;
//    private final AccessLogService accessLogService;
//
//    // 내부망/사무실 IP 화이트리스트 (필요시 추가)
//    private static final Set<String> WHITE_LIST = Set.of(
//            "127.0.0.1", "192.168.0.1", "211.176.76.11"
//    );
//
//    @GetMapping("/check-access")
//    public ResultDTO<String> checkAccess(HttpServletRequest request) throws Exception  {
//        int mtype = ClientInfo.getDeviceType(request);
//        String clientIP = ClientInfo.getClientIP(request);
//
//        // 1. 화이트리스트 IP는 무조건 허용
//        if (WHITE_LIST.contains(clientIP)) {
//            log.info("화이트리스트 허용: {}", clientIP);
//            return ResultDTO.<String>builder()
//                    .success(true)
//                    .message("화이트리스트 IP 허용")
//                    .data("ALLOWED")
//                    .build();
//        }
//
//        try {
//            // 2. 해외 IP/내부망/특수IP 차단
//            String countryCode = ipWhoisService.getOrSaveIPWhoisInfo(clientIP);
//
//            // 내부/특수IP 구분 처리
//            if ("none".equalsIgnoreCase(countryCode) || "SPECIAL".equalsIgnoreCase(countryCode)) {
//                log.warn("INTERNAL/SPECIAL IP 차단: {} (country: {}) url={} agent={}",
//                        clientIP, countryCode, request.getRequestURI(), request.getHeader("User-Agent"));
//                accessLogService.saveAccessLog(request, "blocked", 0, 3, 7, "");
//                return ResultDTO.<String>builder()
//                        .success(false)
//                        .message("내부/로컬/특수 IP 차단")
//                        .data(clientIP)
//                        .build();
//            }
//
//            // 해외 IP 차단
//            if (!"KR".equalsIgnoreCase(countryCode)) {
//                accessLogService.saveAccessLog(request, "blocked", 0, 3, 7, "");
//                log.warn("BLOCK_FOREIGN: {} (country: {}) url={} agent={}",
//                        clientIP, countryCode, request.getRequestURI(), request.getHeader("User-Agent"));
//                return ResultDTO.<String>builder()
//                        .success(false)
//                        .message("해외 IP가 감지되어 차단되었습니다.")
//                        .data(clientIP)
//                        .build();
//            }
//
//            // 3. 추가 인증/권한 체크(등록된 IP만 허용 등)
//            boolean allowed = loginFailService.allowIPChk(mtype, request);
//
//            log.info("mtype: {}, clientIP: {}, allowed: {}", mtype, clientIP, allowed ? "ALLOWED" : "BLOCKED");
//            return ResultDTO.<String>builder()
//                    .success(allowed)
//                    .message(allowed ? "접속 허용" : "접속 차단")
//                    .data(allowed ? "ALLOWED" : clientIP)
//                    .build();
//        } catch (Exception e) {
//            // 4. 시스템 장애 예외 처리
//            log.error("check-access 처리 중 예외", e);
//            return ResultDTO.<String>builder()
//                    .success(false)
//                    .message("시스템 장애 또는 네트워크 오류로 접속이 제한됩니다.")
//                    .data("ERROR")
//                    .build();
//        }
//    }
//}
