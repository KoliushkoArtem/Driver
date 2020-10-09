package pl.coderslab.driver.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.driver.dto.AdviceDTO;
import pl.coderslab.driver.service.AdviceService;

import java.util.List;

@RestController
public class AdviceController {

    private final AdviceService adviceService;


    public AdviceController(AdviceService adviceService) {
        this.adviceService = adviceService;
    }

    @GetMapping("/advice/all")
    @ApiOperation(value = "Get all advices",
            response = List.class)
    public List<AdviceDTO> getAllAdvices() {
        return adviceService.getAll();
    }

    @GetMapping("/advice/get")
    @ApiOperation(value = "Get advice by id")
    public AdviceDTO getAdviceById(@RequestParam(name = "adviceId") long adviceId) {
        return adviceService.findById(adviceId);
    }

    @GetMapping("/advice/topOne")
    @ApiOperation(value = "Get advice with best rating for last 7 days")
    public AdviceDTO getAdviceByRatingForLast7Days() {
        return adviceService.getAdviceByRatingForLast7Days();
    }

    @PostMapping("/advice/add")
    public void addAdvice(@RequestParam AdviceDTO adviceDTO) {
        adviceService.save(adviceDTO);
    }

    @PutMapping("/advice/update")
    public void updateAdvice(@RequestParam(name = "advice") AdviceDTO adviceDto) {
        adviceService.update(adviceDto);
    }

    @DeleteMapping("/advice/update")
    public void deleteAdvice(@RequestParam(name = "adviceId") long adviceId){
        adviceService.delete(adviceId);
    }
}
