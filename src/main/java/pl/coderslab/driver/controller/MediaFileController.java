package pl.coderslab.driver.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.driver.dto.MediaFileDto;
import pl.coderslab.driver.service.MediaFileService;

@RestController
@RequestMapping("/api/files")
@Transactional
public class MediaFileController {

    private final MediaFileService fileService;

    public MediaFileController(MediaFileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable(name = "id") long fileId) {
        MediaFileDto mediaFileDTO = fileService.getById(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mediaFileDTO.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + mediaFileDTO.getName() + "\"")
                .body(new ByteArrayResource(mediaFileDTO.getMediaFile()));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/upload")
    public long uploadFileAndGetFileId(@RequestParam(name = "file") MultipartFile file) {
        return fileService.save(file).getId();
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/update/{id}")
    public void updateFile(@RequestParam(name = "file") MultipartFile file, @PathVariable(name = "id") long id) {

        fileService.update(file, id);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") long fileId) {

        fileService.delete(fileId);
    }
}