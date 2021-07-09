package uz.pdp.lesson11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson11.entity.Measurement;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.repository.MeasurementRepository;

import java.util.List;
import java.util.Optional;
@Service
public class MeasurementService {
    @Autowired
    MeasurementRepository measurementRepository;

    //Create

    public Result addMeasurementService(Measurement measurement) {
        boolean existsByName = measurementRepository.existsByName(measurement.getName());
        if (existsByName) {
            return new Result("Bunday o'lchov birligi mavjud", false);
        }
        measurementRepository.save(measurement);
        return new Result("Muvaffaqiyatli qo'shildi", true);

    }

    //Read

    public List<Measurement> getMeasurements() {
        return measurementRepository.findAll();
    }

    public Measurement getMeasurementById(Integer id) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        return optionalMeasurement.orElse(null);
    }

    //Update

    public Result editMeasurement(Integer id, Measurement measurement) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        if (optionalMeasurement.isPresent()) {
            Measurement editingMeasurement = optionalMeasurement.get();
            editingMeasurement.setName(measurement.getName());
            editingMeasurement.setActive(measurement.isActive());
            measurementRepository.save(editingMeasurement);
            return new Result("Muvaffaqiyatli tahrirlandi", true);
        }
        return new Result("O'lchov birligi topilmadi", false);

    }

    //Delete
    public Result deleteMeasurement(Integer id) {
        measurementRepository.deleteById(id);
        boolean deleted = measurementRepository.existsById(id);
        if (deleted) {
            return new Result("O'lchov birligi o'chirildi", true);
        } else {
            return new Result("O'lchov birligi topilmadi", false);
        }
    }
}
