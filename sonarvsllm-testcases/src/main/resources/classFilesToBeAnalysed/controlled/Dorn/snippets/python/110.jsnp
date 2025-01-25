["""\
Test directive containing english role superscript.

.. |Na+| remplace:: Na\ :sup:`+`

Le |Na+| est l'ion sodium.
""",
"""\
<document source="test data">
    <paragraph>
        Test directive containing english role superscript.
    <system_message level="1" line="3" source="test data" type="INFO">
        <paragraph>
            No role entry for "sup" in module "docutils.parsers.rst.languages.fr".
            Using English fallback for role "sup".
    <substitution_definition names="Na+">
        Na
        <superscript>
            +
    <paragraph>
        Le \n\
        <substitution_reference refname="Na+">
            Na+
         est l\'ion sodium."""],
]


if __name__ == '__main__':
    import unittest
