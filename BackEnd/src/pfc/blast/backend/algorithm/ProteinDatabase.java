package pfc.blast.backend.algorithm;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import javax.servlet.ServletContext;

public class ProteinDatabase {

    // Hidden data members.
    private ServletContext context;
    private long numProteins;
    private final static String PATH = "/db/";
    
    private String path2; // borrar
    
    // Exported constructors.
    public ProteinDatabase(File database, File index) {

    }

    /**
     * Construct a new protein sequence database.
     *
     * @param  theDatabaseFile  Protein sequence database file.
     * @param  theIndexFile     Protein sequence index file.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <TT>theDatabaseFile</TT> is null.
     *     Thrown if <TT>theIndexFile</TT> is null.
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public ProteinDatabase(InputStream databaseStream, InputStream indexStream,
                           InputStream indexLengthStream) throws IOException {

    }
    
    // borrar
    public ProteinDatabase(String path){
        context = null;
        this.path2 = path;
        this.numProteins = 6298;
    }
    /**
     * Construct a new protein sequence database.
     *
     * @param  context  The application context.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <TT>context</TT> is null.
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public ProteinDatabase(ServletContext context) throws IOException {
        if(context == null){
            throw new NullPointerException("Context is null.");    
        }
        this.context = context;
        
        // Properties file con el numero de proteinas y la informacion necesaria
        // para el computo.
        InputStream propertiesStream = context.getResourceAsStream(PATH + "data.properties");
        BufferedReader br = new BufferedReader(new InputStreamReader(propertiesStream));
        this.numProteins = Long.parseLong(br.readLine());
    }


    /**
     * Get the number of protein sequences in this protein sequence database.
     *
     * @return  Number of protein sequences, <I>N</I>.
     */
    public long getProteinCount() {
        return this.numProteins;
    }

    /**
     * Get the protein sequence at the given index in this protein sequence
     * database.
     *
     * @param  i  Index in the range 0 &le; <TT>i</TT> &le; <I>N</I>&minus;1.
     *
     * @return  Protein sequence.
     *
     * @exception  IndexOutOfBoundsException
     *     (unchecked exception) Thrown if <TT>i</TT> is out of bounds.
     * @exception  IOException
     *     Thrown if an I/O error occurred.
     */
    public ProteinSequence getProteinSequence(long i) throws IOException {
        if (0 > i || i >= numProteins) {
            throw new IndexOutOfBoundsException("ProteinDatabase.getProteinSequence(): i (= " + i + ") out of bounds");
        }
        String name = Long.toString(i).concat(".fasta");
        InputStream ins ;
        // borrar
        if(context == null){
            ins = new FileInputStream(path2 + name);
        }else{
            ins = context.getResourceAsStream(PATH + name);
        }
        return new ProteinSequence(ins);
    }

}
