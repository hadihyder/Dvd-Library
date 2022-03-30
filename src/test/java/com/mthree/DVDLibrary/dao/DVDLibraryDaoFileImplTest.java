package com.mthree.DVDLibrary.dao;

import com.mthree.DVDLibrary.dto.DVD;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DVDLibraryDaoFileImplTest {

    private DVDLibraryDao dao;

    public DVDLibraryDaoFileImplTest(){

//        dao = new DVDLibraryDaoFileImpl();

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        dao = ctx.getBean("dao", DVDLibraryDaoFileImpl.class);
    }

    @org.junit.jupiter.api.Test
    void testAddDVD() throws DVDLibraryDaoException {
        DVD dvd = new DVD("Test Title");
        dvd.setDvdReleaseDate(LocalDate.parse("11/12/13"));
        dvd.setDvdDirectorName("Test Director");
        dvd.setDvdStudio("Test Studio");
        dvd.setDvdRating("3.5");
        dvd.setDvdUserRating("Test Rating");


        dao.addDVD(dvd.getDvdTitle(), dvd);

        DVD addedDVD = dao.findDVD(dvd.getDvdTitle());

        //Assert

        assertEquals(dvd.getDvdTitle(), addedDVD.getDvdTitle(),"Check DVD titles.");
        assertEquals(dvd.getDvdReleaseDate(),addedDVD.getDvdReleaseDate(), "Check DVD release date.");
        assertEquals(dvd.getDvdDirectorName(), addedDVD.getDvdDirectorName(), "Check DVD Director name.");
        assertEquals(dvd.getDvdStudio(), addedDVD.getDvdStudio(), "Check DVD Studio.");
        assertEquals(dvd.getDvdRating(), addedDVD.getDvdRating(), "Check DVD Rating.");
        assertEquals(dvd.getDvdUserRating(), addedDVD.getDvdUserRating(), "Check DVD User Rating.");
    }

    @org.junit.jupiter.api.Test
    void deleteDVD() throws DVDLibraryDaoException {
        DVD dvd = new DVD("Test Title");
        dvd.setDvdReleaseDate(LocalDate.parse("11/12/13"));
        dvd.setDvdDirectorName("Test Director");
        dvd.setDvdStudio("Test Studio");
        dvd.setDvdRating("3.5");
        dvd.setDvdUserRating("Test Rating");
        dao.addDVD(dvd.getDvdTitle(), dvd);

        DVD deletedDVD = dao.deleteDVD("Test Title");

        assertNull(deletedDVD, "Removing Test Title should be null.");


        DVD nullDVD = dao.deleteDVD("Invalid Title");
        assertNull(nullDVD, "Removing Invalid Title should be null.");
    }

    @org.junit.jupiter.api.Test
    void updateDVD() throws DVDLibraryDaoException {
        DVD dvd = new DVD("Test Title");
        dvd.setDvdReleaseDate(LocalDate.parse("11/12/13"));
        dvd.setDvdDirectorName("Test Director");
        dvd.setDvdStudio("Test Studio");
        dvd.setDvdRating("3.5");
        dvd.setDvdUserRating("Test Rating");

        DVD updatedDVD = dao.updateDVD(dvd.getDvdTitle(), dvd);
        assertEquals(dvd.getDvdTitle(), updatedDVD.getDvdTitle(),"Check DVD titles.");
        assertEquals(dvd.getDvdReleaseDate(),updatedDVD.getDvdReleaseDate(), "Check DVD release date.");
        assertEquals(dvd.getDvdDirectorName(), updatedDVD.getDvdDirectorName(), "Check DVD Director name.");
        assertEquals(dvd.getDvdStudio(), updatedDVD.getDvdStudio(), "Check DVD Studio.");
        assertEquals(dvd.getDvdRating(), updatedDVD.getDvdRating(), "Check DVD Rating.");
        assertEquals(dvd.getDvdUserRating(), updatedDVD.getDvdUserRating(), "Check DVD User Rating.");

        assertNotEquals("Test", dvd.getDvdTitle(), "Test Title should not match with Test");

    }

    @org.junit.jupiter.api.Test
    void listAllDVDs() throws DVDLibraryDaoException {
        DVD dvd = new DVD("Test Title");
        dvd.setDvdReleaseDate(LocalDate.parse("11/12/13"));
        dvd.setDvdDirectorName("Test Director");
        dvd.setDvdStudio("Test Studio");
        dvd.setDvdRating("3.5");
        dvd.setDvdUserRating("Test Rating");


        assertEquals(1, dao.listAllDVDs().size(), "Should only have one DVD");
        assertTrue(dao.listAllDVDs().get(0).getDvdTitle().equals(dvd.getDvdTitle()), "The one DVD should be Test Title");

    }

    @org.junit.jupiter.api.Test
    void findDVD() throws DVDLibraryDaoException {
        DVD dvd = new DVD("Test Title");
        dvd.setDvdReleaseDate(LocalDate.parse("11/12/13"));
        dvd.setDvdDirectorName("Test Director");
        dvd.setDvdStudio("Test Studio");
        dvd.setDvdRating("3.5");
        dvd.setDvdUserRating("Test Rating");

        dao.addDVD(dvd.getDvdTitle(), dvd);
        DVD foundDVD = dao.findDVD("Test Title");

        assertEquals(dvd.getDvdTitle(), foundDVD.getDvdTitle(), "Found DVD Title should be Test Title");
        assertNotEquals(foundDVD.getDvdTitle(), "Test", "Found DVD should be Test Title");
    }

    @org.junit.jupiter.api.Test
    void searchDVD() throws DVDLibraryDaoException {
        DVD dvd = new DVD("Test Title");
        dvd.setDvdReleaseDate(LocalDate.parse("11/12/13"));
        dvd.setDvdDirectorName("Test Director");
        dvd.setDvdStudio("Test Studio");
        dvd.setDvdRating("3.5");
        dvd.setDvdUserRating("Test Rating");

        dao.addDVD(dvd.getDvdTitle(), dvd);
        DVD searchedDVD = dao.findDVD("Test Title");

        assertEquals(dvd.getDvdTitle(), searchedDVD.getDvdTitle(), "Searched DVD Title should be Test Title");
        assertNotEquals(searchedDVD.getDvdTitle(), "Test", "Searched DVD should be Test Title");
    }
}