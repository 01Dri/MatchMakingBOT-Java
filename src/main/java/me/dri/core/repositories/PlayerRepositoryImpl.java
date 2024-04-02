package me.dri.core.repositories;

import me.dri.core.entities.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class PlayerRepositoryImpl  implements  PlayerRepository{

    private final SessionFactory sessionFactory;

    public PlayerRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Player player) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(player);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public Player findPlayerByDiscordName(String discordName) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Player WHERE discordName = :discordName";
            Query<Player> query = session.createQuery(hql, Player.class);
            query.setParameter("discordName", discordName);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
