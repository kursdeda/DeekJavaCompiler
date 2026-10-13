package ru.kursdeda.objects.nodes;

import lombok.AllArgsConstructor;
import lombok.ToString;
import ru.kursdeda.objects.Token;
import ru.kursdeda.patterns.AstVisitor;

import java.util.List;

public abstract class Statement extends Node {

    @AllArgsConstructor
    public static class Space extends Statement {
        public final Token name;
        public final Statement body;

        @Override
        public void visit(AstVisitor visitor) {
            visitor.accept(this);
        }
    }

    @AllArgsConstructor
    public static class Variable extends Statement {
        public final Token datatype, name;

        @Override
        public void visit(AstVisitor visitor) {
            visitor.accept(this);
        }
    }

    @AllArgsConstructor
    public static class VarInitialization extends Statement {
        public final Variable variable;
        public final Expression value;

        @Override
        public void visit(AstVisitor visitor) {
            visitor.accept(this);
        }
    }

    @AllArgsConstructor
    public static class Function extends Statement {
        public final Token datatype, name;
        public final List<Variable> params;
        public final Statement body;

        @Override
        public void visit(AstVisitor visitor) {
            visitor.accept(this);
        }
    }

    @AllArgsConstructor
    public static class Block extends Statement {
        public final List<Statement> children;

        @Override
        public void visit(AstVisitor visitor) {
            visitor.accept(this);
        }
    }

    @AllArgsConstructor
    public static class Halt extends Statement {
        public final Expression value;

        @Override
        public void visit(AstVisitor visitor) {
            visitor.accept(this);
        }
    }

    @AllArgsConstructor
    public static class Ret extends Statement {
        public final Expression value;

        @Override
        public void visit(AstVisitor visitor) {
            visitor.accept(this);
        }
    }

    @AllArgsConstructor
    public static class Path extends Statement {
        public final List<Token> parts;
        @Override
        public void visit(AstVisitor visitor) {
            visitor.accept(this);
        }
    }

    @AllArgsConstructor
    public static class Assignment extends Statement {
        public final Path path;
        public final Expression value;
        @Override
        public void visit(AstVisitor visitor) {
            visitor.accept(this);
        }
    }

    @AllArgsConstructor
    public static class Call extends Statement {
        public final Path path;
        public final List<Expression> args;
        @Override
        public void visit(AstVisitor visitor) {
            visitor.accept(this);
        }
    }
}
