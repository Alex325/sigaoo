package com.alex.dcc025.validadores;

import com.alex.dcc025.aluno.Aluno;
import com.alex.dcc025.disciplinas.Disciplina;

public class ValidadorCreditos implements ValidadorPreRequisito {

    private final int creditosMinimos;

    public ValidadorCreditos(int creditosMinimos) {
        this.creditosMinimos = creditosMinimos;
    }

    public boolean validar(Aluno aluno) {

        int creditos = 0;

        for (Disciplina disciplina : aluno.getHistorico().keySet()) {
            ValidadorSimples aprovado = new ValidadorSimples(disciplina);

            if (aprovado.validar(aluno)) {
                creditos += disciplina.getCreditos();
            }
        }

        return creditos >= creditosMinimos;
    }

    @Override
    public String toString() {
        return "(" + creditosMinimos + " créditos mínimos)";
    }
}
