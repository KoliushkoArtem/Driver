package pl.coderslab.driver.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.driver.dto.AdviceTestDto;
import pl.coderslab.driver.service.AdviceTestService;
import pl.coderslab.driver.utils.UrlCreator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/test")
@Transactional
public class AdviceTestController {

    private final AdviceTestService testService;

    public AdviceTestController(AdviceTestService testService) {
        this.testService = testService;
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get all tests")
    public ResponseEntity<List<AdviceTestDto>> getAllTests(HttpServletRequest request) {
        List<AdviceTestDto> allTests = testService.getAll();
        allTests.forEach(t -> addMultiMediaDownloadUrl(request, t));
        return ResponseEntity.ok(allTests);
    }

    @GetMapping(value = "/get")
    @ApiOperation(value = "Get test by Id")
    public ResponseEntity<AdviceTestDto> getById(@RequestParam(name = "testId") long testId, HttpServletRequest request) {
        AdviceTestDto testToReturn = testService.findByID(testId);
        addMultiMediaDownloadUrl(request, testToReturn);
        return ResponseEntity.ok(testToReturn);
    }

    @PostMapping("/add")
    public ResponseEntity<AdviceTestDto> add(@RequestBody AdviceTestDto testDto) {
        return ResponseEntity.ok(testService.save(testDto));
    }

    @PutMapping("/update")
    public ResponseEntity<AdviceTestDto> updateAdviceTest(@RequestBody AdviceTestDto testDto) {
        AdviceTestDto testToReturn = testService.update(testDto);
        return ResponseEntity.ok(testToReturn);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam(name = "testId") long testId) {
        testService.delete(testId);
    }

    private void addMultiMediaDownloadUrl(HttpServletRequest request, AdviceTestDto testDto) {
        testDto.getQuestions().forEach(q -> {
            q.getAnswers().forEach(a -> a.setMediaFileDownloadLink(UrlCreator.mediaFileDownloadUrl(request, a.getMediaFileId())));
        });
    }
}
