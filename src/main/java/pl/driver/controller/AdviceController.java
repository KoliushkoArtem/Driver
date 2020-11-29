package pl.driver.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.driver.configuration.YamlUrlConfiguration;
import pl.driver.dto.AdviceDto;
import pl.driver.exceptions.AdviceNotFoundException;
import pl.driver.service.AdviceService;

import java.util.List;

@RestController
@RequestMapping("/api/advices")
@Transactional
public class AdviceController {

    private final AdviceService adviceService;

    private final YamlUrlConfiguration yamlUrlConfiguration;

    public AdviceController(AdviceService adviceService, YamlUrlConfiguration contentUrlConfiguration) {
        this.adviceService = adviceService;
        this.yamlUrlConfiguration = contentUrlConfiguration;
    }

    @GetMapping
    @ApiOperation(value = "Get all advices")
    public ResponseEntity<List<AdviceDto>> getAllAdvices() {
        List<AdviceDto> allAdvices = adviceService.getAll();
        allAdvices.forEach(this::addFileDownloadAndTestUrlsToAdvice);

        return ResponseEntity.ok(allAdvices);
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "Get advice by id")
    public ResponseEntity<AdviceDto> getAdviceById(@PathVariable(name = "id") long adviceId) {
        try {
            return ResponseEntity.ok(addFileDownloadAndTestUrlsToAdvice(adviceService.findById(adviceId)));
        } catch (AdviceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/topOne")
    @ApiOperation(value = "Get advice with best rating for last 7 days")
    public ResponseEntity<AdviceDto> getAdviceByRatingForLast7Days() {
        return ResponseEntity.ok(addFileDownloadAndTestUrlsToAdvice(adviceService.getAdviceByRatingForLast7Days()));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/add")
    @ApiOperation(value = "Create advice")
    public ResponseEntity<AdviceDto> addAdvice(@RequestBody AdviceDto adviceDTO) {
        return ResponseEntity.ok(addFileDownloadAndTestUrlsToAdvice(adviceService.save(adviceDTO)));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/update")
    @ApiOperation(value = "Update advice")
    public ResponseEntity<AdviceDto> updateAdvice(@RequestBody AdviceDto adviceDto) {
        try {
            return ResponseEntity.ok(addFileDownloadAndTestUrlsToAdvice(adviceService.update(adviceDto)));
        } catch (AdviceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete advice by id")
    public HttpStatus deleteAdvice(@PathVariable(name = "id") long adviceId) {
        try {
            adviceService.delete(adviceId);
            return HttpStatus.OK;
        } catch (AdviceNotFoundException e) {
            return HttpStatus.NOT_FOUND;
        }
    }

    private AdviceDto addFileDownloadAndTestUrlsToAdvice(AdviceDto adviceDto) {
        if (adviceDto.getMediaFileId() != null && adviceDto.getMediaFileId() != 0) {
            adviceDto.setMediaFileDownloadLink(yamlUrlConfiguration.getMediaFileDownloadUrl(adviceDto.getMediaFileId()));
        }

        if (adviceDto.getTestId() != null && adviceDto.getTestId() != 0) {
            adviceDto.setTestLink(yamlUrlConfiguration.getTestUrl(adviceDto.getTestId()));
        }

        return adviceDto;
    }
}