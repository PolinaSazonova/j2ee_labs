package ru.nsu.fit;
import ru.nsu.fit.tables.AdminsEntity;
import ru.nsu.fit.tables.MessagesEntity;
import ru.nsu.fit.tables.TopicsEntity;
import ru.nsu.fit.tables.UsersEntity;

import javax.imageio.ImageIO;
import javax.persistence.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * Created by Polina on 10.05.2014.
 */
public class PictureServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userParam = request.getParameter("user");

        if (userParam == null) {
            PrintWriter out = response.getWriter();
            out.println("User parameter \'user\' in uri for user id");
            return;
        }

        Long userId = null;
        try {
            userId = Long.parseLong(userParam);
        } catch (NumberFormatException e) {
            return;
        }


        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("1");
        EntityManager em = emf.createEntityManager();

        insert(em);

        try {
            Query userQuery = em
                    .createQuery("SELECT user from UsersEntity as user where user.id = :userId");
            userQuery.setParameter("userId", userId);
            UsersEntity user = (UsersEntity) userQuery.getSingleResult();

            if (user != null) {

                String email = user.getEmail();

                BufferedImage emailImage = new BufferedImage(500, 50,
                        BufferedImage.TYPE_INT_ARGB);
                Graphics g = emailImage.getGraphics();
                Font font = new Font("Serif", Font.PLAIN, 14);
                g.setFont(font);
                g.setColor(Color.black);
                g.drawString(email, 20, 30);

                response.setContentType("image/png");
                OutputStream os = response.getOutputStream();
                ImageIO.write(emailImage, "png", os);
                os.close();
            } else {

                PrintWriter out = response.getWriter();
                out.println("User with id " + userId
                        + " not found");

            }
        } finally {
            em.close();
        }
    }

    public void insert(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

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

        tx.commit();

        System.out.println("Inserting finished.");
    }

}
