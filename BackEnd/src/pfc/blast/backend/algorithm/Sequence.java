package pfc.blast.backend.algorithm;

import java.util.Arrays;

/**
 * Class Sequence is the abstract base class for a biological sequence.
 * 
 * In a program, a sequence is represented as a byte array (type
 * byte[]). For a sequence of length L>, the byte array contains
 * L+1 bytes. The byte at index 0 is unused and contains a value of -1.
 * The bytes at indexes 1 through L contain the elements of the sequence.
 * These are generally represented as small integers starting at 0, not as
 * characters.
 *
 * @author  Juan Carlos Ruiz Rico
 * @version 02-Nov-2013
 */

public abstract class Sequence {

    // Hidden data members.

    protected String myDescription;
    protected byte[] mySequence;
    protected int myLength;

    // Exported constructors.

    /**
     * Construct a new sequence.
     */
    public Sequence() {
    }

    // Exported operations.

    /**
     * Get this sequence's description.
     *
     * @return  Description string.
     */
    public String description() {
        return myDescription;
    }

    /**
     * Get this sequence's length L. This is the number of elements in
     * this sequence.
     *
     * @return  Length L.
     */
    public int length() {
        return myLength;
    }

    /**
     * Get this sequence's elements. The return value is a byte array of length
     * L+1. The byte at index 0 is unused and contains a value of -1. The
     * bytes at indexes 1 through L contain the elements.
     * 
     * Note: Do not alter the contents of the returned byte array.
     *
     * @return  Array of elements.
     */
    public byte[] sequence() {
        return mySequence;
    }

    /**
     * Determine if this sequence is equal to the given object. Two sequences
     * are equal if they have the same elements.
     *
     * @param  obj  Object to test.
     *
     * @return  True if obj is equal to this sequence, false otherwise.
     */
    public boolean equals(Object obj) {
        return (obj instanceof Sequence) &&
            Arrays.equals(this.mySequence, ((Sequence)obj).mySequence);
    }

    /**
     * Returns a hash code for this sequence.
     *
     * @return  Hash code.
     */
    public int hashCode() {
        return Arrays.hashCode(mySequence);
    }

    /**
     * Returns a character version of this sequence's element at the given
     * index.
     *
     * @param  i  Index in the range 1 .. L.
     *
     * @return  Character corresponding to element i.
     */
    public abstract char charAt(int i);

    /**
     * Returns a string version of this sequence's elements.
     *
     * @return  String version.
     */
    public String elementsToString() {
        char[] c = new char[myLength];
        for (int i = 0; i < myLength; ++i) {
            c[i] = charAt(i + 1);
        }
        return new String(c);
    }


}
