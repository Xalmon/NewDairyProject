package services;

import data.model.Diary;
import data.model.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DiaryServicesImpl;

import static org.junit.jupiter.api.Assertions.*;

public class DiaryServicesImplTest {

    private DiaryServicesImpl diaryServices;

    @BeforeEach
    public void setUp(){
        diaryServices = new DiaryServicesImpl();
    }
    @Test
    public void testThatRegisterIncreasesCount(){

        diaryServices.register("Username", "password");
        assertEquals(1, diaryServices.count());

    }

    @Test
    public void testThatAUsernameCannotBeRegisteredTwice_andCountRemainUnchanged(){

        diaryServices.register("Username", "password");

        assertThrows(IllegalArgumentException.class,
                ()->{diaryServices.register("Username", "password");});
        assertEquals(1, diaryServices.count());

    }

    @Test
    public void testThatRegisteredUserIsSaved(){
        diaryServices.register("Username", "password");
        assertEquals("Username", diaryServices.findByUsername("Username").getUsername());
    }

    @Test
    public void testThatSearchingUnsavedUsername_throwsException(){
        diaryServices.register("Username", "password");
        assertThrows(IllegalArgumentException.class,
                ()->diaryServices.findByUsername("User"));
    }
    @Test
    public void testThatAUserCanDeleteDiary(){
        diaryServices.register("Username", "password");
        diaryServices.register("User", "password");

        assertEquals(2, diaryServices.count());

        diaryServices.delete("Username", "password");
        assertEquals(1, diaryServices.count());

    }

    @Test
    public void testThatSearchingADeletedDiary_throwsException(){
        diaryServices.register("Username", "password");
        diaryServices.register("User", "password");
        assertEquals(2, diaryServices.count());

        diaryServices.delete("Username", "password");
        assertThrows(IllegalArgumentException.class, ()->{diaryServices.findByUsername("Username");});

    }

    @Test
    public void testThatDeletingADiary_WithAWrongPassword_throwsException(){
        diaryServices.register("Username", "password");
        assertThrows(IllegalArgumentException.class,
                ()->{diaryServices.delete("Username", "pass");});
    }

    @Test
    public void testThatUserCanChangePassword(){
        diaryServices.register("Username", "oldPassword");
        diaryServices.update("Username", "oldPassword", "newPassword");

        assertThrows(IllegalArgumentException.class,
                ()->{diaryServices.delete("Username", "password");});
        assertEquals(1, diaryServices.count());
    }

    @Test
    public void testThatUserCanNotChangePasswordWithIncorrectOldPassword(){
        diaryServices.register("Username", "oldPassWord");

        assertThrows(IllegalArgumentException.class,
                ()->{diaryServices.update(
                        "Username",
                        "oldPassword",
                        "newPassword");
                });
    }

    @Test
    public void testThatEntryCanBeAddedToTheDairy(){
        diaryServices.register("Username", "password");
        diaryServices.addEntry("Username", "title", "body");

        Entry entry = diaryServices.findEntry("Username", "title");
        assertEquals("body", entry.getBody());

    }

    @Test
    public void testThatAddEntryThrowsExceptionIfDiaryDoesNotExist(){
        assertThrows(IllegalArgumentException.class,
                ()-> diaryServices.addEntry("Username", "title", "body"));
    }

    @Test
    public void testThatAddEntryThrowsExceptionIfDiaryIsLocked(){
        diaryServices.register("Username", "password");
        diaryServices.lock("Username");

        assertThrows(IllegalArgumentException.class,
                ()-> diaryServices.addEntry("Username", "title", "body"));
    }

    @Test
    public void testThatLockedDiaryCanBeUnlockedToAddEntry(){
        diaryServices.register("Username", "password");
        diaryServices.lock("Username");

        assertThrows(IllegalArgumentException.class,
                ()-> diaryServices.addEntry("Username", "title", "body"));

        diaryServices.unlock("Username", "password");
        diaryServices.addEntry("Username", "title", "body");
        Entry entry = diaryServices.findEntry("Username", "title");
        assertEquals("body", entry.getBody());


    }

    @Test
    public void testThatDiaryIsStillLocked_andThrowsExceptionIfUnlockPasswordIsIncorrect(){
        diaryServices.register("Username", "password");
        diaryServices.lock("Username");

        assertThrows(IllegalArgumentException.class,
                ()-> diaryServices.unlock("Username", "Password"));
    }

}