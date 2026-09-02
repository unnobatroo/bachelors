    using System;
    using System.Collections.Generic;
    using System.Diagnostics;
    using System.Linq;

    namespace Pirates;

    // A custom data structure to hold information about a single loot entry
    struct LootingRecord
    {
        public int RecordIndex;  // The order in which the record was entered (1, 2, 3...)
        public int LootValue;    // How much gold/loot was taken
        public string Region;    // The location of the loot
        public string PirateName; // The name of the pirate
    }

    class Program
    {
        static void Main(string[] args)
        {
            // Read the first line of input: N (number of records) and T (a threshold value)
            string[] inputLine1 = Console.ReadLine().Split(" ");
            int N = int.Parse(inputLine1[0]);
            int T = int.Parse(inputLine1[1]);

            // Create a list to store all the LootingRecord objects
            List<LootingRecord> records = new List<LootingRecord>();

            // Loop N times to read each record from the console
            for (int i = 0; i < N; ++i)
            {
                string[] recordData = Console.ReadLine().Split(" ");

                // Create a new record and assign values from the input
                LootingRecord newRecord = new LootingRecord
                {
                    RecordIndex = i + 1, // Store 1-based index
                    LootValue = int.Parse(recordData[0]),
                    Region = recordData[1],
                    PirateName = recordData[2]
                };

                records.Add(newRecord);
            }

            // Call the specific logic functions (Task B, C, and D are currently commented out)
            SolveTaskA(records);
            // SolveTaskB(records, T);
            // SolveTaskC(records);
            // SolveTaskD(records, T);
        }

        // TASK A: Find the pirate who had the single smallest loot value
        static void SolveTaskA(List<LootingRecord> records)
        {
            int minLoot = 101; // Start with a value higher than any possible loot
            string pirateName = "";

            foreach (var record in records)
            {
                if (record.LootValue < minLoot)
                {
                    minLoot = record.LootValue;
                    pirateName = record.PirateName;
                }
            }

            Console.WriteLine(pirateName);
        }

        // TASK B: Count how many records have a loot value greater than or equal to T
        static void SolveTaskB(List<LootingRecord> records, int T)
        {
            int count = 0;
            foreach (var record in records)
            {
                if (record.LootValue >= T)
                {
                    ++count;
                }
            }
            Console.WriteLine(count);
        }

        // TASK C: Calculate the total loot collected in each unique region
        static void SolveTaskC(List<LootingRecord> records)
        {
            // Dictionary to map Region Name -> Total Loot Sum
            Dictionary<string, int> regionTotals = new Dictionary<string, int>();
            // List to keep track of the order regions first appeared
            List<string> orderedRegions = new List<string>();

            foreach (var record in records)
            {
                string currRegion = record.Region;
                int currLoot = record.LootValue;

                if (!regionTotals.ContainsKey(currRegion))
                {
                    // First time seeing this region: add it to the dictionary and our order list
                    regionTotals.Add(currRegion, currLoot);
                    orderedRegions.Add(currRegion);
                }
                else
                {
                    // Region already exists: just add the loot to the existing total
                    regionTotals[currRegion] += currLoot;
                }
            }

            // Print results in the order the regions were first encountered
            foreach (string region in orderedRegions)
            {
                Console.WriteLine($"{region} {regionTotals[region]}");
            }
        }

        // TASK D: Check Jack's total loot and compare it to threshold T
        static void SolveTaskD(List<LootingRecord> records, int T)
        {
            int totLootJack = 0;
            int firstRecIdx = 0;

            foreach (var record in records)
            {
                if (record.PirateName == "Jack")
                {
                    totLootJack += record.LootValue;

                    // Capture the index of the very first record Jack appears in
                    if (firstRecIdx == 0)
                    {
                        firstRecIdx = record.RecordIndex;
                    }
                }
            }

            // Only print if Jack's total is greater than the threshold T
            if (totLootJack > T)
            {
                Console.WriteLine(firstRecIdx);
                Console.WriteLine($"Jack {totLootJack}");
            }
        }
    }