package com.alex.dcc025.disciplinas;

import java.util.List;

import com.alex.dcc025.exceptions.disciplina.DisciplinaException;
import com.alex.dcc025.validadores.ValidadorPreRequisito;

public class DisciplinaEletiva extends Disciplina {
    public DisciplinaEletiva(String codigo, String nome, List<ValidadorPreRequisito> preRequisitos, List<Disciplina> coRequisitos, int cargaHorariaSemanal) throws DisciplinaException {
        super(codigo, nome, preRequisitos, coRequisitos, cargaHorariaSemanal);
    }
}
