package pl.coderslab.driver.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.driver.dto.AdviceTestDto;
import pl.coderslab.driver.service.AdviceTestService;
import pl.coderslab.driver.utils.UrlCreator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/api/tests")
@Transactional
public class AdviceTestController {

    private final AdviceTestService testService;

    public AdviceTestController(AdviceTestService testService) {
        this.testService = testService;
    }

    @GetMapping
    @ApiOperation(value = "Get all tests")
    public ResponseEntity<List<AdviceTestDto>> getAllTests(HttpServletRequest request) {
        List<AdviceTestDto> allTests = testService.getAll();
        allTests.forEach(t -> addMultiMediaDownloadUrl(request, t));
        return ResponseEntity.ok(allTests);
    }

    @GetMapping(value = "/get/{id}")
    @ApiOperation(value = "Get test by Id")
    public ResponseEntity<AdviceTestDto> getById(@PathVariable(name = "id") long testId, HttpServletRequest request) {
        AdviceTestDto testToReturn = testService.findByID(testId);
        addMultiMediaDownloadUrl(request, testToReturn);
        return ResponseEntity.ok(testToReturn);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public ResponseEntity<AdviceTestDto> add(@RequestBody AdviceTestDto testDto) {
        return ResponseEntity.ok(testService.save(testDto));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/update")
    public ResponseEntity<AdviceTestDto> updateAdviceTest(@RequestBody AdviceTestDto testDto) {
        AdviceTestDto testToReturn = testService.update(testDto);
        return ResponseEntity.ok(testToReturn);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") long testId) {
        testService.delete(testId);
    }

    private void addMultiMediaDownloadUrl(HttpServletRequest request, AdviceTestDto testDto) {
        testDto.getQuestions().forEach(q -> {
            q.getAnswers().forEach(a -> a.setMediaFileDownloadLink(UrlCreator.mediaFileDownloadUrl(request, a.getMediaFileId())));
        });
    }
}
