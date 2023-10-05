package service;

import data.model.Entry;
import data.repository.EntryRepository;
import data.repository.EntryRepositoryImpl;

public class EntryServicesImpl implements EntryServices{

    private EntryRepository entryRepository = new EntryRepositoryImpl();
    @Override
    public Entry addEntry(String ownerName, String title, String body) {
        Entry newEntry = new Entry();
        newEntry.setOwnerName(ownerName);
        newEntry.setTitle(title);
        newEntry.setBody(body);
        entryRepository.save(newEntry);
        return newEntry;
    }

    @Override
    public long count(){
        return entryRepository.count();
    }

    @Override
    public void delete(String ownerName, String title) {
        Entry foundEntry = findEntry(ownerName, title);
        entryRepository.delete(foundEntry);
    }

    @Override
    public Entry findEntry(String ownerName, String title) {
        Entry foundEntry = entryRepository.findByUsername(ownerName, title);
        boolean entryIsNotFound = foundEntry == null;
        if(entryIsNotFound) throw new IllegalArgumentException("Entry is not Found");
        return foundEntry;
    }


}