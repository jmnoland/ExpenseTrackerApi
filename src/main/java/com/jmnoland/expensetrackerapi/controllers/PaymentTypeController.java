package com.jmnoland.expensetrackerapi.controllers;

import com.jmnoland.expensetrackerapi.helpers.RequestHelper;
import com.jmnoland.expensetrackerapi.interfaces.services.PaymentTypeServiceInterface;
import com.jmnoland.expensetrackerapi.models.dtos.PaymentTypeDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.requests.CreatePaymentTypeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/paymenttype")
public class PaymentTypeController {

    private final HttpServletRequest request;
    private final PaymentTypeServiceInterface paymentTypeService;

    @Autowired
    public PaymentTypeController(HttpServletRequest request, PaymentTypeServiceInterface paymentTypeService) {
        this.request = request;
        this.paymentTypeService = paymentTypeService;
    }

    @GetMapping()
    public ServiceResponse<List<PaymentTypeDto>> getPaymentTypes() {
        String clientId = RequestHelper.getClientIdFromHeader(this.request);
        return this.paymentTypeService.getPaymentTypes(clientId);
    }

    @PostMapping()
    public ServiceResponse<PaymentTypeDto> createPaymentType(@RequestBody CreatePaymentTypeRequest paymentType) {
        paymentType.clientId = RequestHelper.getClientIdFromHeader(this.request);
        return this.paymentTypeService.insert(paymentType, paymentType.archivePaymentType);
    }

    @PostMapping("archive")
    public ServiceResponse<String> archivePaymentType(String paymentTypeId) {
        String clientId = RequestHelper.getClientIdFromHeader(this.request);
        return this.paymentTypeService.archivePaymentType(paymentTypeId, clientId);
    }

    @PatchMapping()
    public ServiceResponse<PaymentTypeDto> updatePaymentType(@RequestBody PaymentTypeDto paymentType) {
        paymentType.clientId = RequestHelper.getClientIdFromHeader(this.request);
        return this.paymentTypeService.update(paymentType);
    }

    @DeleteMapping()
    public void deletePaymentType(@RequestBody PaymentTypeDto paymentType) {
        paymentType.clientId = RequestHelper.getClientIdFromHeader(this.request);
        this.paymentTypeService.delete(paymentType);
    }
}
