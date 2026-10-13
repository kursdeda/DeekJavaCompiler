package ru.kursdeda.objects.nodes;

import ru.kursdeda.patterns.AstVisitor;

public abstract class Node {
    public abstract void visit(AstVisitor visitor);
}
