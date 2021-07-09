package uz.pdp.lesson11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson11.entity.Input;
import uz.pdp.lesson11.entity.InputProduct;
import uz.pdp.lesson11.entity.Output;
import uz.pdp.lesson11.entity.OutputProduct;
import uz.pdp.lesson11.repository.InputProductRepository;
import uz.pdp.lesson11.repository.InputRepository;
import uz.pdp.lesson11.repository.OutputProductRepository;
import uz.pdp.lesson11.repository.OutputRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DashboardService {
    @Autowired
    InputRepository inputRepository;
    @Autowired
    InputProductRepository inputProductRepository;
    @Autowired
    OutputRepository outputRepository;
    @Autowired
    OutputProductRepository outputProductRepository;

    LocalDateTime today = LocalDateTime.now();
    String midnight = today.toLocalDate().toString() + "T" + LocalTime.MIN;
    String beforeEnd = today.toLocalDate().toString() + "T" + LocalTime.MAX;
    LocalDateTime startOfTheDay = LocalDateTime.parse(midnight);
    LocalDateTime endOfTheDay = LocalDateTime.parse(beforeEnd);

    // Kunlik kirim bo’lgan mahsulotlar (qiymati, umumiy summasi)

    public List<InputProduct> dailyInputProductAmountAndSum() {
        List<Input> inputs = inputRepository.getAllInputIdBetweenDates(startOfTheDay, endOfTheDay);
        List<InputProduct> dailyInputProducts = new ArrayList<>();
        for (Input input : inputs) {
            Integer id = input.getId();
            List<InputProduct> inputProducts = inputProductRepository.findAllByInputId(id);
            dailyInputProducts.addAll(inputProducts);
        }

        return dailyInputProducts;
    }

    // Kunlik eng ko’p chiqim qilingan mahsulotlar

    public List<OutputProduct> dailyMaxOutputProduct() {
        List<Output> outputs = outputRepository.getOutputIdBetweenDates(startOfTheDay, endOfTheDay);
        List<OutputProduct> dailyOutputProducts = new ArrayList<>();
        for (Output output : outputs) {
            Integer id = output.getId();
            List<OutputProduct> outputProducts = outputProductRepository.findAllByOutputIdOrderByAmountDesc(id);
            dailyOutputProducts.addAll(outputProducts);
        }

        return dailyOutputProducts;
    }

    //    Yaroqlilik muddati yetib qolgan mahsulotlarni olish.

    public List<InputProduct> expiringProducts() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date(timestamp.getTime());
        return inputProductRepository.findAllExpiring(date);

    }
}

