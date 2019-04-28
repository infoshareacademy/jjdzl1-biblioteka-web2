package com.infoshare.servlets;

import com.infoshare.logic.domain.Basket;
import com.infoshare.logic.repository.SelectUserData;
import java.util.List;

public class SelectUserDataImpl {

    public List<Basket> basket() {
        return SelectUserServlet.basket;
    }
}
