package pl.coderslab.driver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.driver.converter.MediaFileDtoConverter;
import pl.coderslab.driver.dto.MediaFileDto;
import pl.coderslab.driver.exceptions.MediaFileNotFoundException;
import pl.coderslab.driver.model.MediaFile;
import pl.coderslab.driver.repository.MediaFileRepository;

@Service
@Slf4j
public class MediaFileService {

    private final MediaFileRepository mediaFileRepository;

    public MediaFileService(MediaFileRepository mediaFileRepository) {
        this.mediaFileRepository = mediaFileRepository;
    }

    public MediaFileDto getById(long id) throws MediaFileNotFoundException {
        MediaFile fileFromRepo = mediaFileRepository.findById(id).orElseThrow(() -> new MediaFileNotFoundException(id));

        log.info("IN mediaFileService getById - mediaFile with id: {} vas successfully loaded", id);

        return MediaFileDtoConverter.convertToMediaFileDTO(fileFromRepo);
    }

    public MediaFile save(MultipartFile file) throws RuntimeException {
        MediaFile savedFile = mediaFileRepository.save(MediaFileDtoConverter.convertMultipartFileToMediaFile(file));

        log.info("IN mediaFileService save - mediaFile with name{} vas saved and received Id: {}", savedFile.getName(), savedFile.getId());

        return savedFile;
    }

    public void update(MultipartFile file, long id) throws RuntimeException {
        MediaFile fileToUpdate = mediaFileRepository.findById(id).orElseThrow(() -> new MediaFileNotFoundException(id));
        MediaFile fileCandidate = MediaFileDtoConverter.convertMultipartFileToMediaFile(file);
        fileToUpdate.setName(fileCandidate.getName());
        fileToUpdate.setContentType(fileCandidate.getContentType());
        fileToUpdate.setMediaFile(fileCandidate.getMediaFile());

        mediaFileRepository.save(fileToUpdate);

        log.info("IN mediaFileService update - mediaFile with Id: {} vas updated", id);
    }

    public void delete(long id) {
        mediaFileRepository.deleteById(id);

        log.info("IN mediaFileService delete - mediaFile with id: {} vas deleted", id);
    }
}
