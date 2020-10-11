package pl.coderslab.driver.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.driver.dto.AdviceTestDto;
import pl.coderslab.driver.service.AdviceTestService;

import java.util.List;

@Controller
@Transactional
public class AdviceTestController {

    private final AdviceTestService testService;

    public AdviceTestController(AdviceTestService testService) {
        this.testService = testService;
    }

    @GetMapping("/test/all")
    @ApiOperation(value = "Get all tests")
    public ResponseEntity<List<AdviceTestDto>> getAllTests() {
        List<AdviceTestDto> allTests = testService.getAll();
        return ResponseEntity.ok(allTests);
    }

    @GetMapping(value = "/test/get")
    @ApiOperation(value = "Get test by Id")
    public ResponseEntity<AdviceTestDto> getById(@RequestParam(name = "testId") long testId) {
        AdviceTestDto testToReturn = testService.findByID(testId);
        return ResponseEntity.ok(testToReturn);
    }

    @PostMapping("/test/add")
    public ResponseEntity<AdviceTestDto> add(@RequestBody AdviceTestDto testDto) {
        return ResponseEntity.ok(testService.save(testDto));
    }

    @PutMapping("/test/update")
    public ResponseEntity<AdviceTestDto> updateAdviceTest(@RequestBody AdviceTestDto testDto) {
        AdviceTestDto testToReturn = testService.update(testDto);
        return ResponseEntity.ok(testToReturn);
    }

    @DeleteMapping("/test/delete")
    public void delete(@RequestParam(name = "testId") long testId) {
        testService.delete(testId);
    }

}
