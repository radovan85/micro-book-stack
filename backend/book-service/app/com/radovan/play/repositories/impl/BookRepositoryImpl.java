package com.radovan.play.repositories.impl;

import com.radovan.play.entity.BookEntity;
import com.radovan.play.repositories.BookRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Singleton
public class BookRepositoryImpl implements BookRepository {

    private final SessionFactory sessionFactory;

    @Inject
    public BookRepositoryImpl(SessionFactory sessionFactory) {
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
    public Optional<BookEntity> findById(Integer bookId) {
        return withSession(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<BookEntity> query = cb.createQuery(BookEntity.class);
            Root<BookEntity> root = query.from(BookEntity.class);

            query.where(cb.equal(root.get("bookId"), bookId));
            return session.createQuery(query).uniqueResultOptional();
        });
    }

    @Override
    public void deleteById(Integer bookId) {
        withSession(session -> {
            BookEntity bookEntity = session.get(BookEntity.class, bookId);
            if (bookEntity != null) {
                session.remove(bookEntity);
            }
            return null; // Vrati null jer metoda nema povratnu vrednost
        });
    }

    @Override
    public BookEntity save(BookEntity bookEntity) {
        return withSession(session -> {
            if (bookEntity.getBookId() == null) {
                session.persist(bookEntity);
            } else {
                session.merge(bookEntity);
            }
            return bookEntity;
        });
    }

    @Override
    public List<BookEntity> findAll() {
        return withSession(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<BookEntity> query = cb.createQuery(BookEntity.class);
            Root<BookEntity> root = query.from(BookEntity.class);
            query.select(root);
            return session.createQuery(query).getResultList();
        });
    }

    @Override
    public List<BookEntity> findAllByGenre(Integer genreId) {
        return withSession(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<BookEntity> query = cb.createQuery(BookEntity.class);
            Root<BookEntity> root = query.from(BookEntity.class);

            query.where(cb.equal(root.get("genreId"), genreId));
            return session.createQuery(query).getResultList();
        });
    }

    @Override
    public void deleteAllByGenreId(Integer genreId) {
        withSession(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaDelete<BookEntity> delete = cb.createCriteriaDelete(BookEntity.class);
            Root<BookEntity> root = delete.from(BookEntity.class);
            delete.where(cb.equal(root.get("genreId"), genreId));
            session.createMutationQuery(delete).executeUpdate();
            return null;
        });
    }

}
