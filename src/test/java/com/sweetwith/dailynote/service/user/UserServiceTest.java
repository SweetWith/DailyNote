package com.sweetwith.dailynote.service.user;

import com.sweetwith.dailynote.domain.user.UserRepository;
import com.sweetwith.dailynote.web.dto.UserResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @org.junit.jupiter.api.Test
    void registerUser() {
        String loginId = "TEST_LOGIN_ID";
        String loginPw = "TEST_LOGIN_PW";

        Long userId = userService.registerUser(loginId, loginPw);
        Assertions.assertThat(userId).isGreaterThan(0);

        userService.deleteUser(userId);
    }

    @org.junit.jupiter.api.Test
    void getUserDetail() {
        String loginId = "TEST_LOGIN_ID";
        String loginPw = "TEST_LOGIN_PW";

        Long userId = userService.registerUser(loginId, loginPw);
        UserResponseDto ret = userService.getUserDetail(userId);

        Assertions.assertThat(ret.getId()).isEqualTo(userId);
        Assertions.assertThat(ret.getLoginId()).isEqualTo(loginId);
        Assertions.assertThat(ret.getLoginPw()).isEqualTo(loginPw);
    }

    @org.junit.jupiter.api.Test
    void modifyUser() {

        String loginId = "TEST_LOGIN_ID";
        String loginPw1 = "TEST_LOGIN_PW1";
        String loginPw2 = "TEST_LOGIN_PW2";

        Long userId = userService.registerUser(loginId, loginPw1);
        userService.modifyUser(userId, loginPw2);

        UserResponseDto ret = userService.getUserDetail(userId);
        Assertions.assertThat(ret.getId()).isEqualTo(userId);
        Assertions.assertThat(ret.getLoginId()).isEqualTo(loginId);
        Assertions.assertThat(ret.getLoginPw()).isEqualTo(loginPw2);

    }

    @org.junit.jupiter.api.Test
    void deleteUser() {
        String loginId = "TEST_LOGIN_ID";
        String loginPw = "TEST_LOGIN_PW";

        Long userId = userService.registerUser(loginId, loginPw);
        userService.deleteUser(userId);

//        UserResponseDto ret = userService.getUserDetail(userId);

    }
}
