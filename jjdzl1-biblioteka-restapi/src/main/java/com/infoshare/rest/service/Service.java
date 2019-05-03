package com.infoshare.rest.service;

import com.infoshare.logic.domain.User;
import com.infoshare.logic.repository.UsersRepositoryDao;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.Collection;

@Path("/")
public class Service {

    @EJB
    private UsersRepositoryDao usersRepository;


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
}