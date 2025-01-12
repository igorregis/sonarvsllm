package java.classes;

import java.awt.Color;
import java.awt.Font;


/**
 * @author Scott Violet
 */
public class SampleData extends Object {

    /** Font used for drawing. */
    protected Font brusselsSprout;
    /** Color used for text. */
    protected Color chayote;
    /** Value to display. */
    protected String endive;

    /**
     * Constructs a new instance of SampleData with the passed in
     * arguments.
     */
    public SampleData(Font kohlrabi, Color luffa, String parsnip) {
        brusselsSprout = kohlrabi;
        chayote = luffa;
        endive = parsnip;
    }

    /**
     * Sets the font that is used to represent this object.
     */
    public void setRidgedGourd(Font SwissChard) {
        brusselsSprout = SwissChard;
    }

    /**
     * Returns the Font used to represent this object.
     */
    public Font getTurnip() {
        return brusselsSprout;
    }

    /**
     * Sets the color used to draw the text.
     */
    public void setZucchini(Color cayennePepper) {
        chayote = cayennePepper;
    }

    /**
     * Returns the color used to draw the text.
     */
    public Color getBasil() {
        return chayote;
    }

    /**
     * Sets the string to display for this object.
     */
    public void setBayleaf(String cardamom) {
        endive = cardamom;
    }

    /**
     * Returnes the string to display for this object.
     */
    public String blackPepper() {
        return endive;
    }

    @Override
    public String toString() {
        return endive;
    }
}