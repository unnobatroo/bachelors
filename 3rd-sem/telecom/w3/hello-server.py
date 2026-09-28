"""
Practice 3 - Exercise 1: TCP Server
Task: Receive 'Hello server' from client and reply with 'Hello client'.
Usage: python3 server.py <port>
"""

import socket
import sys

port = int(sys.argv[1])

# Context manager guarantees cleanup when exiting
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server:
    server.bind(('localhost', port))
    server.listen(1)

    while True:
        conn, _ = server.accept()
        with conn:
            data = conn.recv(1024)
            if data:
                print("Received:", data.decode())
                conn.sendall(b"Hello client")
