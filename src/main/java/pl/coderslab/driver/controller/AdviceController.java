package pl.coderslab.driver.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.driver.dto.AdviceDto;
import pl.coderslab.driver.service.AdviceService;
import pl.coderslab.driver.utils.UrlCreator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/advices")
@Transactional
public class AdviceController {

    private final AdviceService adviceService;


    public AdviceController(AdviceService adviceService) {
        this.adviceService = adviceService;
    }

    @GetMapping
    @ApiOperation(value = "Get all advices")
    public ResponseEntity<List<AdviceDto>> getAllAdvices(HttpServletRequest request) {
        List<AdviceDto> allAdvices = adviceService.getAll();
        allAdvices.forEach(a -> addFileDownloadAndTestUrlsToAdvice(request, a));
        return ResponseEntity.ok(allAdvices);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "Get advice by id")
    public ResponseEntity<AdviceDto> getAdviceById(@PathVariable(name = "id") long adviceId, HttpServletRequest request) {
        AdviceDto adviceToReturn = adviceService.findById(adviceId);
        addFileDownloadAndTestUrlsToAdvice(request, adviceToReturn);
        return ResponseEntity.ok(adviceToReturn);
    }

    @GetMapping("/topOne")
    @ApiOperation(value = "Get advice with best rating for last 7 days")
    public ResponseEntity<AdviceDto> getAdviceByRatingForLast7Days(HttpServletRequest request) {
        AdviceDto adviceToReturn = adviceService.getAdviceByRatingForLast7Days();
        addFileDownloadAndTestUrlsToAdvice(request, adviceToReturn);
        return ResponseEntity.ok(adviceToReturn);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/add")
    public ResponseEntity<AdviceDto> addAdvice(@RequestBody AdviceDto adviceDTO) {
        return ResponseEntity.ok(adviceService.save(adviceDTO));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/update")
    public ResponseEntity<AdviceDto> updateAdvice(@RequestBody AdviceDto adviceDto) {
        return ResponseEntity.ok(adviceService.update(adviceDto));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    public void deleteAdvice(@PathVariable(name = "id") long adviceId) {
        adviceService.delete(adviceId);
    }

    private void addFileDownloadAndTestUrlsToAdvice(HttpServletRequest request, AdviceDto adviceDto) {
        adviceDto.setMediaFileDownloadLink(UrlCreator.mediaFileDownloadUrl(request, adviceDto.getMediaFileId()));
        adviceDto.setTestLink(UrlCreator.testDetailsUrl(request, adviceDto.getTestId()));
    }
}
