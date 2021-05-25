package com.sweetwith.dailynote.web.dto;

import com.sweetwith.dailynote.domain.file.File;
import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class FileDto
{
    private Long fileId;
    private String fileName;
    private String filePath;
    private String fileType;
    private String fileAuthor;
    private Long fileSize;
    private Post post;
    private User user;
    private MultipartFile multipartFile;

    public File toEntity() // exclude multipartFile
    {
        File file = File.builder()
                .fileId(fileId)
                .fileName(fileName)
                .filePath(filePath)
                .fileType(fileType)
                .fileAuthor(fileAuthor)
                .fileSize(fileSize)
                .post(post)
                .user(user)
                .build();

        return file;
    }
}
