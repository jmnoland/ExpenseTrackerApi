package com.jmnoland.expensetrackerapi.helpers;

import com.jmnoland.expensetrackerapi.models.requests.CreateUpdateLineItemRequest;

import java.util.List;

public class ExpenseAmountHelper {

    public static float calculateExpenseAmountFromLineItems(List<CreateUpdateLineItemRequest> lineItems) {
        float amount = 0f;
        for(CreateUpdateLineItemRequest lineItem : lineItems) {
            amount += lineItem.amount * lineItem.quantity;
        }
        return amount;
    }
}
