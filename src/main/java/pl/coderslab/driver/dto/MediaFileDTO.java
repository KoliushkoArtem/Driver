package pl.coderslab.driver.dto;

import lombok.Data;

@Data
public class MediaFileDTO {

    private String name;

    private String contentType;

    private byte[] mediaFile;
}
