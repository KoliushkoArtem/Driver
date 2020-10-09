package pl.coderslab.driver.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.driver.dto.MediaFileDTO;
import pl.coderslab.driver.exceptions.MediaFileNotFoundException;
import pl.coderslab.driver.model.MediaFile;
import pl.coderslab.driver.repository.MediaFileRepository;
import pl.coderslab.driver.utils.ConverterDTO;

import java.io.IOException;

@Service
@Transactional
public class MediaFileService {

    private final MediaFileRepository mediaFileRepository;

    public MediaFileService(MediaFileRepository mediaFileRepository) {
        this.mediaFileRepository = mediaFileRepository;
    }

    public MediaFileDTO getById(long id) throws RuntimeException {
        MediaFile fileFromRepo = mediaFileRepository.findById(id).orElseThrow(() -> new MediaFileNotFoundException(id));
        return ConverterDTO.convertToMediaFileDTO(fileFromRepo);
    }

    public long save(MultipartFile file) {
        MediaFile fileCandidat = new MediaFile();
        fileCandidat.setName(file.getOriginalFilename());
        fileCandidat.setContentType(file.getContentType());
        try {
            fileCandidat.setMediaFile(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException();
        }
        MediaFile savedFile = mediaFileRepository.save(fileCandidat);

        return savedFile.getId();
    }

    public void update(MultipartFile file, long id) throws RuntimeException {
        String filename = file.getName();
        String contentType = file.getContentType();
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException();
        }

        mediaFileRepository.update(filename, contentType, bytes, id);
    }

    public void delete(long id) {
        mediaFileRepository.deleteById(id);
    }
}
