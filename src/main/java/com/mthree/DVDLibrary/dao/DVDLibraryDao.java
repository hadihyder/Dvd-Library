package com.mthree.DVDLibrary.dao;

import com.mthree.DVDLibrary.dto.DVD;

import java.util.List;
import java.util.Map;

public interface DVDLibraryDao {

    DVD addDVD(String title, DVD dvd) throws DVDLibraryDaoException;

    DVD deleteDVD(String title) throws DVDLibraryDaoException;

    DVD updateDVD(String title, DVD dvd) throws DVDLibraryDaoException;

    List<DVD> listAllDVDs() throws DVDLibraryDaoException;

    DVD findDVD(String title) throws DVDLibraryDaoException;

    DVD searchDVD(String title) throws DVDLibraryDaoException;

    List<DVD> moviesReleasedInPastYears(int Years) throws DVDLibraryDaoException;

    List<DVD> findByRating(String rating) throws DVDLibraryDaoException;

    Map<String, List<DVD>> findByDirector(String directorName) throws DVDLibraryDaoException;

    List<DVD> findByStudio(String studio) throws DVDLibraryDaoException;

    Double averageAgeOfMovies() throws DVDLibraryDaoException;

    DVD latestMovie() throws DVDLibraryDaoException;

    DVD oldestMovie() throws DVDLibraryDaoException;

    Double averageNotes() throws DVDLibraryDaoException;


}
