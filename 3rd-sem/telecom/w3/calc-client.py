"""
Practice 3 - Exercise 2: Calculator TCP Client
Task: Prompt user for two numbers and an operator, pack them into
a binary struct, send to server, and display the returned result.

Usage: python3 calc_client.py <port>
"""

import socket
import struct
import sys

# Read port from terminal command line
port = int(sys.argv[1])

# Format string: 2 unsigned ints (I) and 1 char/byte (1s)
packer = struct.Struct('I I 1s')

num1, op, num2 = input("<num> <operator> <num>: ").split(" ")

# Pack data: string operator must be converted to bytes (.encode())
packed_data = packer.pack(num1, num2, op.encode())

client = socket.socket(socket.SOCK_STREAM)
client.connect(('localhost', port))

# Send the packed binary bytes
client.sendall(packed_data)

# Receive calculated result string
response = client.recv(1024)
print("Result from server:", response.decode())

client.close()
