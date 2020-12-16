package server.db;

import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import server.Film;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class FilmManager {
    private static FilmManager instance;

    public static FilmManager getInstance() {
        if (instance == null) {
            instance = new FilmManager();
        }
        return instance;
    }

    // Возвращаем список фильмов из БД
    public List<Film> getFilmList() {
        CriteriaBuilder builder = DBConfiguration.getInstance().getSession().getCriteriaBuilder();
        CriteriaQuery<Film> cq = builder.createQuery(Film.class);
        Root<Film> rootEntry = cq.from(Film.class);
        CriteriaQuery<Film> all = cq.select(rootEntry);

        TypedQuery<Film> allQuery = DBConfiguration.getInstance().getSession().createQuery(all);
        return allQuery.getResultList();
    }

    // Удаляем значение по id
    public void delete(int id) {
        try {
           /* Session session = DBConfiguration.getInstance().getSession();
            String stringQuery = "delete from Film where id = :id";
            Query query = session.createQuery(stringQuery);
            query.setParameter("id", id);
            query.executeUpdate();
            session.close();*/

            Session session = DBConfiguration.getInstance().getSession();
            session.beginTransaction();
            Film film = session.load(Film.class, id);
            session.delete(film);
            session.getTransaction().commit();
            session.close();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    // Добавляем значение
    public void add(Film film) {
        try {
            Session session = DBConfiguration.getInstance().getSession();
            session.beginTransaction();
            session.save(film);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    // Изменяем значение по id
    public void editing(Film film) {
        try {
            Session session = DBConfiguration.getInstance().getSession();
            session.beginTransaction();
            session.update(film);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

}
