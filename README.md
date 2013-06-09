DX-Source-Translator
====================

Java translator for textdomain-based stuff in all languages

This is a helper Java tool for generating .po files in a directory based on some conditions. It relies heavily 
on xgettext so UNIX environment is expected.

That's the basic view for the translator:

![Screenshot](https://github.com/mpeshev/DX-Source-Translator/blob/master/dx-source-translator-image.png?raw=true)

You can pass the input folder for the project, output path to messages.po and tune some changes.

Extension and language are added on purpose - for example, some PHP versions are not compiled with gettext support,
but the C++ language would handle this if your calls are with double quotes.
