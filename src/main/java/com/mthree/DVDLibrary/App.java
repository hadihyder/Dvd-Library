package com.mthree.DVDLibrary;

import com.mthree.DVDLibrary.controller.DVDLibraryController;
import com.mthree.DVDLibrary.dao.DVDLibraryDao;
import com.mthree.DVDLibrary.dao.DVDLibraryDaoFileImpl;
import com.mthree.DVDLibrary.ui.DVDLibraryView;
import com.mthree.DVDLibrary.ui.UserIO;
import com.mthree.DVDLibrary.ui.UserIOConsoleImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {

//        UserIO myIO = new UserIOConsoleImpl();
//        DVDLibraryView myView = new DVDLibraryView(myIO);
//        DVDLibraryDao myDao = new DVDLibraryDaoFileImpl();
//        DVDLibraryController controller = new DVDLibraryController(myDao, myView);
//
//        controller.run();

//        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//
//        DVDLibraryController controller = ctx.getBean("controller", DVDLibraryController.class);
//
//        controller.run();

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.mthree.DVDLibrary");
        appContext.refresh();

        DVDLibraryController controller = appContext.getBean("DVDLibraryController", DVDLibraryController.class);
        controller.run();
    }
}
