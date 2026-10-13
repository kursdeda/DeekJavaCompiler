package ru.kursdeda.objects.nodes;

import lombok.AllArgsConstructor;
import ru.kursdeda.objects.Token;
import ru.kursdeda.patterns.AstVisitor;

import java.util.List;

public abstract class Expression extends Node {
    @AllArgsConstructor
    public static class Identifier extends Expression {
        public final Token name;

        @Override
        public void visit(AstVisitor visitor) {
            visitor.accept(this);
        }
    }

    @AllArgsConstructor
    public static class Number extends Expression {
        public final Token value;

        @Override
        public void visit(AstVisitor visitor) {
            visitor.accept(this);
        }
    }

    @AllArgsConstructor
    public static class Null extends Expression {
        @Override
        public void visit(AstVisitor visitor) {
            visitor.accept(this);
        }
    }

    @AllArgsConstructor
    public static class Path extends Expression {
        public final List<Token> parts;
        @Override
        public void visit(AstVisitor visitor) {
            visitor.accept(this);
        }
    }

    @AllArgsConstructor
    public static class Call extends Expression {
        public final Path path;
        public final List<Expression> args;
        @Override
        public void visit(AstVisitor visitor) {
            visitor.accept(this);
        }
    }
}
