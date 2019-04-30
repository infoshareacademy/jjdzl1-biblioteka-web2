package com.infoshare.logic.repository;

import com.infoshare.logic.domain.Basket;
import com.infoshare.logic.domain.Book;
import com.infoshare.logic.domain.OperationType;
import com.infoshare.logic.domain.User;

import javax.ejb.Local;
import java.time.LocalDate;
import java.util.List;

@Local
public interface BasketRepositoryDao {

    void addToBasketList(User user, Book book, OperationType operationType, LocalDate startDate, LocalDate endDate);

    List basketList();

    List<Basket> basket();

    List createBasketList();

    void removeItemFromBasket(int itemNumber);
}
