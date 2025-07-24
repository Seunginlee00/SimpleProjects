package com.project.my.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessLogDTO {
    private int no; // ✅ 일련번호 필드 추가
    private String uid;  // 암호화된 UID
    private String ipaddr;
    private String date;
    private String time;
    private String OS;
    private String browser;
    private String login_id;
    private String userNm;
    private int success;
    private int route;
    private int errCode;

    private String errorMessage; // 추가: errCode → 메시지 매핑용
}
