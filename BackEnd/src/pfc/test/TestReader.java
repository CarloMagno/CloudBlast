package pfc.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TestReader {
    public TestReader() {
        super();
    }
    
    public String readText(String path) throws FileNotFoundException,
                                               IOException {
            File f = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(f));
            return br.readLine();
    }
}
