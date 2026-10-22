import struct
import sys

formats = ["if?", "?c9s", "9sfi", "?ic"]

for path, fmt in zip(sys.argv[1:5], formats):
    with open(path, "rb") as f:
        print(struct.unpack(fmt, f.read(struct.calcsize(fmt))))

to_pack = [
    ("16si?", (b"elso", 73, True)),
    ("f?c", (76.5, False, b"X")),
    ("i14sf", (64, b"masodik", 83.9)),
    ("ci16s", (b"Z", 95, b"harmadik")),
]

for fmt, values in to_pack:
    print(struct.pack(fmt, *values))
