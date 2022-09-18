package Build;

import java.util.Scanner;

public class RandomBuilder
{
    private static int randomStartKey;
    public static int getRandomStartKey() {
        return randomStartKey;
    }
    public static void setRandomStartKey(int randomStartNum) {
        randomStartKey = randomStartNum;
    }

    int randomWithRange(double min, double max)
    {
        double range = (min - max) + 1;
        return (int) (min + (Math.random() * range));
    }

    public static void  randomBuildStart()
    {
        Scanner randomInput = new Scanner(System.in);

        System.out.println("Please Enter [8] for randomly generated build:");

        int randomStartKey = randomInput.nextInt();
        setRandomStartKey(randomStartKey);
        while (randomStartKey != 8 )
        {
            System.out.println("Please enter the correct menu option. ");
            randomStartKey = randomInput.nextInt();
            setRandomStartKey(randomStartKey);
        }
    }
}
