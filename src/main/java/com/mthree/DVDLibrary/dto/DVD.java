package com.mthree.DVDLibrary.dto;

import java.time.LocalDate;

public class DVD {

    private String dvdTitle;
    private LocalDate dvdReleaseDate;
    private String dvdRating;
    private String dvdDirectorName;
    private String dvdStudio;
    private String dvdUserRating;

    public DVD (String title){
        this.dvdTitle = title;
    }

    public LocalDate getDvdReleaseDate() {
        return dvdReleaseDate;
    }

    public void setDvdReleaseDate(LocalDate dvdReleaseDate) {

        this.dvdReleaseDate = dvdReleaseDate;
    }

    public String getDvdTitle() {
        return dvdTitle;
    }

    public String getDvdRating() {
        return dvdRating;
    }

    public void setDvdRating(String dvdRating) {
        this.dvdRating = dvdRating;
    }

    public String getDvdDirectorName() {
        return dvdDirectorName;
    }

    public void setDvdDirectorName(String dvdDirectorName) {
        this.dvdDirectorName = dvdDirectorName;
    }

    public String getDvdStudio() {
        return dvdStudio;
    }

    public void setDvdStudio(String dvdStudio) {
        this.dvdStudio = dvdStudio;
    }

    public String getDvdUserRating() {
        return dvdUserRating;
    }

    public void setDvdUserRating(String dvdUserRating) {
        this.dvdUserRating = dvdUserRating;
    }
}
