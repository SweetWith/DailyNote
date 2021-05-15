
package com.sweetwith.dailynote.web.dto;

import com.sweetwith.dailynote.domain.user.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private Long id;
    private String loginId;
    private String loginPw;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.loginId = entity.getLoginId();
        this.loginPw = entity.getLoginPw();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
