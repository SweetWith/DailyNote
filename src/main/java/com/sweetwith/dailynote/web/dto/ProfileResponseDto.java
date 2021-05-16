
package com.sweetwith.dailynote.web.dto;

import com.sweetwith.dailynote.domain.profile.Profile;
import com.sweetwith.dailynote.domain.user.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProfileResponseDto {
    private Long id;
    private String name;
    private String email;
    private User user;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public ProfileResponseDto(Profile entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.user = entity.getUser();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
