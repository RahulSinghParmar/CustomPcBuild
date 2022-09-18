/*
Name    : Rahul Singh Parmar
email   : rahulsinghparmar4@gmail.com
Project : Custom Personal Computer Builder
*/


package Build;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {

    private static int mainMenu;

    public static int getMainMenu() {
        return mainMenu;
    }

    public static void main(String[] args) {
        printBanner();
        printMenu();
    }

    public static void printMenu()
    {
        Scanner mainInput = new Scanner(System.in);

        do{
            System.out.println("Please enter a number from the menu. ");
            System.out.println();
            System.out.println("[1] Express Builder: A build will be created by the program based on your preferences(for new user). ");
            System.out.println("[2] Custom Builder: A build created by the parts you manually SELECT (for advanced users). ");
            System.out.println("[3] Random Build: A build will be randomly generated.");
            System.out.println("[4] PC Building Glossary: Brief information about each PC part and what it does. ");
            System.out.println("[5] Quit Builder program ");

            mainMenu = mainInput.nextInt();

            switch (mainMenu) {
                case 1:
                    ExpressPC_Menu();
                    break;
                case 2:
                    System.out.println("Custom Pc Builder ");
                    break;
                case 3:
                    RandomPC_Menu();
                    break;
                case 4:
                    Glossary.Glossaryinfo();
                    break;
                case 5:
                    System.out.println("Thanks for using this program! ☺ ");
                    break;
                default:
                    System.out.println("Please enter a valid option from the menu. ");
                    System.out.println();
            }
        }
        while (mainMenu != 5);
    }


    public static void printBanner()
    {
        System.out.println("          ╔═══════════════════════════════════╗     ");
        System.out.println("          ║          𝗪𝗲𝗹𝗰𝗼𝗺𝗲 𝘁𝗼 Rahul's       ║     ");
        System.out.println("          ║          𝗖𝘂𝘀𝘁𝗼𝗺 𝗣𝗖 𝗕𝘂𝗶𝗹𝗱𝗲𝗿          ║     ");
        System.out.println("          ╚═══════════════════════════════════╝     ");
        currentDateFormat();
        System.out.println();
    }

    public static void currentDateFormat()
    {
        Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println("Today is : " + dateFormat.format(currentDate));
    }

    public static void ExpressPC_Menu()
    {
        ExpressBuilder.ExpressPC_type();
        ExpressBuilder.ExpressPC_budget();
        ExpressBuilder.ExpressPC_windows();
        ExpressBuilder.ExpressPC_tax();
        ExpressBuilder.ExpressPC_print();
    }

    public static void RandomPC_Menu()
    {
        RandomBuilder.randomBuildStart();
        ExpressPC_Menu();
    }
}

