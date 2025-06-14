package org.shop.repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileRepository<T extends Serializable>  {

    private final Path txtDir;
    private final Path binDir;

    public FileRepository(Path txtDir, Path binDir) throws IOException {
        this.txtDir = txtDir;
        this.binDir = binDir;

        if(!Files.exists(txtDir)) {
            Files.createDirectories(txtDir);
        }

        if(!Files.exists(binDir)) {
            Files.createDirectories(binDir);
        }
    }

    public void writeTxt(String text, String filename) throws IOException {
        Files.writeString(txtDir.resolve(filename), text);
    }
    public void writeBin(T object, String filename) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(binDir.resolve(filename)))) {
            oos.writeObject(object);
        }
    }

    public String readTxt(String filename) throws IOException {
        return Files.readString(txtDir.resolve(filename));
    }

    public T readBin(String filename) throws IOException, ClassNotFoundException {

        try(ObjectInputStream oos = new ObjectInputStream(Files.newInputStream(binDir.resolve(filename)))) {

            return (T) oos.readObject();
        }
    }

    public boolean doesTxtFileExist(String filename) {
        return Files.exists(txtDir.resolve(filename));
    }

    public boolean doesBinFileExist(String filename) {
        return Files.exists(binDir.resolve(filename));
    }
}
