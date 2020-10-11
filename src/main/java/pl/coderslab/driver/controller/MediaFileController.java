package pl.coderslab.driver.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.driver.dto.MediaFileDto;
import pl.coderslab.driver.service.MediaFileService;

@RestController
@RequestMapping("/file")
@Transactional
public class MediaFileController {

    private final MediaFileService fileService;

    public MediaFileController(MediaFileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(name = "fileId") long fileId) {
        MediaFileDto mediaFileDTO = fileService.getById(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mediaFileDTO.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + mediaFileDTO.getName() + "\"")
                .body(new ByteArrayResource(mediaFileDTO.getMediaFile()));
    }

    @PostMapping("/upload")
    public long uploadFileAndGetFileId(@RequestParam(name = "file") MultipartFile file) {
        return fileService.save(file).getId();
    }

    @PutMapping("/update")
    public void updateFile(@RequestParam(name = "file") MultipartFile file, @RequestParam(name = "fileId") long id) {

        fileService.update(file, id);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam(name = "fileId") long fileId) {

        fileService.delete(fileId);
    }
}