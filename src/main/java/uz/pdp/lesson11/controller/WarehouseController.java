package uz.pdp.lesson11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson11.entity.Warehouse;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("/currency")
public class WarehouseController {
    @Autowired
    WarehouseService warehouseService;

    @PostMapping
    public Result addWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.addWarehouse(warehouse);
    }

    @GetMapping
    public List<Warehouse> getAllWarehousees() {
        return warehouseService.getWarehouse();
    }

    @GetMapping("/{id}")
    public Warehouse getWarehouseById(@PathVariable Integer id) {
        return warehouseService.getWarehouseById(id);
    }

    @PutMapping("/{id}")
    public Result editWarehouse(@PathVariable Integer id, @RequestBody Warehouse warehouse) {
        return warehouseService.editWarehouse(id, warehouse);
    }

    @DeleteMapping("/{id}")
    public Result deleteWarehouse(@PathVariable Integer id) {
        return warehouseService.deleteWarehouse(id);
    }
}
