package com.project.my.user.service;

import com.project.my.common.dto.ResultDTO;
import com.project.my.common.exception.ApiException;
import com.project.my.common.exception.ExceptionData;
import com.project.my.common.dto.response.SearchDto;
import com.project.my.common.util.PasswordUtil;
import com.project.my.user.dto.UsersRequest;
import com.project.my.user.entity.Users;
import com.project.my.user.repository.UserQueryRepository;
import com.project.my.user.repository.UsersRepository;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

//    private final UserQueryRepository userQueryRepository;

  @Transactional

  public ResultDTO<Void> resetMemberPassword(String memberId, String adminId) {
    try {
      Long decryptedId = Long.valueOf(cryptoUtil.decryptAES(memberId));

      // 1. 회원 정보 조회
      Member member = memberRepository.findById(decryptedId)
          .orElseThrow(() -> new IllegalArgumentException("회원정보 없음"));

      // 2. 새 salt 생성 및 비밀번호 암호화
      String salt = PasswordUtil.getSalt();
      String newPassword = "m@1111";
      String encrypted = PasswordUtil.hashSSHA(newPassword, salt);

      // 3. 멤버 정보 수정
      member.setPasswd(encrypted);
      member.setSalt(salt);
      member.setIsTempPassword(1);

      // 4. 저장
      memberRepository.save(member);

      // 관리자 비밀번호 초기화 이력을 저장하는 테이블 정보 구현 필요
      manageLogService.saveManageLog(request, "리셋", "비밀번호 초기화", member.getUserId(), member.getUserNm(), adminId);

      // 5. 성공 반환
      return ResultDTO.<Void>builder()
          .success(true)
          .message("관리자가 비밀번호를 초기화하였습니다.")
          .build();

    } catch (Exception ex) {
      log.error("비밀번호 초기화 중 오류", ex);
      return ResultDTO.<Void>builder()
          .success(false)
          .message("비밀번호 초기화 중 오류가 발생했습니다: " + ex.getMessage())
          .build();
    }
  }


/*
* 회원가입
* */

    public ResultDTO<Void> userSignUp(UsersRequest request)  {

        if(usersRepository.existsByLoginId(request.loginId())){
            throw new ApiException(ExceptionData.EXISTS_USER);
        }

      String salt = PasswordUtil.getSalt();
      String encrypted = PasswordUtil.hashSSHA(newPassword, salt);



      Users saveUser = usersRepository.save(user);


        return new UserResponseDto(saveUser);
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
