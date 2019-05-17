package com.infoshare.logic.repository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;

@Stateless
public class StatsRepositoryDaoBean implements StatsRepositoryDao {

    public HashMap<String, String> statsMap = new HashMap<>();

    @PersistenceContext(name = "librarydb")
    private EntityManager entityManager;

    @Override
    public HashMap<String, String> generateStats() {

        statsMap.clear();
        DateTimeFormatter shortTimeFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        String timeOfStatsGenerating = LocalTime.now().format(shortTimeFormat);

        statsMap.put("time", timeOfStatsGenerating);
        statsMap.put("allBooksCount", countBooks("all"));
        statsMap.put("borowedBooksCount", countBooks("borrow"));
        statsMap.put("reservedBooksCount", countBooks("reservation"));
        statsMap.put("availableBooksCount", countBooks("available"));

        statsMap.put("allUsers", countUsers("all"));
        statsMap.put("users", countUsers("user"));
        statsMap.put("admins", countUsers("admin"));
        statsMap.put("disabled", countUsers("disabled"));

        statsMap.put("activeReservation", countOperations("activeReservation"));
        statsMap.put("expiredReservation", countOperations("expiredReservation"));



        return statsMap;
    }

    public String countBooks(String status) {
        String stringQuery = null;
        if (status.equals("all")) {
            stringQuery = "select count(b) from Book b";
        } else if (status.equals("borrow")) {
            stringQuery = "select count(b) from Book b where b.status='Wypożyczona'";
        } else if (status.equals("reservation")) {
            stringQuery = "select count(b) from Book b where b.status='Zarezerwowana'";
        } else if (status.equals("available")) {
            stringQuery = "select count(b) from Book b where b.status='Dostępna'";
        }

        Query query = entityManager.createQuery(stringQuery);
        String countBooks = String.valueOf(query.getSingleResult());
        return countBooks;
    }

    public String countUsers(String status) {
        String stringQuery = null;
        if (status.equals("all")) {
            stringQuery = "select count(u) from User u";
        } else if (status.equals("user")) {
            stringQuery = "select count(u) from User u where u.admin='USER'";
        } else if (status.equals("admin")) {
            stringQuery = "select count(u) from User u where u.admin='ADMIN'";
        } else if (status.equals("disabled")) {
            stringQuery = "select count(u) from User u where u.status='Nieaktywny'";
        }

        Query query = entityManager.createQuery(stringQuery);
        String countUser = String.valueOf(query.getSingleResult());
        return countUser;
    }

    public String countOperations(String status) {

        String stringQuery = null;
        if (status.equals("all")) {
            stringQuery = "select count(o) from Operation o";
        } else if (status.equals("activeReservation")) {
            stringQuery = "select count(o) from Operation o where o.operationType='RESERVATION' and o.endDate >=current_date";
        } else if (status.equals("expiredReservation")) {
            stringQuery = "select count(o) from Operation o where o.operationType='RESERVATION' and o.endDate < current_date";
        }

        Query query = entityManager.createQuery(stringQuery);
        String countOperation = String.valueOf(query.getSingleResult());
        return countOperation;
    }
}
