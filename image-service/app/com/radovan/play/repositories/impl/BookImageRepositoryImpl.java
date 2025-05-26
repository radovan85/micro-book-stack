package com.radovan.play.repositories.impl;

import com.radovan.play.entity.BookImageEntity;
import com.radovan.play.repositories.BookImageRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Singleton
public class BookImageRepositoryImpl implements BookImageRepository {

    private final SessionFactory sessionFactory;

    @Inject
    public BookImageRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Generic withSession method for managing transactions
    private <T> T withSession(Function<Session, T> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                T result = function.apply(session);
                tx.commit();
                return result;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public BookImageEntity save(BookImageEntity imageEntity) {
        return withSession(session -> {
            if (imageEntity.getId() == null) {
                session.persist(imageEntity);
            } else {
                session.merge(imageEntity);
            }
            session.flush();
            return imageEntity;
        });
    }

    @Override
    public Optional<BookImageEntity> findByBookId(Integer bookId) {
        return withSession(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<BookImageEntity> cq = cb.createQuery(BookImageEntity.class);
            Root<BookImageEntity> root = cq.from(BookImageEntity.class);
            cq.where(cb.equal(root.get("bookId"), bookId));
            cq.select(root);
            List<BookImageEntity> results = session.createQuery(cq).getResultList();
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        });
    }

    @Override
    public List<BookImageEntity> findAll() {
        return withSession(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<BookImageEntity> query = cb.createQuery(BookImageEntity.class);
            Root<BookImageEntity> root = query.from(BookImageEntity.class);
            query.select(root);
            return session.createQuery(query).getResultList();
        });
    }

    @Override
    public void deleteById(Integer imageId) {
        withSession(session -> {
            // Option 1: Using Hibernate's createMutationQuery (recommended for Hibernate 6+)
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaDelete<BookImageEntity> deleteCriteria = cb.createCriteriaDelete(BookImageEntity.class);
            Root<BookImageEntity> root = deleteCriteria.from(BookImageEntity.class);
            deleteCriteria.where(cb.equal(root.get("id"), imageId));
            session.createMutationQuery(deleteCriteria).executeUpdate();
            return null;
        });
    }

    @Override
    public Optional<BookImageEntity> findById(Integer imageId) {
        return withSession(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<BookImageEntity> query = cb.createQuery(BookImageEntity.class);
            Root<BookImageEntity> root = query.from(BookImageEntity.class);
            query.where(cb.equal(root.get("id"), imageId));
            List<BookImageEntity> results = session.createQuery(query).getResultList();
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        });
    }
}