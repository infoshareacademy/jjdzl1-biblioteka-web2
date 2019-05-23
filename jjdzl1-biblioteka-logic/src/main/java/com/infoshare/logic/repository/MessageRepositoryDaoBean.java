package com.infoshare.logic.repository;

import com.infoshare.logic.domain.Message;
import com.infoshare.logic.utils.ReadProperties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.FileNotFoundException;
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
    public List<Message> getMessage(Integer id, Integer page) throws FileNotFoundException {

        Integer recordPerPage = Integer.parseInt(ReadProperties.readPropertie("records-per-page"));
        Integer offset = null;

        if (page == null) page = 1;
        offset = recordPerPage * page - recordPerPage;

        String query = "select m from Message m";

        if (id != null) {
            query += " where m.operation.user.id=" + id;
        }

        TypedQuery<Message> messageTypedQuery = entityManager.createQuery(query, Message.class);
        List<Message> messageList = messageTypedQuery
                .setMaxResults(recordPerPage)
                .setFirstResult(offset)
                .getResultList();

        return messageList;
    }

    @Override
    public Long countMessage(Integer id) throws FileNotFoundException {

        Integer recordPerPage = Integer.parseInt(ReadProperties.readPropertie("records-per-page"));
        Integer offset = null;
        Long numbersOfMessage = null;

        String query = "select count(m) from Message m";

        if (id != null) {
            query += " where m.operation.user.id=" + id;
        }

        Query countMessage = entityManager.createQuery(query);
        numbersOfMessage =  (Long) countMessage.getSingleResult();

        return numbersOfMessage;
    }


    @Override
    public void addMessage(Message message) {
        entityManager.persist(message);
    }

}
