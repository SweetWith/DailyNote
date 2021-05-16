package com.sweetwith.dailynote.service.user;

import com.sweetwith.dailynote.domain.profile.Profile;
import com.sweetwith.dailynote.domain.profile.ProfileRepository;
import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.web.dto.ProfileResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Long registerProfile(String loginId, String loginPw, User user) {
        Profile profile = new Profile(loginId, loginPw, user);
        return profileRepository.save(profile).getId();
    }

    public ProfileResponseDto getProfileDetail(Long id) {
        Optional<Profile> profile = profileRepository.findById(id);

        return new ProfileResponseDto(profile.get());
    }

    public void modifyProfile(Long id, String name, String email) {
        profileRepository.updateNameAndEmail(id, name, email);
    }

    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }

}

