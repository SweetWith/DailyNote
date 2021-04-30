package com.sweetwith.dailynote.domain.like;

import com.sweetwith.dailynote.domain.posts.Post;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class PostLike {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name="Post_Id")
    private Post post;

    public PostLike(){
    }

}
