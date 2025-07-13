package com.alex.dcc025.validadores;

import com.alex.dcc025.aluno.Aluno;

public interface ValidadorPreRequisito {
    boolean validar(Aluno aluno);
    String toString();
}
