package uz.pdp.lesson11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson11.entity.Input;
import uz.pdp.lesson11.payload.InputDto;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.service.InputService;

import java.util.List;

@RestController
@RequestMapping("/input")
public class InputController {
    @Autowired
    InputService inputService;

    @PostMapping
    public Result addInput(@RequestBody InputDto inputDto) {
        return inputService.addInput(inputDto);
    }


    @GetMapping
    public List<Input> getAllInputs() {
        return inputService.getInputs();
    }

    @GetMapping("/{id}")
    public Input getInputById(@PathVariable Integer id) {
        return inputService.getInputById(id);
    }

    @PutMapping("/{id}")
    public Result editInput(@PathVariable Integer id, @RequestBody InputDto inputDto) {
        return inputService.editInput(id, inputDto);
    }

    @DeleteMapping("/{id}")
    public Result deleteInput(@PathVariable Integer id) {
        return inputService.deleteInput(id);
    }
}
