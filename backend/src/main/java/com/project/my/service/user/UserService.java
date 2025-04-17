package com.project.my.service.user;

import com.java.project.api.common.exception.AlreadyExistElementException;
import com.java.project.api.common.exception.NotFoundElementException;
import com.java.project.api.config.jwt.JwtProvider;
import com.java.project.api.dto.login.UserRegisterRequest;
import com.java.project.api.dto.response.SearchDto;
import com.java.project.api.dto.response.UserResponse;
import com.java.project.api.entity.user.Users;
import com.java.project.api.entity.user.query.UserRepositoryImpl;
import com.java.project.api.entity.user.query.UsersRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final UserRepositoryImpl userRepositoryImpl;

/*
* 회원가입
* */

    public String userSignUp(UserRegisterRequest request) throws InvalidInputException {

        if (usersRepository.findByLoginId(request.loginId()).isPresent()) {
            throw new AlreadyExistElementException("중복된 id 입니다.");
        }

        Users user = request.toEntity(passwordEncoder.encode(request.password()));
        usersRepository.save(user);
        return "가입되었습니다.";
    }


    /*
     * 회원 수정
     * */
    public String userUpdate(UserRegisterRequest request) {

        Users findUser = usersRepository.findByLoginId(request.loginId())
            .orElseThrow(() -> new NotFoundElementException("없는 회원 입니다."));

        Optional.ofNullable(request.password())
            .map(passwordEncoder::encode)
            .ifPresent(newPassword -> findUser.update(request, newPassword));


         return "수정 되었습니다.";
    }


    /*
     * 회원 조회
     * */
    @Transactional(readOnly = true)
    public UserResponse userSearch(UserRegisterRequest request){

        Users findUser = usersRepository.findByLoginId(request.loginId())
            .orElseThrow(() -> new NotFoundElementException("없는 회원 입니다."));
        return new UserResponse(findUser);
    }


    /*
     * 회원 목록 조회
     * */
    @Transactional(readOnly = true)
    public Page<UserResponse> userListSearch(SearchDto dto, Pageable pageable) {
        return userRepositoryImpl.findList(dto, pageable)
            .map(UserResponse::new);
    }

    /*
     * 회원 삭제
     * */
    public String delete(List<Long> deleteIds)  {

//        Users findUser = usersRepository.findById(di).orElseThrow(() -> new NotFoundElementException("없는 회원 입니다."));

        for (Long di : deleteIds) {
            Users findUser = usersRepository.findById(di).orElseThrow(() -> new NotFoundElementException("없는 회원 입니다."));
            usersRepository.delete(findUser);
        }

//        if (usersRepository.findByLoginId(request.loginId()).isPresent()) {
//            throw new InvalidInputException("중복된 id 입니다.");
//        }




        return "가입되었습니다.";
    }



}
