package com.project.java.config;

import com.project.my.user.repository.UserQueryRepository;
import com.project.my.user.repository.UsersRepository;
import com.project.my.user.service.UserService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class UserServiceTestConfig {

  @Bean
  public UsersRepository usersRepository() {
    return Mockito.mock(UsersRepository.class); // 테스트용 mock 객체
  }

  @Bean
  public UserQueryRepository userRepositoryImpl() {
    return Mockito.mock(UserQueryRepository.class); // 혹은 생성자에 필요한 의존성도 넘겨줘야 할 수 있음
  }


  @Bean
  public UserService userService(PasswordEncoder passwordEncoder, UsersRepository usersRepository,
                                 UserQueryRepository userImplRepository) {
    return new UserService(passwordEncoder, usersRepository,userImplRepository);
  }
}
