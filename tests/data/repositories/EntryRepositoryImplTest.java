package data.repositories;

import data.model.Entry;
import data.repository.EntryRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntryRepositoryImplTest {

    private EntryRepositoryImpl entryRepository;
    @BeforeEach
    void setUp() {
        entryRepository = new EntryRepositoryImpl();
    }

    @Test public void saveEntry_countIncreases() {
        Entry entry = new Entry();
        entryRepository.save(entry);

        assertEquals(1, entryRepository.count());
    }
    @Test public void saveOneDiary_FindEntryTest(){
        Entry entry = new Entry();
        Entry newEntry = entryRepository.save(entry);

        assertEquals(newEntry, entryRepository.findById(1));

    }

    @Test public void updateEntryTest(){
        Entry entry = new Entry();

        entry.setTitle("The sun is shining brightly today.");
        entry.setBody("It's a beautiful day outside.");
        entryRepository.save(entry);
        assertEquals("The sun is shining brightly today.",
                entryRepository.findById(1).getTitle());
        assertEquals("It's a beautiful day outside.",
                entryRepository.findById(1).getBody());

        Entry updateEntry = new Entry();
        updateEntry.setId(1);
        updateEntry.setTitle("A sudden rain shower caught us by surprise.");
        updateEntry.setBody("We got drenched but had fun.");
        entryRepository.save(updateEntry);
        assertEquals("A sudden rain shower caught us by surprise.",
                entryRepository.findById(1).getTitle());
        assertEquals("We got drenched but had fun.",
                entryRepository.findById(1).getBody());
    }
    @Test public void findingAEntryThatDoesNotExist_ReturnNull(){
        assertNull(entryRepository.findById(1));
    }
    @Test public void moreEntries_increasesCount(){
        Entry entry = new Entry();

        entry.setTitle("A delicious meal was prepared for dinner.");
        entry.setBody("The aroma filled the entire house.");
        entryRepository.save(entry);

        Entry updateEntry = new Entry();
        updateEntry.setTitle("We celebrated a friend's birthday last night.");
        updateEntry.setBody("It was a fun gathering with lots of laughter.");
        entryRepository.save(updateEntry);

        assertEquals(2, entryRepository.count());

    }

    @Test public void deleteEntry_reducesCount() {
        Entry entry = new Entry();

        entry.setTitle("The mountains were covered in snow.");
        entry.setBody("We went skiing and had a blast.");
        entryRepository.save(entry);

        Entry updateEntry = new Entry();
        updateEntry.setTitle("A surprise gift arrived in the mail today.");
        updateEntry.setBody("It was unexpected and made my day.");
        entryRepository.save(updateEntry);


        assertEquals(2, entryRepository.count());

        entryRepository.delete(entry);
        assertEquals(1, entryRepository.count());

    }
    @Test public void clearEntryTest(){
        Entry entry = new Entry(); entryRepository.save(entry);
        Entry newEntry = new Entry(); entryRepository.save(newEntry);
        Entry extraEntry = new Entry(); entryRepository.save(extraEntry);

        assertEquals(3, entryRepository.count());

        entryRepository.clear();
        assertEquals(0, entryRepository.count());

    }

    @Test public void AllEntriesCanBeReceivedTest() {
        Entry entry = new Entry();
        entryRepository.save(entry);
        Entry newEntry = new Entry();
        entryRepository.save(newEntry);
        Entry extraEntry = new Entry();
        entryRepository.save(extraEntry);

        assertEquals(3, entryRepository.count());

        Iterable<Entry> allEntries = List.of(new Entry[]{entry, newEntry, extraEntry});
        assertEquals(allEntries, entryRepository.findAll());

    }

}