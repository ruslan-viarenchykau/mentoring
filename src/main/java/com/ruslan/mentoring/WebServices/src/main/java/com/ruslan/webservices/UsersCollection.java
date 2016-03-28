package com.ruslan.webservices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("users")
public class UsersCollection {

    @POST
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response addUser(@FormParam("id") String id,
                        @FormParam("firstName") String firstName,
                        @FormParam("lastName") String lastName,
                        @FormParam("login") String login,
                        @FormParam("email") String email) {

        User user = UserStorage.addUser(id, new User(id, firstName, lastName, login, email));
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserIds() throws JsonProcessingException {
        return Response.ok(new ObjectMapper().writeValueAsString(UserStorage.getUserIds())).build();
    }

    @GET
    @Path("{userId}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public User getUser(@PathParam("userId") String id) {
        return UserStorage.getUser(id);
    }

    @PUT
    @Path("{userId}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public User putUser(@PathParam("userId") String id,
                        @FormParam("firstName") String firstName,
                        @FormParam("lastName") String lastName,
                        @FormParam("login") String login,
                        @FormParam("email") String email) {

        return UserStorage.putUser(id, new User(id, firstName, lastName, login, email));
    }

    @DELETE
    @Path("{userId}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response deleteUser(@PathParam("userId") String userId) {
        User user = UserStorage.deleteUser(userId);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @DELETE
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public void clear() {
        UserStorage.clear();
    }

}
