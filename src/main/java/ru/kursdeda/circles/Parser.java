package ru.kursdeda.circles;

import lombok.RequiredArgsConstructor;
import ru.kursdeda.objects.Token;
import ru.kursdeda.objects.nodes.Expression;
import ru.kursdeda.objects.nodes.Node;
import ru.kursdeda.objects.nodes.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class Parser {
    private final List<Token> input;
    private final Stack<Integer> history = new Stack<>();
    private int pos;

    public Statement.Block parse() {
        return new Statement.Block(nodeList(Token.Type.EOF, Token.Type.SEMICOLON, this::space));
    }

    /* Statement */

    private Statement statement() {
        Statement stmt = space();
        if (stmt != null) return stmt;

        stmt = halt();
        if (stmt != null) return stmt;

        stmt = ret();
        if (stmt != null) return stmt;

        stmt = block();
        if (stmt != null) return stmt;

        stmt = function();
        if (stmt != null) return stmt;

        stmt = varInitialization();
        if (stmt != null) return stmt;

        stmt = variable();
        if (stmt != null) return stmt;

        stmt = path();
        if (stmt != null) return stmt;

        throw new RuntimeException("Invalid statement at " + pos + " (" + input.get(pos) + ")");
    }

    private Statement.Space space() {
        if (!match(Token.Type.SPACE)) return null;
        var name = consume(Token.Type.IDENTIFIER);
        consume(Token.Type.COLON);
        return new Statement.Space(name, statement());
    }

    private Statement.Variable variable() {
        if (!match(Token.Type.DATATYPE)) return null;
        return new Statement.Variable(prev(), consume(Token.Type.IDENTIFIER));
    }

    private Statement.VarInitialization varInitialization() {
        dump();
        var variable = variable();
        if (variable == null) return null;
        if (!match(Token.Type.EQUAL)) {
            restore();
            return null;
        }
        return new Statement.VarInitialization(variable, expression());
    }

    private Statement.Function function() {
        dump();
        if (!(match(Token.Type.DATATYPE) || match(Token.Type.VOID))) return null;
        var datatype = prev();
        var name = consume(Token.Type.IDENTIFIER);
        if (!match(Token.Type.COLON)) {
            restore();
            return null;
        }

        List<Statement.Variable> params = new ArrayList<>();
        if (match(Token.Type.OPEN_PARENTHESIS)) {
            params = nodeArgs(Token.Type.CLOSE_PARENTHESIS, Token.Type.COMMA, this::variable);
            consume(Token.Type.COLON);
        }

        return new Statement.Function(datatype, name, params, statement());
    }

    private Statement.Halt halt() {
        if (!match(Token.Type.HALT)) return null;
        return new Statement.Halt(expression());
    }

    private Statement.Ret ret() {
        if (!match(Token.Type.RET)) return null;
        return new Statement.Ret(expression());
    }

    private Statement.Block block() {
        if (!match(Token.Type.OPEN_BRACE)) return null;
        return new Statement.Block(nodeList(Token.Type.CLOSE_BRACE, Token.Type.SEMICOLON, this::statement));
    }

    private Statement path() {
        if (!match(Token.Type.IDENTIFIER)) return null;
        pos--;
        var path = new Statement.Path(pathList());
        if (!match(Token.Type.OPEN_PARENTHESIS)) {
            consume(Token.Type.EQUAL);
            return new Statement.Assignment(path, expression());
        }
        return new Statement.Call(path, nodeArgs(Token.Type.CLOSE_PARENTHESIS, Token.Type.COMMA, this::expression));
    }

    /* Expression */

    private Expression expression() {
        return pathExpression();
    }

    private Expression pathExpression() {
        if (!match(Token.Type.IDENTIFIER)) return topExpression();
        pos--;
        Expression.Path path = new Expression.Path(pathList());
        if (!match(Token.Type.OPEN_PARENTHESIS))
            return path;
        return new Expression.Call(path, nodeArgs(Token.Type.CLOSE_PARENTHESIS, Token.Type.COMMA, this::expression));
    }

    private Expression topExpression() {
        if (match(Token.Type.NUMBER)) return new Expression.Number(prev());
        if (match(Token.Type.NULL)) return new Expression.Null();
        throw new RuntimeException(String.format("Invalid statement at %d : %s", pos, input.get(pos)));
    }

    /* Utils */

    private List<Token> pathList() {
        List<Token> parts = new ArrayList<>();

        do parts.add(consume(Token.Type.IDENTIFIER));
        while (match(Token.Type.DOT));

        return parts;
    }

    private <T extends Node> List<T> nodeList(Token.Type end, Token.Type split, Supplier<T> supplier) {
        if (match(end)) return new ArrayList<>();
        List<T> out = new ArrayList<>();
        while (true) {
            out.add(supplier.get());
            if (prev().type()!= Token.Type.CLOSE_BRACE) consume(split);
            if (match(end)) return out;
        }
    }

    private <T extends Node> List<T> nodeArgs(Token.Type end, Token.Type split, Supplier<T> supplier) {
        if (match(end)) return new ArrayList<>();
        List<T> out = new ArrayList<>();
        while (true) {
            out.add(supplier.get());
            if (match(end)) return out;
            consume(split);
        }
    }

    private void dump() {
        history.push(pos);
    }

    private void restore() {
        pos = history.pop();
    }

    private boolean match(Token.Type... t) {
        for (Token.Type type : t)
            if (input.get(pos).type().equals(type)) {
                pos++;
                return true;
            }
        return false;
    }

    private Token prev() {
        return input.get(pos - 1);
    }

    private Token consume(Token.Type... t) {
        if (!match(t))
            throw new RuntimeException(String.format("Required %s at %d but found %s", Arrays.toString(t), pos, input.get(pos)));
        return prev();
    }
}
