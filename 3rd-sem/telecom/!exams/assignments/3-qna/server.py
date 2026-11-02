import socket
import select
import sys
import struct
import random

def main():
    if len(sys.argv) < 3:
        print("Usage: python3 server.py <hostname> <port_number>")
        sys.exit(1)

    host = sys.argv[1]
    port = int(sys.argv[2])

    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server_socket.bind((host, port))
    server_socket.listen(5)

    secret_number = random.randint(1, 100)
    print(f"[SERVER] Listening on {host}:{port}. Secret number: {secret_number}")

    sockets_list = [server_socket]
    game_over = False

    struct_fmt = "ci"
    struct_size = struct.calcsize(struct_fmt)

    try:
        while True:
            read_sockets, _, _ = select.select(sockets_list, [], [])

            for notified_socket in read_sockets:
                if notified_socket == server_socket:
                    client_socket, client_address = server_socket.accept()
                    sockets_list.append(client_socket)
                    print(f"[SERVER] Client connected from {client_address}")

                else:
                    try:
                        data = notified_socket.recv(struct_size)
                        
                        if not data or len(data) < struct_size:
                            print("[SERVER] Client disconnected.")
                            sockets_list.remove(notified_socket)
                            notified_socket.close()
                            continue

                        op_bytes, number = struct.unpack(struct_fmt, data)
                        op = op_bytes.decode('ascii')
                        print(f"[SERVER] Received query: ('{op}', {number})")

                        if game_over:
                            resp = struct.pack(struct_fmt, b'V', 0)
                            notified_socket.sendall(resp)
                        else:
                            if op == '=':
                                if number == secret_number:
                                    game_over = True
                                    print(f"[SERVER] Correct guess ({number})! Client won.")
                                    resp = struct.pack(struct_fmt, b'Y', 0) # Y: Win
                                else:
                                    resp = struct.pack(struct_fmt, b'N', 0) # N: No
                                notified_socket.sendall(resp)

                            elif op == '>':
                                if secret_number > number:
                                    resp = struct.pack(struct_fmt, b'I', 0) # I: Yes
                                else:
                                    resp = struct.pack(struct_fmt, b'N', 0) # N: No
                                notified_socket.sendall(resp)

                            elif op == '<':
                                if secret_number < number:
                                    resp = struct.pack(struct_fmt, b'I', 0) # I: Yes
                                else:
                                    resp = struct.pack(struct_fmt, b'N', 0) # N: No
                                notified_socket.sendall(resp)

                            else:
                                resp = struct.pack(struct_fmt, b'N', 0)
                                notified_socket.sendall(resp)

                    except Exception as e:
                        print(f"[SERVER] Client error: {e}")
                        if notified_socket in sockets_list:
                            sockets_list.remove(notified_socket)
                        notified_socket.close()

    except KeyboardInterrupt:
        print("\n[SERVER] Server stopping.")
    finally:
        server_socket.close()

if __name__ == "__main__":
    main()