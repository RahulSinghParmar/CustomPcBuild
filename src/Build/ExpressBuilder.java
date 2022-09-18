package Build;


import java.util.Scanner;

public class ExpressBuilder extends RandomBuilder
{

    private static int userType;
    private static double userBudget, userTax;
    private static String userWindows;

    public static double getUserType() {
        return userType;
    }

    public static void setUserType(int numType) {
        userType = numType;
    }
    public static double getUserBudget() {
        return userBudget;
    }

    public static void setUserBudget(double numBudget) {
        userBudget = numBudget;
    }

    public static String getUserWindows() {
        return userWindows;
    }

    public static void setUserWindows(String answer) {
        userWindows = answer;
    }

    public static double getUserTax() {
        return userTax;
    }

    public static void setUserTax(double numTax) {
        userTax = numTax;
    }

    public static void ExpressPC_type()
    {
        Scanner expressInput = new Scanner(System.in);
        RandomBuilder give = new RandomBuilder();

        if (MainMenu.getMainMenu() == 3)
        {
            int userType = give.randomWithRange(2, 3);
            ExpressBuilder.setUserType(userType);
        }
        else {
            System.out.println("This is the Express Builder. Please answer the questions and your build will be created based on your selections.");
            System.out.println("1. What will this PC be used for? Enter [1] for Office Work 🏢, [2] for Gaming 🎮  [3] for Photo/Video Editing 🎥.");
            System.out.println();

            int userType = expressInput.nextInt();
            ExpressBuilder.setUserType(userType);
            while (userType > 3 || userType < 1)
            {
                System.out.println("Please enter the correct menu option.");
                userType = expressInput.nextInt();
                ExpressBuilder.setUserType(userType);
            }
        }
    }

    public static void ExpressPC_budget()
    {
        Scanner expressInput = new Scanner(System.in);
        RandomBuilder give = new RandomBuilder();

        if (MainMenu.getMainMenu() == 3)
        {
            double userBudget = give.randomWithRange(40000.00, 500000.00);
            ExpressBuilder.setUserBudget(userBudget);
        }
        else {

            if (getUserType() == 1)
            {
                System.out.println("2. What is your maximum budget in Rupees before shipping and taxes? " +
                        "Enter a number from ₹ 50,000 to ₹ 5,00,000. ");
                System.out.println();

                double userBudget = expressInput.nextDouble();
                ExpressBuilder.setUserBudget(userBudget);
                while (userBudget > 500000 || userBudget < 40000)
                {
                    System.out.println("Please enter a valid amount.");
                    userBudget = expressInput.nextDouble();
                    ExpressBuilder.setUserBudget(userBudget);
                }
            }
            else {
                System.out.println("2. What is your maximum budget in Rupees before shipping and taxes? " +
                        "Enter a number from ₹ 50,000 to ₹ 5,00,000.");
                System.out.println();

                double userBudget = expressInput.nextDouble();
                ExpressBuilder.setUserBudget(userBudget);
                while (userBudget > 500000 || userBudget < 50000) {
                    System.out.println("Please enter a valid amount.");
                    userBudget = expressInput.nextDouble();
                    ExpressBuilder.setUserBudget(userBudget);
                }
            }
        }
    }

    public static void ExpressPC_windows()
    {
        Scanner expressInput = new Scanner(System.in);

        if (MainMenu.getMainMenu() == 3) {
            ExpressBuilder.setUserWindows("Y");
        }
        else {

            System.out.println("3. Do you need a Windows 10 Operating System Key? Enter [Y] for Yes or [N] for No.");
            System.out.println();

            String userWindows = expressInput.next();
            ExpressBuilder.setUserWindows(userWindows);
            while (!userWindows.equalsIgnoreCase("Y") && !userWindows.equalsIgnoreCase("N"))
            {
                System.out.println("Please enter a valid answer.");
                userWindows = expressInput.next();
                ExpressBuilder.setUserWindows(userWindows);
            }
        }
    }

    public static void ExpressPC_tax()
    {
        Scanner expressInput = new Scanner(System.in);
        RandomBuilder give = new RandomBuilder();

        if (MainMenu.getMainMenu() == 3)
        {
            double userTax = give.randomWithRange(0.00, 10.00);
            ExpressBuilder.setUserTax(userTax);
        }
        else {
            System.out.println("4. What is your state sales tax rate (%)? Enter a number from 0 to 10");
            System.out.println();

            double userTax = expressInput.nextDouble();
            ExpressBuilder.setUserTax(userTax);
            while (userTax > 10 || userTax < 0)
            {
                System.out.println("Please enter a valid number.");
                userTax = expressInput.nextDouble();
                ExpressBuilder.setUserTax(userTax);
            }
        }
    }

    public static void ExpressPC_print()
    {
        PC_Types.PCtaker select = new PC_Types.PCtaker();
        select.PC_Build();
    }

}
