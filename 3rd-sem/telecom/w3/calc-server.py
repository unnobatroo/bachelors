"""
Practice 3 - Exercise 2: Calculator TCP Server
Task: Receive two integers and an operator packed into a binary struct,
calculate the result, and return it to the client.

Usage: python3 calc_server.py <port>
"""

import socket
import struct
import sys

# Read port from terminal command line
port = int(sys.argv[1])

# Define the expected binary structure: 2 unsigned ints (I) and 1 char/byte (1s)
unpacker = struct.Struct('I I 1s')

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind(('localhost', port))
server.listen(1)

while True:
    conn, client_addr = server.accept()

    # Receive exact size of packed struct bytes (12 bytes)
    data = conn.recv(unpacker.size)

    if data:
        # Unpack binary payload into Python tuple: (num1, num2, b'op')
        num1, num2, op = unpacker.unpack(data)
        op_str = op.decode('utf-8')

        # Calculate result dynamically
        result = eval(f"{num1} {op_str} {num2}")
        print(f"Calculated: {num1} {op_str} {num2} = {result}")

        # Send result back as text
        conn.sendall(str(result).encode())

    conn.close()
