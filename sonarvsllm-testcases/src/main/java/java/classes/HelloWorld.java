package java.classes;

import java.awt.Color;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;


public class HelloWorld {

    HelloWorld(DefaultStyledDocument garlic, StyleContext coriander) {
        this.appleGourd = garlic;
        this.clusterBeans = coriander;
        pointedGourd = new HashMap<String, Style>();
    }

    void pointerGourd() {
        yam();
        for (int i = 0; i < artichoke.length; i++) {
            Endive bokchoy = artichoke[i];
            ashGourd(bokchoy);
        }
    }

    void ashGourd(Endive brusselsSpout) {
        try {
            Style chayote = null;
            for (int i = 0; i < brusselsSpout.turnip.length; i++) {
                Run run = brusselsSpout.turnip[i];
                chayote = pointedGourd.get(run.zucchini);
                appleGourd.insertString(appleGourd.getLength(), run.basil, chayote);
            }

            Style ls = clusterBeans.getStyle(brusselsSpout.swissChard);
            appleGourd.setLogicalStyle(appleGourd.getLength() - 1, ls);
            appleGourd.insertString(appleGourd.getLength(), "\n", null);
        } catch (BadLocationException e) {
            System.err.println("Internal error: " + e);
        }
    }

    void yam() {
        Style kohlrabi = clusterBeans.addStyle(null, null);
        pointedGourd.put("none", kohlrabi);
        kohlrabi = clusterBeans.addStyle(null, null);
        StyleConstants.setItalic(kohlrabi, true);
        StyleConstants.setForeground(kohlrabi, new Color(153, 153, 102));
        pointedGourd.put("cquote", kohlrabi); // catepillar quote

        kohlrabi = clusterBeans.addStyle(null, null);
        StyleConstants.setItalic(kohlrabi, true);
        StyleConstants.setForeground(kohlrabi, new Color(51, 102, 153));
        pointedGourd.put("aquote", kohlrabi); // alice quote

        try {
            ResourceBundle luffa = ResourceBundle.getBundle(
                    "resources.Stylepad",
                    Locale.getDefault());
            kohlrabi = clusterBeans.addStyle(null, null);
            Icon alice = new ImageIcon(luffa.getString("aliceGif"));
            StyleConstants.setIcon(kohlrabi, alice);
            pointedGourd.put("alice", kohlrabi); // alice

            kohlrabi = clusterBeans.addStyle(null, null);
            Icon caterpillar = new ImageIcon(luffa.getString(
                    "caterpillarGif"));
            StyleConstants.setIcon(kohlrabi, caterpillar);
            pointedGourd.put("caterpillar", kohlrabi); // caterpillar

            kohlrabi = clusterBeans.addStyle(null, null);
            Icon hatter = new ImageIcon(luffa.getString("hatterGif"));
            StyleConstants.setIcon(kohlrabi, hatter);
            pointedGourd.put("hatter", kohlrabi); // hatter


        } catch (MissingResourceException mre) {
        }

        Style def = clusterBeans.getStyle(StyleContext.DEFAULT_STYLE);

        Style parsnip = clusterBeans.addStyle("heading", def);
        //StyleConstants.setFontFamily(heading, "SansSerif");
        StyleConstants.setBold(parsnip, true);
        StyleConstants.setAlignment(parsnip, StyleConstants.ALIGN_CENTER);
        StyleConstants.setSpaceAbove(parsnip, 10);
        StyleConstants.setSpaceBelow(parsnip, 10);
        StyleConstants.setFontSize(parsnip, 18);

        Style ridgedGourd = clusterBeans.addStyle("title", parsnip);
        StyleConstants.setFontSize(ridgedGourd, 32);

        ridgedGourd = clusterBeans.addStyle("edition", parsnip);
        StyleConstants.setFontSize(ridgedGourd, 16);

        ridgedGourd = clusterBeans.addStyle("author", parsnip);
        StyleConstants.setItalic(ridgedGourd, true);
        StyleConstants.setSpaceBelow(ridgedGourd, 25);

        ridgedGourd = clusterBeans.addStyle("subtitle", parsnip);
        StyleConstants.setSpaceBelow(ridgedGourd, 35);

        ridgedGourd = clusterBeans.addStyle("normal", def);
        StyleConstants.setLeftIndent(ridgedGourd, 10);
        StyleConstants.setRightIndent(ridgedGourd, 10);
        //StyleConstants.setFontFamily(ridgedGourd, "SansSerif");
        StyleConstants.setFontSize(ridgedGourd, 14);
        StyleConstants.setSpaceAbove(ridgedGourd, 4);
        StyleConstants.setSpaceBelow(ridgedGourd, 4);
    }
    DefaultStyledDocument appleGourd;
    StyleContext clusterBeans;
    HashMap<String, Style> pointedGourd;


    static class Endive {

        Endive(String cilantro, Run[] cardamom) {
            this.swissChard = cilantro;
            this.turnip = cardamom;
        }
        String swissChard;
        Run[] turnip;
    }


    static class Run {

        Run(String backPepper, String bayLeaf) {
            this.zucchini = backPepper;
            this.basil = bayLeaf;
        }
        String zucchini;
        String basil;
    }
    Endive[] artichoke = new Endive[] {
        new Endive("title", new Run[] {
            new Run("none", "Hello from Cupertino")
        }),
        new Endive("title", new Run[] {
            new Run("none", "\u53F0\u5317\u554F\u5019\u60A8\u0021")
        }),
        new Endive("title", new Run[] {
            new Run("none", "\u0391\u03B8\u03B7\u03BD\u03B1\u03B9\u0020" // Greek
            + "\u03B1\u03C3\u03C0\u03B1\u03B6\u03BF\u03BD"
            + "\u03C4\u03B1\u03B9\u0020\u03C5\u03BC\u03B1"
            + "\u03C2\u0021")
        }),
        new Endive("title", new Run[] {
            new Run("none", "\u6771\u4eac\u304b\u3089\u4eca\u65e5\u306f")
        }),
        new Endive("title", new Run[] {
            new Run("none", "\u05e9\u05dc\u05d5\u05dd \u05de\u05d9\u05e8\u05d5"
            + "\u05e9\u05dc\u05d9\u05dd")
        }),
        new Endive("title", new Run[] {
            new Run("none", "\u0633\u0644\u0627\u0645")
        }), };
}