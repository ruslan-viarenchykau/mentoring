package com.ruslan.webservices;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import static org.junit.Assert.*;

public class UsersCollectionTest {
    private Client client;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080").path("/web-controllers/users");
    }

    @After
    public void clear() throws Exception {
        target.request(MediaType.APPLICATION_JSON_TYPE).delete();
        client.close();
    }

    @Test
    public void addUserTest() throws Exception {
        User user = addUser();

        assertNotNull(user);
        assertEquals("1", user.getId());
        assertEquals("1", user.getFirstName());
        assertEquals("1", user.getLastName());
        assertEquals("1", user.getLogin());
        assertEquals("1", user.getEmail());
    }

    @Test
    public void getUserIdsTest() throws Exception {
        addUser();
        String response = target.request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
        assertEquals("[\"1\"]", response);
    }

    @Test
    public void getUserTest() throws Exception {
        addUser();
        User response = getUser("1");

        assertEquals("1", response.getId());
        assertEquals("1", response.getFirstName());
        assertEquals("1", response.getLastName());
        assertEquals("1", response.getLogin());
        assertEquals("1", response.getEmail());
    }

    @Test
    public void putUserTest() throws Exception {
        WebTarget target = client.target("http://localhost:8080").path("/web-controllers/users/1");
        Form form = new Form();

        form.param("id", "1");
        form.param("firstName", "1");
        form.param("lastName", "1");
        form.param("login", "1");
        form.param("email", "1");

        User user = target.request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), User.class);

        assertNotNull(user);
        assertEquals("1", user.getId());
        assertEquals("1", user.getFirstName());
        assertEquals("1", user.getLastName());
        assertEquals("1", user.getLogin());
        assertEquals("1", user.getEmail());
    }

    @Test
    public void deleteUserTest() throws Exception {
        addUser();

        WebTarget target = client.target("http://localhost:8080").path("/web-controllers/users/1");
        target.request().delete();

        User user = getUser("1");
        assertNull(user);
    }

    @Test
    public void clearTest() throws Exception {
        addUser();
        target.request().delete();

        User user = getUser("1");
        assertNull(user);
    }

    private User addUser() {
        Form form = new Form();

        form.param("id", "1");
        form.param("firstName", "1");
        form.param("lastName", "1");
        form.param("login", "1");
        form.param("email", "1");

        return target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), User.class);
    }

    private User getUser(String id) {
        WebTarget target = client.target("http://localhost:8080").path("/web-controllers/users/" + id);
        return target.request(MediaType.APPLICATION_JSON_TYPE).get(User.class);
    }
}