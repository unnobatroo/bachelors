// Specification: https://progalap.elte.hu/specifikacio/v1/?uuid=12cafa07-6029-42c2-8a82-b7b7b94a08ca
// Structogramma: https://progalap.elte.hu/stuki/v1/?uuid=94927a8e-f74e-4b93-ae10-9a6ba6035a2a
using System;
namespace ConsoleApp1;

class Program
{
    static void Main(string[] args)
    {
        bool isDataCorrect = true;
        int weeksNum = 0;
        int lapsNum = 0;
        int[,] records = null;
        int[] weeklySums = null;

        string firstLine = Console.ReadLine();
        if (firstLine != null)
        {
            string[] dimensions = firstLine.Split(new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);

            if (dimensions.Length != 2)
            {
                isDataCorrect = false;
            }
            else
            {
                if (!int.TryParse(dimensions[0], out weeksNum) || weeksNum < 2 || weeksNum > 10)
                {
                    isDataCorrect = false;
                }

                if (isDataCorrect)
                {
                    if (!int.TryParse(dimensions[1], out lapsNum) || lapsNum < 1 || lapsNum > 10)
                    {
                        isDataCorrect = false;
                    }
                }
            }
        }
        else
        {
            isDataCorrect = false;
        }


        if (isDataCorrect)
        {
            records = new int[weeksNum, lapsNum];
            weeklySums = new int[weeksNum];

            for (int i = 0; i < weeksNum && isDataCorrect; i++)
            {
                string line = Console.ReadLine();

                if (line == null)
                {
                    isDataCorrect = false;
                }
                else
                {
                    string[] lapTimes = line.Split(new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);

                    if (lapTimes.Length != lapsNum)
                    {
                        isDataCorrect = false;
                    }
                    else
                    {
                        for (int j = 0; j < lapsNum && isDataCorrect; j++)
                        {
                            if (!int.TryParse(lapTimes[j], out records[i, j]) || records[i, j] <= 0)
                            {
                                isDataCorrect = false;
                            }
                        }
                    }
                }
            }
        }

        if (isDataCorrect)
        {
            for (int i = 0; i < weeksNum; i++)
            {
                for (int j = 0; j < lapsNum; j++)
                {
                    weeklySums[i] += records[i, j];
                }
            }
        }

        if (isDataCorrect)
        {
            bool hasImproved = true;
            for (int idx = 1; idx < weeksNum && hasImproved; idx++)
            {
                if (weeklySums[idx] >= weeklySums[idx - 1])
                {
                    hasImproved = false;
                }
            }

            if (hasImproved)
            {
                Console.WriteLine("true");
            } else
            {
                Console.WriteLine("false");
            }
        }
    }
}