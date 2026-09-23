package br.com.petadote;

import br.com.petadote.model.*;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("  PETADOTE - ONG DE ADOÇÃO DE ANIMAIS");
        System.out.println("==============================================\n");

        // REQ01 - Cadastrar abrigos/lares temporários
        Abrigo abrigoCentral = new Abrigo("Abrigo Central", "Rua das Flores, 123", 3);
        Abrigo larTemporario = new Abrigo("Lar Temporário Esperança", "Av. dos Anjos, 45", 4);
        System.out.println("[REQ01] Abrigos cadastrados:");
        System.out.println("  - " + abrigoCentral.getNome() + " | capacidade " + abrigoCentral.getCapacidade());
        System.out.println("  - " + larTemporario.getNome() + " | capacidade " + larTemporario.getCapacidade());

        // REQ02 - Cadastrar animais com herança (Cao, Gato, Outro)
        Cao rex = new Cao("Rex", LocalDate.of(2023, 5, 10), "Labrador Retriever");
        Gato mimi = new Gato("Mimi", LocalDate.of(2024, 1, 15), "Siamês");
        Outro coelho = new Outro("Pernalonga", LocalDate.of(2024, 3, 1), "Coelho");
        Cao thor = new Cao("Thor", LocalDate.of(2022, 8, 20), "Pastor Alemão");
        Gato luna = new Gato("Luna", LocalDate.of(2024, 6, 5), "Persa");
        Cao bob = new Cao("Bob", LocalDate.of(2023, 11, 11), "Golden Retriever");

        // REQ03 - Informações reais da raça (InfoRaca; em produção via TheDogAPI/TheCatAPI)
        rex.setInfoRaca(new InfoRaca("Grande", 30.0, "Amigável, Inteligente, Leal"));
        mimi.setInfoRaca(new InfoRaca("Pequeno", 4.5, "Ativo, Sociável, Curioso"));
        luna.setInfoRaca(new InfoRaca("Médio", 5.0, "Calmo, Independente, Afetuoso"));
        System.out.println("\n[REQ03] Info da raça (simulada): " + rex.getRaca()
                + " | porte " + rex.getInfoRaca().getPorte()
                + " | peso médio " + rex.getInfoRaca().getPesoMedio() + " kg");

        // REQ19 - Validar capacidade do abrigo
        System.out.println("\n[REQ19] Capacidade do abrigo:");
        System.out.println("  Adicionar Rex -> " + abrigoCentral.adicionarAnimal(rex));
        System.out.println("  Adicionar Mimi -> " + abrigoCentral.adicionarAnimal(mimi));
        System.out.println("  Adicionar Pernalonga -> " + abrigoCentral.adicionarAnimal(coelho));
        System.out.println("  Abrigo Central: " + abrigoCentral.getQuantidadeAnimais() + "/" + abrigoCentral.getCapacidade());
        System.out.println("  Adicionar Thor (capacidade cheia) -> " + abrigoCentral.adicionarAnimal(thor) + " (esperado: false)");

        // REQ20 - Bloquear exclusão de abrigo com animais
        System.out.println("\n[REQ20] Exclusão de abrigo:");
        System.out.println("  Abrigo Central pode excluir? " + abrigoCentral.podeExcluir() + " (esperado: false)");
        System.out.println("  Lar Temporário pode excluir? " + larTemporario.podeExcluir() + " (esperado: true)");

        // REQ04 - Cadastrar adotantes
        Adotante maria = new Adotante("Maria da Silva", "123.456.789-00", "Rua A, 10", 1);
        Adotante joao = new Adotante("João Souza", "987.654.321-00", "Rua B, 20", 0);
        System.out.println("\n[REQ04] Adotantes: " + maria.getNome() + " e " + joao.getNome());

        // REQ06 + REQ14 - Abrir processo (status inicial Em Avaliação)
        System.out.println("\n[REQ06/REQ14] Abertura de processo:");
        ProcessoAdocao pRex = new ProcessoAdocao(rex, maria);
        System.out.println("  Abrir processo Rex/Maria -> " + pRex.abrirProcesso() + " | status: " + pRex.getStatus());
        System.out.println("  Status do Rex: " + rex.getStatus());
        ProcessoAdocao pRex2 = new ProcessoAdocao(rex, joao);
        System.out.println("  Abrir 2º processo para Rex -> " + pRex2.abrirProcesso() + " (esperado: false)");

        // REQ07 + REQ15 - Avaliação prévia com parecer do responsável do abrigo
        System.out.println("\n[REQ07/REQ15] Avaliação prévia:");
        pRex.registrarAvaliacao(new Avaliacao(LocalDate.now(), "Responsável do Abrigo Central", Parecer.POSITIVO, "Animal saudável e dócil"));
        System.out.println("  Avaliação favorável? " + pRex.avaliacaoFavoravel());
        System.out.println("  Aprovar processo -> " + pRex.aprovarProcesso() + " | status: " + pRex.getStatus());

        ProcessoAdocao pMimi = new ProcessoAdocao(mimi, joao);
        pMimi.abrirProcesso();
        System.out.println("  Concluir processo do Mimi SEM avaliação -> " + pMimi.concluirProcesso() + " (esperado: false)");

        // REQ09 + REQ18 - Agendar visitas pós-adoção
        System.out.println("\n[REQ09/REQ18] Visitas pós-adoção:");
        pRex.agendarVisita(new Visita(LocalDate.now().plusDays(30)));
        pRex.agendarVisita(new Visita(LocalDate.now().plusDays(60)));
        System.out.println("  2 visitas agendadas para o processo do Rex");

        // REQ08 - Concluir processo (animal vira Adotado)
        System.out.println("\n[REQ08] Conclusão do processo:");
        System.out.println("  Concluir processo do Rex -> " + pRex.concluirProcesso() + " | status: " + pRex.getStatus());
        System.out.println("  Status do Rex: " + rex.getStatus() + " (esperado: ADOTADO)");

        // REQ17 - Bloquear conclusão de animal em tratamento veterinário
        System.out.println("\n[REQ17] Animal em tratamento veterinário:");
        mimi.setStatus(StatusAnimal.EM_TRATAMENTO_VETERINARIO);
        pMimi.registrarAvaliacao(new Avaliacao(LocalDate.now(), "Responsável do Abrigo Central", Parecer.POSITIVO, "Em recuperação"));
        pMimi.aprovarProcesso();
        pMimi.agendarVisita(new Visita(LocalDate.now().plusDays(30)));
        pMimi.agendarVisita(new Visita(LocalDate.now().plusDays(60)));
        System.out.println("  Concluir processo do Mimi (em tratamento) -> " + pMimi.concluirProcesso() + " (esperado: false)");

        // REQ10 - Registrar resultado de visita
        System.out.println("\n[REQ10] Resultado de visita:");
        Visita visitaRex = pRex.getVisitas().get(0);
        visitaRex.registrarResultado("Animal saudável e adaptado", "Sem observações relevantes");
        System.out.println("  Visita concluída? " + visitaRex.isConcluida() + " | Situação: " + visitaRex.getSituacaoAnimal());

        // REQ16 - Limite de 3 processos ativos por adotante
        System.out.println("\n[REQ16] Limite de processos ativos:");
        larTemporario.adicionarAnimal(thor);
        larTemporario.adicionarAnimal(luna);
        larTemporario.adicionarAnimal(bob);
        Cao extra = new Cao("Duke", LocalDate.of(2024, 9, 1), "Beagle");
        larTemporario.adicionarAnimal(extra);
        ProcessoAdocao p1 = new ProcessoAdocao(thor, joao);
        ProcessoAdocao p2 = new ProcessoAdocao(luna, joao);
        ProcessoAdocao p3 = new ProcessoAdocao(bob, joao);
        System.out.println("  Processo 1 (Thor) -> " + p1.abrirProcesso());
        System.out.println("  Processo 2 (Luna) -> " + p2.abrirProcesso());
        System.out.println("  Processo 3 (Bob) -> " + p3.abrirProcesso());
        ProcessoAdocao p4 = new ProcessoAdocao(extra, joao);
        System.out.println("  Processo 4 (Duke) -> " + p4.abrirProcesso() + " (esperado: false, limite de 3 ativos)");

        // REQ05 - Histórico de processos do adotante
        System.out.println("\n[REQ05] Histórico de processos:");
        System.out.println("  Maria possui " + maria.getHistoricoProcessos().size() + " processo(s)");
        System.out.println("  João possui " + joao.getHistoricoProcessos().size() + " processo(s)");

        // REQ11 - Relatório de animais disponíveis por abrigo e espécie
        System.out.println("\n[REQ11] Animais disponíveis por abrigo e espécie:");
        gerarRelatorioDisponiveis(abrigoCentral);
        gerarRelatorioDisponiveis(larTemporario);

        // REQ12 - Relatório de ações concluídas por período (CSV)
        System.out.println("\n[REQ12] Ações concluídas (CSV):");
        System.out.print(gerarRelatorioCsv(List.of(pRex, pMimi, p1, p2, p3),
                LocalDate.now().minusMonths(1), LocalDate.now().plusMonths(1)));

        // REQ13 - Processos pendentes de avaliação há mais de 15 dias
        System.out.println("\n[REQ13] Processos pendentes há mais de 15 dias:");
        Gato sofia = new Gato("Sofia", LocalDate.of(2024, 4, 2), "Maine Coon");
        ProcessoAdocao pendente = new ProcessoAdocao(sofia, maria);
        pendente.abrirProcesso();
        pendente.setDataAbertura(LocalDate.now().minusDays(20));
        listarProcessosPendentes(List.of(pRex, pMimi, p1, p2, p3, pendente));

        System.out.println("\n==============================================");
        System.out.println("  FIM DA DEMONSTRAÇÃO");
        System.out.println("==============================================");
    }

    private static void gerarRelatorioDisponiveis(Abrigo abrigo) {
        System.out.println("  Abrigo: " + abrigo.getNome());
        for (Animal a : abrigo.getAnimais()) {
            String especie = a instanceof Cao ? "Cão" : a instanceof Gato ? "Gato" : "Outro";
            System.out.println("    - " + a.getNome() + " (" + especie + ") | status: " + a.getStatus());
        }
    }

    private static String gerarRelatorioCsv(List<ProcessoAdocao> processos, LocalDate inicio, LocalDate fim) {
        StringBuilder csv = new StringBuilder("animal;adotante;data_conclusao;status\n");
        for (ProcessoAdocao p : processos) {
            if (p.getStatus() == StatusProcesso.CONCLUIDO
                    && p.getDataConclusao() != null
                    && !p.getDataConclusao().isBefore(inicio)
                    && !p.getDataConclusao().isAfter(fim)) {
                csv.append(p.getAnimal().getNome())
                        .append(";").append(p.getAdotante().getNome())
                        .append(";").append(p.getDataConclusao())
                        .append(";").append(p.getStatus()).append("\n");
            }
        }
        return csv.toString();
    }

    private static void listarProcessosPendentes(List<ProcessoAdocao> processos) {
        LocalDate limite = LocalDate.now().minusDays(15);
        boolean encontrou = false;
        for (ProcessoAdocao p : processos) {
            if (p.getStatus() == StatusProcesso.EM_AVALIACAO && p.getDataAbertura().isBefore(limite)) {
                System.out.println("    - Animal: " + p.getAnimal().getNome()
                        + " | Adotante: " + p.getAdotante().getNome()
                        + " | Aberto em: " + p.getDataAbertura());
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("    Nenhum processo pendente há mais de 15 dias.");
        }
    }
}