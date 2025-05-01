package com.project.java.user;

import com.project.java.config.UserServiceTestConfig;
import com.project.my.MainApplication;
import com.project.my.dto.login.UserRegisterRequest;
import com.project.my.entity.enums.Role;
import com.project.my.entity.user.Users;
import com.project.my.entity.user.query.UsersRepository;
import com.project.my.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

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

//    User user = userRepository.save(new
//    // then
//    assertEquals("가입되었습니다.", result);
  }
//
//
////  @Test
////  void 회원_수정_성공() {User("choi", "choi@example.com"));
//    user.setName("newName");
//
//    User updated = userRepository.save(user);
//    assertEquals("newName", updated.getName());
//  }


    @Test
  void 회원_수정_성공() {

      // given
      UserRegisterRequest request = new UserRegisterRequest(
              "test4", "test4@naver.com", "test1234", "test4", Role.M, 2L);

      log.debug("들어옴..");

      // when
      userService.userUpdate(request);

      // then (수정된 내용을 검증)
//      Users updatedUser = usersRepository.findById(2L).orElseThrow();
//      assertThat(updatedUser.getNickname()).isEqualTo("test4");
//      assertThat(updatedUser.getEmail()).isEqualTo("test4@naver.com");
//      assertThat(updatedUser.getPassword()).isEqualTo("test1234");
  }


//  @Test
//  void 회원_조회_성공() {
//    User saved = userRepository.save(new User("lee", "lee@example.com"));
//    Optional<User> result = userRepository.findById(saved.getId());
//
//    assertTrue(result.isPresent());
//    assertEquals("lee", result.get().getName());
//  }


//  @Test
//  void 회원_삭제_성공() {
//    User user = userRepository.save(new User("park", "park@example.com"));
//    userRepository.delete(user);
//
//    Optional<User> result = userRepository.findById(user.getId());
//    assertFalse(result.isPresent());
//  }



}
