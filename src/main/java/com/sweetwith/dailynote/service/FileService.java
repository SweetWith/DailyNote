package com.sweetwith.dailynote.service;

import com.sweetwith.dailynote.domain.file.File;
import com.sweetwith.dailynote.domain.file.FileRepository;
import com.sweetwith.dailynote.web.dto.FileDto;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileService
{
    private Map<Long, Long> postIdToFileIdMap;
    private Map<Long, Long> userIdToFileIdMap;

    private FileRepository fileRepository;

    public FileService(FileRepository fileRepository)
    {
        this.fileRepository = fileRepository;

        postIdToFileIdMap = new HashMap<>();
        userIdToFileIdMap = new HashMap<>();
    }

    // CREATE
    @Transactional
    public FileDto createFile(FileDto fileDto)
    {
        java.io.File dir = new java.io.File(fileDto.getFilePath());
        if (!dir.exists())
            dir.mkdirs();

        try
        {
            fileDto.getMultipartFile().transferTo(new java.io.File(fileDto.getFilePath() + "/" + fileDto.getFileName()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        fileRepository.save(fileDto.toEntity());
        
        postIdToFileIdMap.put(fileDto.getPostId(), fileDto.getFileId());
        userIdToFileIdMap.put(fileDto.getUserId(), fileDto.getFileId());

        return fileDto;
    }

    // READ
    @Transactional
    public FileDto readFileByFileId(Long fileId)
    {
        File file = fileRepository.findById(fileId).get();

        MultipartFile multipartFile = null;
        try
        {
            java.io.File ioFile = new java.io.File(file.getFilePath() + "/" + file.getFileName());
            FileItem fileItem = new DiskFileItem(ioFile.getName(), Files.probeContentType(ioFile.toPath()), false, ioFile.getName(), (int) ioFile.length(), ioFile.getParentFile());
            IOUtils.copy(new FileInputStream(ioFile), fileItem.getOutputStream());
            multipartFile = new CommonsMultipartFile((org.apache.commons.fileupload.FileItem) fileItem);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        FileDto fileDto = FileDto.builder()
                .fileId(file.getFileId())
                .fileName(file.getFileName())
                .filePath(file.getFilePath())
                .fileType(file.getFileType())
                .fileAuthor(file.getFileAuthor())
                .fileSize(file.getFileSize())
                .postId(file.getPostId())
                .userId(file.getUserId())
                .multipartFile(multipartFile)
                .build();

        return fileDto;
    }
    @Transactional
    public FileDto readFileByPostId(Long postId)
    {
        Long fileId = postIdToFileIdMap.get(postId);
        return readFileByFileId(fileId);
    }
    @Transactional
    public FileDto readFileByUserId(Long userId)
    {
        Long fileId = userIdToFileIdMap.get(userId);
        return readFileByFileId(fileId);
    }

    // UPDATE
    @Transactional
    public FileDto updateFile(FileDto fileDto) // Same with create now
    {
        return createFile(fileDto);
    }

    // DELETE
    @Transactional
    public void deleteFileByFileId(Long fileId)
    {
        File file = fileRepository.findById(fileId).get();
        fileRepository.deleteById(file.getPostId());
        postIdToFileIdMap.remove(file.getPostId());
        userIdToFileIdMap.remove(file.getUserId());
    }
    @Transactional
    public void deleteFileByPostId(Long postId)
    {
        Long fileId = postIdToFileIdMap.get(postId);
        deleteFileByFileId(fileId);
    }
    @Transactional
    public void deleteFileByUserId(Long userId)
    {
        Long fileId = postIdToFileIdMap.get(userId);
        deleteFileByFileId(fileId);
    }
}
