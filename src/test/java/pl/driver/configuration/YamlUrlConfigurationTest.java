package pl.driver.configuration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class YamlUrlConfigurationTest {

    private final YamlUrlConfiguration urlConfiguration = new YamlUrlConfiguration();

    @BeforeEach
    void setUp(TestInfo testInfo) {
        log.info(String.format("test started: %s", testInfo.getDisplayName()));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        log.info(String.format("test finished: %s", testInfo.getDisplayName()));
    }

    @Test
    @DisplayName("When given media file id asserted that it will be added to the end of media file download Url and returned as String")
    void getMediaFileDownloadUrl() {
        String testMediaFileUrl = "test media file download url";
        urlConfiguration.setMediaFileDownloadUrl(testMediaFileUrl);
        long mediaFileId = 123L;

        String mediaFileDownloadUrlToTest = urlConfiguration.getMediaFileDownloadUrl(mediaFileId);

        assertEquals((testMediaFileUrl + mediaFileId), mediaFileDownloadUrlToTest);
    }

    @Test
    @DisplayName("When given advice test id asserted that it will be added to the end of get advice test Url and returned as String")
    void getTestUrl() {
        String testUrl = "test url to get advice test";
        urlConfiguration.setTestUrl(testUrl);
        long testId = 123L;

        String testUrlToTest = urlConfiguration.getTestUrl(testId);

        assertEquals((testUrl + testId), testUrlToTest);
    }
}