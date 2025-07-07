package com.alex.dcc025;

public class ValidadorLogicoOR implements ValidadorPreRequisito {
    
    private ValidadorLogicoSimples validadorLogicoSimples = new ValidadorLogicoSimples();
    
    public boolean validar(Aluno aluno, Disciplina disciplina) {

        boolean valido = false;

        for (Disciplina disciplina2 : disciplina.getPreRequisitos()) {
            if (validadorLogicoSimples.validar(aluno, disciplina2)) {
                valido = true;
                break;
            }
        }

        return valido;
    }
}
