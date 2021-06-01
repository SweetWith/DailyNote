package com.sweetwith.dailynote.domain.hashtag;

import com.sweetwith.dailynote.domain.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class HashTag extends BaseTimeEntity {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(length = 50, nullable = false)
    private String tagName;

    @OneToMany(mappedBy = "hashTag")
    private List<PostHashTag> PostHashTag = new ArrayList<>();

    public HashTag(String tageName) {
        this.tagName = tageName;
    }

    public HashTag() {

    }
}
