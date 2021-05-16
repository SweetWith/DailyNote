package com.sweetwith.dailynote.domain.file;

import com.sweetwith.dailynote.domain.BaseTimeEntity;
import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Getter
public class File extends BaseTimeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "file_author", nullable = false)
    private String fileAuthor;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    /*
    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "id")
    private Post post;

    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "Id")
    private User user;
    */
}
