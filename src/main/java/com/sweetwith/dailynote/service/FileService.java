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
import java.util.*;

@Service
public class FileService
{
    private Map<Long, List<Long>> postIdToFileIdListMap;
    private Map<Long, List<Long>> userIdToFileIdListMap;

    private FileRepository fileRepository;

    public FileService(FileRepository fileRepository)
    {
        this.fileRepository = fileRepository;

        postIdToFileIdListMap = new HashMap<>();
        userIdToFileIdListMap = new HashMap<>();
    }

    public void clear()
    {
        postIdToFileIdListMap.clear();
        userIdToFileIdListMap.clear();
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

        fileDto.setFileId(fileRepository.save(fileDto.toEntity()).getFileId());

        postIdToFileIdListMap.putIfAbsent(fileDto.getPostId(), new LinkedList<>());
        List<Long> postIdToFileIdList = postIdToFileIdListMap.get(fileDto.getPostId());
        postIdToFileIdList.add(fileDto.getFileId());

        userIdToFileIdListMap.putIfAbsent(fileDto.getUserId(), new LinkedList<>());
        List<Long> userIdToFileIdList = userIdToFileIdListMap.get(fileDto.getUserId());
        userIdToFileIdList.add(fileDto.getFileId());

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
    public List<FileDto> readFilesByPostId(Long postId)
    {
        List<FileDto> files = new LinkedList<>();
        List<Long> postIdToFileIdList = postIdToFileIdListMap.get(postId);
        for (Long fileId : postIdToFileIdList)
            files.add(readFileByFileId(fileId));
        return files;
    }
    @Transactional
    public List<FileDto> readFilesByUserId(Long userId)
    {
        List<FileDto> files = new LinkedList<>();
        List<Long> userIdToFileIdList = userIdToFileIdListMap.get(userId);
        for (Long fileId : userIdToFileIdList)
            files.add(readFileByFileId(fileId));
        return files;
    }

    // UPDATE
    @Transactional
    public FileDto updateFile(FileDto fileDto) // Same with create now
    {
        return createFile(fileDto);
    }

    // DELETE
    @Transactional
    public void deleteFileByFileId(Long fileId, boolean byOtherId)
    {
        File file = fileRepository.findById(fileId).get();
        fileRepository.deleteById(file.getFileId());

        if (!byOtherId)
        {
            List<Long> postIdToFileIdList = postIdToFileIdListMap.get(file.getPostId());
            for(Long fileIdInList : postIdToFileIdList)
            {
                if(fileIdInList.equals(fileId))
                {
                    postIdToFileIdList.remove(fileId);
                    break;
                }
            }

            List<Long> userIdToFileIdList = userIdToFileIdListMap.get(file.getUserId());
            for(Long fileIdInList : userIdToFileIdList)
            {
                if(fileIdInList.equals(fileId))
                {
                    userIdToFileIdList.remove(fileId);
                    break;
                }
            }
        }
    }
    @Transactional
    public void deleteFilesByPostId(Long postId)
    {
        List<Long> postIdToFileIdList = postIdToFileIdListMap.get(postId);
        for (Long fileId : postIdToFileIdList)
            deleteFileByFileId(fileId, true);
        postIdToFileIdListMap.remove(postId);
    }
    @Transactional
    public void deleteFilesByUserId(Long userId)
    {
        List<Long> userIdToFileIdList = userIdToFileIdListMap.get(userId);
        for (Long fileId : userIdToFileIdList)
            deleteFileByFileId(fileId, true);
        postIdToFileIdListMap.remove(userId);
    }
}
