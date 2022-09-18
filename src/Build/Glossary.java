package Build;

import java.util.Scanner;

public class Glossary
{
    public static void Glossaryinfo()
    {
        Scanner input = new Scanner(System.in);
        int glossMenu;

        do {
            System.out.println("This is the PC Building Glossary. Enter a number to select the topic you want to learn about. ");
            System.out.println("[1] Processor (CPU)");
            System.out.println("[2] Motherboard (Board)");
            System.out.println("[3] Memory (RAM)");
            System.out.println("[4] Video Card/Graphics Card (GPU)");
            System.out.println("[5] Power Supply (PSU)");
            System.out.println("[6] Quit and return to the Main Menu");
            glossMenu = input.nextInt();

            switch (glossMenu) {
                case 1:
                    System.out.println("CPU Info \n");
                    break;
                case 2:
                    System.out.println("Board Info \n");
                    break;
                case 3:
                    System.out.println("RAM Info \n");
                    break;
                case 4:
                    System.out.println("GPU Info \n");
                    break;
                case 5:
                    System.out.println("PSU Info \n");
                    break;
                case 6:
                    System.out.println("Quit and Return to the Main Menu \n");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: \n" + glossMenu);
            }
        }
        while (glossMenu != 6);
    }
}
