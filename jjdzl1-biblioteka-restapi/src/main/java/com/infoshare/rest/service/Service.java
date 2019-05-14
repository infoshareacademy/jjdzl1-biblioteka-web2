package com.infoshare.rest.service;

import com.infoshare.logic.domain.*;
import com.infoshare.logic.repository.BooksRepositoryDao;
import com.infoshare.logic.repository.OperationsRepositoryDao;
import com.infoshare.logic.repository.UsersRepositoryDao;
import com.infoshare.logic.utils.Hasher;
import com.infoshare.logic.utils.PBKDF2Hasher;
import com.infoshare.logic.validation.BookValidator;
import com.infoshare.logic.validation.UserValidator;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Path("/")
public class Service {

    public static final Logger LOGGER = Logger.getLogger(Service.class.getName());

    @EJB
    private UsersRepositoryDao usersRepository;

    @EJB
    private BooksRepositoryDao booksRepository;

    @EJB
    private OperationsRepositoryDao operationsRepository;

    @EJB
    private UserValidator userValidator;

    @EJB
    private BookValidator bookValidator;

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
    @Path("/user")
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

        user = User.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .status(user.getStatus())
                .admin(user.getAdmin())
                .email(user.getEmail())
                .build();

        List<String> validationResult = userValidator.validationResult;
        validationResult.clear();
        userValidator.userValidation(user);

        if (validationResult.isEmpty()) {
            usersRepository.addNewUser(user);
            User returnUserData = usersRepository.findUserByLogin(user.getLogin()).get(0);
            LOGGER.info("Dodano użytkownika o loginie: " + user.getLogin());
            return getUser(returnUserData.getId());
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(User user) throws SQLException, ClassNotFoundException {

        if (user.getId() == null) return Response.status(Response.Status.BAD_REQUEST).build();

        if (usersRepository.getUserById(user.getId()) != null) {
            Hasher hasher = new PBKDF2Hasher();
            user.setPassword(hasher.hash(user.getPassword()));

            List<String> validationResult = userValidator.validationResult;
            validationResult.clear();
            userValidator.userToEditValidation(user);

            if (validationResult.isEmpty()) {
                usersRepository.updateUserAfterEdit(user);
                LOGGER.info("Edytowano dane użytkownika o loginie: " + user.getLogin());
                return Response.ok(user).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@QueryParam("id") Integer id) throws SQLException, ClassNotFoundException {

        if (usersRepository.getUserById(id) != null) {
            usersRepository.deleteUser(id);
            LOGGER.info("Usunięto użytkownika o id= " + id);
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

    @GET
    @Path("/book")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@QueryParam("id") int id) throws SQLException, ClassNotFoundException {

        Book book = booksRepository.getBookById(id);
        if (book == null) {
            LOGGER.info("Nie odnaleziono książki");
            return Response.noContent().build();
        }
        return Response.ok(book).build();
    }

    @POST
    @Path("/book")
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

        List<String> validationResult = bookValidator.validationResult;
        validationResult.clear();
        bookValidator.bookValidation(book);

        if (validationResult.isEmpty()) {
            booksRepository.addNewBook(book);
            LOGGER.info("Dodano nową książkę o tytule " + book.getTitle());
            return Response.ok(book).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }


    @PUT
    @Path("/book")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(Book book) throws SQLException, ClassNotFoundException {

        if ((Integer) book.getId() == null) return Response.status(Response.Status.BAD_REQUEST).build();

        if (booksRepository.getBookById(book.getId()) != null) {

            List<String> validationResult = bookValidator.validationResult;
            validationResult.clear();
            bookValidator.bookValidation(book);

            if (validationResult.isEmpty()) {
                booksRepository.editBook(book);
                LOGGER.info("Edytowano dane książki o id " + book.getId());

                return Response.ok(book).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/book")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBook(@QueryParam("id") Integer id) throws SQLException, ClassNotFoundException, FileNotFoundException {

        if (booksRepository.getBookById(id) != null) {
            booksRepository.deleteBook(id);
            LOGGER.info("Usunięto książkę o id= " + id);
            return getBooks("1");
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/operations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOperations(@QueryParam("type") String type, @QueryParam("userId") String id) throws SQLException, ClassNotFoundException {

        if (type == null) type = "all";
        Collection<Operation> operations = operationsRepository.AllOperationList(type, null);
        if (operations.isEmpty()) {
            return Response.noContent().build();
        }

        String log = id;
        if (id == null) log = "wszyscy";
        LOGGER.info("Wylistowano operacje typu: " + type + " dla użytkownika :" + log);
        return Response.ok(operations).build();
    }

    @GET
    @Path("/operation")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOperation(@QueryParam("id") int id) throws SQLException, ClassNotFoundException {

        Operation operation = operationsRepository.getOperation(id);
        if (operation == null) {
            LOGGER.info("Nie odnaleziono operacji o id= " + id);
            return Response.noContent().build();
        }
        LOGGER.info("Pobrano dane operacji o id=" + id);
        return Response.ok(operation).build();
    }

    @POST
    @Path("/operation")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOperation(Operation operation) throws SQLException, ClassNotFoundException {

        Book book = booksRepository.getBookById(operation.getBookId());
        User user = usersRepository.getUserById(operation.getUser().getId());

        if (book != null && user != null) {
            if (book.getStatus().equals(BookStatus.Dostępna)) {

                operation = Operation.builder()
                        .user(operation.getUser())
                        .userName(user.getLastName() + ", " + user.getFirstName())
                        .bookId(operation.getBookId())
                        .bookTitle(book.getTitle())
                        .author(book.getAuthorLastName() + "," + book.getAuthorFirstName())
                        .operationDate(operation.getOperationDate())
                        .operationType(operation.getOperationType())
                        .startDate(operation.getStartDate())
                        .endDate(operation.getEndDate())
                        .build();

                operationsRepository.addRestOperation(operation);

                if (operation.getOperationType().equals(OperationType.BORROW)) {
                    book.setStatus(BookStatus.Wypożyczona);
                } else {
                    book.setStatus(BookStatus.Zarezerwowana);
                }
                operationsRepository.addRestOperation(operation);
                booksRepository.editBook(book);
                LOGGER.info("Dodano nową operację dla użytkownika " + user.getLogin());
            }
            return Response.ok(operation).build();
        }
        return Response.noContent().build();
    }

    @DELETE
    @Path("/operation")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOperation(@QueryParam("id") Integer id) throws SQLException, ClassNotFoundException, FileNotFoundException {

        if (operationsRepository.getOperation(id) != null) {
            operationsRepository.deleteOperation(id);
            LOGGER.info("Usunięto operację o id=" + id);
            return getOperations("all", null);
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}