package com.mthree.DVDLibrary.controller;

import com.mthree.DVDLibrary.dao.DVDLibraryDao;
import com.mthree.DVDLibrary.dao.DVDLibraryDaoException;
import com.mthree.DVDLibrary.dto.DVD;
import com.mthree.DVDLibrary.ui.DVDLibraryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DVDLibraryController {

    private DVDLibraryView view;
    private DVDLibraryDao dao;

    @Autowired
    public DVDLibraryController(DVDLibraryDao dao, DVDLibraryView view) {
        this.view = view;
        this.dao = dao;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {
                menuSelection = getMenuSelection();
                switch (menuSelection) {
                    case 1:
                        createDVD();
                        break;
                    case 2:
                        removeDVD();
                        break;
                    case 3:
                        editDVD();
                        break;
                    case 4:
                        listDVDs();

                        break;
                    case 5:
                        findADVD();
                        break;
                    case 6:
                        searchADVD();
                        break;
                    case 7:
                        findMoviesOfPastYears();
                        break;
                    case 8:
                        moviesBasedOnMPAARating();
                        break;
                    case 9:
                        moviesBasedOnDirector();
                        break;
                    case 10:
                        moviesOfStudio();
                        break;
                    case 11:
                        averageAgeMovies();
                        break;
                    case 12:
                        newestMovie();
                        break;
                    case 13:
                        oldestMovie();
                        break;
                    case 14:
                        avgNotes();
                        break;
                    case 15:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }
            exitMessage();
        }catch (DVDLibraryDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void avgNotes() throws DVDLibraryDaoException {
        Double avg = dao.averageNotes();
        view.displayAvgNotes(avg);
    }

    private void oldestMovie() throws DVDLibraryDaoException {
        DVD movie = dao.oldestMovie();
        view.displayOldestMovie(movie);
    }

    private void newestMovie() throws DVDLibraryDaoException {
        DVD movie = dao.latestMovie();
        view.displayNewestMovie(movie);
    }

    private void averageAgeMovies() throws DVDLibraryDaoException {
        Double age = dao.averageAgeOfMovies();
        view.displayAverageAge(age);
    }

    private void moviesOfStudio() throws DVDLibraryDaoException {
        String studio = view.getStudio();
        List<DVD> movieList = dao.findByStudio(studio);
        view.displayMoviesBasedOnStudio(movieList);
    }

    private void moviesBasedOnDirector() throws DVDLibraryDaoException {
        String directorName = view.getDirectorName();
        Map<String, List<DVD>> movieList = dao.findByDirector(directorName);
        view.displayMoviesBasedOnDirector(movieList, directorName);
    }

    private void moviesBasedOnMPAARating() throws DVDLibraryDaoException {
        String rating = view.getRating();
        List<DVD> movieList = dao.findByRating(rating);
        view.displayMoviesBasedOnMPAARating(movieList);
    }

    private void findMoviesOfPastYears() throws DVDLibraryDaoException {
        int years = view.getYears();
        List<DVD> movieList = dao.moviesReleasedInPastYears(years);
        view.displayMoviesOfPastYears(movieList);
    }

    private int getMenuSelection() {
       return view.printMenuAndGetSelection();
    }

    private void createDVD() throws DVDLibraryDaoException {
        view.displayAddDVDBanner();
        DVD newDVD = view.getNewDVDInfo();
        dao.addDVD(newDVD.getDvdTitle().toLowerCase(), newDVD);
        view.displayAddSuccessBanner();
    }

    private void removeDVD() throws DVDLibraryDaoException {
        view.displayRemoveDVDBanner();
        String title = view.getTitle();
        dao.deleteDVD(title.toLowerCase());
        view.displayRemoveSuccessBanner();
    }

    private void editDVD() throws DVDLibraryDaoException {
        view.displayEditDVDBanner();
        String title = view.getTitle();
        DVD updateDVD = view.getNewDVDInfo();
        dao.updateDVD(title.toLowerCase(), updateDVD);
        view.displayEditSuccessBanner();
    }

    private void listDVDs() throws DVDLibraryDaoException {
        view.displayListDVDBanner();
        dao.latestMovie();
        List<DVD> dvdList = dao.listAllDVDs();
        view.printDVDsList(dvdList);
        view.displayListSuccessBanner();
    }

    private void findADVD() throws DVDLibraryDaoException {
        view.displayFindDVDBanner();
        String title = view.getTitle();
        DVD dvd = dao.findDVD(title);
        view.printDVDInfo(dvd);
    }

    private void searchADVD() throws DVDLibraryDaoException {
        view.displaySearchBanner();
        String title = view.getTitle();
        DVD dvd = dao.searchDVD(title.toLowerCase());
        view.printSearchResults(dvd);
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    public void exitMessage(){
        view.displayExitBanner();
    }
}
