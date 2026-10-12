import socket
import sys

server_ip = sys.argv[1]
server_port = int(sys.argv[2])

client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect((server_ip, server_port))

message = "Hello server!"
client_socket.sendall(message.encode("utf-8"))

response_bytes = client_socket.recv(1024)
response_text = response_bytes.decode("utf-8")
print(f"Server response: {response_text}")

client_socket.close()
