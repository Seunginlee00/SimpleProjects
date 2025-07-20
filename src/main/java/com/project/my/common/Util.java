package com.project.my.common;

import com.project.my.user.entity.Users;
import com.project.my.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class Util {

    private final UsersRepository usersRepository;

    public Users getCommonUserInfo(Long id) {
        Users users = usersRepository.getReferenceById(id);
        return users;
    }

}
