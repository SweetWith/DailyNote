package com.sweetwith.dailynote.domain.posts;

import com.sweetwith.dailynote.domain.BaseTimeEntity;
import com.sweetwith.dailynote.domain.hashtag.PostHashTag;
import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.web.dto.PostRequestDto;
import com.sweetwith.dailynote.web.dto.PostResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private Integer readAuthority;

    @ManyToOne
    @JoinColumn(name="User_Id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<PostHashTag> postHashTags = new ArrayList<>();

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public Post(PostRequestDto postRequestDto){
        title = postRequestDto.getTitle();
        content = postRequestDto.getContent();
        readAuthority = postRequestDto.getReadAuthority();
        user = postRequestDto.getUser();
    }

    public Post(PostResponseDto postResponseDto){
        Id = postResponseDto.getId();
        title = postResponseDto.getTitle();
        content = postResponseDto.getTitle();
    }

}
