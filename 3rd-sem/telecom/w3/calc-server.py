"""
Practice 3 - Exercise 2: Calculator TCP Server
Task: Receive binary struct (two ints + 1 op byte), calculate result, return string.
Usage: python3 calc_server.py <port>
"""

import socket
import struct
import sys

port = int(sys.argv[1])
packer = struct.Struct('I I 1s')

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server:
    server.bind(('localhost', port))
    server.listen(1)

    while True:
        conn, _ = server.accept()
        with conn:
            data = conn.recv(packer.size)
            if data:
                n1, n2, op = packer.unpack(data)
                result = eval(f"{n1} {op.decode()} {n2}")
                conn.sendall(str(result).encode())
