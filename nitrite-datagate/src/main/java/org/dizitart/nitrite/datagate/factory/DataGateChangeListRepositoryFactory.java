/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dizitart.nitrite.datagate.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;
import org.dizitart.nitrite.datagate.repository.DataGateChangeListRepository;
import org.dizitart.nitrite.datagate.repository.DataGateChangeListRepositoryDefaultImpl;

/**
 *
 * @author tareq
 */
public class DataGateChangeListRepositoryFactory {

  private static DataGateChangeListRepositoryFactory instance = getInstance();
  private Class<? extends DataGateChangeListRepository> repositoryClazz = DataGateChangeListRepositoryDefaultImpl.class;
  private static final Logger LOG = Logger.getLogger(DataGateChangeListRepositoryFactory.class.getName());

  private DataGateChangeListRepositoryFactory() {

  }

  public static final DataGateChangeListRepositoryFactory getInstance() {
    if (instance == null) {
      instance = new DataGateChangeListRepositoryFactory();
    }
    return instance;
  }

  /**
   * Sets the type of the repository class to use
   *
   * @param clazz the new repository class
   */
  public void setRepository(Class<? extends DataGateChangeListRepository> clazz) {
    repositoryClazz = clazz;
  }

  public DataGateChangeListRepository get(String username) {
    try {
      DataGateChangeListRepository repo = repositoryClazz.getDeclaredConstructor().newInstance();
      repo.setUser(username);
      return repo;
    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
      LOG.severe(ex.getMessage());
      LOG.info("Repository class not found, defaulting to default repository");
      DataGateChangeListRepository repo = new DataGateChangeListRepositoryDefaultImpl();
      repo.setUser(username);
      return repo;
    }
  }

}
