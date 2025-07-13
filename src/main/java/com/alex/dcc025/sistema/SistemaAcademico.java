package com.alex.dcc025.sistema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alex.dcc025.aluno.Aluno;
import com.alex.dcc025.constantes.Constantes;
import com.alex.dcc025.disciplinas.Disciplina;
import com.alex.dcc025.disciplinas.DisciplinaEletiva;
import com.alex.dcc025.disciplinas.DisciplinaObrigatoria;
import com.alex.dcc025.disciplinas.DisciplinaOptativa;
import com.alex.dcc025.exceptions.matricula.CargaHorariaExcedidaException;
import com.alex.dcc025.exceptions.matricula.CoRequisitoNaoAtendido;
import com.alex.dcc025.exceptions.matricula.ConflitoDeHorarioException;
import com.alex.dcc025.exceptions.matricula.GerenciamentoVagasException;
import com.alex.dcc025.exceptions.matricula.PrerequisitoNaoCumpridoException;
import com.alex.dcc025.exceptions.matricula.TurmaCheiaException;
import com.alex.dcc025.exceptions.matricula.ValidacaoMatriculaException;
import com.alex.dcc025.turma.Turma;
import com.alex.dcc025.util.Teclado;
import com.alex.dcc025.validadores.ValidadorPreRequisito;

public class SistemaAcademico {

    private Aluno alunoSelecionado;

    private Map<Disciplina, List<Turma>> turmasDisponiveis = new HashMap<>();

    private Map<Turma, String> relatorio = new HashMap<>();

    private boolean running = true;

    private boolean relatorioGerado = false;


    public SistemaAcademico(Aluno alunoSelecionado, List<Disciplina> disciplinas) {

        for (Disciplina disciplina : disciplinas) {
            List<Turma> turmas = new ArrayList<>(disciplina.getTurmas());
            turmasDisponiveis.put(disciplina, turmas);
        }

        this.alunoSelecionado = alunoSelecionado;

    }

    public void run() {

        while (running) {
            Teclado.limparTela();
            boasVindas();

            mostrarOpcoes();

            int opcao = escolherOpcao();

            running = !executarOpcao(opcao);
        }

    }

    private boolean executarOpcao(int opcao) {

        boolean sair = false;

        Teclado.limparTela();

        switch (opcao) {
            

            case 1 -> {
                
                mostrarHistorico();
                Teclado.esperarInput();
            }
            case 2 -> {

                mostrarTurmasDisponiveis();

                escolherERemoverTurma();

            }
            case 3 -> {
                mostrarPlanejamento();
                Teclado.esperarInput();
            }
            case 4 -> {
                
                relatorioGerado = false;

                relatorio.clear();

                mostrarTurmasRequeridas();

                if (!alunoSelecionado.getPlanejamento().isEmpty()) {
                    if(!fazerMatricula()) {
                        System.out.println("Matrícula não realizada. Verifique co-requisitos.");
                    } else {
                        relatorioGerado = true;
                        
                        System.out.println("Matrícula finalizada!");

                        for (Turma turma : alunoSelecionado.getPlanejamento()) {
                            turmasDisponiveis.get(turma.getDisciplina()).add(turma);
                        }

                        alunoSelecionado.getPlanejamento().clear();
                    }

                }
                Teclado.esperarInput();                
            }
            case 5 -> {
                if (!relatorioGerado) { System.out.println("Não há relatório a ser mostrado."); Teclado.esperarInput(); break; }

                System.out.println("Relatório de matrícula:");
                for (Turma turma : relatorio.keySet()) {
                    System.out.println(turma.nomeQualificado() + " - " + relatorio.get(turma));
                }

                Teclado.esperarInput();
            }
            case 6 -> {
                sair = true;
            }
        }
        
        return sair;
    }

    
    private int escolherOpcao() {
        
        List<Integer> opcoesValidas = List.of(1, 2, 3, 4, 5, 6);
        
        int opcao;
        
        do {
            opcao = Teclado.lerInteiro();
            limparLinhas(1);
        } while (!opcoesValidas.contains(opcao));
        
        return opcao;
    }
    
