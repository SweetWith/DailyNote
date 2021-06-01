package com.sweetwith.dailynote.domain.hashtag;

import com.sweetwith.dailynote.domain.posts.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@ToString
public class PostHashTag {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    public PostHashTag(Post post, HashTag hashTag) {
        this.post = post;
        this.hashTag = hashTag;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="Post_Id")
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="HashTag_Id")
    private HashTag hashTag;


}
