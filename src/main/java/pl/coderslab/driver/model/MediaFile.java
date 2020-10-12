package pl.coderslab.driver.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class MediaFile extends Auditable {

    private String name;

    private String contentType;

    @Lob
    private byte[] mediaFile;
}

