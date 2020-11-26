package pl.driver.dto;

import lombok.Data;

@Data
public class MediaFileDto {

    private String name;

    private String contentType;

    private byte[] mediaFile;
}