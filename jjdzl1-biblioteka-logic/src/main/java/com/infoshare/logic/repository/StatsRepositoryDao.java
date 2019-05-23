package com.infoshare.logic.repository;

import javax.ejb.Local;
import java.util.HashMap;

@Local
public interface StatsRepositoryDao {
    HashMap<String, String> generateStats();

    String countUsers(String status);

    String countBooks(String status);

    String countOperations(String status);
}
