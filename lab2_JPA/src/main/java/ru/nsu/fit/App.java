package ru.nsu.fit;

import ru.nsu.fit.tables.AdminsEntity;
import ru.nsu.fit.tables.MessagesEntity;
import ru.nsu.fit.tables.TopicsEntity;
import ru.nsu.fit.tables.UsersEntity;

import javax.persistence.*;
import java.util.List;

public class App {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("1");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        insert(em);
        tx.commit();

        printDB(em);
        em.close();
    }

    public static void insert(EntityManager em) {
        System.out.println("Inserting started...");

        for (int userNumber = 0; userNumber < 10; userNumber++) {
            String userName = "user #" + userNumber;

            //add users
            UsersEntity user = new UsersEntity(userName, userName + "@someDN");
            em.persist(user);

            //add topics
            int topicNumber = userNumber;
            TopicsEntity topic = new TopicsEntity("Topic #" + topicNumber);
            em.persist(topic);

            //add messages
            for (int contentNumber = 0; contentNumber < 3; contentNumber++) {
                MessagesEntity message = new MessagesEntity(user, topic, "some content #" + contentNumber);
                em.persist(message);
            }
        }

        //add admins
        for (int adminNumber = 0; adminNumber < 3; adminNumber++) {
            String adminName = "admin #" + adminNumber;
            AdminsEntity admin = new AdminsEntity(adminName, adminName + "@someDN", adminNumber);
            em.persist(admin);
        }

        System.out.println("Inserting finished.");
    }

    public static void printDB(EntityManager em) {
        Query userQuery = em.createQuery("SELECT user from UsersEntity as user");
        List<?> users = userQuery.getResultList();

        System.out.println("Users:");
        for( Object db_user : users ) {
            UsersEntity user = (UsersEntity) db_user;
            System.out.println(user);
        }

        Query topicQuery = em.createQuery("SELECT topic from TopicsEntity as topic");
        List<?> topics = topicQuery.getResultList();
        System.out.println("Topics:");
        for( Object db_topics : topics ) {
            TopicsEntity topic = (TopicsEntity) db_topics;
            System.out.println(topic);
        }

        System.out.println("Messages:");
        Query messageQuery = em.createQuery("SELECT message from MessagesEntity as message " +
                "inner join message.userId inner join message.topicId");
        List<?> messages = messageQuery.getResultList();

        for( Object db_message : messages ) {
            MessagesEntity message = (MessagesEntity) db_message;
            System.out.println(message);
        }
    }
}
