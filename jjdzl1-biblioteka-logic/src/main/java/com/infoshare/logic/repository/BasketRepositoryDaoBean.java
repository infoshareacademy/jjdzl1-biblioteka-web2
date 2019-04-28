package com.infoshare.logic.repository;

import com.infoshare.logic.domain.Basket;
import com.infoshare.logic.domain.Book;
import com.infoshare.logic.domain.OperationType;
import com.infoshare.logic.domain.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class BasketRepositoryDaoBean implements BasketRepositoryDao {

    @Inject
    private SelectUserData selectUserData;

    @Override
    public void addToBasketList(User user, Book book, OperationType operationType, LocalDate startDate, LocalDate endDate) {
        selectUserData.basket().add(new Basket(book, user, operationType, startDate, endDate));
    }

    @Override
    public List basketList() {
        return selectUserData.basket();
    }

    @Override
    public List createBasketList() {
        List<Basket> basket = new ArrayList<>();
        return basket;
    }

    @Override
    public void removeItemFromBasket(int itemNumber) {
        selectUserData.basket().remove(itemNumber);
    }
}
