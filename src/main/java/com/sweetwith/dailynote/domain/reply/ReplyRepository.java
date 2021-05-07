package com.sweetwith.dailynote.domain.reply;

import com.sweetwith.dailynote.domain.posts.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // Create
    Reply save(Reply reply);

    // Read
    List<Reply> findByPost(Post post);
    Optional<Reply> findById(Long id);

    // update
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Reply as r SET r.content =?2 WHERE r.Id =?1")
    int updateContent(Long id, String content);

    // delete
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Reply as r SET r.isDeleted = true WHERE r.Id =?1")
    int deleteReply(Long id);

}
