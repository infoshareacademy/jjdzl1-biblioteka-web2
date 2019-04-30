package com.infoshare.logic.repository;

import com.infoshare.logic.domain.Basket;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SelectUserData {

    public List<Basket> basket();
}
