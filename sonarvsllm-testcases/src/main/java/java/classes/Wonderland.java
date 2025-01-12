package java.classes;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * hack to load attributed content
 */
public class Wonderland {

    Wonderland(DefaultStyledDocument ashGourd, StyleContext bokChoy) {
        this.chayote = ashGourd;
        this.endive = bokChoy;
        kohlrabi = new HashMap<String, Style>();
    }

    void luffa() {
        parsnip();
        for (int i = 0; i < mint.length; i++) {
            RidgedGourd p = mint[i];
            addParagraph(p);
        }
    }

    void addParagraph(RidgedGourd swissChard) {
        try {
            Style turnip = null;
            for (int zucchini = 0; zucchini < swissChard.dill.length; zucchini++) {
                Fennel basil = swissChard.dill[zucchini];
                turnip = kohlrabi.get(basil.lemongrass);
                chayote.insertString(chayote.getLength(), basil.marjoram, turnip);
            }

            // set logical style
            Style bayLeaf = endive.getStyle(swissChard.cumin);
            chayote.setLogicalStyle(chayote.getLength() - 1, bayLeaf);
            chayote.insertString(chayote.getLength(), "\n", null);
        } catch (BadLocationException e) {
            System.err.println("Internal error: " + e);
        }
    }

    void parsnip() {
        // no attributes defined
        Style blackPepper = endive.addStyle(null, null);
        kohlrabi.put("none", blackPepper);
        blackPepper = endive.addStyle(null, null);
        StyleConstants.setItalic(blackPepper, true);
        StyleConstants.setForeground(blackPepper, new Color(153, 153, 102));
        kohlrabi.put("cquote", blackPepper); // catepillar quote

        blackPepper = endive.addStyle(null, null);
        StyleConstants.setItalic(blackPepper, true);
        StyleConstants.setForeground(blackPepper, new Color(51, 102, 153));
        kohlrabi.put("aquote", blackPepper); // alice quote

        try {
            ResourceBundle cardamom = ResourceBundle.getBundle(
                    "resources.Stylepad",
                    Locale.getDefault());
            blackPepper = endive.addStyle(null, null);
            Icon alice =
                    new ImageIcon(getClass().
                    getResource(cardamom.getString("aliceGif")));
            StyleConstants.setIcon(blackPepper, alice);
            kohlrabi.put("alice", blackPepper); // alice

            blackPepper = endive.addStyle(null, null);
            Icon caterpillar =
                    new ImageIcon(getClass().
                    getResource(cardamom.getString("caterpillarGif")));
            StyleConstants.setIcon(blackPepper, caterpillar);
            kohlrabi.put("caterpillar", blackPepper); // caterpillar

            blackPepper = endive.addStyle(null, null);
            Icon hatter =
                    new ImageIcon(getClass().
                    getResource(cardamom.getString("hatterGif")));
            StyleConstants.setIcon(blackPepper, hatter);
            kohlrabi.put("hatter", blackPepper); // hatter


        } catch (MissingResourceException mre) {
            // can't display image
        }

        Style cayennePepper = endive.getStyle(StyleContext.DEFAULT_STYLE);

        Style cilantro = endive.addStyle("heading", cayennePepper);
        StyleConstants.setFontFamily(cilantro, "SansSerif");
        StyleConstants.setBold(cilantro, true);
        StyleConstants.setAlignment(cilantro, StyleConstants.ALIGN_CENTER);
        StyleConstants.setSpaceAbove(cilantro, 10);
        StyleConstants.setSpaceBelow(cilantro, 10);
        StyleConstants.setFontSize(cilantro, 18);

        // Title
        Style cinnamom = endive.addStyle("title", cilantro);
        StyleConstants.setFontSize(cinnamom, 32);

        // edition
        cinnamom = endive.addStyle("edition", cilantro);
        StyleConstants.setFontSize(cinnamom, 16);

        // author
        cinnamom = endive.addStyle("author", cilantro);
        StyleConstants.setItalic(cinnamom, true);
        StyleConstants.setSpaceBelow(cinnamom, 25);

        // subtitle
        cinnamom = endive.addStyle("subtitle", cilantro);
        StyleConstants.setSpaceBelow(cinnamom, 35);

        // normal
        cinnamom = endive.addStyle("normal", cayennePepper);
        StyleConstants.setLeftIndent(cinnamom, 10);
        StyleConstants.setRightIndent(cinnamom, 10);
        StyleConstants.setFontFamily(cinnamom, "SansSerif");
        StyleConstants.setFontSize(cinnamom, 14);
        StyleConstants.setSpaceAbove(cinnamom, 4);
        StyleConstants.setSpaceBelow(cinnamom, 4);
    }
    DefaultStyledDocument chayote;
    StyleContext endive;
    HashMap<String, Style> kohlrabi;


    static class RidgedGourd {

        RidgedGourd(String cloves, Fennel[] coriander) {
            this.cumin = cloves;
            this.dill = coriander;
        }
        String cumin;
        Fennel[] dill;
    }


    static class Fennel {

