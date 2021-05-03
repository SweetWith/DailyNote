package com.sweetwith.dailynote.web.dto;

import com.sweetwith.dailynote.domain.file.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
@Builder
public class FileDto
{
    private Long fileId;
    private String fileName;
    private String filePath;
    private String fileType;
    private String fileAuthor;
    private Long fileSize;
    private Long postId;
    private Long userId;
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
                .postId(postId)
                .userId(userId)
                .build();

        return file;
    }
}
