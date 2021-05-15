package com.sweetwith.dailynote.web.dto;

import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReplyRequestDto {
    private String content;
    private Post post;
    private User user;
    private Long replyParentId;

}
