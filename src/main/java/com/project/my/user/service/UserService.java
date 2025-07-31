package com.project.my.user.service;

import com.project.my.common.dto.ResultDTO;
import com.project.my.common.exception.ApiException;
import com.project.my.common.exception.ExceptionData;
import com.project.my.common.util.PasswordUtil;
import com.project.my.user.dto.UsersRequest;
import com.project.my.user.entity.Users;
import com.project.my.user.repository.UsersRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

//    private final UserQueryRepository userQueryRepository;



/*
* 회원가입
* */
    @Transactional(readOnly = true)
    public ResultDTO<Void> userSignUp(UsersRequest request)  {

      try{
        if(usersRepository.existsByLoginId(request.loginId())){
          throw new ApiException(ExceptionData.EXISTS_USER);
        }

        String salt = PasswordUtil.getSalt();
        String encrypted = PasswordUtil.hashSSHA(request.passwd(), salt);

        Users user = request.toEntity(encrypted);


        Users saveUser = usersRepository.save(user);


        return ResultDTO.<Void>builder()
            .success(true)
            .message("회원 가입 되었습니다.")
            .build();
      }catch (Exception ex) {
        log.error("회원 가입 중 오류", ex);
        return ResultDTO.<Void>builder()
            .success(false)
            .message("회원 가입 중 오류가 발생했습니다: " + ex.getMessage())
            .build();
      }


    }


//    /*
//     * 회원 수정
//     * */
//    public UserResponseDto userUpdate(Long userId, UserRegisterRequest request) {
//
//        Users findUser = usersRepository.findById(userId)
//                .orElseThrow(()-> new ApiException(ExceptionData.NOT_FOUND_USER));
//
//        Optional.ofNullable(request.password())
//            .map(passwordEncoder::encode)
//            .ifPresent(newPassword -> findUser.update(request, newPassword));
//
//         return UserResponseDto.from(findUser);
//    }
//
//    /*
//     * 회원 조회
//     * */
//    @Transactional(readOnly = true)
//    public UserResponse userSearch(UserRegisterRequest request){
//
//        Users findUser = usersRepository.findByLoginId(request.loginId())
//            .orElseThrow(() -> new ApiException(ExceptionData.NOT_FOUND_USER));
//        return new UserResponse(findUser);
//    }
//
//
//    /*
//     * 회원 목록 조회
//     * */
//    @Transactional(readOnly = true)
//    public Page<UserResponse> userListSearch(SearchDto dto, Pageable pageable) {
//        return userQueryRepository.findList(dto, pageable)
//            .map(UserResponse::new);
//    }
//
//    /*
//     * 회원 삭제
//     * */
//    public void delete(List<Long> deleteIds)  {
//
//        for (Long di : deleteIds) {
//            Users findUser = usersRepository.findById(di).orElseThrow(() -> new ApiException(ExceptionData.NOT_FOUND_USER));
//            usersRepository.delete(findUser);
//        }
//
//    }



}
