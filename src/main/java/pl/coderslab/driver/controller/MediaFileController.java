package pl.coderslab.driver.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.driver.dto.MediaFileDTO;
import pl.coderslab.driver.service.MediaFileService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MediaFileController {

    private final MediaFileService fileService;

    public MediaFileController(MediaFileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/file/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(name = "fileId") long fileId) {
        MediaFileDTO mediaFileDTO = fileService.getById(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mediaFileDTO.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + mediaFileDTO.getName() + "\"")
                .body(new ByteArrayResource(mediaFileDTO.getMediaFile()));
    }

    @PostMapping("/file/upload")
    public String uploadFileAndGetFileDownloadUrl(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request) {
        long fileId = fileService.save(file);

        return request.getHeader("host") + "/api/file/download?fileId=" + fileId;
    }

    @PutMapping("/file/update")
    public void updateFile(@RequestParam(name = "file") MultipartFile file, @RequestParam(name = "fileId") long id) {

        fileService.update(file, id);
    }

    @DeleteMapping("/file/delete")
    public void delete(@RequestParam(name = "fileId") long fileId) {

        fileService.delete(fileId);
    }
}