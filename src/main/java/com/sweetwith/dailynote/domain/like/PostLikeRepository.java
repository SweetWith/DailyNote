package com.sweetwith.dailynote.domain.like;

import com.sweetwith.dailynote.domain.posts.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    PostLike save(PostLike postLike);

    List<PostLike> findByPost(Post post);
    // TODO List<PostLike> findByUsers(User user);

    void deleteById(Long id);
}
