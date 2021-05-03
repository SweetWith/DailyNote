package com.sweetwith.dailynote.service.user;

import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.domain.user.UserRepository;
import com.sweetwith.dailynote.web.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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

    public void modifyUser(Long id, String loginId, String loginPw) {
        userRepository.updateTitleAndContent(id, loginId, loginPw);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}

