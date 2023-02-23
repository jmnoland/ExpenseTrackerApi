package com.jmnoland.expensetrackerapi.models.requests;

import java.util.List;

public class BulkCreateUpdateExpenseRequest {
    public String clientId;
    public List<CreateUpdateExpenseRequest> expenseRequestList;
    public BulkCreateUpdateExpenseRequest() {};
}
