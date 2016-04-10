package com.ruslan.mentoring.jpa.controllers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

abstract class AbstractController {
    protected static final String MESSAGE_KEY = "message";

    @PersistenceContext
    protected EntityManager entityManager;
}
