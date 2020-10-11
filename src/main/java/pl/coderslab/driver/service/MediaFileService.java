package pl.coderslab.driver.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.driver.converter.MediaFileDtoConverter;
import pl.coderslab.driver.dto.MediaFileDto;
import pl.coderslab.driver.exceptions.MediaFileNotFoundException;
import pl.coderslab.driver.model.MediaFile;
import pl.coderslab.driver.repository.MediaFileRepository;

@Service
public class MediaFileService {

    private final MediaFileRepository mediaFileRepository;

    public MediaFileService(MediaFileRepository mediaFileRepository) {
        this.mediaFileRepository = mediaFileRepository;
    }

    public MediaFileDto getById(long id) throws MediaFileNotFoundException {
        MediaFile fileFromRepo = mediaFileRepository.findById(id).orElseThrow(() -> new MediaFileNotFoundException(id));

        return MediaFileDtoConverter.convertToMediaFileDTO(fileFromRepo);
    }

    public MediaFile save(MultipartFile file) throws RuntimeException {

        return mediaFileRepository.save(MediaFileDtoConverter.convertMultipartFileToMediaFile(file));
    }

    public void update(MultipartFile file, long id) throws RuntimeException {
        MediaFile fileToUpdate = mediaFileRepository.findById(id).orElseThrow(() -> new MediaFileNotFoundException(id));
        MediaFile fileCandidate = MediaFileDtoConverter.convertMultipartFileToMediaFile(file);
        fileToUpdate.setName(fileCandidate.getName());
        fileToUpdate.setContentType(fileCandidate.getContentType());
        fileToUpdate.setMediaFile(fileCandidate.getMediaFile());

        mediaFileRepository.save(fileToUpdate);
    }

    public void delete(long id) {

        mediaFileRepository.deleteById(id);
    }
}