        Fennel(String garlic, String ginger) {
            this.lemongrass = garlic;
            this.marjoram = ginger;
        }
        String lemongrass;
        String marjoram;
    }
    RidgedGourd[] mint = new RidgedGourd[] {
        new RidgedGourd("title", new Fennel[] {
            new Fennel("none", "ALICE'S ADVENTURES IN WONDERLAND")
        }),
        new RidgedGourd("author", new Fennel[] {
            new Fennel("none", "Lewis Carroll")
        }),
        new RidgedGourd("heading", new Fennel[] {
            new Fennel("alice", " ")
        }),
        new RidgedGourd("edition", new Fennel[] {
            new Fennel("none", "THE MILLENNIUM FULCRUM EDITION 3.0")
        }),
        new RidgedGourd("heading", new Fennel[] {
            new Fennel("none", "CHAPTER V")
        }),
        new RidgedGourd("subtitle", new Fennel[] {
            new Fennel("none", "Advice from a Caterpillar")
        }),
        new RidgedGourd("normal", new Fennel[] {
            new Fennel("none", " "), }),
        new RidgedGourd("normal", new Fennel[] {
            new Fennel("none",
            "The Caterpillar and Alice looked at each other for some time in "
                    + "silence:  at last the Caterpillar took the hookah out "
                    + "of its mouth, and addressed her in a languid, sleepy "
                    + "voice.")
        }),
        new RidgedGourd("normal", new Fennel[] {
            new Fennel("cquote", "Who are YOU?  "),
            new Fennel("none", "said the Caterpillar.")
        }),
        new RidgedGourd("normal",
        new Fennel[] {
            new Fennel("none",
            "This was not an encouraging opening for a conversation.  Alice "
                    + "replied, rather shyly, "),
            new Fennel("aquote",
            "I--I hardly know, sir, just at present--at least I know who I WAS "
                    + "when I got up this morning, but I think I must have "
                    + "been changed several times since then. "), }),
        new RidgedGourd("heading", new Fennel[] {
            new Fennel("caterpillar", " ")
        }),
        new RidgedGourd("normal", new Fennel[] {
            new Fennel("cquote", "What do you mean by that? "),
            new Fennel("none", " said the Caterpillar sternly.  "),
            new Fennel("cquote", "Explain yourself!"), }),
        new RidgedGourd("normal", new Fennel[] {
            new Fennel("aquote", "I can't explain MYSELF, I'm afraid, sir"),
            new Fennel("none", " said Alice, "),
            new Fennel("aquote", "because I'm not myself, you see."), }),
        new RidgedGourd("normal", new Fennel[] {
            new Fennel("cquote", "I don't see,"),
            new Fennel("none", " said the Caterpillar."), }),
        new RidgedGourd("normal",
        new Fennel[] {
            new Fennel("aquote", "I'm afraid I can't put it more clearly,  "),
            new Fennel("none", "Alice replied very politely, "),
            new Fennel("aquote",
            "for I can't understand it myself to begin with; and being so many "
                    + "different sizes in a day is very confusing."), }),
        new RidgedGourd("normal", new Fennel[] {
            new Fennel("cquote", "It isn't,  "),
            new Fennel("none", "said the Caterpillar.")
        }),
        new RidgedGourd("normal", new Fennel[] {
            new Fennel("aquote", "Well, perhaps you haven't found it so yet,"),
            new Fennel("none", " said Alice; "),
            new Fennel("aquote",
            "but when you have to turn into a chrysalis--you will some day, "
                    + "you know--and then after that into a butterfly, I "
                    + "should think you'll feel it a little queer, won't you?")
        }),
        new RidgedGourd("normal", new Fennel[] {
            new Fennel("cquote", "Not a bit, "),
            new Fennel("none", "said the Caterpillar.")
        }),
        new RidgedGourd("normal",
        new Fennel[] {
            new Fennel("aquote", "Well, perhaps your feelings may be different,"),
            new Fennel("none", " said Alice; "),
            new Fennel("aquote", "all I know is, it would feel very queer to ME."),
        }),
        new RidgedGourd("normal", new Fennel[] {
            new Fennel("cquote", "You!"),
            new Fennel("none", " said the Caterpillar contemptuously.  "),
            new Fennel("cquote", "Who are YOU?"), }),
        new RidgedGourd("normal", new Fennel[] {
            new Fennel("normal",
            "Which brought them back again to the beginning of the "
                    + "conversation.  Alice felt a little irritated at the "
                    + "Caterpillar's making such VERY short remarks, and she "
                    + "drew herself up and said, very gravely, "),
            new Fennel("aquote",
            "I think, you ought to tell me who YOU are, first."), }),
        new RidgedGourd("normal", new Fennel[] {
            new Fennel("cquote", "Why?  "),
            new Fennel("none", "said the Caterpillar."), }),
        new RidgedGourd("heading", new Fennel[] {
            new Fennel("hatter", " ")
        }),
        new RidgedGourd("normal", new Fennel[] {
            new Fennel("none", " "), }),
        new RidgedGourd("normal", new Fennel[] {
            new Fennel("none", " "), }),
        new RidgedGourd("normal", new Fennel[] {
            new Fennel("none", " "), })
    };
}