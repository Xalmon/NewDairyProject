package service;

import data.model.Diary;
import data.model.Entry;

public interface DiaryServices {

    void register(String username, String password);

    long count();

    Diary findByUsername(String username);

    void delete(String username, String password);

    void update(String username, String oldPassword, String newPassword);


    Entry addEntry(String username, String title, String body);

    Entry findEntry(String username, String title);

    void lock(String username);

    void unlock(String username, String password);
}