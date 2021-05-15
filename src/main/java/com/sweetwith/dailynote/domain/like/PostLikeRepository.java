package com.sweetwith.dailynote.domain.like;

import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    // Create
    PostLike save(PostLike postLike);

    // Read
    List<PostLike> findByPost(Post post);
    Optional<PostLike> findByPostAndUser(Post post, User user);

    // Delete
    void deleteByPostAndUser(Post post, User user);
}
