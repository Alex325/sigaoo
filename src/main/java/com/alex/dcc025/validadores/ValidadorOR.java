package com.alex.dcc025.validadores;

import java.util.List;

import com.alex.dcc025.aluno.Aluno;
import com.alex.dcc025.disciplinas.Disciplina;

public class ValidadorOR implements ValidadorPreRequisito {
    
    private List<Disciplina> aValidar;
    
    public ValidadorOR(List<Disciplina> aValidar) {
        this.aValidar = aValidar;
    }

    public boolean validar(Aluno aluno) {

        boolean valido = false;

        for (Disciplina disciplina : aValidar) {

            ValidadorSimples validador = new ValidadorSimples(disciplina);

            if (validador.validar(aluno)) {
                valido = true;
                break;
            }
        }

        return valido;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < aValidar.size(); i++) {
            sb.append(aValidar.get(i).getCodigo());
            if (i < aValidar.size() - 1) {
                sb.append(" OR ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
