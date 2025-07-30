package com.project.my.auth.entity;

import com.project.my.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "access_log")  // 소문자로 통일
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessLog extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long uid; // 고유 번호

  @Column(name = "ipaddr", length = 20, nullable = false)
  private String ipaddr; // 접속한 사용자 IP

  @Column(name = "access_date", nullable = false)
  private LocalDateTime accessDate; //접속 일시

  @Column(name = "lgoin_id", length = 60, nullable = false)
  private String loginId; //접속한 사용자의 IDL

  @Column(name = "user_nm", length = 20)   // userNm → usernm
  private String userNm; //접속한 사용자의 이름

  @Column(name = "os", length = 60)
  private String os;  //사용자의 운영체제(Windows, Mac, Linux, Android 등)

  @Column(name = "browser", length = 30)
  private String browser; //접속한 브라우저(Chrome, Edge, Firefox 등)

  @Column(name = "hit", nullable = false)
  private int hit; // 접속 시도 횟수

  @Column(name = "success", nullable = false)
  private Integer success; //성공/실패 여부(0/1)

  @Column(name = "date", length = 8, nullable = false)
  private String date;

// 통계 혹은 검색 용?
  @Column(name = "time", length = 10, nullable = false)
  private String time; // 접속시간

  @Column(name = "ym", length = 6, nullable = false)   // YM → ym
  private String ym; // 연월

  @Column(name = "md", length = 4, nullable = false)   // MD → md
  private String md; // 월일

  @Column(name = "year", length = 4, nullable = false)
  private String year; // 년

  @Column(name = "month", length = 2, nullable = false)
  private String month; // 월

  @Column(name = "day", length = 2, nullable = false)
  private String day; // 일
  // 여까지
  @Column(name = "route", nullable = false)
  private int route; // 접속 경로 구분(예: 1=웹, 2=앱, 3=API 등, 프로젝트에 따라 의미 부여)

  @Column(name = "err_code", nullable = false)   // errCode → errcode
  private int errCode; //	에러 코드

  @Column(name = "display", nullable = false)
  private int display = 1; //표시 여부(1=표시, 0=비표시)
}
