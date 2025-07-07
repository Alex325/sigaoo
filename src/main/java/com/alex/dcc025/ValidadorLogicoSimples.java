package com.alex.dcc025;

public class ValidadorLogicoSimples implements ValidadorPreRequisito {
    public boolean validar(Aluno aluno, Disciplina disciplina) {
        return aluno.getHistorico().containsKey(disciplina) && aluno.getHistorico().get(disciplina) >= 60;
    }
}
