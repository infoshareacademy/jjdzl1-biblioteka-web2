package com.infoshare.logic.repository;

import com.infoshare.logic.domain.Message;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MessageRepositoryDao {

    List<Message> getMessage(Integer id);

    void addMessage(Message message);
}
