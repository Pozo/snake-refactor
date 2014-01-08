package com.epam.snake.toplist;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class TopListReader {
    private ArrayList<Toplist> lista = new ArrayList<Toplist>();
    
    private static final String FILE_NAME = "toplista.ser";

    @SuppressWarnings("unchecked")
    void read() {
        // A fájl megnyitása
        try {
            InputStream file = new FileInputStream(FILE_NAME);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput in;
            in = new ObjectInputStream(buffer);

            // A fájl tartalmának bemásolása a lista ArrayListbe
            lista = (ArrayList<Toplist>) in.readObject();

            // A fájl bezárása
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void fajlbairas() {
        // A fájl megnyitása
        try {
            OutputStream file = new FileOutputStream(FILE_NAME);

            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput out;
            out = new ObjectOutputStream(buffer);

            // A lista ArrayList fájlba írása
            out.writeObject(lista);

            // A fájl bezárása
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Toplist> getLista() {
        return lista;
    }
}
