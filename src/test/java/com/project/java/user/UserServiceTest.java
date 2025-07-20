package com.project.java.user;

import com.project.java.config.UserServiceTestConfig;
import com.project.my.MainApplication;
import com.project.my.user.dto.UserRegisterRequest;
import com.project.my.user.entity.Role;
import com.project.my.user.entity.Users;
import com.project.my.user.repository.UsersRepository;
import com.project.my.user.service.UserService;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@RequiredArgsConstructor
@ActiveProfiles("test")
@SpringBootTest(classes = {MainApplication.class, UserServiceTestConfig.class})
public class UserServiceTest {

  @Autowired
  private UserService userService;

  @Autowired
  private UsersRepository usersRepository;

  @Test
  void userCreate() {
    // given
    UserRegisterRequest request = new UserRegisterRequest(
        "test2", "test2@naver.com", "test123", "test2", Role.M, 0L);

    log.debug("테스트 들어옴");
    // when
    String result = userService.userSignUp(request);


//    assertEquals("가입되었습니다.", result);
  }


    @Test
  void 회원_수정_성공() {

      // given
      UserRegisterRequest request = new UserRegisterRequest(
              "test4", "test44@naver.com", "test1234", "test44", Role.M, 2L);

      log.debug("들어옴..");

      // when
      userService.userUpdate(request.userId(), request);

      // then (수정된 내용을 검증)
      Users updatedUser = usersRepository.findById(2L).orElseThrow();
      assertThat(updatedUser.getNickname()).isEqualTo("test44");
      assertThat(updatedUser.getEmail()).isEqualTo("test44@naver.com");
  }


  @Test
  void 회원_조회_성공() {

    // 1. 테스트용 유저를 먼저 저장
    Users savedUser = usersRepository.save(
        Users.builder()
            .loginId("lee")
            .email("lee@example.com")
            .password("1234")
            .nickname("이이이")
            .role(Role.M)
            .build()
    );


    Optional<Users> result = usersRepository.findById(savedUser.getId());

    // 3. 조회 결과 검증
    assertTrue(result.isPresent()); // 값이 있어야 함
    assertEquals("lee", result.get().getLoginId()); // loginId 확인
    assertEquals("lee@example.com", result.get().getEmail()); // email 확인

  }


  @Test
  void 회원_삭제_성공() {
    Users user1 = usersRepository.save(
        Users.builder()
            .loginId("lee")
            .email("lee@example.com")
            .password("1234")
            .nickname("이이이")
            .role(Role.M)
            .build()
    );

    Users user2 = usersRepository.save(
        Users.builder()
            .loginId("kim")
            .email("kim@example.com")
            .password("1234")
            .nickname("김김김")
            .role(Role.M)
            .build()
    );

    Users user3 = usersRepository.save(
        Users.builder()
            .loginId("park")
            .email("park@example.com")
            .password("1234")
            .nickname("박박박")
            .role(Role.M)
            .build()
    );

    List<Long> deleteIds = List.of(user1.getId(), user2.getId(), user3.getId());

    userService.delete(deleteIds);


    // then: deleted 플래그가 true로 바뀌었는지 확인
    for (Long id : deleteIds) {
      Users user = usersRepository.findById(id).orElseThrow();
      assertTrue(user.isDelete());
    }

  }



}
