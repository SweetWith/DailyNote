package com.sweetwith.dailynote.service.user;

import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.domain.user.UserRepository;
import com.sweetwith.dailynote.web.dto.UserResponseDto;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    @DisplayName("Register User. After receive registered info and check.")
    public void searchByUserID() {

        String loginId = "TEST_LOGIN_ID";
        String loginPw = "TEST_LOGIN_PW";
    
        Long userId = userService.registerUser(loginId, loginPw);
        UserResponseDto ret = userService.getUserDetail(userId);

        Assertions.assertThat(ret.getId()).isEqualTo(userId);
        Assertions.assertThat(ret.getLoginId()).isEqualTo(loginId);
        Assertions.assertThat(ret.getLoginPw()).isEqualTo(loginPw);
    }

}
