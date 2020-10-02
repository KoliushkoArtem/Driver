package pl.coderslab.driver.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class MediaFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String fileExtension;

    @Lob
    private byte[] file;
}

