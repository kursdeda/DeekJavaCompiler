package ru.kursdeda.circles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.kursdeda.objects.Token;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Lexer {

    /* Data*/

    private final String input;
    private final StringBuilder buffer = new StringBuilder();
    private final List<Token> output = new ArrayList<>();
    private int pos;

    /* Main */

    public List<Token> tokenize() {
        while (hasChar())
            iterate(pop());

        write();
        return output;
    }

    private void iterate(char c) {
        if (Character.isWhitespace(c))
            write();
        else if (Token.Type.SYMBOLS.containsKey(String.valueOf(c))) {
            write();
            buffer.append(c);
            write();
        } else buffer.append(c);
    }

    /* Utils */

    private void write() {
        if (buffer.isEmpty()) return;
        output.add(new Token(Token.Type.fromString(buffer.toString()), buffer.toString()));
        buffer.setLength(0);
    }

    private boolean hasChar() {
        return pos < input.length();
    }

    private char pop() {
        return input.charAt(pos++);
    }

}