package com.sweetwith.dailynote.domain.reply;

import com.sweetwith.dailynote.domain.BaseTimeEntity;
import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.web.dto.ReplyRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Reply extends BaseTimeEntity {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(length = 500, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name="Post_Id")
    private Post post;

    @ManyToOne
    @JoinColumn(name="User_Id")
    private User user;

    @Column
    private Long replyParentId;

    @Column
    private Boolean isDeleted;

    public Reply(ReplyRequestDto replyRequestDto){
        this.content = replyRequestDto.getContent();
        this.post = replyRequestDto.getPost();
        this.user = replyRequestDto.getUser();
        this.replyParentId = replyRequestDto.getReplyParentId() == null ? 0 : replyRequestDto.getReplyParentId();
        this.isDeleted = false;
    }

}
