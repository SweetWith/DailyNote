package com.sweetwith.dailynote.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    // Create
    User save(User user);

    // Read
    Optional<User> findById(Long id);

    // update
    @Modifying
    @Query("UPDATE Post as p SET p.loginId =?2 , p.loginPw =?3 WHERE p.Id =?1")
    boolean updateTitleAndContent(Long Id, String loginId, String loginPw);

    // delete
    void deleteById(Long id);
}
