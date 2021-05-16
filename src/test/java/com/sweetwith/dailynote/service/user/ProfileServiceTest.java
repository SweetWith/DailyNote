package com.sweetwith.dailynote.service.user;

import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.web.dto.ProfileResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProfileServiceTest {

    @Autowired
    ProfileService profileService;

    @Test
    void registerProfile() {
        String loginId = "TEST_LOGIN_ID";
        String loginPw = "TEST_LOGIN_PW";

        String name = "TEST_NAME";
        String email = "TEST_EMAIL";
        User user = new User(loginId, loginPw);


        Long profileId = profileService.registerProfile(name, email, user);

        Assertions.assertThat(profileId).isGreaterThan(0);
        profileService.deleteProfile(profileId);
    }

    @Test
    void getProfileDetail() {
        String loginId = "TEST_LOGIN_ID";
        String loginPw = "TEST_LOGIN_PW";

        String name = "TEST_NAME";
        String email = "TEST_EMAIL";
        User user = new User(loginId, loginPw);


        Long profileId = profileService.registerProfile(name, email, user);

        ProfileResponseDto ret = profileService.getProfileDetail(profileId);
        Assertions.assertThat(ret.getId()).isEqualTo(profileId);
        Assertions.assertThat(ret.getName()).isEqualTo(name);
        Assertions.assertThat(ret.getEmail()).isEqualTo(email);
        Assertions.assertThat(ret.getUser()).isEqualTo(user);

        profileService.deleteProfile(profileId);
    }

    @Test
    void modifyProfile() {
        String loginId = "TEST_LOGIN_ID";
        String loginPw = "TEST_LOGIN_PW";

        String name_1 = "TEST_NAME_1";
        String email_1 = "TEST_EMAIL_1";
        String name_2 = "TEST_NAME_2";
        String email_2 = "TEST_EMAIL_2";

        User user = new User(loginId, loginPw);


        Long profileId = profileService.registerProfile(name_1, email_1, user);
        profileService.modifyProfile(profileId, name_2, email_2);

        ProfileResponseDto ret = profileService.getProfileDetail(profileId);
        Assertions.assertThat(ret.getId()).isEqualTo(profileId);
        Assertions.assertThat(ret.getName()).isEqualTo(name_2);
        Assertions.assertThat(ret.getEmail()).isEqualTo(email_2);
        Assertions.assertThat(ret.getUser()).isEqualTo(user);

        profileService.deleteProfile(profileId);
    }

    @Test
    void deleteProfile() {
        String loginId = "TEST_LOGIN_ID";
        String loginPw = "TEST_LOGIN_PW";

        String name = "TEST_NAME";
        String email = "TEST_EMAIL";
        User user = new User(loginId, loginPw);


        Long profileId = profileService.registerProfile(name, email, user);

        profileService.deleteProfile(profileId);
        
    }
}