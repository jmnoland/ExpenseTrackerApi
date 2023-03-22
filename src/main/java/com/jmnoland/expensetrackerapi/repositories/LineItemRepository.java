package com.jmnoland.expensetrackerapi.repositories;

import com.jmnoland.expensetrackerapi.database.mongodb.LineItemDAO;
import com.jmnoland.expensetrackerapi.interfaces.repositories.LineItemRepositoryInterface;
import com.jmnoland.expensetrackerapi.models.entities.LineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LineItemRepository implements LineItemRepositoryInterface {

    private final LineItemDAO lineItemDAO;
    @Autowired
    public LineItemRepository(LineItemDAO lineItemDAO) {
        this.lineItemDAO = lineItemDAO;
    }

    public Optional<LineItem> getExpenseLineItems(String expenseId) {
        return this.lineItemDAO.findLineItemByExpenseId(expenseId);
    }

    public void update(LineItem lineItem) { this.lineItemDAO.save(lineItem); }

    public void bulkUpdate(List<LineItem> lineItemList) {
        this.lineItemDAO.saveAll(lineItemList);
    }

    public void insert(LineItem lineItem) { this.lineItemDAO.insert(lineItem); }

    public void bulkInsert(List<LineItem> lineItemList) {
        this.lineItemDAO.insert(lineItemList);
    }

    public void delete(LineItem lineItem) { this.lineItemDAO.delete(lineItem); }
}
