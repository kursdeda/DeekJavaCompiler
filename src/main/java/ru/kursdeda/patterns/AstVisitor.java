package ru.kursdeda.patterns;

import ru.kursdeda.objects.nodes.Expression;
import ru.kursdeda.objects.nodes.Statement;

public interface AstVisitor {
    void accept(Expression.Identifier identifier);

    void accept(Expression.Number number);

    void accept(Expression.Null aNull);

    void accept(Expression.Path path);

    void accept(Expression.Call call);

    void accept(Statement.Space space);

    void accept(Statement.Variable variable);

    void accept(Statement.VarInitialization varInitialization);

    void accept(Statement.Function function);

    void accept(Statement.Block block);

    void accept(Statement.Halt halt);

    void accept(Statement.Ret ret);

    void accept(Statement.Path path);

    void accept(Statement.Assignment assignment);

    void accept(Statement.Call call);
}
