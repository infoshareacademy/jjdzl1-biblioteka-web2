package com.infoshare.rest.service;

import com.infoshare.logic.domain.Book;
import com.infoshare.logic.domain.BookStatus;
import com.infoshare.logic.domain.User;
import com.infoshare.logic.repository.BooksRepositoryDao;
import com.infoshare.logic.repository.UsersRepositoryDao;
import com.infoshare.logic.utils.Hasher;
import com.infoshare.logic.utils.PBKDF2Hasher;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Logger;

@Path("/")
public class Service {

    public static final Logger LOGGER = Logger.getLogger(Service.class.getName());

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
    public Response getUser(@QueryParam("id") int id) throws SQLException, ClassNotFoundException {

        User user = usersRepository.getUserById(id);
        if (user == null) {
            LOGGER.info("Nie odnaleziono użytkownika");
            return Response.noContent().build();
        }
        return Response.ok(user).build();
    }

    @POST
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) throws SQLException, ClassNotFoundException {

        User.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .status(user.getStatus())
                .admin(user.getAdmin())
                .email(user.getEmail())
                .build();

        usersRepository.addNewUser(user);
        User returnUserData = usersRepository.findUserByLogin(user.getLogin()).get(0);
        LOGGER.info("Dodano użytkownika o loginie: "+user.getLogin());
        return getUser(returnUserData.getId());
    }

    @PUT
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(User user) throws SQLException, ClassNotFoundException {

        if (usersRepository.getUserById(user.getId())!=null) {

            Hasher hasher = new PBKDF2Hasher();
            user.setPassword(hasher.hash(user.getPassword()));

            usersRepository.updateUserAfterEdit(user);
            LOGGER.info("Edytowano dane użytkownika o loginie: "+user.getLogin());

            return Response.ok(user).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@QueryParam("id") Integer id) throws SQLException, ClassNotFoundException {

        if (usersRepository.getUserById(id)!=null) {
            usersRepository.deleteUser(id);
            return getUsers();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
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