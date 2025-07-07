package com.alex.dcc025;

public abstract class Disciplina {
    private Disciplina[] preRequisitos;

    public Disciplina(Disciplina[] preRequisitos) {
        this.preRequisitos = preRequisitos;
    }

    public Disciplina[] getPreRequisitos() {
        return preRequisitos;
    }
}
