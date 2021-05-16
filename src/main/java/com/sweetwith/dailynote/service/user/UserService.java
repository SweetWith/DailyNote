package com.sweetwith.dailynote.service.user;

import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.domain.user.UserRepository;
import com.sweetwith.dailynote.web.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long registerUser(String loginId, String loginPw) {
        User user = new User(loginId, loginPw);
        return userRepository.save(user).getId();
    }

    public UserResponseDto getUserDetail(Long id) {
        Optional<User> user = userRepository.findById(id);

        return new UserResponseDto(user.get());
    }

    public void modifyUser(Long id, String loginPw) {
        userRepository.updateLoginPw(id, loginPw);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}

