// Specification: https://progalap.elte.hu/specifikacio/v1/?uuid=b3bd07d4-d5b9-4e1c-9c61-2219d5c1b1d1
// Structogram: https://progalap.elte.hu/stuki/v1/?uuid=cdf17d1b-deff-42b9-8ac0-e88366452148

using System.Diagnostics.Contracts;

namespace ConsoleApp2;

class Program
{
    static int rowsNum, colsNum;
    static int[,] readings;
    static void Main(string[] args)
    {
        string[] dims = Console.ReadLine().Split(' ');
        rowsNum = int.Parse(dims[0]);
        colsNum = int.Parse(dims[1]);
        readings = new int[rowsNum, colsNum];

        for (int rowIdx = 0; rowIdx < rowsNum; ++rowIdx)
        {
            string[] rowValStrings = Console.ReadLine().Split(' ');
            int colIdx = 0;
            foreach (string val in rowValStrings)
            {
                readings[rowIdx, colIdx] = int.Parse(val);
                ++colIdx;
            }
        }

        int countOfEights = 0;

        for (int rowIdx = 0; rowIdx < rowsNum; ++rowIdx)
        {
            countOfEights += countInRows(rowIdx);
        }

        Console.WriteLine(countOfEights);
    }

    public static int countInRows(int rowIdx)
    {
        int count = 0;
        for (int colIdx = 0; colIdx < colsNum; ++colIdx)
        {
            if (readings[rowIdx, colIdx] == 8)
            {
                ++count;
            }
        }

        return count;
    }
}
