package com.alex.dcc025;

public class ValidadorLogicoCreditos implements ValidadorPreRequisito {
    private Aluno aluno;


    public boolean validar(Aluno aluno, Disciplina disciplina) {
        for (Disciplina disciplina2 : disciplina.getPreRequisitos()) {
            
        }
        return false;
    }
}
