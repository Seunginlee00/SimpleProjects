package com.project.my.config.service;

import com.project.my.user.dto.UsersDTO;
import com.project.my.user.entity.Users;
import com.project.my.user.repository.UsersRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UsersRepository userRepository;

  @Transactional(readOnly = true) // ✅ 세션 유지
  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

    log.info("----------------loadUserByUsername-----------------------------");

    Users users = userRepository.getWithRoles(userId);

    if (users == null) {
      throw new UsernameNotFoundException("사용자 없음");
    }

    // MemberRoleList → Enum 이름으로 추출
    List<String> roleNames = users.getRoleList().stream()
        .map(r -> r.getUsersRole().name())
        .collect(Collectors.toList());

    UsersDTO userDTO = new UsersDTO(
        users.getLoginId(),
        users.getPasswd(),
        users.getSalt(),
        users.getUserNm(),
        roleNames,
        users.getPasswdChangeDate()
    );

    log.info("Loaded userDTO: {}", userDTO);

    return userDTO;
  }

}