"""
Practice 3 - Exercise 1: TCP Client
Task: Send 'Hello server' to server and print 'Hello client' response.
Usage: python3 client.py <port>
"""

import socket
import sys

port = int(sys.argv[1])

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as client:
    client.connect(('localhost', port))
    client.sendall(b"Hello server")
    print("Response:", client.recv(1024).decode())
