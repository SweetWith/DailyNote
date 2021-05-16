package com.sweetwith.dailynote.web.dto;

import com.sweetwith.dailynote.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostRequestDto {
    private String title;
    private String content;
    private int readAuthority;
    private User user;
}
