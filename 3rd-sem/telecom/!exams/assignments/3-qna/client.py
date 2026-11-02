import socket
import sys
import struct
import time

def send_query(sock, struct_fmt, op_char, number):
    """Packs and sends query struct, returning parsed response from server."""
    msg = struct.pack(struct_fmt, op_char.encode('ascii'), number)
    sock.sendall(msg)

    data = sock.recv(struct.calcsize(struct_fmt))
    if not data or len(data) < struct.calcsize(struct_fmt):
        return None, None

    resp_op_byte, resp_num = struct.unpack(struct_fmt, data)
    return resp_op_byte.decode('ascii'), resp_num

def main():
    if len(sys.argv) < 3:
        print("Usage: python3 client.py <hostname> <port_number>")
        sys.exit(1)

    host = sys.argv[1]
    port = int(sys.argv[2])

    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.connect((host, port))
    print(f"[CLIENT] Connected to server at {host}:{port}")

    struct_fmt = "ci"
    low = 1
    high = 100

    try:
        while low <= high:
            mid = (low + high) // 2

            print(f"[CLIENT] Asking ('=', {mid})")
            resp_op, _ = send_query(client_socket, struct_fmt, '=', mid)

            if resp_op is None or resp_op in ['Y', 'K', 'V']:
                print(f"[CLIENT] Received termination code '{resp_op}'. Disconnecting.")
                break

            print(f"[CLIENT] Asking ('>', {mid})")
            resp_op, _ = send_query(client_socket, struct_fmt, '>', mid)

            if resp_op is None or resp_op in ['Y', 'K', 'V']:
                print(f"[CLIENT] Received termination code '{resp_op}'. Disconnecting.")
                break
            elif resp_op == 'I':
                low = mid + 1
            elif resp_op == 'N':
                high = mid - 1

            time.sleep(0.05)

    finally:
        client_socket.close()
        print("[CLIENT] Connection closed.")

if __name__ == "__main__":
    main()