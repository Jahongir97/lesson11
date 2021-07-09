package uz.pdp.lesson11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson11.entity.Input;
import uz.pdp.lesson11.entity.InputProduct;
import uz.pdp.lesson11.payload.InputProductDto;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.service.InputProductService;

import java.util.List;

@RestController
@RequestMapping("/inputProduct")
public class InputProductController {
    @Autowired
    InputProductService inputProductService;

    @PostMapping
    public Result addInputProduct(@RequestBody InputProductDto inputProductDto) {
        return inputProductService.addInputProduct(inputProductDto);
    }


    @GetMapping
    public List<InputProduct> getAllInputProducts() {
        return inputProductService.getInputProducts();
    }

    @GetMapping("/{id}")
    public InputProduct getInputProductById(@PathVariable Integer id) {
        return inputProductService.getInputProductById(id);
    }

    @PutMapping("/{id}")
    public Result editInputProduct(@PathVariable Integer id, @RequestBody InputProductDto inputProductDto) {
        return inputProductService.editInputProduct(id, inputProductDto);
    }

    @DeleteMapping("/{id}")
    public Result deleteInputProduct(@PathVariable Integer id) {
        return inputProductService.deleteInputProduct(id);
    }
}
