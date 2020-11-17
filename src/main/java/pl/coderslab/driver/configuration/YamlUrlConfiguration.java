package pl.coderslab.driver.configuration;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import pl.coderslab.driver.utils.YamlPropertySourceFactory;

@Configuration
@ConfigurationProperties(prefix = "urls")
@PropertySource(value = "classpath:urls.yaml", factory = YamlPropertySourceFactory.class)
@Setter
public class YamlUrlConfiguration {

    private String mediaFileDownloadUrl;

    private String testUrl;

    public String getMediaFileDownloadUrl(long mediaFileId) {
        return mediaFileDownloadUrl + mediaFileId;
    }

    public String getTestUrl(long testId) {
        return testUrl + testId;
    }
}
