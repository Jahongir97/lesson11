package uz.pdp.lesson11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson11.entity.Supplier;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.service.SupplierService;

import java.util.List;

@RestController
@RequestMapping("/client")
public class SupplierController {

    @Autowired
    SupplierService supplierService;


    @PostMapping
    public Result addSupplier(@RequestBody Supplier client) {
        return supplierService.addSupplier(client);
    }

    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return supplierService.getSuppliers();
    }

    @GetMapping("/{id}")
    public Supplier getSupplierById(@PathVariable Integer id) {
        return supplierService.getSupplierById(id);
    }

    @PutMapping("/{id}")
    public Result editSupplier(@PathVariable Integer id, @RequestBody Supplier client) {
        return supplierService.editSupplier(id, client);
    }

    @DeleteMapping("/{id}")
    public Result deleteSupplier(@PathVariable Integer id) {
        return supplierService.deleteSupplier(id);
    }
}
