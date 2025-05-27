package com.radovan.play.repositories.impl;

import com.radovan.play.entity.BookGenreEntity;
import com.radovan.play.repositories.BookGenreRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Singleton
public class BookGenreRepositoryImpl implements BookGenreRepository {

    private SessionFactory sessionFactory;

    @Inject
    private void initialize(SessionFactory sessionFactory) {
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
    public Optional<BookGenreEntity> findById(Integer genreId) {
        return withSession(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<BookGenreEntity> query = cb.createQuery(BookGenreEntity.class);
            Root<BookGenreEntity> root = query.from(BookGenreEntity.class);

            query.select(root).where(cb.equal(root.get("genreId"), genreId));
            return session.createQuery(query).uniqueResultOptional();
        });
    }

    @Override
    public BookGenreEntity save(BookGenreEntity genreEntity) {
        return withSession(session -> {
            if (genreEntity.getGenreId() == null) {
                session.persist(genreEntity);
            } else {
                session.merge(genreEntity);
            }
            return genreEntity;
        });
    }

    @Override
    public List<BookGenreEntity> findAll() {
        return withSession(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<BookGenreEntity> query = cb.createQuery(BookGenreEntity.class);
            Root<BookGenreEntity> root = query.from(BookGenreEntity.class);
            query.select(root);
            return session.createQuery(query).getResultList();
        });
    }

    @Override
    public void deleteById(Integer genreId) {
        withSession(session -> {
            BookGenreEntity bookGenreEntity = session.get(BookGenreEntity.class, genreId);
            if (bookGenreEntity != null) {
                session.remove(bookGenreEntity);
            }
            return null; // Vrati null jer metoda nema povratnu vrednost
        });
    }

    @Override
    public Optional<BookGenreEntity> findByName(String name) {
        return withSession(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<BookGenreEntity> query = cb.createQuery(BookGenreEntity.class);
            Root<BookGenreEntity> root = query.from(BookGenreEntity.class);

            query.select(root).where(cb.equal(root.get("name"), name));
            return session.createQuery(query).uniqueResultOptional();
        });
    }
}
