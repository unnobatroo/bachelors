import json
import math

with open("grades.json") as f:
    d = json.load(f)

curr = sum(
    d[x]["point"] / d[x]["max"]
    for x in ["homework", "socket"]
)

gr = {2: .5, 3: .6, 4: .75, 5: .9}
k = d["kathara"]

for g, t in gr.items():
    z = max(k["min_percent"], 3 * t - curr)
    p = math.ceil(z * k["max"])

    print(f"{g}: {p if p <= k['max'] else 'Nope'}")
