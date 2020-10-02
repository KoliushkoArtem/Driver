package pl.coderslab.driver.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "files")
public class AdviceFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String fileExtension;

    @Lob
    private byte[] file;
}

