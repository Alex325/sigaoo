package com.alex.dcc025.disciplinas;

import java.util.ArrayList;
import java.util.List;

import com.alex.dcc025.exceptions.disciplina.CodigoDisciplinaInvalidoException;
import com.alex.dcc025.exceptions.disciplina.DisciplinaException;
import com.alex.dcc025.turma.Turma;
import com.alex.dcc025.validadores.ValidadorPreRequisito;

public abstract class Disciplina {

    private final String codigo;
    private final String nome;

    private final List<ValidadorPreRequisito> preRequisitos;
    private final List<Disciplina> coRequisitos;
    private final List<Turma> turmas;
    
    private final int cargaHorariaSemanal;
    private final int creditos;

    public Disciplina(String codigo, String nome, List<ValidadorPreRequisito> preRequisitos, List<Disciplina> coRequisitos, int cargaHorariaSemanal) throws DisciplinaException {

        if (!codigoValido(codigo)) throw new CodigoDisciplinaInvalidoException("Código de disciplina inválido: " + codigo);

        this.codigo = codigo;
        this.nome = nome;
        this.preRequisitos = (preRequisitos) == null ? new ArrayList<>() : preRequisitos;
        this.coRequisitos = (coRequisitos) == null ? new ArrayList<>() : coRequisitos;
        this.cargaHorariaSemanal = cargaHorariaSemanal;
        this.creditos = this.cargaHorariaSemanal;
        this.turmas = new ArrayList<>();

    }

    public List<ValidadorPreRequisito> getPreRequisitos() {
        return preRequisitos;
    }

    public List<Disciplina> getCoRequisitos() {
        return coRequisitos;
    }

    public int getCreditos() {
        return creditos;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public List<Turma> getTurmas() {
        return this.turmas;
    }

    public void addTurma(Turma turma) {
        this.turmas.add(turma);
    }
    
    public int getCargaHoraria() {
        return this.cargaHorariaSemanal;
    }
    
    private boolean codigoValido(String codigo) {
        return codigo.matches("([A-Z]{3,3})([0-9]{3,3})");
    }

}
