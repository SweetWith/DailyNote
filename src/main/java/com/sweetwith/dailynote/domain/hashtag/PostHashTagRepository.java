package com.sweetwith.dailynote.domain.hashtag;

import com.sweetwith.dailynote.domain.posts.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostHashTagRepository extends JpaRepository<PostHashTag, Long> {

    PostHashTag save(PostHashTag postHashTag);

    List<PostHashTag> findPostHashTagByPost(Post post);
    List<PostHashTag> findPostHashTagByHashTag(HashTag hashTag);

}
