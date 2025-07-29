package com.project.my.user.dto;
import com.project.my.user.entity.Users;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UsersDTO extends User
{

  private Long userId;
  private String loginId;
  private String passwd;
  private String salt;
  private String userNm;
  private List<String> roleNames;
  private LocalDateTime passwdChangeDate;

  public static List<SimpleGrantedAuthority> toAuthorities(List<String> roleNames) {
    return roleNames.stream()
        .map(str -> new SimpleGrantedAuthority("ROLE_" + str))
        .collect(Collectors.toList());
  }

  public Map<String, Object> getClaims() {

    Map<String, Object> dataMap = new HashMap<>();

    dataMap.put("loginId", loginId);
    dataMap.put("userNm", userNm);
    dataMap.put("roles", roleNames);

    return dataMap;
  }

  public UsersDTO(String loginId, String passwd, String salt, String userNm, List<String> roleNames) {
    super(
        loginId,
        passwd,
        toAuthorities(roleNames));

    this.loginId = loginId;
    this.passwd = passwd;
    this.salt = salt;
    this.userNm = userNm;
    this.roleNames = roleNames;
  }

  // ✅ 필요한 경우 생성자 오버로딩 (패스워드 변경일 포함)
  public UsersDTO(String userId, String passwd, String salt, String userNm, List<String> roleNames, LocalDateTime passwdChangeDate) {
    this(userId, passwd, salt, userNm, roleNames);
    this.passwdChangeDate = passwdChangeDate;
  }


}
