package ru.kursdeda;

import ru.kursdeda.circles.Lexer;
import ru.kursdeda.circles.Parser;
import ru.kursdeda.objects.Token;
import ru.kursdeda.objects.nodes.Statement;
import ru.kursdeda.patterns.AstDebugger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of(args[0])).replace("\r", "");
        List<Token> tokens = new Lexer(input).tokenize();
        tokens.add(new Token(Token.Type.EOF, ""));
        tokens.forEach(System.out::println);
        var ast = new Parser(tokens).parse();
        AstDebugger db = new AstDebugger();
        ast.visit(db);
        System.out.println(db.getB());
    }
}