    private void escolherERemoverTurma() {
        
        System.out.println("\nEscolha uma turma (Código e turma, retorna ao menu caso não exista):\n");
        
        String turmaString = Teclado.ler();
        
        if (!turmaString.matches("([A-Z]{3,3}[0-9]{3,3}) ([A-Z])")) return;
        
        String[] partes = turmaString.split(" ");
        
        Disciplina disciplinaSelecionada = Constantes.codigoToDisciplina.get(partes[0]);
        
        if (disciplinaSelecionada == null) return;
        
        Turma turmaSelecionada = null;
        
        for (Turma turma : turmasDisponiveis.get(disciplinaSelecionada)) {
            if (turma.getId().equals(partes[1])) {
                turmaSelecionada = turma;
                break;
            }            
        }
        
        if (turmaSelecionada == null) return;
        
        alunoSelecionado.requererMatricula(turmaSelecionada);
        
        turmasDisponiveis.get(disciplinaSelecionada).remove(turmaSelecionada);
        
    }
    
    
    public boolean fazerMatricula() {
        
        List<Turma> turmasPlanejadas = alunoSelecionado.getPlanejamento();
        
        List<Turma> turmasObrigatoriasComCoRequisito = new ArrayList<>();
        List<Turma> turmasEletivasComCoRequisito = new ArrayList<>();
        List<Turma> turmasOptativasComCoRequisito = new ArrayList<>();
        
        List<Turma> turmasObrigatorias = new ArrayList<>();
        List<Turma> turmasEletivas = new ArrayList<>();
        List<Turma> turmasOptativas = new ArrayList<>();
        
        List<Turma> turmasMatriculadas = new ArrayList<>();
        
        for (Turma turma : turmasPlanejadas) {
    
            try {
                verificarCoRequisito(turma);
            } catch (ValidacaoMatriculaException e) {
                System.out.println("Selecione co-requisito: " + e.getMessage());
                return false;
            }
            
        }
        
        for (Turma turma : turmasPlanejadas) {
            if (turma.getDisciplina().getCoRequisitos().isEmpty()) continue;
            
            if (turma.getDisciplina() instanceof DisciplinaObrigatoria) {
                turmasObrigatoriasComCoRequisito.add(turma);
            } else if (turma.getDisciplina() instanceof DisciplinaEletiva) {
                turmasEletivasComCoRequisito.add(turma);
            } else if (turma.getDisciplina() instanceof DisciplinaOptativa) {
                turmasOptativasComCoRequisito.add(turma);
            }

        }
        

        try {
            matricularCoRequisitos(turmasObrigatoriasComCoRequisito, turmasMatriculadas);
        } catch (ValidacaoMatriculaException | GerenciamentoVagasException e) {
            turmasObrigatoriasComCoRequisito.forEach(turma -> {
                relatorio.put(turma, e.getMessage());
            });
        }
        finally {
            turmasPlanejadas.removeAll(turmasObrigatoriasComCoRequisito);
        }

        for (Turma turma : turmasPlanejadas) {
            if (turma.getDisciplina() instanceof DisciplinaObrigatoria) {
                turmasObrigatorias.add(turma);
            } else if (turma.getDisciplina() instanceof DisciplinaEletiva) {
                turmasEletivas.add(turma);
            } else if (turma.getDisciplina() instanceof DisciplinaOptativa) {
                turmasOptativas.add(turma);
            }
        }
        
        try {
            matricularCoRequisitos(turmasEletivasComCoRequisito, turmasMatriculadas);
        } catch (ValidacaoMatriculaException | GerenciamentoVagasException e) {
            turmasEletivasComCoRequisito.forEach(turma -> {
                relatorio.put(turma, e.getMessage());
            });
        }
        finally {
            turmasPlanejadas.removeAll(turmasEletivasComCoRequisito);
        }


        for (Turma turma : turmasObrigatorias) {
            try {
                matricularTurma(turma, turmasObrigatorias, turmasMatriculadas);
            } catch (ValidacaoMatriculaException | GerenciamentoVagasException e) {
                relatorio.put(turma, e.getMessage());
            }
        }

        try {
            matricularCoRequisitos(turmasEletivasComCoRequisito, turmasMatriculadas);
        } catch (ValidacaoMatriculaException | GerenciamentoVagasException e) {
            turmasEletivasComCoRequisito.forEach(turma -> {
                relatorio.put(turma, e.getMessage());
            });
        }
        finally {
            turmasPlanejadas.removeAll(turmasEletivasComCoRequisito);
        }

        for (Turma turma : turmasEletivas) {
            try {
                matricularTurma(turma, turmasEletivas, turmasMatriculadas);
            } catch (ValidacaoMatriculaException | GerenciamentoVagasException e) {
                relatorio.put(turma, e.getMessage());
            }
        }

        for (Turma turma : turmasOptativas) {
            try {
                matricularTurma(turma, turmasOptativas, turmasMatriculadas);
            } catch (ValidacaoMatriculaException | GerenciamentoVagasException e) {
                relatorio.put(turma, e.getMessage());
            }
        }

        for (Turma turma : turmasMatriculadas) {
            alunoSelecionado.addToHistorico(turma.getDisciplina(), 60);
        }

        return true;
    }

