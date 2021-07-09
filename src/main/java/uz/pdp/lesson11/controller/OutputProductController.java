package uz.pdp.lesson11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson11.entity.OutputProduct;
import uz.pdp.lesson11.payload.OutputProductDto;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.service.OutputProductService;

import java.util.List;

@RestController
@RequestMapping("/outputProduct")
public class OutputProductController {
    @Autowired
    OutputProductService outputProductService;

    @PostMapping
    public Result addInputProduct(@RequestBody OutputProductDto outputProductDto) {
        return outputProductService.addOutputProduct(outputProductDto);
    }


    @GetMapping
    public List<OutputProduct> getAllInputProducts() {
        return outputProductService.getOutputProducts();
    }

    @GetMapping("/{id}")
    public OutputProduct getInputProductById(@PathVariable Integer id) {
        return outputProductService.getOutputProductById(id);
    }

    @PutMapping("/{id}")
    public Result editInputProduct(@PathVariable Integer id, @RequestBody OutputProductDto outputProductDto) {
        return outputProductService.editOutputProduct(id, outputProductDto);
    }

    @DeleteMapping("/{id}")
    public Result deleteInputProduct(@PathVariable Integer id) {
        return outputProductService.deleteOutputProduct(id);
    }
}
