"""
Practice 3 - Exercise 2: Calculator TCP Client
Task: Prompt user, pack numbers and operator into binary struct, display result.
Usage: python3 calc_client.py <port>
"""

import socket
import struct
import sys

port = int(sys.argv[1])
packer = struct.Struct('I I 1s')

n1, op, n2 = input("Enter <num1> <operator> <num2>: ").split()

packed_data = packer.pack(int(n1), int(n2), op.encode())

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as client:
    client.connect(('localhost', port))
    client.sendall(packed_data)
    print("Result:", client.recv(1024).decode())
