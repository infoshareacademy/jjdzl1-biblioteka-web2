package com.infoshare.repository;

import com.infoshare.logic.domain.Basket;
import com.infoshare.logic.domain.Book;
import com.infoshare.logic.domain.OperationType;
import com.infoshare.logic.domain.User;
import com.infoshare.servlets.SelectUserServlet;

import javax.ejb.Stateless;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class BasketRepositoryDaoBean implements BasketRepositoryDao {

    public List<Basket> basket = SelectUserServlet.basket;

    @Override
    public void addToBasketList(User user, Book book, OperationType operationType, LocalDate startDate, LocalDate endDate) {
        basket.add(new Basket(book, user, operationType, startDate, endDate));
    }

    @Override
    public List basketList() {
        return basket;
    }

    @Override
    public List createBasketList() {
        List<Basket> basket = new ArrayList<>();
        return basket;
    }

    @Override
    public void removeItemFromBasket(int itemNumber) {
        basket.remove(itemNumber);
    }
}
