package com.jmnoland.expensetrackerapi.controllers;

import com.jmnoland.expensetrackerapi.interfaces.services.PaymentTypeServiceInterface;
import com.jmnoland.expensetrackerapi.models.dtos.PaymentTypeDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paymenttype")
public class PaymentTypeController {

    private final PaymentTypeServiceInterface paymentTypeService;

    @Autowired
    public PaymentTypeController(PaymentTypeServiceInterface paymentTypeService) {
        this.paymentTypeService = paymentTypeService;
    }

    @GetMapping()
    public List<PaymentTypeDto> getPaymentTypes(String userId) {
        return this.paymentTypeService.getPaymentTypes(userId);
    }

    @PostMapping()
    public ServiceResponse<PaymentTypeDto> createPaymentType(@RequestBody PaymentTypeDto paymentType) {
        return this.paymentTypeService.insert(paymentType);
    }

    @PatchMapping()
    public ServiceResponse<PaymentTypeDto> updatePaymentType(@RequestBody PaymentTypeDto paymentType) {
        return this.paymentTypeService.update(paymentType);
    }

    @DeleteMapping()
    public void deletePaymentType(@RequestBody PaymentTypeDto paymentType) {
        this.paymentTypeService.delete(paymentType);
    }
}
