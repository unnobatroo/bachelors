namespace ConsoleApp1;

class Task_2
{
    static int[] playList1Ids;
    static int[] playList2Ids;
    static void Main(string[] args)
    {
        int playList1Num = Convert.ToInt32(Console.ReadLine());
        playList1Ids = new int[playList1Num];
        string input = Console.ReadLine();
        for (int i = 0; i < playList1Num; i++)
        {
            playList1Ids[i] = Convert.ToInt32(input.Split(" ")[i]);
        }

        int playList2Num = Convert.ToInt32(Console.ReadLine());
        playList2Ids = new int[playList2Num];

        string input2 = Console.ReadLine();

        for (int i = 0; i < playList2Num; i++)
        {
            playList2Ids[i] = Convert.ToInt32(input2.Split(" ")[i]);
        }

        int commonCnt = 0;
        int[] commonIds = new int[playList1Num];
        for (int i = 0; i < playList1Num; i++)
        {
            if (isCommon(ref i))
            {
                commonIds[commonCnt] = playList1Ids[i]; // when using list => commonIds.Add(playList1Ids[i])
                commonCnt++;
            }
        }

        for (int i = 0; i < commonCnt; i++)
            Console.Write(commonIds[i] + " ");
    }

    public static bool isCommon(ref int songIdIndex)
    {
        int j = 0;
        while (j < playList2Ids.Length && playList1Ids[songIdIndex] != playList2Ids[j])
        {
            j++;
        }
        bool exists = j < playList2Ids.Length;
        return exists;
    }
}