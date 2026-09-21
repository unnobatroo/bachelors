import socket
import struct
import sys

packer = struct.Struct("20s i")

if len(sys.argv) == 1:
    print(socket.gethostname())

else:
    command = sys.argv[1]
    index = int(sys.argv[2])

    with open("domains.bin", "rb") as file:
        file.seek(packer.size * index)
        data = file.read(packer.size)
        domain_bytes, port = packer.unpack(data)
        domain = domain_bytes.decode()

        if command == "domain":
            print(domain, socket.gethostbyname(domain))

        elif command == "port":
            print(port, socket.getservbyport(port))
