package pl.coderslab.driver.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.driver.model.Advice;
import pl.coderslab.driver.service.AdviceService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdviceController {

    private final AdviceService adviceService;

    public AdviceController(AdviceService adviceService) {
        this.adviceService = adviceService;
    }

    @GetMapping("/advice")
    @ApiOperation(value = "Get all advices",
            response = List.class)
    public List<Advice> getAll() {
        return adviceService.getAll();
    }
}
