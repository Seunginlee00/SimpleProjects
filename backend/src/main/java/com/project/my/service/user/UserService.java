package com.project.my.service.user;

import com.project.my.common.exception.ApiException;
import com.project.my.common.exception.ExceptionData;
import com.project.my.dto.login.UserRegisterRequest;
import com.project.my.dto.login.UserResponseDto;
import com.project.my.dto.response.SearchDto;
import com.project.my.dto.response.UserResponse;
import com.project.my.entity.user.Users;
import com.project.my.entity.user.query.UserRepositoryImpl;
import com.project.my.entity.user.query.UsersRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    private final UserRepositoryImpl userRepositoryImpl;

/*
* 회원가입
* */

    public UserResponseDto userSignUp(UserRegisterRequest request)  {

        if(usersRepository.existsByLoginId(request.loginId())){
            throw new ApiException(ExceptionData.EXISTS_USER);
        }

        Users user = request.toEntity(passwordEncoder.encode(request.password()));
        Users saveUser = usersRepository.save(user);


        return new UserResponseDto(saveUser);
    }


    /*
     * 회원 수정
     * */
    public UserResponseDto userUpdate(Long userId, UserRegisterRequest request) {

        Users findUser = usersRepository.findById(userId)
                .orElseThrow(()-> new ApiException(ExceptionData.NOT_FOUND_USER));

        Optional.ofNullable(request.password())
            .map(passwordEncoder::encode)
            .ifPresent(newPassword -> findUser.update(request, newPassword));

         return UserResponseDto.from(findUser);
    }

    /*
     * 회원 조회
     * */
    @Transactional(readOnly = true)
    public UserResponse userSearch(UserRegisterRequest request){

        Users findUser = usersRepository.findByLoginId(request.loginId())
            .orElseThrow(() -> new ApiException(ExceptionData.NOT_FOUND_USER));
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
    public void delete(List<Long> deleteIds)  {

        for (Long di : deleteIds) {
            Users findUser = usersRepository.findById(di).orElseThrow(() -> new ApiException(ExceptionData.NOT_FOUND_USER));
            usersRepository.delete(findUser);
        }

    }



}
