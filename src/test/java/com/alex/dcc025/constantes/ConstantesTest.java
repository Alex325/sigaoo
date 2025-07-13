package com.alex.dcc025.constantes;

import java.util.ArrayList;
import java.util.List;
import com.alex.dcc025.disciplinas.Disciplina;
import com.alex.dcc025.disciplinas.DisciplinaEletiva;
import com.alex.dcc025.disciplinas.DisciplinaObrigatoria;
import com.alex.dcc025.disciplinas.DisciplinaOptativa;
import com.alex.dcc025.turma.Horario;
import com.alex.dcc025.turma.Turma;
import com.alex.dcc025.validadores.ValidadorAND;
import com.alex.dcc025.validadores.ValidadorOR;
import com.alex.dcc025.validadores.ValidadorSimples;

public class ConstantesTest {
    public static List<Disciplina> disciplinasSemPreRequisito;
    public static List<Disciplina> disciplinasComPreRequisito;
    public static List<Disciplina> todasDisciplinas = new ArrayList<>();

    static {
        try {

            disciplinasSemPreRequisito = List.of(
                new DisciplinaObrigatoria("DCC025", "Algoritmos", null, null, 4),
                new DisciplinaObrigatoria("DCC027", "Estruturas de Dados", null, null, 4),
                new DisciplinaObrigatoria("DCC028", "Circuitos Digitais", null, null, 4),
                new DisciplinaObrigatoria("MAT158", "Álgebra Linear", null, null, 4),
                new DisciplinaObrigatoria("DCC031", "Organização de Computadores", null, null, 4),
                new DisciplinaObrigatoria("PHT012", "História e Teoria da Arquitetura e Urbanismo II", null, null, 4),
                new DisciplinaOptativa("DCC026", "Desenvolvimento Web", null, null, 4),
                new DisciplinaEletiva("CEL032", "Circuitos Lógicos", null, null, 4),
                new DisciplinaOptativa("DCC029", "Segurança da Informação", null, null, 4),
                new DisciplinaOptativa("DCC032", "Redes de Computadores", null, null, 4),
                new DisciplinaEletiva("DCC033", "Machine Learning", null, null, 4)
            );

            disciplinasSemPreRequisito.get(0).getCoRequisitos().add(disciplinasSemPreRequisito.get(1));
            disciplinasSemPreRequisito.get(1).getCoRequisitos().add(disciplinasSemPreRequisito.get(0));

        } catch (Exception e) {
            System.out.println("Isso não deveria acontecer");
        }

        try {
            disciplinasComPreRequisito = List.of(
                new DisciplinaObrigatoria("DCC034", "Computação gráfica", List.of(new ValidadorSimples(disciplinasSemPreRequisito.get(0)), new ValidadorOR(List.of(disciplinasSemPreRequisito.get(2), disciplinasSemPreRequisito.get(1)))), null, 4),
                new DisciplinaObrigatoria("DCC036", "Desenvolvimento de Jogos", List.of(new ValidadorAND(List.of(disciplinasSemPreRequisito.get(0), disciplinasSemPreRequisito.get(6)))), null, 4),
                new DisciplinaObrigatoria("DCC037", "Engenharia de Software", List.of(new ValidadorOR(List.of(disciplinasSemPreRequisito.get(6), disciplinasSemPreRequisito.get(7)))), null, 4),
                new DisciplinaOptativa("DCC038", "Banco de Dados Avançado", List.of(new ValidadorAND(List.of(disciplinasSemPreRequisito.get(8), disciplinasSemPreRequisito.get(9)))), null, 4),
                new DisciplinaEletiva("DCC039", "Computação Quântica", List.of(new ValidadorOR(List.of(disciplinasSemPreRequisito.get(0), disciplinasSemPreRequisito.get(1)))), null, 4),
                new DisciplinaObrigatoria("PHT013", "História e Teoria da Arquitetura e Urbanismo III", List.of(new ValidadorSimples(disciplinasSemPreRequisito.get(4))), null, 4),
                new DisciplinaObrigatoria("DCC034", "Computação gráfica", List.of(new ValidadorAND(List.of(disciplinasSemPreRequisito.get(0), disciplinasSemPreRequisito.get(3))), new ValidadorOR(List.of(disciplinasSemPreRequisito.get(2), disciplinasSemPreRequisito.get(1)))), null, 4)
            );
        } catch (Exception e) {
            System.out.println("Isso não deveria acontecer");
        }

        
        todasDisciplinas.addAll(disciplinasSemPreRequisito);
        todasDisciplinas.addAll(disciplinasComPreRequisito);

        
        try {

            for (Disciplina disciplina : todasDisciplinas) {
             
                disciplina.addTurma(new Turma("A", disciplina, 10, new Horario(2, "08:00-12:00")));
                disciplina.addTurma(new Turma("B", disciplina, 10, new Horario(3, "08:00-12:00")));
                disciplina.addTurma(new Turma("C", disciplina, 10, new Horario(4, "08:00-12:00")));
                disciplina.addTurma(new Turma("D", disciplina, 10, new Horario(5, "08:00-12:00")));
                disciplina.addTurma(new Turma("E", disciplina, 10, new Horario(6, "08:00-12:00")));
                disciplina.addTurma(new Turma("F", disciplina, 10, new Horario(7, "08:00-12:00")));

            }
        } catch (Exception e) {
            System.out.println("Isso não deveria acontecer");
        }

    }
}
