package com.sweetwith.dailynote.domain.posts;

import com.sweetwith.dailynote.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Create
    Post save(Post post);

    // Read
    Optional<Post> findById(Long id);
    List<Post> findByUser(User user);
    List<Post> findAll();

    // update
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post as p SET p.title =?2 , p.content =?3 WHERE p.Id =?1")
    int updateTitleAndContent(Long id, String title, String content);

    // delete
    void deleteById(Long id);
}
