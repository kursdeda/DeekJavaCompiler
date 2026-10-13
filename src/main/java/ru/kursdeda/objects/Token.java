package ru.kursdeda.objects;

import java.util.Locale;
import java.util.Map;

public record Token(Type type, String value) {
    public enum Type {
        IDENTIFIER, NUMBER, NULL,
        SPACE, HALT, RET,
        VOID, BYTE, WORD, INT, LONG, FLOAT, DUAL,
        PLUS, MINUS, STAR, SLASH, PERCENT, EQUAL,
        OPEN_PARENTHESIS, CLOSE_PARENTHESIS, OPEN_BRACE, CLOSE_BRACE, OPEN_BRACKET, CLOSE_BRACKET, LEFT_ARROW, RIGHT_ARROW,
        COLON, SEMICOLON, DOT, COMMA,
        EOF;

        public static final Map<String, Type> SYMBOLS = Map.ofEntries(
                Map.entry("+", PLUS),
                Map.entry("-", MINUS),
                Map.entry("*", STAR),
                Map.entry("/", SLASH),
                Map.entry("%", PERCENT),
                Map.entry("=", EQUAL),
                Map.entry("(", OPEN_PARENTHESIS),
                Map.entry(")", CLOSE_PARENTHESIS),
                Map.entry("{", OPEN_BRACE),
                Map.entry("}", CLOSE_BRACE),
                Map.entry("[", OPEN_BRACKET),
                Map.entry("]", CLOSE_BRACKET),
                Map.entry(":", COLON),
                Map.entry(";", SEMICOLON),
                Map.entry(".", DOT),
                Map.entry(",", COMMA)
        );

        public static final Type[] KEYWORDS = {
                SPACE, NULL, HALT, RET, VOID, BYTE, WORD, INT, LONG, FLOAT, DUAL
        };
        public static final Type[] DATATYPE = {
                BYTE, WORD, INT, LONG, FLOAT, DUAL
        };

        public static Type fromString(String s) {
            if (s.matches("\\d+")) return NUMBER;
            if (SYMBOLS.containsKey(s)) return SYMBOLS.get(s);
            for (Type keyword : KEYWORDS)
                if (keyword.name().toLowerCase(Locale.ROOT).equals(s)) return keyword;
            return IDENTIFIER;
        }
    }
}
