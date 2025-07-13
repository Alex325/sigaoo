package com.alex.dcc025.validadores;

import com.alex.dcc025.aluno.Aluno;
import com.alex.dcc025.disciplinas.Disciplina;

public class ValidadorSimples implements ValidadorPreRequisito {

    private final Disciplina aValidar;

    public ValidadorSimples(Disciplina aValidar) {
        this.aValidar = aValidar;
    }

    public boolean validar(Aluno aluno) {
        return aluno.getHistorico().containsKey(aValidar) && aluno.getHistorico().get(aValidar) >= 60;
    }

    @Override
    public String toString() {
        return "(" + aValidar.getCodigo() +
                ")";
    }
}
