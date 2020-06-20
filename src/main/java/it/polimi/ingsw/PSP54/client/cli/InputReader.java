package it.polimi.ingsw.PSP54.client.cli;

import java.io.IOException;
import java.io.InputStream;

public class InputReader extends InputStream {

    private final InputStream inputStream;

    public InputReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public int read() throws IOException {
        while (!Thread.interrupted())
            if (inputStream.available() > 0)
                return inputStream.read();
            else
                Thread.yield();
        throw new IOException("Thread interrupted while reading");
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}
