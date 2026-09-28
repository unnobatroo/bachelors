"""
Practice 3 - Exercise 1: TCP Server
Task: Write a simple client-server application where the client writes
'Hello server' to the server and the server responds with 'Hello client'.

Usage: python3 server.py <port>
"""

import socket
import sys

# 1. Read port number from command line argument
port = int(sys.argv[1])

# 2. Create an IPv4 (AF_INET) TCP (SOCK_STREAM) socket
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# 3. Bind socket to localhost and specified port
server.bind(('localhost', port))

# 4. Set socket to listening mode for incoming connection requests
server.listen(1)

# 5. Handle connections in a loop
while True:
    conn, client_addr = server.accept()  # Wait and accept connection
    data = conn.recv(1024)               # Receive up to 1024 bytes

    if data:
        print("Received:", data.decode())
        conn.sendall("Hello client".encode())  # Send response back

    conn.close()  # Close the active client connection
