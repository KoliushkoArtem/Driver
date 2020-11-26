package pl.driver.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.driver.configuration.YamlUrlConfiguration;
import pl.driver.dto.AdviceTestDto;
import pl.driver.exceptions.AdviseTestNotFoundException;
import pl.driver.service.AdviceTestService;

import java.util.List;

@Controller
@RequestMapping("/api/tests")
@Transactional
public class AdviceTestController {

    private final YamlUrlConfiguration yamlUrlConfiguration;

    private final AdviceTestService testService;

    public AdviceTestController(YamlUrlConfiguration yamlUrlConfiguration, AdviceTestService testService) {
        this.yamlUrlConfiguration = yamlUrlConfiguration;
        this.testService = testService;
    }

    @GetMapping
    @ApiOperation(value = "Get all tests")
    public ResponseEntity<List<AdviceTestDto>> getAllTests() {
        List<AdviceTestDto> allTests = testService.getAll();
        allTests.forEach(this::addMultiMediaDownloadUrl);

        return ResponseEntity.ok(allTests);
    }

    @GetMapping(value = "/get/{id}")
    @ApiOperation(value = "Get test by Id")
    public ResponseEntity<AdviceTestDto> getById(@PathVariable(name = "id") long testId) {
        try {
            return ResponseEntity.ok(addMultiMediaDownloadUrl(testService.findByID(testId)));
        } catch (AdviseTestNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    @ApiOperation(value = "Create test")
    public ResponseEntity<AdviceTestDto> add(@RequestBody AdviceTestDto testDto) {
        return ResponseEntity.ok(addMultiMediaDownloadUrl(testService.save(testDto)));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/update")
    @ApiOperation(value = "Update test")
    public ResponseEntity<AdviceTestDto> updateAdviceTest(@RequestBody AdviceTestDto testDto) {
        try {
            return ResponseEntity.ok(addMultiMediaDownloadUrl(testService.update(testDto)));
        } catch (AdviseTestNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete test by id")
    public void delete(@PathVariable(name = "id") long testId) {
        testService.delete(testId);
    }

    private AdviceTestDto addMultiMediaDownloadUrl(AdviceTestDto testDto) {
        testDto.getQuestions()
                .forEach(q -> q.getAnswers().forEach(a -> a.setMediaFileDownloadLink(yamlUrlConfiguration.getMediaFileDownloadUrl(a.getMediaFileId()))));

        return testDto;
    }
}
