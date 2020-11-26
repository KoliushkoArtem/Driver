package pl.driver.configuration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class YamlUrlConfigurationTest {

    private final YamlUrlConfiguration urlConfiguration = new YamlUrlConfiguration();

    @Test
    void getMediaFileDownloadUrl() {
        String testMediaFileUrl = "test media file download url";
        urlConfiguration.setMediaFileDownloadUrl(testMediaFileUrl);
        long mediaFileId = 123L;

        String mediaFileDownloadUrlToTest = urlConfiguration.getMediaFileDownloadUrl(mediaFileId);

        assertEquals((testMediaFileUrl + mediaFileId), mediaFileDownloadUrlToTest);
    }

    @Test
    void getTestUrl() {
        String testUrl = "test url to get advice test";
        urlConfiguration.setTestUrl(testUrl);
        long testId = 123L;

        String testUrlToTest = urlConfiguration.getTestUrl(testId);

        assertEquals((testUrl + testId), testUrlToTest);
    }
}