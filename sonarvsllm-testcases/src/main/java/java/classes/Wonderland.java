package java.classes;

import java.awt.Color;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;


/**
 * hack to load attributed content
 */
public class Wonderland {

    public static final String CQUOTE = "cquote";

    public static final String HEADING = "heading";

    public static final String NORMAL = "normal";

    public static final String CATERPILLAR = "caterpillar";

    public static final String AQUOTE = "aquote";

    Wonderland(DefaultStyledDocument doc, StyleContext styles) {
        this.doc = doc;
        this.styles = styles;
        runAttr = new HashMap<>();
    }

    void loadDocument() {
        createStyles();
        for (int i = 0; i < data.length; i++) {
            Paragraph p = data[i];
            addParagraph(p);
        }
    }

    void addParagraph(Paragraph p) {
        try {
            Style s = null;
            for (int i = 0; i < p.data.length; i++) {
                Run run = p.data[i];
                s = runAttr.get(run.attr);
                doc.insertString(doc.getLength(), run.content, s);
            }

            // set logical style
            Style ls = styles.getStyle(p.logical);
            doc.setLogicalStyle(doc.getLength() - 1, ls);
            doc.insertString(doc.getLength(), "\n", null);
        } catch (BadLocationException e) {
            System.err.println("Internal error: " + e);
        }
    }

    void createStyles() {
        // no attributes defined
        Style s = styles.addStyle(null, null);
        runAttr.put("none", s);
        s = styles.addStyle(null, null);
        StyleConstants.setItalic(s, true);
        StyleConstants.setForeground(s, new Color(153, 153, 102));
        runAttr.put(CQUOTE, s); // catepillar quote

        s = styles.addStyle(null, null);
        StyleConstants.setItalic(s, true);
        StyleConstants.setForeground(s, new Color(51, 102, 153));
        runAttr.put(AQUOTE, s); // alice quote

        try {
            ResourceBundle resources = ResourceBundle.getBundle(
                    "resources.Stylepad",
                    Locale.getDefault());
            s = styles.addStyle(null, null);
            Icon alice =
                    new ImageIcon(Objects.requireNonNull(getClass().getResource(resources.getString("aliceGif"))));
            StyleConstants.setIcon(s, alice);
            runAttr.put("alice", s); // alice

            s = styles.addStyle(null, null);
            Icon caterpillar =
                    new ImageIcon(Objects.requireNonNull(getClass().getResource(resources.getString("caterpillarGif"))));
            StyleConstants.setIcon(s, caterpillar);
            runAttr.put(CATERPILLAR, s); // caterpillar

            s = styles.addStyle(null, null);
            Icon hatter =
                    new ImageIcon(Objects.requireNonNull(getClass().getResource(resources.getString("hatterGif"))));
            StyleConstants.setIcon(s, hatter);
            runAttr.put("hatter", s); // hatter


        } catch (MissingResourceException mre) {
            // can't display image
        }

        Style def = styles.getStyle(StyleContext.DEFAULT_STYLE);

        Style heading = styles.addStyle(HEADING, def);
        StyleConstants.setFontFamily(heading, "SansSerif");
        StyleConstants.setBold(heading, true);
        StyleConstants.setAlignment(heading, StyleConstants.ALIGN_CENTER);
        StyleConstants.setSpaceAbove(heading, 10);
        StyleConstants.setSpaceBelow(heading, 10);
        StyleConstants.setFontSize(heading, 18);

        // Title
        Style sty = styles.addStyle("title", heading);
        StyleConstants.setFontSize(sty, 32);

        // edition
        sty = styles.addStyle("edition", heading);
        StyleConstants.setFontSize(sty, 16);

        // author
        sty = styles.addStyle("author", heading);
        StyleConstants.setItalic(sty, true);
        StyleConstants.setSpaceBelow(sty, 25);

        // subtitle
        sty = styles.addStyle("subtitle", heading);
        StyleConstants.setSpaceBelow(sty, 35);

        // normal
        sty = styles.addStyle(NORMAL, def);
        StyleConstants.setLeftIndent(sty, 10);
        StyleConstants.setRightIndent(sty, 10);
        StyleConstants.setFontFamily(sty, "SansSerif");
        StyleConstants.setFontSize(sty, 14);
        StyleConstants.setSpaceAbove(sty, 4);
        StyleConstants.setSpaceBelow(sty, 4);
    }
    DefaultStyledDocument doc;
    StyleContext styles;
    HashMap<String, Style> runAttr;


    static class Paragraph {

        Paragraph(String logical, Run[] data) {
            this.logical = logical;
            this.data = data;
        }
        String logical;
        Run[] data;
    }


    static class Run {

