package de.coerdevelopment.higherlower.repository;

import de.coerdevelopment.higherlower.api.RankingEntry;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RankingRepository {

    private static RankingRepository instance;
    
    public static RankingRepository getInstance() {
        if (instance == null) {
            instance = new RankingRepository();
        }
        return instance;
    }
    
    private final String tableName = "hl_ranking";
    private final int maxEntries = 100;
    private SQL sql;

    public RankingRepository() {
        sql = SQL.getSQL();
    }

    public void createTable() {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + tableName +
                    " (name VARCHAR(32) NOT NULL," +
                    "score INTEGER NOT NULL," +
                    "time BIGINT NOT NULL," +
                    "CONSTRAINT pk_ranking PRIMARY KEY(name))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean doesNameExists(String name) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT name FROM " + tableName +
                    " WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertScore(String name, int score) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("INSERT INTO " + tableName + " (name,score,time) VALUES (?,?,?)");
            ps.setString(1, name);
            ps.setInt(2, score);
            ps.setLong(3, System.currentTimeMillis());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getScore(String name) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT score FROM " + tableName +
                    " WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void updateScore(String name, int score) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("UPDATE " + tableName + " SET score = ?, time = ?" +
                    " WHERE name = ?");
            ps.setInt(1, score);
            ps.setLong(2, System.currentTimeMillis());
            ps.setString(3, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<RankingEntry> getAllRankings() {
        List<RankingEntry> entries = new ArrayList<>();
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT * FROM " + tableName + " ORDER BY score DESC LIMIT " + maxEntries);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                entries.add(getRankingEntryByResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }

    private RankingEntry getRankingEntryByResultSet(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        int score = rs.getInt("score");
        long time = rs.getLong("time");
        return new RankingEntry(name, score, time);
    }

}
