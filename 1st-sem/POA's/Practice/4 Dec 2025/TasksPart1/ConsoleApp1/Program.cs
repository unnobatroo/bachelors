// Specification: https://progalap.elte.hu/specifikacio/v1/?uuid=712ad14c-b67c-4632-969b-fc7fedf72485
// Structogram: https://progalap.elte.hu/stuki/v1/?uuid=a8c7d76e-34eb-49ee-8fd0-c074e293b6d7

namespace ConsoleApp1;

class Program
{
    // ****** TASK 1 ******
    static int rows, cols;
    static int[,] codes;
    static void Main(string[] args)
    {
        // ****** TASK 1 ******
        
        // --- INPUT PARSING ---
        string[] dimChars = Console.ReadLine().Split(' ');
        rows = int.Parse(dimChars[0]);
        cols = int.Parse(dimChars[1]);
        codes = new int[rows, cols];

        for (int rowIdx = 0; rowIdx < rows; ++rowIdx)
        {
            string[] rowValStrings = Console.ReadLine().Split(' ');
            int colIdx = 0;

            foreach (string val in rowValStrings)
            {
                codes[rowIdx, colIdx] = int.Parse(val);
                ++colIdx;
            }
        }

        // --- ALGORITHM ---
        int sevensRowsCount = 0;
        for (int rowIdx = 0; rowIdx < rows; ++rowIdx)
            if (areAllSeven(rowIdx))
                ++sevensRowsCount;

        Console.WriteLine(sevensRowsCount);
    }

    // ****** TASK 1 ******
    public static bool areAllSeven(int rowIdx)
    {
        int colIdx = 0;
        while (colIdx < cols && codes[rowIdx, colIdx] == 7)
            ++colIdx;

        return colIdx == cols;
    }
}