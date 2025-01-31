package BLL;

import entity.Admin;
import jakarta.persistence.EntityManager;

public class AdminBll {

    public static void criar(Admin admin){
        EntityManager em = DbConnection.getEntityManager();
        em.getTransaction().begin();
        em.persist(admin);
        em.getTransaction().commit();
    }

    public static Admin findAdminByUsername(String username){
        EntityManager em = DbConnection.getEntityManager();
        System.out.println("Searching for admin with username: " + username);
        try {
            Admin admin = em.createQuery("SELECT a FROM Admin a WHERE a.username = :username", Admin.class)
                    .setParameter("username", username)
                    .getResultList()
                    .stream()
                    .findFirst()
                    .orElse(null);
            System.out.println("Found admin: " + admin);
            return admin;
        } finally {
            em.close();
        }
    }


    public static void createDefaultAdminIfNotExists() {
        Admin admin = findAdminByUsername("admin");
        if (admin == null) {
            Admin defaultAdmin = new Admin();
            defaultAdmin.setUsername("admin");
            defaultAdmin.setSenha("admin123");
            criar(defaultAdmin);
        }
    }

}

