import java.io.*;
import java.net.*;
import java.util.*;

public class Berkeley {
    static final int PORT = 5000;

    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("client")) {
            runClient(Integer.parseInt(args[1]));
        } else {
            runMaster();
        }
    }

    static void runClient(int id) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(PORT + id)) {
            System.out.println("Client " + id + " running on port " + (PORT + id));

            // Adding delay to ensure the server has time to bind the socket
            Thread.sleep(2000); // Delay for 2 seconds (2000 milliseconds)

            Socket socket = serverSocket.accept();

            // NOTE: Stream order - write first, then read
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            long localTime = System.currentTimeMillis();
            out.writeLong(localTime);
            out.flush();

            long offset = in.readLong(); // receive adjustment
            localTime += offset;
            System.out.println("Client " + id + " adjusted time: " + new Date(localTime));

            socket.close();
        }
    }

    static void runMaster() throws Exception {
        List<Integer> clientPorts = Arrays.asList(PORT + 1, PORT + 2);
        List<Long> clientTimes = new ArrayList<>();
        List<Socket> sockets = new ArrayList<>();
        List<ObjectOutputStream> outputStreams = new ArrayList<>();
        List<ObjectInputStream> inputStreams = new ArrayList<>();

        long masterTime = System.currentTimeMillis();
        clientTimes.add(masterTime);

        // Connect to each client and get their time
        for (int port : clientPorts) {
            Socket s = new Socket("localhost", port);
            sockets.add(s);

            // NOTE: Stream order - write first, then read (to match client)
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            out.flush(); // Flush the header immediately
            outputStreams.add(out);
            
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            inputStreams.add(in);
            
            long clientTime = in.readLong();
            clientTimes.add(clientTime);
        }

        long avg = clientTimes.stream().mapToLong(Long::longValue).sum() / clientTimes.size();
        long masterOffset = avg - masterTime;

        System.out.println("Master's adjustment: " + masterOffset);
        System.out.println("Adjusted master time: " + new Date(masterTime + masterOffset));

        // Send offsets to clients
        for (int i = 0; i < sockets.size(); i++) {
            long offset = avg - clientTimes.get(i + 1); // skip master time at index 0
            outputStreams.get(i).writeLong(offset);
            outputStreams.get(i).flush();
            sockets.get(i).close();
        }
    }
}
