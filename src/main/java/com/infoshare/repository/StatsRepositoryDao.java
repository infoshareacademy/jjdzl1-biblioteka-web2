package com.infoshare.repository;

import javax.ejb.Local;
import java.util.HashMap;

@Local
public interface StatsRepositoryDao {
    HashMap<String,String> generateStats();


}
