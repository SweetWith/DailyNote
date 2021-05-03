package com.sweetwith.dailynote.domain.file;

import com.sweetwith.dailynote.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

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
}
