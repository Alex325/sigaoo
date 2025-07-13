package com.alex.dcc025.aluno;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alex.dcc025.disciplinas.Disciplina;
import com.alex.dcc025.exceptions.matricula.FormatoMatriculaInvalidoException;
import com.alex.dcc025.turma.Turma;

public class Aluno {

    private String nome;
    private final String matricula;
    
    private Map<Disciplina, Integer> historico;
    private List<Turma> planejamento;

    private final int cargaHorariaMaxima = 20;

    public Aluno(String nome, String matricula) throws FormatoMatriculaInvalidoException {
        this.nome = nome;

        if (!matriculaValida(matricula)) {
            throw new FormatoMatriculaInvalidoException("Formato de matrícula inválido: " + matricula);
        }

        this.matricula = matricula;
        this.historico = new HashMap<>();
        this.planejamento = new ArrayList<>();
    }

    public Map<Disciplina, Integer> getHistorico() {
        return this.historico;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void requererMatricula(Turma turma) {
        this.planejamento.add(turma);
    }

    private boolean matriculaValida(String matricula) {
        return matricula.matches("([0-9]{4,4})([0-9]{2,2})([0-9]{3,3})");
    }

    public List<Turma> getPlanejamento() {
        return this.planejamento;
    }

    public int getCargaHorariaMaxima() {
        return cargaHorariaMaxima;
    }

    public void addToHistorico(Disciplina disciplina, int nota) {
        this.historico.put(disciplina, nota);
    }
}
