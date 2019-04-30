package com.infoshare.servlets;

import com.infoshare.logic.domain.Basket;
import com.infoshare.logic.repository.BasketRepositoryDao;
import com.infoshare.logic.repository.SelectUserData;

import javax.ejb.EJB;
import java.util.List;

public class SelectUserDataImpl implements SelectUserData {

    @EJB
    private BasketRepositoryDao basketRepositoryDao;

    public List<Basket> basket() {
        return basketRepositoryDao.basket();
    }
}
