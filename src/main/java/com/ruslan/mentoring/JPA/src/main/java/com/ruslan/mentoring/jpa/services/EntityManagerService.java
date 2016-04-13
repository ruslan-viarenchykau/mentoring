package com.ruslan.mentoring.jpa.services;

import com.ruslan.mentoring.jpa.models.PersistentObject;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class EntityManagerService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void create(Object entity) {
        entityManager.persist(entity);
    }

    public <T extends PersistentObject> T find(Class<T> entityClass, Long id) {
        return entityManager.find(entityClass, id);
    }

    @Transactional
    public <T extends PersistentObject> boolean update(T entity) {
        if (entityManager.find(entity.getClass(), entity.getId()) == null) {
            return false;
        }
        entityManager.merge(entity);
        return true;
    }

    @Transactional
    public <T extends PersistentObject> boolean delete(Class<T> entityClass, Long id) {
        T entity = find(entityClass, id);
        if (entity == null) {
            return false;
        }
        entityManager.remove(entity);
        return true;
    }

    public <T extends PersistentObject> List<Long> getIds(Class<T> entityClass) {
        List<T> entries = getAll(entityClass);
        List<Long> ids = new ArrayList<>();
        for (T entry: entries) {
            ids.add(entry.getId());
        }
        return ids;
    }

    private <T extends PersistentObject> List<T> getAll(Class<T> entityClass) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> rootEntry = criteriaQuery.from(entityClass);
        CriteriaQuery<T> all = criteriaQuery.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }
}
