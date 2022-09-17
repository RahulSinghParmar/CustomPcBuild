/*
Name    : Rahul Singh Parmar
email   : rahulsinghparmar4@gmail.com
Project : Custom Personal Computer Builder
*/
// --------------------------------------------------------------


package Build;

import java.util.Scanner;

public class MainMenu {

    private static int mainMenu;

    public static void main(String [] args) {
        printBanner();
        printMenu();
    }

    private static void printMenu()
    {
        Scanner mainInput = new Scanner (System.in);

        do{
            System.out.println("Please enter a number from the menu. ");
            System.out.println("Menu: ");
            System.out.println("");
            System.out.println("1. Add");
            System.out.println("2. Remove");
            System.out.println("3. Exit");
            System.out.println("4. dadad");
            System.out.println("5. dsafsfw");
            System.out.println("Enter your choice: ");

            mainMenu = mainInput.nextInt();

            switch (mainMenu)
            {
                case 1:
                    System.out.println("Enter your choice");
                    //ExpressPC_Menu();
                    break;
                case 2:
                    System.out.println("Custom Pc Builder");
                    break;
                /*case 3:
                    Expdada();
                    break;
                case 4:
                    dasd();
                    break;*/
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
        while (mainMenu != 2);
    }


    public static void printBanner()
    {
        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("║          𝗪𝗲𝗹𝗰𝗼𝗺𝗲 𝘁𝗼 Rahul's       ║");
        System.out.println("║          𝗖𝘂𝘀𝘁𝗼𝗺 𝗣𝗖 𝗕𝘂𝗶𝗹𝗱𝗲𝗿          ║");
        System.out.println("╚═══════════════════════════════════╝");
        currentDateFormat();
        System.out.println();
    }

    private static void currentDateFormat()
    {
        System.out.println("Current Date: " + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
    }

    /*private static void ExpressPC_Menu()
    {
        ExpressBuilder.ExpressPC_type();
        ExpressBuilder.ExpressPC_budeget();
        ExpressBuilder.ExpressPC_windows();
        ExpressBuilder.ExpressPC_tax();
        ExpressBuilder.ExpressPC_print();
    }*/


}

