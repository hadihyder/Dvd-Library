package com.mthree.DVDLibrary.dao;

import com.mthree.DVDLibrary.dto.DVD;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DVDLibraryDaoFileImpl implements DVDLibraryDao{

    private static final String DVD_FILE = "dvd.txt";
    private static final String DELIMITER = "::";


    private Map<String, DVD> DVDMap = new HashMap<>();

    @Override
    public DVD addDVD(String title, DVD dvd) throws DVDLibraryDaoException {
        loadDVD();
        DVD newDVD = DVDMap.put(title, dvd);
        writeDVD();
        return  newDVD;
    }

    @Override
    public DVD deleteDVD(String title) throws DVDLibraryDaoException {
        loadDVD();
        DVD removeDVD = DVDMap.remove(title);
        writeDVD();
        return removeDVD;
    }

    @Override
    public DVD updateDVD(String title, DVD dvd) throws DVDLibraryDaoException {
        loadDVD();
        DVDMap.remove(title);
        DVD updateDVD = DVDMap.put(dvd.getDvdTitle().toLowerCase(), dvd);
        writeDVD();
        return updateDVD;
    }

    @Override
    public List<DVD> listAllDVDs() throws DVDLibraryDaoException {
        loadDVD();
        return new ArrayList<DVD>(DVDMap.values());
    }

    @Override
    public DVD findDVD(String title) throws DVDLibraryDaoException {
        loadDVD();
        return DVDMap.get(title);
    }

    @Override
    public DVD searchDVD(String title) throws DVDLibraryDaoException {
        loadDVD();
        return DVDMap.get(title);
    }

    @Override
    public List<DVD> moviesReleasedInPastYears(int years) throws DVDLibraryDaoException {
        List<DVD> dvdList = this.listAllDVDs();
        LocalDate ld = LocalDate.now();
        int currYear = ld.getYear();
        int pastYear = currYear - years;
        List<DVD> moviesList = dvdList.stream()
                .filter((m) -> m.getDvdReleaseDate().getYear() <= currYear &&
                        m.getDvdReleaseDate().getYear() >= pastYear)
                .collect(Collectors.toList());


        return moviesList;
    }

    @Override
    public List<DVD> findByRating(String rating) throws DVDLibraryDaoException {
        List<DVD> dvdList = this.listAllDVDs();

        List<DVD> moviesList = dvdList.stream()
                .filter((m) -> m.getDvdRating().equals(rating))
                .collect(Collectors.toList());
        return moviesList;
    }

    @Override
    public Map<String, List<DVD>> findByDirector(String directorName) throws DVDLibraryDaoException {
        List<DVD> dvdList = this.listAllDVDs();
        Map<String, List<DVD>> moviesList = dvdList.stream()
                .filter((m) -> m.getDvdDirectorName().equals(directorName))
                .collect(Collectors.groupingBy((m) -> m.getDvdRating()));

        return moviesList;
    }

    @Override
    public List<DVD> findByStudio(String studio) throws DVDLibraryDaoException {
        List<DVD> dvdList = this.listAllDVDs();
        List<DVD> moviesList = dvdList.stream()
                .filter((m) -> m.getDvdStudio().equals(studio))
                .collect(Collectors.toList());

        return moviesList;
    }

    @Override
    public Double averageAgeOfMovies() throws DVDLibraryDaoException {
        List<DVD> dvdList = this.listAllDVDs();
        LocalDate ld = LocalDate.now();
        int currYear = ld.getYear();
        Double averageAge = dvdList.stream()
                .mapToInt((m) -> currYear - m.getDvdReleaseDate().getYear())
                .average().getAsDouble();

        return averageAge;
    }

    @Override
    public DVD latestMovie() throws DVDLibraryDaoException {

        List<DVD> dvdList = this.listAllDVDs();
        Comparator<DVD> comparator = Comparator.comparing(DVD::getDvdReleaseDate);
        DVD latest = dvdList.stream()
                .max(comparator)
                .get();

        return latest;
    }

    @Override
    public DVD oldestMovie() throws DVDLibraryDaoException {
        List<DVD> dvdList = this.listAllDVDs();
        Comparator<DVD> comparator = Comparator.comparing(DVD::getDvdReleaseDate);
        DVD oldest = dvdList.stream()
                .min(comparator)
                .get();
        return oldest;
    }

    @Override
    public Double averageNotes() throws DVDLibraryDaoException {
        List<DVD> dvdList = this.listAllDVDs();

        Double avg = dvdList.stream()
                .mapToInt(m -> {
                    int c = 0;
                    if(m.getDvdUserRating()!= null)
                         c++;
                    return c;
                })
                .average().getAsDouble();

        return avg;
    }

    private String marshallDVD(DVD dvd) {

        String dvdAsText = dvd.getDvdTitle() + DELIMITER;
        dvdAsText += dvd.getDvdReleaseDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + DELIMITER;
        dvdAsText += dvd.getDvdRating() + DELIMITER;
        dvdAsText += dvd.getDvdDirectorName() + DELIMITER;
        dvdAsText += dvd.getDvdStudio() + DELIMITER;
        dvdAsText += dvd.getDvdUserRating();

        return dvdAsText;
    }

    private DVD unmarshallDVD(String dvdAsText) {
        String[] dvdTokens = dvdAsText.split(DELIMITER);
        String title = dvdTokens[0];

        DVD dvdFromFile = new DVD(title);
        dvdFromFile.setDvdReleaseDate(LocalDate.parse(dvdTokens[1], DateTimeFormatter.ofPattern("MM-dd-yyyy")));
        dvdFromFile.setDvdRating(dvdTokens[2]);
        dvdFromFile.setDvdDirectorName(dvdTokens[3]);
        dvdFromFile.setDvdStudio(dvdTokens[4]);
        dvdFromFile.setDvdUserRating(dvdTokens[5]);

        return dvdFromFile;
    }

    private void loadDVD() throws DVDLibraryDaoException{
        Scanner sc;

        try {
            sc = new Scanner(
                    new BufferedReader(
                            new FileReader(DVD_FILE)
                    )
            );
        } catch (FileNotFoundException e) {
            throw new DVDLibraryDaoException("Could not load address data into memory.", e);
        }

        String currentLine;

        DVD currDVD;

        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();

            currDVD = unmarshallDVD(currentLine);

            DVDMap.put(currDVD.getDvdTitle().toLowerCase(), currDVD);
        }
        sc.close();
    }

    private void writeDVD() throws DVDLibraryDaoException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(DVD_FILE));
        } catch (IOException e) {
            throw new DVDLibraryDaoException("Could not save address data", e);
        }

        String dvdAsText;
        List<DVD> dvdList = this.listAllDVDs();

        for(DVD currDVD: dvdList) {

            dvdAsText = marshallDVD(currDVD);
            out.println(dvdAsText);

            out.flush();
        }
        out.close();
    }

}
