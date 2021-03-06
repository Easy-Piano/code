package ru.mipt.cs.easypiano.profile;
//Dima
import java.sql.*;

//здесь используем h2 database
public class Profile implements ProfileLoader, StatsLoader {
    private String name;

    private void setSongs(String[] songs) {
        String[] songs1 = songs;
    }

    private void setAccuracy(int[] accuracy) {
        int[] accuracy1 = accuracy;
    }

    private void setName(String name) {
        this.name = name;
    }

    @Override
    public void LoadUserFromDB(String db) throws Exception {
        Connection connection = ConnectToDB(this.name);
        //downloading profile info
        connection.close();
    }
    //Нужно уточнить имя db

    @Override
    public Connection ConnectToDB(String name) throws Exception {
                Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/test", name, "");
    }

    @Override
    public void LoadUserStatsFromDB(String db) {

    }
}
