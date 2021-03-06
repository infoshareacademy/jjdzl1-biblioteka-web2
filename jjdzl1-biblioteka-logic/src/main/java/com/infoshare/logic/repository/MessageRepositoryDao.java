package com.infoshare.logic.repository;

import com.infoshare.logic.domain.Message;

import javax.ejb.Local;
import java.io.FileNotFoundException;
import java.util.List;

@Local
public interface MessageRepositoryDao {

    List<Message> getMessage(Integer id, Integer page) throws FileNotFoundException;

    void addMessage(Message message);

    Long countMessage(Integer id) throws FileNotFoundException;
}
