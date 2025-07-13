package com.alex.dcc025.sistema;

import org.junit.jupiter.api.Test;

import com.alex.dcc025.aluno.Aluno;
import com.alex.dcc025.constantes.ConstantesTest;
import com.alex.dcc025.exceptions.matricula.CargaHorariaExcedidaException;
import com.alex.dcc025.exceptions.matricula.CoRequisitoNaoAtendido;
import com.alex.dcc025.exceptions.matricula.PrerequisitoNaoCumpridoException;
import com.alex.dcc025.turma.Turma;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class SistemaAcademicoTest {
    
    private List<Aluno> alunos;

    public SistemaAcademicoTest() {

        try
        {
            alunos = List.of(
                new Aluno("Isabella", "202433006"),
                new Aluno("Alex", "202465551"),
                new Aluno("João", "202433007"),
                new Aluno("Maria", "202433008"),
                new Aluno("Pedro", "202433009"),
                new Aluno("Ana", "202433010"),
                new Aluno("Lucas", "202433011"),
                new Aluno("Carla", "202433012"),
                new Aluno("Jorge", "202433013")
            );
        } catch (Exception e) {
            System.out.println("Isso não deveria acontecer");
        }

        alunos.get(0).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(3), 60);
        alunos.get(0).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(5), 60);
        alunos.get(0).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(2), 60);
        alunos.get(0).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(4), 60);

        alunos.get(0).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(0).getTurmas().get(0));

        alunos.get(1).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(0), 60);
        alunos.get(1).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(1), 60);
        alunos.get(1).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(2), 60);
        alunos.get(1).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(4), 60);

        alunos.get(1).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(0).getTurmas().get(0));   
        alunos.get(1).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(1).getTurmas().get(1));   

        alunos.get(2).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(0), 60);
        alunos.get(2).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(1), 60);
        alunos.get(2).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(5), 60);
        alunos.get(2).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(6), 60);  

        alunos.get(4).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(0), 60);
        alunos.get(4).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(1), 60);
        alunos.get(4).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(2), 60);
        alunos.get(4).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(3), 60);

        alunos.get(5).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(0), 60);
        alunos.get(5).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(1), 60);
        alunos.get(5).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(2), 60);
        alunos.get(5).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(4), 59);
        
        alunos.get(6).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(0), 60);
        alunos.get(6).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(1), 60);
        alunos.get(6).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(2), 60);
        alunos.get(6).addToHistorico(ConstantesTest.disciplinasSemPreRequisito.get(4), 59);

    }
    
    @Test
    public void testCoRequisitoNaoAtendido() {
        
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(0), ConstantesTest.todasDisciplinas);
        Aluno aluno = alunos.get(0);

        assertThrows(CoRequisitoNaoAtendido.class, () -> {
            sistemaAcademico.verificarCoRequisito(aluno.getPlanejamento().get(0));
        });
        
    }

    @Test
    public void testCoRequisitoAtendido() {

        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(1), ConstantesTest.todasDisciplinas);
        Aluno aluno = alunos.get(1);
        
        assertDoesNotThrow(() -> {
            sistemaAcademico.verificarCoRequisito(aluno.getPlanejamento().get(0));
        });
        assertDoesNotThrow(() -> {
            sistemaAcademico.verificarCoRequisito(aluno.getPlanejamento().get(1));
        });
    }

    @Test
    public void testMatriculaComCoRequisitoSucesso() {
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(1), ConstantesTest.todasDisciplinas);
        Aluno aluno = alunos.get(1);
                
        
        
        assertDoesNotThrow(() -> {
            sistemaAcademico.verificarCoRequisito(aluno.getPlanejamento().get(0));
        });
        assertDoesNotThrow(() -> {
            sistemaAcademico.verificarCoRequisito(aluno.getPlanejamento().get(1));
        });
    }

    @Test
    public void testMatriculaComCoRequisitoFalha() {
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(0), ConstantesTest.todasDisciplinas);
        Aluno aluno = alunos.get(0);
        
        assertThrows(CoRequisitoNaoAtendido.class, () -> {
            sistemaAcademico.verificarCoRequisito(aluno.getPlanejamento().get(0));
        });
    }

    @Test
    public void testMatriculaComPreRequisitoANDSucesso() {
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(2), ConstantesTest.todasDisciplinas);
        
        Turma turma = ConstantesTest.disciplinasComPreRequisito.get(1).getTurmas().get(0);
        List<Turma> listaAtual = new ArrayList<>();
        List<Turma> turmasMatriculadas = new ArrayList<>();

        assertDoesNotThrow(() -> {
            sistemaAcademico.matricularTurma(turma, listaAtual, turmasMatriculadas);
        });

    }

    @Test
    public void testMatriculaComPreRequisitoANDFalha() {
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(0), ConstantesTest.todasDisciplinas);
        
        Turma turma = ConstantesTest.disciplinasComPreRequisito.get(1).getTurmas().get(0);
        List<Turma> listaAtual = new ArrayList<>();
        List<Turma> turmasMatriculadas = new ArrayList<>();
        assertThrows(PrerequisitoNaoCumpridoException.class, () -> {
            sistemaAcademico.matricularTurma(turma, listaAtual, turmasMatriculadas);
        });

    }

    @Test
    public void testMatriculaComPreRequisitoORSucesso() {
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(1), ConstantesTest.todasDisciplinas);
        
        Turma turma = ConstantesTest.disciplinasComPreRequisito.get(4).getTurmas().get(0);

        List<Turma> listaAtual = new ArrayList<>();
        List<Turma> turmasMatriculadas = new ArrayList<>();

        assertDoesNotThrow(() -> {
            sistemaAcademico.matricularTurma(turma, listaAtual, turmasMatriculadas);
        });
    }

    @Test
    public void testMatriculaComPreRequisitoORFalha() {
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(3), ConstantesTest.todasDisciplinas);
        
        Turma turma = ConstantesTest.disciplinasComPreRequisito.get(0).getTurmas().get(0);
        List<Turma> listaAtual = new ArrayList<>();
        List<Turma> turmasMatriculadas = new ArrayList<>();
        assertThrows(PrerequisitoNaoCumpridoException.class, () -> {
            sistemaAcademico.matricularTurma(turma, listaAtual, turmasMatriculadas);
        });
    }
    @Test
    public void testMatriculaComPreRequisitoANDEORSucesso() {
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(4), ConstantesTest.todasDisciplinas);
        
        Turma turma = ConstantesTest.disciplinasComPreRequisito.get(6).getTurmas().get(0);
        List<Turma> listaAtual = new ArrayList<>();
        List<Turma> turmasMatriculadas = new ArrayList<>();
        
        assertDoesNotThrow(() -> {
            sistemaAcademico.matricularTurma(turma, listaAtual, turmasMatriculadas);
        });
    }

    @Test
    public void testMatriculaComPreRequisitoANDEORFalha() {
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(1), ConstantesTest.todasDisciplinas);
        
        Turma turma = ConstantesTest.disciplinasComPreRequisito.get(2).getTurmas().get(0);
        List<Turma> listaAtual = new ArrayList<>();
        List<Turma> turmasMatriculadas = new ArrayList<>();
        
        assertThrows(PrerequisitoNaoCumpridoException.class, () -> {
            sistemaAcademico.matricularTurma(turma, listaAtual, turmasMatriculadas);
        });
    }

    @Test
    public void testPreRequisitoReprovado() {
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(5), ConstantesTest.todasDisciplinas);
        
        Turma turma = ConstantesTest.disciplinasComPreRequisito.get(5).getTurmas().get(0);
        List<Turma> listaAtual = new ArrayList<>();
        List<Turma> turmasMatriculadas = new ArrayList<>();
        
        assertThrows(PrerequisitoNaoCumpridoException.class, () -> {
            sistemaAcademico.matricularTurma(turma, listaAtual, turmasMatriculadas);
        });
    }

    @Test
    public void testPreRequisito() {
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(5), ConstantesTest.todasDisciplinas);
        
        Turma turma = ConstantesTest.disciplinasComPreRequisito.get(0).getTurmas().get(0);
        List<Turma> listaAtual = new ArrayList<>();
        List<Turma> turmasMatriculadas = new ArrayList<>();
        
        assertDoesNotThrow(() -> {
            sistemaAcademico.matricularTurma(turma, listaAtual, turmasMatriculadas);
        });
    }

    @Test
    public void testCargaHorariaExcedida() {
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(6), ConstantesTest.todasDisciplinas);
        
        Turma turma = ConstantesTest.disciplinasSemPreRequisito.get(5).getTurmas().get(1);
        List<Turma> listaAtual = new ArrayList<>();
        List<Turma> turmasMatriculadas = new ArrayList<>(List.of(
            ConstantesTest.disciplinasSemPreRequisito.get(0).getTurmas().get(0),
            ConstantesTest.disciplinasSemPreRequisito.get(1).getTurmas().get(0),
            ConstantesTest.disciplinasSemPreRequisito.get(2).getTurmas().get(0),
            ConstantesTest.disciplinasSemPreRequisito.get(3).getTurmas().get(0),
            ConstantesTest.disciplinasSemPreRequisito.get(4).getTurmas().get(0)
        ));
        
        assertThrows(CargaHorariaExcedidaException.class, () -> {
            sistemaAcademico.matricularTurma(turma, listaAtual, turmasMatriculadas);
        });
    }

    @Test
    public void testConflitoDeHorarioObrigatoriaEEletiva() {
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(7), ConstantesTest.todasDisciplinas);
        
        Turma turmaObrigatoria = ConstantesTest.disciplinasSemPreRequisito.get(2).getTurmas().get(0);
        Turma turmaEletiva = ConstantesTest.disciplinasSemPreRequisito.get(7).getTurmas().get(0);

        alunos.get(7).requererMatricula(turmaObrigatoria);
        alunos.get(7).requererMatricula(turmaEletiva);

        
        sistemaAcademico.fazerMatricula();

        assertTrue(sistemaAcademico.getRelatorio().get(turmaObrigatoria).equals("Matriculado"));
        assertEquals(sistemaAcademico.getRelatorio().get(turmaEletiva), "Horário da turma " + turmaEletiva.nomeQualificado() + " conflita com a turma já matriculada " + turmaObrigatoria.nomeQualificado());

    }

    @Test
    public void testConflitoDeHorarioObrigatoriaEOptativa() {
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(7), ConstantesTest.todasDisciplinas);
        
        Turma turmaObrigatoria = ConstantesTest.disciplinasSemPreRequisito.get(2).getTurmas().get(0);
        Turma turmaOptativa = ConstantesTest.disciplinasSemPreRequisito.get(8).getTurmas().get(0);
        alunos.get(7).requererMatricula(turmaObrigatoria);
        alunos.get(7).requererMatricula(turmaOptativa);

        sistemaAcademico.fazerMatricula();

        assertTrue(sistemaAcademico.getRelatorio().get(turmaObrigatoria).equals("Matriculado"));
        assertEquals(sistemaAcademico.getRelatorio().get(turmaOptativa), "Horário da turma " + turmaOptativa.nomeQualificado() + " conflita com a turma já matriculada " + turmaObrigatoria.nomeQualificado());
    }

    @Test
    public void testConflitoDeHorarioEletivaEOptativa() {
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(7), ConstantesTest.todasDisciplinas);
        
        Turma turmaEletiva = ConstantesTest.disciplinasSemPreRequisito.get(7).getTurmas().get(0);
        Turma turmaOptativa = ConstantesTest.disciplinasSemPreRequisito.get(8).getTurmas().get(0);
        
        alunos.get(7).requererMatricula(turmaEletiva);
        alunos.get(7).requererMatricula(turmaOptativa);

        sistemaAcademico.fazerMatricula();

        assertEquals("Matriculado", sistemaAcademico.getRelatorio().get(turmaEletiva));
        assertEquals("Horário da turma " + turmaOptativa.nomeQualificado() + " conflita com a turma já matriculada " + turmaEletiva.nomeQualificado(), sistemaAcademico.getRelatorio().get(turmaOptativa));
    }

    @Test
    public void testConflitoDeHorarioMesmaPrecedencia() {
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(7), ConstantesTest.todasDisciplinas);
        
        Turma turma1 = ConstantesTest.disciplinasSemPreRequisito.get(3).getTurmas().get(0);
        Turma turma2 = ConstantesTest.disciplinasSemPreRequisito.get(4).getTurmas().get(0);
        
        alunos.get(7).requererMatricula(turma1);
        alunos.get(7).requererMatricula(turma2);

        sistemaAcademico.fazerMatricula();

        assertEquals("Horário da turma " + turma1.nomeQualificado() + " conflita com a turma " + turma2.nomeQualificado(), sistemaAcademico.getRelatorio().get(turma1));
        assertEquals("Horário da turma " + turma2.nomeQualificado() + " conflita com a turma " + turma1.nomeQualificado(), sistemaAcademico.getRelatorio().get(turma2));
    }

    @Test
    public void testDescarteCargaHorariaExcedida() {
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(6), ConstantesTest.todasDisciplinas);
        
        alunos.get(6).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(2).getTurmas().get(0));
        alunos.get(6).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(3).getTurmas().get(1));
        alunos.get(6).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(4).getTurmas().get(2));
        alunos.get(6).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(5).getTurmas().get(3));
        alunos.get(6).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(6).getTurmas().get(4));
        alunos.get(6).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(7).getTurmas().get(5));

        sistemaAcademico.fazerMatricula();
        
        assertEquals("Matriculado", sistemaAcademico.getRelatorio().get(ConstantesTest.disciplinasSemPreRequisito.get(2).getTurmas().get(0)));
        assertEquals("Matriculado", sistemaAcademico.getRelatorio().get(ConstantesTest.disciplinasSemPreRequisito.get(3).getTurmas().get(1)));
        assertEquals("Matriculado", sistemaAcademico.getRelatorio().get(ConstantesTest.disciplinasSemPreRequisito.get(4).getTurmas().get(2)));
        assertEquals("Matriculado", sistemaAcademico.getRelatorio().get(ConstantesTest.disciplinasSemPreRequisito.get(5).getTurmas().get(3)));
        assertEquals("Matriculado", sistemaAcademico.getRelatorio().get(ConstantesTest.disciplinasSemPreRequisito.get(7).getTurmas().get(5)));
        assertEquals("Carga horária máxima excedida. Turma descartada.", sistemaAcademico.getRelatorio().get(ConstantesTest.disciplinasSemPreRequisito.get(6).getTurmas().get(4)));
    }

    @Test
    public void testGeracaoRelatorio() {
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(7), ConstantesTest.todasDisciplinas);
        
        alunos.get(7).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(2).getTurmas().get(0));
        alunos.get(7).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(3).getTurmas().get(1));
        alunos.get(7).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(4).getTurmas().get(2));
        alunos.get(7).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(5).getTurmas().get(3));
        alunos.get(7).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(6).getTurmas().get(4));
        alunos.get(7).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(7).getTurmas().get(5));

        sistemaAcademico.fazerMatricula();
        
        assertEquals("Matriculado", sistemaAcademico.getRelatorio().get(ConstantesTest.disciplinasSemPreRequisito.get(2).getTurmas().get(0)));
        assertEquals("Matriculado", sistemaAcademico.getRelatorio().get(ConstantesTest.disciplinasSemPreRequisito.get(3).getTurmas().get(1)));
        assertEquals("Matriculado", sistemaAcademico.getRelatorio().get(ConstantesTest.disciplinasSemPreRequisito.get(4).getTurmas().get(2)));
        assertEquals("Matriculado", sistemaAcademico.getRelatorio().get(ConstantesTest.disciplinasSemPreRequisito.get(5).getTurmas().get(3)));
        assertEquals("Matriculado", sistemaAcademico.getRelatorio().get(ConstantesTest.disciplinasSemPreRequisito.get(7).getTurmas().get(5)));
        assertEquals("Carga horária máxima excedida. Turma descartada.", sistemaAcademico.getRelatorio().get(ConstantesTest.disciplinasSemPreRequisito.get(6).getTurmas().get(4)));
    }

    @Test
    public void testHistoricoAtualizado() {
        SistemaAcademico sistemaAcademico = new SistemaAcademico(alunos.get(8), ConstantesTest.todasDisciplinas);
        
        alunos.get(8).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(2).getTurmas().get(0));
        alunos.get(8).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(3).getTurmas().get(1));
        alunos.get(8).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(4).getTurmas().get(2));
        alunos.get(8).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(5).getTurmas().get(3));
        alunos.get(8).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(6).getTurmas().get(4));
        alunos.get(8).requererMatricula(ConstantesTest.disciplinasSemPreRequisito.get(7).getTurmas().get(5));

        sistemaAcademico.fazerMatricula();
        
        assertTrue(alunos.get(8).getHistorico().containsKey(ConstantesTest.disciplinasSemPreRequisito.get(2)));
        assertTrue(alunos.get(8).getHistorico().containsKey(ConstantesTest.disciplinasSemPreRequisito.get(3)));
        assertTrue(alunos.get(8).getHistorico().containsKey(ConstantesTest.disciplinasSemPreRequisito.get(4)));
        assertTrue(alunos.get(8).getHistorico().containsKey(ConstantesTest.disciplinasSemPreRequisito.get(5)));
        assertTrue(alunos.get(8).getHistorico().containsKey(ConstantesTest.disciplinasSemPreRequisito.get(7)));
    }

}
