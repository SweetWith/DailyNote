package com.sweetwith.dailynote.domain.like;

import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table
public class PostLike {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name="Post_Id")
    private Post post;

    @ManyToOne
    @JoinColumn(name="User_Id")
    private User user;

    public PostLike(Post post, User user){
        this.post = post;
        this.user = user;
    }
}
