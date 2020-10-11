package pl.coderslab.driver.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.driver.dto.AdviceDto;
import pl.coderslab.driver.service.AdviceService;

import java.util.List;

@RestController
@RequestMapping("/advice")
@Transactional
public class AdviceController {

    private final AdviceService adviceService;


    public AdviceController(AdviceService adviceService) {
        this.adviceService = adviceService;
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get all advices")
    public ResponseEntity<List<AdviceDto>> getAllAdvices() {
        List<AdviceDto> allAdvices = adviceService.getAll();
        return ResponseEntity.ok(allAdvices);
    }

    @GetMapping("/get")
    @ApiOperation(value = "Get advice by id")
    public ResponseEntity<AdviceDto> getAdviceById(@RequestParam(name = "adviceId") long adviceId) {
        return ResponseEntity.ok(adviceService.findById(adviceId));
    }

    @GetMapping("/topOne")
    @ApiOperation(value = "Get advice with best rating for last 7 days")
    public ResponseEntity<AdviceDto> getAdviceByRatingForLast7Days() {
        return ResponseEntity.ok(adviceService.getAdviceByRatingForLast7Days());
    }

    @PostMapping(value = "/add")
    public ResponseEntity<AdviceDto> addAdvice(@RequestBody AdviceDto adviceDTO) {
        return ResponseEntity.ok(adviceService.save(adviceDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<AdviceDto> updateAdvice(@RequestBody AdviceDto adviceDto) {
        return ResponseEntity.ok(adviceService.update(adviceDto));
    }

    @DeleteMapping("/delete")
    public void deleteAdvice(@RequestParam(name = "adviceId") long adviceId) {
        adviceService.delete(adviceId);
    }
}
