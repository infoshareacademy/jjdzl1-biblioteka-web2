package com.infoshare.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Map;

import static com.infoshare.dao.DBCon.preparedStatement;

public class StatsQuery {

    public static Map<String, String> statsMap = new HashMap<String, String>();

    public static Map<String, String> generateStats() throws SQLException {

        statsMap.clear();
        Integer booksCount = null;
        DateTimeFormatter shortTimeFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        String timeOfStatsGenerating = LocalTime.now().format(shortTimeFormat);

        statsMap.put("time", timeOfStatsGenerating);

        ResultSet rs = null;
        try {
            rs = StatsQuery.countBooks();

            while (rs.next()) {
                String countBook = String.valueOf(rs.getInt(1));
                statsMap.put("booksCount", countBook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            rs.close();
        }

        try {
            rs = StatsQuery.countActiveUsers();

            while (rs.next()) {
                String activeUsers = String.valueOf(rs.getInt(1));
                statsMap.put("activeUsers", activeUsers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            rs.close();
        }

        try {
            rs = StatsQuery.countDisabledUsers();

            while (rs.next()) {
                String disabledUsers = String.valueOf(rs.getInt(1));
                statsMap.put("disabledUsers", disabledUsers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            rs.close();
        }





        return statsMap;
    }

    public static ResultSet countBooks() throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM books WHERE 1";
        return preparedStatement(query).executeQuery();
    }

    public static ResultSet countActiveUsers() throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM users WHERE status='Aktywny'";
        return preparedStatement(query).executeQuery();
    }

    public static ResultSet countDisabledUsers() throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM users WHERE status='Nieaktywny'";
        return preparedStatement(query).executeQuery();
    }

}
