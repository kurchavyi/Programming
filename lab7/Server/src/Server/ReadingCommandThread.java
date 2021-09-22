package Server;
import data.TransferWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ReadingCommandThread extends Thread {
    private final SocketChannel socketChanel;
    private TransferWrapper command;

    public ReadingCommandThread(SocketChannel socketChanel) {
        this.socketChanel = socketChanel;
    }

    @Override
    public void run() {
        this.command = read();
    }


    private TransferWrapper read() {
        TransferWrapper transferWrapper = null;
        ByteBuffer bufferRead = ByteBuffer.allocate(2048);
        try {
            socketChanel.read(bufferRead);
        } catch (IOException e) {
            return null;
        }
        try {
            transferWrapper = deserialize(bufferRead);
            bufferRead.clear();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return transferWrapper;
    }

    public TransferWrapper getCommand() {
        return this.command;
    }

    private TransferWrapper deserialize(ByteBuffer byteBuffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream;
        TransferWrapper transferWrapper;
        try {
            byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            transferWrapper = (TransferWrapper) objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
            byteBuffer.clear();
        } catch (StreamCorruptedException e) {
            return null;
        }
        return transferWrapper;
    }
}
