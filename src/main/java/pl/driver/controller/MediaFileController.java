package pl.driver.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.driver.dto.MediaFileDto;
import pl.driver.exceptions.MediaFileNotFoundException;
import pl.driver.service.MediaFileService;

@RestController
@RequestMapping("/api/files")
@Transactional
public class MediaFileController {

    private final MediaFileService fileService;

    public MediaFileController(MediaFileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/download/{id}")
    @ApiOperation(value = "Download media file by id")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable(name = "id") long fileId) {
        try {
            MediaFileDto mediaFileDTO = fileService.getById(fileId);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mediaFileDTO.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + mediaFileDTO.getName() + "\"")
                    .body(new ByteArrayResource(mediaFileDTO.getMediaFile()));
        } catch (MediaFileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/upload")
    @ApiOperation(value = "Upload media file")
    public ResponseEntity<Long> uploadFileAndGetFileId(@RequestParam(name = "file") MultipartFile file) {
        try {
            return ResponseEntity.ok(fileService.save(file).getId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/update/{id}")
    @ApiOperation(value = "Update media file by id")
    public HttpStatus updateFile(@RequestParam(name = "file") MultipartFile file, @PathVariable(name = "id") long id) {
        try {
            fileService.update(file, id);
            return HttpStatus.OK;
        } catch (RuntimeException e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete media file by id")
    public HttpStatus delete(@PathVariable(name = "id") long fileId) {
        try {
            fileService.delete(fileId);
            return HttpStatus.OK;
        } catch (MediaFileNotFoundException e) {
            return HttpStatus.NOT_FOUND;
        }
    }
}