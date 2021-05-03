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
            .multipartFile(new MultipartFile() {
                @Override public String getName() { return "test.txt"; }
                @Override public String getOriginalFilename() { return "test.txt"; }
                @Override public String getContentType() { return "text/plain"; }
                @Override public boolean isEmpty() { return false; }
                @Override public long getSize() { return 0; }
                @Override public byte[] getBytes() { return new byte[0]; }
                @Override public InputStream getInputStream() { return null; }
                @Override public void transferTo(File dest) throws IllegalStateException { }
            })
            .build();

    @Test
    @DisplayName("Check createFile")
    public void checkCreateFile()
    {
        fileService.clear();
        fileService.createFile(fileDto);
        Assertions.assertThat(fileRepository.findById(1L).get().equals(fileDto.toEntity()));
    }

    @Test
    @DisplayName("Check readFileByFileId")
    public void checkReadFileByFileId()
    {
        fileService.clear();
        fileService.createFile(fileDto);
        Assertions.assertThat(fileService.readFileByFileId(1L).equals(fileDto));
    }

    @Test
    @DisplayName("Check readFileByPostId")
    public void checkReadFileByPostId()
    {
        fileService.clear();
        fileService.createFile(fileDto);
        fileService.readFileByPostId(1L);
        Assertions.assertThat(fileService.readFileByPostId(1L).equals(fileDto));
    }

    @Test
    @DisplayName("Check readFileByUserId")
    public void checkReadFileByUserId()
    {
        fileService.clear();
        fileService.createFile(fileDto);
        fileService.readFileByUserId(2L);
        Assertions.assertThat(fileService.readFileByUserId(2L).equals(fileDto));
    }

    @Test
    @DisplayName("Check updateFile")
    public void checkUpdateFile()
    {
        fileService.clear();
        fileService.updateFile(fileDto);
        Assertions.assertThat(fileRepository.findById(1L).get().equals(fileDto.toEntity()));
    }

    @Test
    @DisplayName("Check deleteFileByFileId")
    public void checkDeleteFileByFileId()
    {
        fileService.clear();
        fileService.createFile(fileDto);
        fileService.deleteFileByFileId(1L);
        Assertions.assertThat(!fileRepository.existsById(1L));
    }

    @Test
    @DisplayName("Check deleteFileByPostId")
    public void checkDeleteFileByPostId()
    {
        fileService.clear();
        fileService.createFile(fileDto);
        fileService.deleteFileByPostId(1L);
        Assertions.assertThat(!fileRepository.existsById(1L));
    }

    @Test
    @DisplayName("Check deleteFileByUserId")
    public void checkDeleteFileByUserId()
    {
        fileService.clear();
        fileService.createFile(fileDto);
        fileService.deleteFileByUserId(2L);
        Assertions.assertThat(!fileRepository.existsById(2L));
    }
}
