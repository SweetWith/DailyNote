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
    private Long fileId;

    @Column(nullable = false) private String fileName;
    @Column(nullable = false) private String filePath;
    @Column(nullable = false) private String fileType;
    @Column(nullable = false) private String fileAuthor;
    @Column(nullable = false) private Long fileSize;
    @Column(nullable = false) private Long postId;
    @Column(nullable = false) private Long userId;

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
