/*
package com.project.my.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginFailService {
  private final AccessLogService accessLogService;
  private final MemberRepository memberRepository;
  private final BlockLogRepository blockLogRepository;
  private final LDAPAuthFailServiceImpl ldapAuthFailService;
  private final AllowHostRepository allowHostRepository;

  */
/**
   * IP 차단 여부 확인 후 해제 가능 여부 판단
   *//*

  public boolean isIPBlocked(String ip, int type) {
    int checkResult = accessLogService.checkIPBlock(ip, type);
    return checkResult == 8; // 8: 차단 유지 중
  }

  */
/**
   * 로그인 실패 5회 이상이면 시간 차 검사 후 계정 잠금 판단
   *//*

  public int isUserLocked(HttpServletRequest request,String userId, String clientIP) {
    // 사용자 정보가 존재하는지 확인
    boolean userExists = memberRepository.countByUserId(userId) > 0;
    int route = getDeviceType(request);

    if (userExists) {
      int failCount = memberRepository.findAccessFailedCountByUserId(userId).orElse(0);
      if (failCount >= 5) {
        int gapTime = checkUserAccessDateDiff(userId); // 5분 경과 여부
        if (gapTime == 1) {
          log.info("🔓 [계정차단 해제] userId={}, last accessDate 경과됨. 계정잠금 해제", userId);
          memberRepository.clearAccountLock(userId); // access=1, 실패횟수 초기화
          return 0;
        } else {
          log.info("⛔ [계정차단 유지] userId={}, 아직 5분 경과 안됨", userId);
          accessLogService.saveAccessLog(request, userId, 0, route, 2, null);
          //success(실패0/성공1),route(외부1,사내2),errCode(승인0/불허1/차단2/대기3/실패4/미가입5
          return 7;
        }
      }
    } else {

      // 사용자 없음 → ldap_auth_fail 기준 체크
      int failCount = ldapAuthFailService.getFailCount(userId);
      log.info("⛔ [사용자 없음] userId={} 미가입자, failCount={} ", userId, failCount);
      if (failCount >= 5) {
        int gapTime = ldapAuthFailService.checkTimeDiff(clientIP); // 5분 기준
        if (gapTime == 1) {
          log.info("🔓 [계정차단 해제] userId={} 미가입자, last accessDate 경과됨. 계정잠금 해제", userId);
          ldapAuthFailService.clearIPAddressLock(clientIP);
          return 0;
        } else {
          log.info("⛔ [계정차단 유지] userId={} 미가입자, 아직 5분 경과 안됨", userId);
          accessLogService.saveAccessLog(request, userId, 0, route, 5, null); // errCode:5 (미가입)
          ldapAuthFailService.incrementFail(userId, 0, clientIP); // errorCode 0
          return 7;
        }
      } else {
        if (!ldapAuthFailService.isFailRecordExists(userId, clientIP)) {
          accessLogService.saveAccessLog(request, userId, 0, route, 5, null); // 미가입자
          ldapAuthFailService.createFailUser(userId, 0, clientIP); // 최초 실패 등록
        } else {
          accessLogService.saveAccessLog(request, userId, 0, route, 5, null); // 미가입자
          ldapAuthFailService.incrementFail(userId, 0, clientIP); // 실패 횟수 증가
        }
        return ldapAuthFailService.getFailCount(userId);
      }
    }

    return 0; // 차단 조건 아님
  }

  public int checkUserAccessDateDiff(String userId) {
    final int LIMIT_SECONDS = 300;
    Optional<LocalDateTime> optionalAccessDate = memberRepository.findAccessDateByUserId(userId);
    if (optionalAccessDate.isPresent()) {
      LocalDateTime accessDate = optionalAccessDate.get();
      log.debug("🕓 [DB AccessDate 조회] userId={}, accessDate={}", userId, accessDate); // ✅ 먼저 로그 출력

      LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
      long seconds = Duration.between(accessDate, now).getSeconds();
      log.debug("🔍 [TimeGap Check] userId={}, accessDate={}, now={}, diff={}초",
          userId, accessDate, now, seconds);
      return seconds > LIMIT_SECONDS ? 1 : 0;
    } else {
      log.debug("⚠️ [TimeGap Check] userId={}, accessDate 없음", userId); // 조회 결과 없음도 로그
    }
    return 0; // 조회 결과가 없으면 기본값 0
  }

  */
/**
   * 로그인 실패 기록 및 실패 횟수 증가
   *//*

  public void recordLoginFail(String userId, HttpServletRequest request) {
    accessLogService.saveAccessLog(request, userId, 0, 1, 5, null);
    memberRepository.incrementLoginFail(LocalDateTime.now(), userId);
  }

  */
/**
   * 실패 횟수 만료 시 초기화
   *//*

  public void clearLoginFail(String userId) {
    memberRepository.clearLoginFailCount(userId);
  }

  */
/**
   * 현재 실패 횟수 반환
   *//*

  public int getCurrentFailCount(String userId) {
    return memberRepository.findAccessFailedCountByUserId(userId).orElse(0);
  }

  public int isUserExisted(String userId) {
    return memberRepository.countByUserId(userId);
  }

  private void clearUserIDLock(String userId) {
    memberRepository.clearLoginFailCount(userId);
  }

  private int isUserStatusChk(String userId) {
    return memberRepository.findAccessStatusByUserId(userId).orElse(0);
  }

  public int handleLoginSuccess(HttpServletRequest request, String userId, String clientIP) {
    int status = isUserStatusChk(userId); // 1:접속허용, 2:승인대기, 0:불허
    int route = getDeviceType(request);

    if (status == 1) {
      // 로그인 성공 시 로그인 실패 횟수 초기화
      clearUserIDLock(userId);
      ldapAuthFailService.clearIPAddressLock(clientIP);

      accessLogService.saveAccessLog(request, userId, 1, route, 0, null);

      // 마지막 로그인 시간 업데이트
      memberRepository.updateLastLoginDate(userId, LocalDateTime.now());

      return 11; // 접속 허용
    } else if (status == 2) {
      accessLogService.saveAccessLog(request, userId, 0, route, 3, null); // 승인대기
      return 12; // 승인 대기
    } else if (status == 0) {
      accessLogService.saveAccessLog(request, userId, 0, route, 1, null); // 관리자 불허
      return 10; // 접속 불허
    }

    return 0; // 기타 예외 상황
  }

  public boolean allowIPChk(int mtype, HttpServletRequest request) {
    if (mtype == 1 || mtype == 2) return true;

    String clientIP = ClientInfo.getClientIP(request);
    long count = allowHostRepository.countByIpaddrAndPermission(clientIP, 0);

    return count == 1;
  }
}
*/
