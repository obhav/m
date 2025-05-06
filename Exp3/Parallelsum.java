import mpi.*;

public class Parallelsum {
    public static void main(String[] args) throws MPIException {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int arr[] = {1,2,3,4,5,6,7,8,9,10};
        int localsum = 0;
        int recvBuffer[] = new int[1];

        int chunkSize = arr.length / size;
        int remainder = arr.length % size;

        
        int startindex = rank * chunkSize + Math.min(rank, remainder);
        int endindex = startindex + chunkSize + (rank < remainder ? 1 : 0);

        for (int i = startindex; i < endindex; i++) {
            localsum += arr[i];
        }

        System.out.println("Process : " + rank + " intermediate sum : " + localsum);

        MPI.COMM_WORLD.Reduce(new int[]{localsum}, 0, recvBuffer, 0, 1, MPI.INT, MPI.SUM, 0);

        if (rank == 0) {
            System.out.println("Final Sum : " + recvBuffer[0]);
        }

        MPI.Finalize();
    }
}

