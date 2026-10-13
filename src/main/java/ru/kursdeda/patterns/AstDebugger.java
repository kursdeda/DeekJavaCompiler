package ru.kursdeda.patterns;

import lombok.Getter;
import ru.kursdeda.objects.nodes.Expression;
import ru.kursdeda.objects.nodes.Statement;

public class AstDebugger implements AstVisitor {
    @Getter
    private final StringBuilder b = new StringBuilder();

    @Override
    public void accept(Expression.Identifier identifier) {
        b.append(identifier.name.value());
    }

    @Override
    public void accept(Expression.Number number) {
        b.append(number.value.value());
    }

    @Override
    public void accept(Expression.Null aNull) {
        b.append("null");
    }

    @Override
    public void accept(Expression.Path path) {
        path.parts.forEach(token -> b.append(token.value()).append('.'));
    }

    @Override
    public void accept(Expression.Call call) {
        call.path.visit(this);
        b.append("(");
        call.args.forEach(e -> {
            e.visit(this); b.append(',');
        });
        b.append(")");
    }

    @Override
    public void accept(Statement.Space space) {
        b.append("space ").append(space.name.value()).append(":");
        space.body.visit(this);
        b.append(';');
    }

    @Override
    public void accept(Statement.Variable variable) {
        b.append(variable.datatype.type().name()).append(' ').append(variable.name.value()).append(';');
    }

    @Override
    public void accept(Statement.VarInitialization varInitialization) {
        varInitialization.variable.visit(this);
        b.append(" = ");
        varInitialization.value.visit(this);
        b.append(";");
    }

    @Override
    public void accept(Statement.Function function) {
        b.append(function.datatype.type().name()).append(' ').append(function.name.value());
        b.append("(");
        function.params.forEach(this::accept);
        b.append("):");
        function.body.visit(this);
    }

    @Override
    public void accept(Statement.Block block) {
        b.append("{");
        block.children.forEach(s -> s.visit(this));
        b.append("}");
    }

    @Override
    public void accept(Statement.Halt halt) {
        b.append("halt ");
        halt.value.visit(this);
        b.append(";");
    }

    @Override
    public void accept(Statement.Ret ret) {
        b.append("ret ");
        ret.value.visit(this);
        b.append(";");
    }

    @Override
    public void accept(Statement.Path path) {
        path.parts.forEach(token -> b.append(token.value()).append('.'));
    }

    @Override
    public void accept(Statement.Assignment assignment) {
        assignment.path.visit(this);
        b.append(" = ");
        assignment.value.visit(this);
        b.append(";");
    }

    @Override
    public void accept(Statement.Call call) {
        call.path.visit(this);
        b.append("(");
        call.args.forEach(e -> {
            e.visit(this); b.append(',');
        });
        b.append(")");
    }
}
