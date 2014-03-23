package pfc.blast.backend.algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;

public class ProteinDatabase {

    // Hidden data members.
    private ServletContext context;
    private long numProteins;
    private final static String PATH = "/db/";
    
    // Estos constructores con este atributo es para probar el testeo del
    // algoritmo en local.
    private String path2; // borrar
    public ProteinDatabase(File database, File index) {

    }

    public ProteinDatabase(InputStream databaseStream, InputStream indexStream,
                           InputStream indexLengthStream) throws IOException {

    }
    
    public ProteinDatabase(String path){
        context = null;
        this.path2 = path;
        this.numProteins = 6298;
    }
    // Hasta aquí.
    
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
        
        // Properties file con el número de proteinas y la información necesaria
        // para el cómputo.
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

        ins = context.getResourceAsStream(PATH + name);

        return new ProteinSequence(ins);
    }

}
