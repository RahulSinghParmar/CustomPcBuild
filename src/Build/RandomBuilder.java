package Build;

import java.util.Scanner;

class RandomBuilder
{
    private static int randomStartKey;
    public static int getRandomStartKey() {
        return randomStartKey;
    }
    public static void setRandomStartKey(int randomStartNum) {
        randomStartKey = randomStartNum;
    }

    int randomWithRange(int min, int max)
    {
        //max = 3 and min = 2 always
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    double randomWithRange(double min, double max)
    {
        double range = (max - min) + 1;
        return (Math.random() * range) + min;
    }

    public static void  randomBuildStart()
    {
        Scanner randomInput = new Scanner(System.in);

        System.out.println("Please Enter [8] for randomly generated build:");

        int randomStartKey = randomInput.nextInt();
        setRandomStartKey(randomStartKey);
        while (randomStartKey != 8)
        {
            System.out.println("Please enter the correct menu option. ");
            randomStartKey = randomInput.nextInt();
            setRandomStartKey(randomStartKey);
        }
    }
}
