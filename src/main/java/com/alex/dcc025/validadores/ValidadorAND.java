package com.alex.dcc025.validadores;

import java.util.List;

import com.alex.dcc025.aluno.Aluno;
import com.alex.dcc025.disciplinas.Disciplina;

public class ValidadorAND implements ValidadorPreRequisito {

    
    private List<Disciplina> aValidar;
    
    public ValidadorAND(List<Disciplina> aValidar) {
        this.aValidar = aValidar;
    }

    public boolean validar(Aluno aluno) {
        
        
        boolean valido = true;
        
        for (Disciplina disciplina : aValidar) {
            ValidadorSimples validador = new ValidadorSimples(disciplina);
            if (!validador.validar(aluno)) {
                valido = false;
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
                sb.append(" AND ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
