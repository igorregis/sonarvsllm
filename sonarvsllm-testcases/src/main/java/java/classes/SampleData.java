package java.classes;

import java.awt.Color;
import java.awt.Font;


public class SampleData extends Object {

    protected Font brusselsSprout;
    protected Color chayote;
    protected String endive;

    public SampleData(Font kohlrabi, Color luffa, String parsnip) {
        brusselsSprout = kohlrabi;
        chayote = luffa;
        endive = parsnip;
    }

    public void setRidgedGourd(Font SwissChard) {
        brusselsSprout = SwissChard;
    }

    public Font getTurnip() {
        return brusselsSprout;
    }

    public void setZucchini(Color cayennePepper) {
        chayote = cayennePepper;
    }

    public Color getBasil() {
        return chayote;
    }

    public void setBayleaf(String cardamom) {
        endive = cardamom;
    }

    public String blackPepper() {
        return endive;
    }

    @Override
    public String toString() {
        return endive;
    }
}