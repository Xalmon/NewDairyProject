package service;

import data.model.Diary;
import data.model.Entry;
import data.repository.DiaryRepository;
import data.repository.DiaryRepositoryImpl;


public class DiaryServicesImpl implements DiaryServices {

    private DiaryRepository diaryRepository = new DiaryRepositoryImpl();
    private EntryServices entryServices = new EntryServicesImpl();


    @Override
    public void register(String username, String password) {
        validateUser(username);
        Diary diary = new Diary();
        diary.setUsername(username);
        diary.setPassword(password);
        diaryRepository.save(diary);
    }

    private void validateUser(String username) {
        for(Diary diary: diaryRepository.findAll())
            if (diary.getUsername().equals(username))
                throw new IllegalArgumentException("Username Already Exist");
    }

    @Override
    public long count() {
        return diaryRepository.count();
    }

    @Override
    public Diary findByUsername(String username) {
        for(Diary diary: diaryRepository.findAll())
            if(diary.getUsername().equals(username))
                return diary;
        throw new IllegalArgumentException("Diary not found");
    }

    @Override
    public void delete(String username, String password) {
        Diary diary = findByUsername(username);
        if(diary.getPassword().equals(password)) diaryRepository.delete(diary);
        else throw new IllegalArgumentException("Invalid Credentials");
    }

    @Override
    public void update(String username, String oldPassword, String newPassword) {
        Diary diary = findByUsername(username);
        if(diary.getPassword().equals(oldPassword)) diary.setPassword(newPassword);
        else throw new IllegalArgumentException("Invalid Credentials");
    }

    @Override
    public Entry addEntry(String username, String title, String body) {
        validate(username);
        Entry entry = entryServices.addEntry(username, title, body);
        return entry;
    }

    private void validate(String username) {
        Diary foundDiary = diaryRepository.findByUsername(username);
        if(foundDiary == null)
            throw new IllegalArgumentException("Diary not Found");
        if(foundDiary.isLocked())
            throw new IllegalArgumentException("Diary is Locked");
    }

    @Override
    public Entry findEntry(String username, String title) {
        Entry entry = entryServices.findEntry(username, title);
        return entry;
    }

    @Override
    public void lock(String username) {
        Diary foundDiary = findByUsername(username);
        foundDiary.setLocked(true);
        diaryRepository.save(foundDiary);
    }

    @Override
    public void unlock(String username, String password){
        Diary diary = diaryRepository.findByUsername(username);
        if(diary.getPassword().equals(password)) diary.setLocked(false);
        else throw new IllegalArgumentException("Incorrect Password");
        diaryRepository.save(diary);
    }

}