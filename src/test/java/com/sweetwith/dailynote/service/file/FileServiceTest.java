package com.sweetwith.dailynote.service.file;

import com.sweetwith.dailynote.domain.file.File;
import com.sweetwith.dailynote.domain.file.FileRepository;
import com.sweetwith.dailynote.domain.posts.Post;
import com.sweetwith.dailynote.domain.posts.PostRepository;
import com.sweetwith.dailynote.domain.user.User;
import com.sweetwith.dailynote.domain.user.UserRepository;
import com.sweetwith.dailynote.service.FileService;
import com.sweetwith.dailynote.web.dto.FileDto;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class FileServiceTest
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    private FileDto fileDto;

    @Before
    public void init()
    {
        fileService.clear();

        userRepository.deleteAll();;
        postRepository.deleteAll();

        User user = userRepository.save(new User("locinId", "loginPw"));
        Post post = postRepository.save(new Post("title", "content", user));
        fileDto = FileDto.builder()
                .fileName("test.txt")
                .filePath("C:\\Users\\kcassia")
                .fileType("text/plain")
                .fileAuthor("kcassia")
                .fileSize(10L)
                .post(post)
                .user(user)
                .multipartFile(new MockMultipartFile("test", "test.txt", "text/plain", "Hello World".getBytes()))
                .build();
    }

    @Test
    @DisplayName("Check createFile")
    public void checkCreateFile()
    {
        // init

        // do
        FileDto createdFile = fileService.createFile(fileDto);

        // check
        Assertions.assertThat(fileRepository.findById(createdFile.getFileId()).get().equals(createdFile.toEntity()));
    }

    @Test
    @DisplayName("Check readFileByFileId")
    public void checkReadFileByFileId()
    {
        // init

        // check
        FileDto createdFile = fileService.createFile(fileDto);

        // check
        Assertions.assertThat(fileService.readFileByFileId(createdFile.getFileId()).equals(createdFile));
    }

    @Test
    @DisplayName("Check readFilesByPostId")
    public void checkReadFilesByPostId()
    {
        // init

        // do
        FileDto createdFile = fileService.createFile(fileDto);

        // check
        Assertions.assertThat(fileService.readFilesByPostId(createdFile.getPost().getId()).get(0).equals(createdFile));
    }

    @Test
    @DisplayName("Check readFilesByUserId")
    public void checkReadFilesByUserId()
    {
        // init

        // do
        FileDto createdFile = fileService.createFile(fileDto);

        // check
        Assertions.assertThat(fileService.readFilesByUserId(createdFile.getUser().getId()).get(0).equals(createdFile));
    }

    @Test
    @DisplayName("Check updateFile")
    public void checkUpdateFile()
    {
        // init

        // do
        FileDto updatedFile = fileService.updateFile(fileDto);

        // check
        Assertions.assertThat(fileRepository.findById(updatedFile.getFileId()).get().equals(updatedFile.toEntity()));
    }

    @Test
    @DisplayName("Check deleteFileByFileId")
    public void checkDeleteFileByFileId()
    {
        // init
        FileDto createdFileDto = fileService.createFile(fileDto);

        // do
        fileService.deleteFileByFileId(createdFileDto.getFileId(), false);

        // check
        Assertions.assertThat(!fileRepository.existsById(createdFileDto.getFileId()));
    }

    @Test
    @DisplayName("Check deleteFilesByPostId")
    public void checkDeleteFilesByPostId()
    {
        // init
        FileDto createdFileDto = fileService.createFile(fileDto);

        // do
        fileService.deleteFilesByPostId(createdFileDto.getPost().getId());

        // check
        Assertions.assertThat(!fileRepository.existsById(createdFileDto.getFileId()));
    }

    @Test
    @DisplayName("Check deleteFilesByUserId")
    public void checkDeleteFilesByUserId()
    {
        // init
        FileDto createFileDto = fileService.createFile(fileDto);

        // do
        fileService.deleteFilesByUserId(createFileDto.getUser().getId());

        // check
        Assertions.assertThat(!fileRepository.existsById(createFileDto.getFileId()));
    }
}
