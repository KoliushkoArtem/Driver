package pl.coderslab.driver.service;

import org.springframework.stereotype.Service;
import pl.coderslab.driver.repository.MediaFileRepository;

@Service
public class MediaFileService {

    private final MediaFileRepository mediaFileRepository;

    public MediaFileService(MediaFileRepository mediaFileRepository) {
        this.mediaFileRepository = mediaFileRepository;
    }
}
