with open("years.txt") as f:
    years = f.readline().split()

leap = []

for year in years:
    year = int(year)

    if (year % 4 == 0 and year % 100 != 0) or year % 400 == 0:
        leap.append(year)

print("Leap years:")
for year in leap:
    print(year)
