package com.sweetwith.dailynote.service.file;

import com.sweetwith.dailynote.domain.file.FileRepository;
import com.sweetwith.dailynote.service.FileService;
import com.sweetwith.dailynote.web.dto.FileDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class FileServiceTest
{
    @Autowired FileRepository fileRepository;
    @Autowired FileService fileService;
    static FileDto fileDto = FileDto.builder()
            .fileName("test.txt")
            .filePath("C:\\Users\\kcassia")
            .fileType("text/plain")
            .fileAuthor("kcassia")
            .fileSize(10L)
            .postId(1L)
            .userId(2L)
            .multipartFile(new MockMultipartFile("test", "test.txt", "text/plain", "Hello World".getBytes()))
            .build();

    @Test
    @DisplayName("Check createFile")
    public void checkCreateFile()
    {
        fileService.clear();
        Long fileId = fileService.createFile(fileDto).getFileId();
        Assertions.assertThat(fileRepository.findById(fileId).get().equals(fileDto.toEntity()));
    }

    @Test
    @DisplayName("Check readFileByFileId")
    public void checkReadFileByFileId()
    {
        fileService.clear();
        Long fileId = fileService.createFile(fileDto).getFileId();
        Assertions.assertThat(fileService.readFileByFileId(fileId).equals(fileDto));
    }

    @Test
    @DisplayName("Check readFilesByPostId")
    public void checkReadFilesByPostId()
    {
        fileService.clear();
        fileService.createFile(fileDto).getFileId();
        Assertions.assertThat(fileService.readFilesByPostId(1L).get(0).equals(fileDto));
    }

    @Test
    @DisplayName("Check readFilesByUserId")
    public void checkReadFilesByUserId()
    {
        fileService.clear();
        fileService.createFile(fileDto).getFileId();
        Assertions.assertThat(fileService.readFilesByUserId(2L).get(0).equals(fileDto));
    }

    @Test
    @DisplayName("Check updateFile")
    public void checkUpdateFile()
    {
        fileService.clear();
        Long fileId = fileService.updateFile(fileDto).getFileId();
        Assertions.assertThat(fileRepository.findById(fileId).get().equals(fileDto.toEntity()));
    }

    @Test
    @DisplayName("Check deleteFileByFileId")
    public void checkDeleteFileByFileId()
    {
        fileService.clear();
        Long fileId = fileService.createFile(fileDto).getFileId();
        fileService.deleteFileByFileId(fileId, false);
        Assertions.assertThat(!fileRepository.existsById(fileId));
    }

    @Test
    @DisplayName("Check deleteFilesByPostId")
    public void checkDeleteFilesByPostId()
    {
        fileService.clear();
        fileService.createFile(fileDto).getFileId();
        fileService.deleteFilesByPostId(1L);
        Assertions.assertThat(!fileRepository.existsById(1L));
    }

    @Test
    @DisplayName("Check deleteFilesByUserId")
    public void checkDeleteFilesByUserId()
    {
        fileService.clear();
        fileService.createFile(fileDto);
        fileService.deleteFilesByUserId(2L);
        Assertions.assertThat(!fileRepository.existsById(2L));
    }
}