    public void matricularCoRequisitos(List<Turma> turmas, List<Turma> turmasMatriculadas) throws ValidacaoMatriculaException, GerenciamentoVagasException {
        
        int cargaHorariaAcumulada = 0;
        
        for (Turma turma : alunoSelecionado.getPlanejamento()) {
            cargaHorariaAcumulada += turma.getDisciplina().getCargaHoraria();
        }
        
        for (Turma turma : turmas) {
            for (Turma turma2 : turmas) {
                if (turma == turma2)  continue; // Não verificar a si mesmo

                if (turma.getHorario().horarioConflitante(turma2.getHorario())) {
                    throw new ConflitoDeHorarioException("Horário da turma " + turma.nomeQualificado() + " conflita com a turma " + turma2.nomeQualificado());
                }
                    
            }
        }

        for (Turma turma : turmasMatriculadas) {
            for (Turma turma2 : turmas) {
                if (turma2.getHorario().horarioConflitante(turma.getHorario())) {
                    throw new ConflitoDeHorarioException("Horário da turma " + turma2.nomeQualificado() + " conflita com a turma já matriculada " + turma.nomeQualificado());
                }
            }
        }

        for (Turma turma : turmas) {
            for (ValidadorPreRequisito preRequisito : turma.getDisciplina().getPreRequisitos()) {
                if (!preRequisito.validar(alunoSelecionado)) {
                    throw new PrerequisitoNaoCumpridoException("Pré-requisito " + preRequisito + " não cumprido para a disciplina " + turma.getDisciplina().getNome());
                }
            }
        }


        
        for (Turma turma : turmas) {
            if (cargaHorariaAcumulada + turma.getDisciplina().getCargaHoraria() > alunoSelecionado.getCargaHorariaMaxima()) {
                throw new CargaHorariaExcedidaException("Carga horária máxima excedida. Turma descartada.");

            }
            cargaHorariaAcumulada += turma.getDisciplina().getCargaHoraria();
        }

        
        for (Turma turma : turmas) {
            try {
                turma.matricularAluno(alunoSelecionado);
                relatorio.put(turma, "Matriculado");
            } catch (TurmaCheiaException e) {
                for (Turma turma2 : turmas) {
                    relatorio.put(turma, e.getMessage());
                    turma2.desmatricularAluno(alunoSelecionado);
                }
            }
        }
        turmasMatriculadas.addAll(turmas);
    }

    public void matricularTurma(Turma turma, List<Turma> listaAtual, List<Turma> turmasMatriculadas) throws ValidacaoMatriculaException, GerenciamentoVagasException {
        
        int cargaHorariaAcumulada = 0;
        
        for (Turma t : turmasMatriculadas) {
            cargaHorariaAcumulada += t.getDisciplina().getCargaHoraria();
        }
        
        for (Turma t : listaAtual) {
            if (t == turma) continue; // Não verificar a si mesmo
            
            if (t.getHorario().horarioConflitante(turma.getHorario())) {
                throw new ConflitoDeHorarioException("Horário da turma " + turma.nomeQualificado() + " conflita com a turma " + t.nomeQualificado());
            }

        }

        for (Turma t : turmasMatriculadas) {
            if (t.getHorario().horarioConflitante(turma.getHorario())) {
                throw new ConflitoDeHorarioException("Horário da turma " + turma.nomeQualificado() + " conflita com a turma já matriculada " + t.nomeQualificado());
            }
        }

        for (ValidadorPreRequisito preRequisito : turma.getDisciplina().getPreRequisitos()) {
            if (!preRequisito.validar(alunoSelecionado)) { 
                throw new PrerequisitoNaoCumpridoException("Pré-requisito " + preRequisito + " não cumprido para a disciplina " + turma.getDisciplina().getNome());
            }
        }


        if (cargaHorariaAcumulada + turma.getDisciplina().getCargaHoraria() > alunoSelecionado.getCargaHorariaMaxima()) {
            throw new CargaHorariaExcedidaException("Carga horária máxima excedida. Turma descartada.");
        }

        turma.matricularAluno(alunoSelecionado);
        turmasMatriculadas.add(turma);
        relatorio.put(turma, "Matriculado");


    }

