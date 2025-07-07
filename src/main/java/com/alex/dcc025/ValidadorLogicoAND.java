package com.alex.dcc025;

public class ValidadorLogicoAND implements ValidadorPreRequisito {

    private ValidadorLogicoSimples validadorLogicoSimples = new ValidadorLogicoSimples();

    public boolean validar(Aluno aluno, Disciplina disciplina) {

        boolean valido = true;

        for (Disciplina disciplina2 : disciplina.getPreRequisitos()) {
            if (!validadorLogicoSimples.validar(aluno, disciplina2)) {
                valido = false;
                break;
            }
        }

        return valido;
    }
}
