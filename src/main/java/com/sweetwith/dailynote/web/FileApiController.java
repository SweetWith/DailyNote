package com.sweetwith.dailynote.web;

import com.sweetwith.dailynote.service.FileService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileApiController
{
    private FileService fileService;

    public FileApiController(FileService fileService)
    {
        this.fileService = fileService;
    }
}
