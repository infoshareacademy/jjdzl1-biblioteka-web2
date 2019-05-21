package com.infoshare.logic.repository;

import com.infoshare.logic.domain.Message;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class MessageRepositoryDaoBean implements MessageRepositoryDao {

    @EJB
    private UsersRepositoryDao usersRepository;

    @EJB
    private OperationsRepositoryDao operationsRepository;

    @EJB
    private BooksRepositoryDao booksRepositor;


    @PersistenceContext(name = "librarydb")
    private EntityManager entityManager;

    @Override
    public List<Message> getMessage(Integer id) {

        String query = "select m from Message m";

        if (id != null) {
            query += " where m.operation.user.id=" + id;
        }

        TypedQuery<Message> messageTypedQuery=entityManager.createQuery(query,Message.class);
        List<Message> messageList=messageTypedQuery.getResultList();

        return messageList;
    }

    @Override
    public void addMessage(Message message) {
        entityManager.persist(message);
    }

}
