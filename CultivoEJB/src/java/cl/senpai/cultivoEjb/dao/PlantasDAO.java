/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.senpai.cultivoEjb.dao;

import cl.senpai.cultivoEjb.dto.Planta;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Senpai
 */
@Stateless
public class PlantasDAO implements PlantasDAOLocal {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CultivoEJBPU");

    @Override
    public List<Planta> findAll() {
    
        EntityManager em = emf.createEntityManager();
        try {
            return em.createNamedQuery("Planta.findAll", Planta.class).getResultList();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }finally{
            em.close();
        }
        
    }

    @Override
    public void add(Planta p) {
    
        EntityManager em = emf.createEntityManager();
        try {
            em.persist(p);
            em.flush();
        } catch (Exception e) {
        
        }finally{
            em.close();
        }
        
    }

    @Override
    public void remove(Planta p) {
    
        EntityManager em = emf.createEntityManager();
        try {
            em.remove(em.find(Planta.class, p.getId()));
            em.flush();
        } catch (Exception e) {
        
        }finally{
            em.close();
        }
        
    }

    @Override
    public void removeAll() {
    
        EntityManager em = emf.createEntityManager();
        try {
            //Con jpa se usa createQuerry o createNamedQuerry - Recomendado por independencia al motor
            em.createQuery("DELETE FROM Planta").executeUpdate();
            em.flush();
            
            //Con sql se usa createNativeQuerry
            //em.createNativeQuery("DELETE FROM plantas");
        } catch (Exception e) {
        
        }finally{
            
        }
        
    }
    
    
    
}
