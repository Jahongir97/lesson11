package uz.pdp.lesson11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson11.entity.Measurement;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.service.MeasurementService;

import java.util.List;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {

    @Autowired
    MeasurementService measurementService;

    @PostMapping
    public Result addMeasurementController(@RequestBody Measurement measurement) {
        return measurementService.addMeasurementService(measurement);
    }

    @GetMapping
    public List<Measurement> getAllMeasurements() {
        return measurementService.getMeasurements();
    }

    @GetMapping("/{id}")
    public Measurement getMeasurementById(@PathVariable Integer id) {
        return measurementService.getMeasurementById(id);
    }

    @PutMapping("/{id}")
    public Result editMeasurement(@PathVariable Integer id, @RequestBody Measurement measurement) {
        return measurementService.editMeasurement(id, measurement);
    }

    @DeleteMapping("/{id}")
    public Result deleteMeasurement(@PathVariable Integer id) {
        return measurementService.deleteMeasurement(id);
    }
}