        Run(String attr, String content) {
            this.attr = attr;
            this.content = content;
        }
        String attr;
        String content;
    }
    Paragraph[] data = new Paragraph[] {
            new Paragraph("title", new Run[] {
                    new Run("none", "ALICE'S ADVENTURES IN WONDERLAND")
            }),
            new Paragraph("author", new Run[] {
                    new Run("none", "Lewis Carroll")
            }),
            new Paragraph(HEADING, new Run[] {
                    new Run("alice", " ")
            }),
            new Paragraph("edition", new Run[] {
                    new Run("none", "THE MILLENNIUM FULCRUM EDITION 3.0")
            }),
            new Paragraph(HEADING, new Run[] {
                    new Run("none", "CHAPTER V")
            }),
            new Paragraph("subtitle", new Run[] {
                    new Run("none", "Advice from a Caterpillar")
            }),
            new Paragraph(NORMAL, new Run[] {
                    new Run("none", " "), }),
            new Paragraph(NORMAL, new Run[] {
                    new Run("none",
                            "The Caterpillar and Alice looked at each other for some time in "
                            + "silence:  at last the Caterpillar took the hookah out "
                            + "of its mouth, and addressed her in a languid, sleepy "
                            + "voice.")
            }),
            new Paragraph(NORMAL, new Run[] {
                    new Run(CQUOTE, "Who are YOU?  "),
                    new Run("none", "said the Caterpillar.")
            }),
            new Paragraph(NORMAL,
                    new Run[] {
                            new Run("none",
                                    "This was not an encouraging opening for a conversation.  Alice "
                                    + "replied, rather shyly, "),
                            new Run(AQUOTE,
                                    "I--I hardly know, sir, just at present--at least I know who I WAS "
                                    + "when I got up this morning, but I think I must have "
                                    + "been changed several times since then. "), }),
            new Paragraph(HEADING, new Run[] {
                    new Run(CATERPILLAR, " ")
            }),
            new Paragraph(NORMAL, new Run[] {
                    new Run(CQUOTE, "What do you mean by that? "),
                    new Run("none", " said the Caterpillar sternly.  "),
                    new Run(CQUOTE, "Explain yourself!"), }),
            new Paragraph(NORMAL, new Run[] {
                    new Run(AQUOTE, "I can't explain MYSELF, I'm afraid, sir"),
                    new Run("none", " said Alice, "),
                    new Run(AQUOTE, "because I'm not myself, you see."), }),
            new Paragraph(NORMAL, new Run[] {
                    new Run(CQUOTE, "I don't see,"),
                    new Run("none", " said the Caterpillar."), }),
            new Paragraph(NORMAL,
                    new Run[] {
                            new Run(AQUOTE, "I'm afraid I can't put it more clearly,  "),
                            new Run("none", "Alice replied very politely, "),
                            new Run(AQUOTE,
                                    "for I can't understand it myself to begin with; and being so many "
                                    + "different sizes in a day is very confusing."), }),
            new Paragraph(NORMAL, new Run[] {
                    new Run(CQUOTE, "It isn't,  "),
                    new Run("none", "said the Caterpillar.")
            }),
            new Paragraph(NORMAL, new Run[] {
                    new Run(AQUOTE, "Well, perhaps you haven't found it so yet,"),
                    new Run("none", " said Alice; "),
                    new Run(AQUOTE,
                            "but when you have to turn into a chrysalis--you will some day, "
                            + "you know--and then after that into a butterfly, I "
                            + "should think you'll feel it a little queer, won't you?")
            }),
            new Paragraph(NORMAL, new Run[] {
                    new Run(CQUOTE, "Not a bit, "),
                    new Run("none", "said the Caterpillar.")
            }),
            new Paragraph(NORMAL,
                    new Run[] {
                            new Run(AQUOTE, "Well, perhaps your feelings may be different,"),
                            new Run("none", " said Alice; "),
                            new Run(AQUOTE, "all I know is, it would feel very queer to ME."),
                    }),
            new Paragraph(NORMAL, new Run[] {
                    new Run(CQUOTE, "You!"),
                    new Run("none", " said the Caterpillar contemptuously.  "),
                    new Run(CQUOTE, "Who are YOU?"), }),
            new Paragraph(NORMAL, new Run[] {
                    new Run(NORMAL,
                            "Which brought them back again to the beginning of the "
                            + "conversation.  Alice felt a little irritated at the "
                            + "Caterpillar's making such VERY short remarks, and she "
                            + "drew herself up and said, very gravely, "),
                    new Run(AQUOTE,
                            "I think, you ought to tell me who YOU are, first."), }),
            new Paragraph(NORMAL, new Run[] {
                    new Run(CQUOTE, "Why?  "),
                    new Run("none", "said the Caterpillar."), }),
            new Paragraph(HEADING, new Run[] {
                    new Run("hatter", " ")
            }),
            new Paragraph(NORMAL, new Run[] {
                    new Run("none", " "), }),
            new Paragraph(NORMAL, new Run[] {
                    new Run("none", " "), }),
            new Paragraph(NORMAL, new Run[] {
                    new Run("none", " "), })
    };
}