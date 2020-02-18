/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.senpai.cultivoEjb.dao;

import cl.senpai.cultivoEjb.dto.Planta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Senpai
 */
@Local
public interface PlantasDAOLocal {
    
   List<Planta> findAll();
   void add(Planta p);
   void remove(Planta p);
   void removeAll();
}
