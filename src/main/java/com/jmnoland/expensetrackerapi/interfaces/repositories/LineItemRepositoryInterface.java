package com.jmnoland.expensetrackerapi.interfaces.repositories;

import com.jmnoland.expensetrackerapi.models.entities.LineItem;

import java.util.List;
import java.util.Optional;

public interface LineItemRepositoryInterface {
    Optional<LineItem> getExpenseLineItems(String expenseId);
    void update(LineItem lineItem);
    void bulkUpdate(List<LineItem> lineItemList);
    void insert(LineItem lineItem);
    void bulkInsert(List<LineItem> lineItemList);
    void delete(LineItem lineItem);
}
