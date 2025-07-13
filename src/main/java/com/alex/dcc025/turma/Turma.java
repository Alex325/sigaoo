package com.alex.dcc025.turma;

import java.util.ArrayList;
import java.util.List;

import com.alex.dcc025.aluno.Aluno;
import com.alex.dcc025.disciplinas.Disciplina;
import com.alex.dcc025.exceptions.matricula.TurmaCheiaException;


public class Turma {
    private final String id;
    private final Disciplina disciplina;
    private final int capacidadeMaxima;
    private int alunosMatriculados;
    private final Horario horario;
    
    private List<Aluno> alunosMatriculadosLista;

    public Turma(String id, Disciplina disciplina, int capacidadeMaxima, Horario horario) {
        this.id = id;
        this.disciplina = disciplina;
        this.capacidadeMaxima = capacidadeMaxima;
        this.alunosMatriculadosLista = new ArrayList<>();
        this.alunosMatriculados = alunosMatriculadosLista.size();
        this.horario = horario;
    }

    public String horarioLegivel() {
        return this.horario.horarioLegivel();
    }

    public void matricularAluno(Aluno aluno) throws TurmaCheiaException {
        if (alunosMatriculados < capacidadeMaxima) {
            if (!this.alunosMatriculadosLista.contains(aluno)) {
                this.alunosMatriculadosLista.add(aluno);
                this.alunosMatriculados = this.alunosMatriculadosLista.size();
            }
        } else {
            throw new TurmaCheiaException("Não há vagas disponíveis na turma " + nomeQualificado());
        }
            
    }

    public void desmatricularAluno(Aluno aluno) {
        if (this.alunosMatriculadosLista.contains(aluno)) {
            this.alunosMatriculadosLista.add(aluno);
            this.alunosMatriculados = this.alunosMatriculadosLista.size();
        }
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public String getId() {
        return id;
    }

    public String nomeQualificado() {
        return disciplina.getCodigo() + " " + id;
    }

    public Horario getHorario() {
        return this.horario;
    }

}
