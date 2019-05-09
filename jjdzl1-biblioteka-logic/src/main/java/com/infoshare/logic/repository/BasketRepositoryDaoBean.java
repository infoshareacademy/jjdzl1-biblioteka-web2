package com.infoshare.logic.repository;

import com.infoshare.logic.domain.*;

import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class BasketRepositoryDaoBean implements BasketRepositoryDao {

    public List<Basket> basket = basket();

    @Override
    public List<Basket> basket() {
        List<Basket> basket = new ArrayList<>();
        return basket;
    }

    @Override
    public List<Basket> setBasketToAttribute(HttpServletRequest request, List<Basket> basket) {
        request.getSession().setAttribute("basket", basket);
        return basket;
    }

    @Override
    public void addToBasketList(User user, Book book, OperationType operationType, LocalDate startDate, LocalDate endDate, HttpServletRequest request) {

        int checkIfBookIsInBasket = 0;

        for (Basket basket1 : basket) {
            if (basket1.getBook().getId() == book.getId()) {
                checkIfBookIsInBasket++;
            }
        }

        if (checkIfBookIsInBasket == 0) {
            basket.add(new Basket(book, user, operationType, startDate, endDate));
            setBasketToAttribute(request, basket);
        }
    }

    @Override
    public List basketList() {
        return basket;
    }

    @Override
    public void removeItemFromBasket(int itemNumber, HttpServletRequest request) {
        setBasketToAttribute(request, basket).remove(itemNumber);
    }

    @Override
    public void clearBasketList(List<Basket> basketToClear) {
        if (basketToClear != null)
            basketToClear.clear();
    }

}