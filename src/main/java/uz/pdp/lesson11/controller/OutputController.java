package uz.pdp.lesson11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson11.entity.Output;
import uz.pdp.lesson11.payload.OutputDto;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.service.OutputService;

import java.util.List;

@RestController
@RequestMapping("/output")
public class OutputController {
    @Autowired
    OutputService outputService;

    @PostMapping
    public Result addOutput(@RequestBody OutputDto outputDto) {
        return outputService.addOutput(outputDto);
    }


    @GetMapping
    public List<Output> getAllOutputs() {
        return outputService.getOutput();
    }

    @GetMapping("/{id}")
    public Output getOutputById(@PathVariable Integer id) {
        return outputService.getOutputById(id);
    }

    @PutMapping("/{id}")
    public Result editOutput(@PathVariable Integer id, @RequestBody OutputDto outputDto) {
        return outputService.editOutput(id, outputDto);
    }

    @DeleteMapping("/{id}")
    public Result deleteOutput(@PathVariable Integer id) {
        return outputService.deleteOutput(id);
    }
}
