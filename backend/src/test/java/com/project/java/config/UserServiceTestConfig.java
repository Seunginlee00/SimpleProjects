package com.project.java.config;

import com.project.my.entity.user.query.UserRepositoryImpl;
import com.project.my.entity.user.query.UsersRepository;
import com.project.my.service.user.UserService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class UserServiceTestConfig {

  @Bean
  public UsersRepository usersRepository() {
    return Mockito.mock(UsersRepository.class); // 테스트용 mock 객체
  }

  @Bean
  public UserRepositoryImpl userRepositoryImpl() {
    return Mockito.mock(UserRepositoryImpl.class); // 혹은 생성자에 필요한 의존성도 넘겨줘야 할 수 있음
  }


  @Bean
  public UserService userService(PasswordEncoder passwordEncoder, UsersRepository usersRepository,
                                 UserRepositoryImpl userImplRepository) {
    return new UserService(passwordEncoder, usersRepository,userImplRepository);
  }
}
