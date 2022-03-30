package com.mthree.DVDLibrary.ui;

import com.mthree.DVDLibrary.dto.DVD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
public class DVDLibraryView {

    private UserIO io;

    @Autowired
    public DVDLibraryView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {

        io.print("============");
        io.print("Initial Menu:");
        io.print("\tPlease select the operation you wish to perform: ");
        io.print("\t 1. Add DVD");
        io.print("\t 2. Remove DVD");
        io.print("\t 3. Edit DVD");
        io.print("\t 4. List All DVDs");
        io.print("\t 5. Find DVD");
        io.print("\t 6. Search DVD by title");
        io.print("\t 7. Find all movies release in the last N years");
        io.print("\t 8. Find all the movies with a given MPAA rating");
        io.print("\t 9. Find all the movies by a given director");
        io.print("\t 10. Find all the movies released by a particular studio");
        io.print("\t 11. Find the average age of the movies in the collection");
        io.print("\t 12. Find the newest movie in your collection");
        io.print("\t 13. Find the oldest movie in your collection");
        io.print("\t 14. Find the average number of notes associated with movies in your collection");
        io.print("\t 15. Exit");

        int choice = 15;
        try {
            choice = io.readInt("Please select from the above choices. ", 1, 15);
        } catch (NumberFormatException e) {
            try {
                io.print("Invalid Input! Please enter a Number. ");
                choice = io.readInt("Please select from the above choices. ", 1, 7);
            } catch (NumberFormatException c) {
                io.print("Invalid Input. EXITING...");
            }
        }
        return choice;
    }

    public void displayAddDVDBanner() {
        io.print("=========");
        io.print("Add new DVD:");
    }
    public DVD getNewDVDInfo() {
        LocalDate date;
        String title = io.readString("Please enter Title: ");
        String releaseDate = io.readString("Please enter Release Date (mm-dd-yyyy): ");
        try{
            date = LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        } catch (DateTimeException e) {
            io.print("Invalid Input for Date, check format. Try Again!");
            releaseDate = io.readString("Please enter Release Date (mm-dd-yyyy): ");
            date = LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        }
        String directorName = io.readString("Please enter Director Name: ");
        String studio = io.readString("Please enter Studio: ");
        String rating = io.readString("Please enter Rating: ");
        String userRating = io.readString("Please enter User Rating or Note: ");

        DVD currDVD = new DVD(title);
        currDVD.setDvdReleaseDate(date);
        currDVD.setDvdDirectorName(directorName);
        currDVD.setDvdStudio(studio);
        currDVD.setDvdRating(rating);
        currDVD.setDvdUserRating(userRating);

        return  currDVD;
    }

    public void displayAddSuccessBanner() {
        io.readString("Press enter to go to main menu. ");
    }

    public void displayRemoveDVDBanner() {
        io.print("========");
        io.print("Remove DVD: ");
    }

    public String getTitle() {
        return io.readString("Please enter title of DVD: ");
    }

    public void displayRemoveSuccessBanner() {
        io.print("DVD Deleted!");
        io.readString("Press enter to go to main menu. ");
    }

    public void displayEditDVDBanner() {
        io.print("============");
        io.print("Edit DVD: ");
    }

    public void displayEditSuccessBanner() {
        io.print("Updated DVD!");
        io.readString("Press enter to go to main menu. ");
    }

    public void displayListDVDBanner(){
        io.print("List DVDs: ");
    }

    public void printDVDsList(List<DVD> dvdList){
        if(dvdList.size() > 0) {
            for (DVD currDVD : dvdList) {
                io.print(currDVD.getDvdTitle());
            }
        } else {
            io.print("No DVDs in list.");
        }
    }

    public void displayListSuccessBanner() {
        io.readString("Press enter to go to main menu.");
    }

    public void displayFindDVDBanner() {
        io.print("======");
        io.print("Find DVD: ");
    }

    public void printDVDInfo(DVD dvd) {
        if(dvd != null) {
            String dateToString = dvd.getDvdReleaseDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
            io.print("Title:: "+ dvd.getDvdTitle());
            io.print("Release Date:: " + dateToString);
            io.print("Ratings:: " + dvd.getDvdRating());
            io.print("Director Name:: " +dvd.getDvdDirectorName());
            io.print("Studio:: " +dvd.getDvdStudio());
            io.print("User Rating:: " +dvd.getDvdUserRating());
        } else {
            io.print("No Such DVD found!");
        }

        io.readString("Press enter to go to main menu. ");
    }

    public void displaySearchBanner(){
        io.print("=======");
        io.print("Search DVD: ");
    }

    public void printSearchResults(DVD dvd) {
        if(dvd != null) {
            io.print("Search result:-");
            io.print("Title:: " +dvd.getDvdTitle());
        } else {
            io.print("No Results Found!");
        }
        io.readString("Press enter to go to main menu. ");
    }

    public int getYears() {
        return io.readInt("Please enter no of past years:: ");
    }

    public void displayMoviesOfPastYears(List<DVD> movies) {
        if(movies.size() > 0) {
            for (DVD currDVD : movies) {
                io.print(currDVD.getDvdTitle());
            }
        } else {
            io.print("No Movies in list.");
        }
        io.readString("Press enter to go to main menu");
    }

    public String getRating(){
        return io.readString("Please enter rating:: ");
    }
    public void displayMoviesBasedOnMPAARating(List<DVD> movies) {
        if(movies.size() > 0) {
            for (DVD currDVD : movies) {
                io.print(currDVD.getDvdTitle());
            }
        } else {
            io.print("No Movies in list.");
        }
        io.readString("Press enter to go to main menu");
    }

    public String getDirectorName(){
        return io.readString("Please enter Director Name:: ");
    }
    public void displayMoviesBasedOnDirector(Map<String,List<DVD>> movies, String directorName){
        if(movies.size() > 0) {
            io.print("Movies by " + directorName + " :");
            for (List<DVD> movieList : movies.values()) {
                for(DVD currDVD: movieList) {
                    io.print("Rating " +currDVD.getDvdRating() + " - ");
                    io.print(currDVD.getDvdTitle());
                }
            }
        } else {
            io.print("No Movies in list.");
        }
        io.readString("Press enter to go to main menu");
    }

    public String getStudio(){
        return io.readString("Please enter Studio Name:: ");
    }
    public void displayMoviesBasedOnStudio(List<DVD> movies) {
        if(movies.size() > 0) {
            for (DVD currDVD : movies) {
                io.print(currDVD.getDvdTitle());
            }
        } else {
            io.print("No Movies in list.");
        }
        io.readString("Press enter to go to main menu");
    }

    public void displayAverageAge(Double age) {
        io.print("Average Age of Movies is: " +age);
        io.readString("Press enter to go to main menu");
    }

    public void displayNewestMovie(DVD movie) {
        io.print(movie.getDvdTitle());
        io.readString("Press enter to go to main menu");
    }

    public void displayOldestMovie(DVD movie) {
        io.print(movie.getDvdTitle());
        io.readString("Press enter to go to main menu");
    }

    public void displayAvgNotes(Double avg) {
        io.print("Average User Notes associated with the movies is " +avg);
            io.readString("Press enter to go to main menu");

    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorMessage(String message){
        io.print("ERROR!!");
        io.print(message);
    }
}
