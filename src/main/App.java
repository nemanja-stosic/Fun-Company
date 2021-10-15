package main;

import controller.Controller;
import view.UserInterface;

public class App {

    public static void launch() {
        Controller controller = new Controller();
        controller.userInterface = new UserInterface();


        //setting listeners for actions
        controller.userInterface.setListeners(controller);

    }
}
