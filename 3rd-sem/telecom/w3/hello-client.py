"""
Practice 3 - Exercise 1: TCP Client
Task: Write a simple client-server application where the client writes
'Hello server' to the server and the server responds with 'Hello client'.

Usage: python3 client.py <port>
"""

import socket
import sys

# 1. Read port number from command line argument
port = int(sys.argv[1])

# 2. Create an IPv4 (AF_INET) TCP (SOCK_STREAM) socket
client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# 3. Connect to the server running on localhost at the target port
client.connect(('localhost', port))

# 4. Send encoded string message to server
client.sendall("Hello server".encode())

# 5. Receive response from server and print decoded text
data = client.recv(1024)
print("Response:", data.decode())

# 6. Close socket connection
client.close()
