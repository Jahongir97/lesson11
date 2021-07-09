package uz.pdp.lesson11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @GetMapping("/dailyInputProductAmountAndSum")
    public Result dailyInputProductAmountAndSum() {
        return dashboardService.dailyInputProductAmountAndSum();
    }

    @GetMapping("/dailyMaxOutputProduct")
    public Result dailyMaxOutputProduct() {
        return dashboardService.dailyMaxOutputProduct();
    }

    @GetMapping("/expiringProducts")
    public Result expiringProducts() {
        return dashboardService.expiringProducts();
    }


}
