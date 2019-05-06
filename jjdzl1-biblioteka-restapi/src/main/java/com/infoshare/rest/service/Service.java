package com.infoshare.rest.service;

import com.infoshare.logic.domain.Book;
import com.infoshare.logic.domain.BookStatus;
import com.infoshare.logic.domain.User;
import com.infoshare.logic.repository.BooksRepositoryDao;
import com.infoshare.logic.repository.UsersRepositoryDao;

import javax.ejb.EJB;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Collection;

@Path("/")
public class Service {

    @EJB
    private UsersRepositoryDao usersRepository;

    @EJB
    private BooksRepositoryDao booksRepository;

    @Context
    private UriInfo uriInfo;


    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() throws SQLException, ClassNotFoundException {

        Collection<User> users = usersRepository.listOfUsers("");
        if (users.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(users).build();
    }

    @GET
    @Path("/user/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@QueryParam("id") int id) throws NoResultException, SQLException, ClassNotFoundException {

        User user = usersRepository.getUserById(id);
        if (user == null) {
            return Response.noContent().build();
        }
        return Response.ok(user).build();
    }


    @GET
    @Path("/books/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks(@PathParam("page") String page) throws SQLException, ClassNotFoundException, FileNotFoundException {

        Collection<Book> books = booksRepository.bookList("title", Integer.parseInt(page));
        if (books.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(books).build();
    }

    @POST
    @Path("/addBook")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBook(Book book) {

        book = Book.builder()
                .title(book.getTitle())
                .authorFirstName(book.getAuthorFirstName())
                .authorLastName(book.getAuthorLastName())
                .daterelease(book.getDaterelease())
                .isbn(book.getIsbn())
                .status(BookStatus.Dostępna)
                .description(book.getDescription())
                .build();

        booksRepository.addNewBook(book);
        return Response.ok(book).build();
    }

}