    public void verificarCoRequisito(Turma turma) throws ValidacaoMatriculaException {

        List<Disciplina> disciplinasPlanejadas = new ArrayList<>(alunoSelecionado.getPlanejamento().size());

        for (Turma t : alunoSelecionado.getPlanejamento()) {
            disciplinasPlanejadas.add(t.getDisciplina());
        }

        if (turma.getDisciplina().getCoRequisitos().isEmpty()) return;

        for (Disciplina coRequisito : turma.getDisciplina().getCoRequisitos()) {
            if (!disciplinasPlanejadas.contains(coRequisito)) {
                throw new CoRequisitoNaoAtendido("Co-requisito " + coRequisito.getCodigo() + " não selecionado junto à disciplina " + turma.getDisciplina().getCodigo());
            }
        }
        

    }
    
    private void boasVindas() {
        System.out.println("Bem-vindo ao Sistema Acadêmico!");
        System.out.println("Aluno: " + alunoSelecionado.getNome());
        System.out.println("Matrícula: " + alunoSelecionado.getMatricula());
    }
    
    private void mostrarOpcoes() {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Mostrar histórico de disciplinas");
        System.out.println("2. Requerer matrícula em uma turma");
        System.out.println("3. Ver planejamento");
        System.out.println("4. Matricular");
        System.out.println("5. Mostrar relatório");
        System.out.println("6. Sair");
    }

    private void mostrarHistorico() {
        System.out.println("Disciplinas cursadas por " + alunoSelecionado.getNome() + ":");
        for (Disciplina disciplina : alunoSelecionado.getHistorico().keySet()) {
            System.out.println("\t" + disciplina.getCodigo() + " - " + disciplina.getNome() + " - Nota: " + alunoSelecionado.getHistorico().get(disciplina));
        }
        
        
    }
    
    private void mostrarTurmasRequeridas() {
        System.out.println("Turmas requeridas:");
        
        if (alunoSelecionado.getPlanejamento().isEmpty()) {
            System.out.println("Nenhuma turma requerida.");
            Teclado.esperarInput();
            return;
        }

        for (Turma turma : alunoSelecionado.getPlanejamento()) {
            System.out.println(turma.getDisciplina().getNome() + " - " + turma.getId());
        }

        Teclado.esperarInput();
    }

    private void mostrarPlanejamento() {
        System.out.println("Planejamento de " + alunoSelecionado.getNome() + ":");

        if (alunoSelecionado.getPlanejamento().isEmpty()) {
            System.out.println("Vazio");
            return;
        }

        for (Turma turma : alunoSelecionado.getPlanejamento()) {
            System.out.println("\t" + turma.getDisciplina().getCodigo() + " - " + turma.getDisciplina().getNome() + " - Turma: " + turma.getId());
        }
    }

    private void mostrarTurmasDisponiveis() {
        System.out.println("Turmas disponíveis:");
        for (Disciplina disciplina : turmasDisponiveis.keySet()) {
            System.out.println(disciplina.getCodigo() + " - " + disciplina.getNome());
            
            for (Turma turma : turmasDisponiveis.get(disciplina)) {
                System.out.println("\tTurma: " + turma.getId() + ", Horário: " + turma.horarioLegivel());
            }
        }
    }
    
    private void limparLinhas(int linhas) {
        for (int i = 0; i < linhas; i++) {
            System.out.print("\033[1A");
            System.out.print("\033[2K");
        }
    }

    public Map<Turma, String> getRelatorio() {
        return relatorio;
    }

}
