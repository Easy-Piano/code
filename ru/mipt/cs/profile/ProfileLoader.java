package ru.mipt.cs.profile;
//Dima

interface ProfileLoader {
    void LoadUserFromDB(String db) throws Exception;
    java.sql.Connection ConnectToDB(String name) throws Exception;
}
