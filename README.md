IMC calculator

Aplicativo Android para cálculo de IMC e métricas adicionais de saúde, com histórico persistido,
tela de detalhes, ajuda com fórmulas, exportação do histórico em CSV e lembretes por notificação.

> Projeto acadêmico da disciplina **Programação para Dispositivos Móveis**.  
> Desenvolvido para executar corretamente no **emulador do Android Studio**.

Funcionalidades

Cálculos de saúde
- IMC (peso/altura) com valor numérico, classificação e interpretação
- TMB (Taxa Metabólica Basal) – Mifflin-St Jeor
- Necessidade calórica diária = TMB × fator de atividade (sedentário, leve, moderado, intenso)
- Peso ideal – Devine
- Percentual de gordura (estimativa) 

Persistência e navegação
- Histórico de medições salvo localmente com Room
- Tela de Histórico ordenada por data/hora
- Tela de Detalhes com todos os indicadores daquela medição
- Navegação com Navigation Compose (NavHost)

Extras 
- Exportar histórico em CSV e compartilhar (FileProvider + ACTION_SEND)
- Lembretes por notificação (WorkManager), com opção de testar

 Telas
- Home: entrada de dados, validações e cálculo
- Histórico: lista de medições salvas
- Detalhes: dados completos da medição selecionada
- Ajuda: fórmulas e explicações usadas no app
- Configurações: controle das notificações

Arquitetura (MVVM) e organização

O projeto segue uma arquitetura mínima baseada em MVVM, com separação de responsabilidades:

- `ui/`: telas Compose e componentes visuais
- `ui/viewmodel/`: ViewModels com estado e eventos
- `domain/`: regras de negócio e cálculos (use cases)
- `data/`: persistência (Room) e repositórios
- `reminder/`: worker, canal e agendamento de notificações

Decisão principal: toda lógica de cálculo fica fora dos composables (domínio/use cases).

Fórmulas (resumo)

- IMC: `peso(kg) / altura(m)^2`
- TMB (Mifflin-St Jeor):
  - Homem: `10*w + 6.25*h - 5*a + 5`
  - Mulher: `10*w + 6.25*h - 5*a - 161`
  - (w=kg, h=cm, a=anos)
- Calorias diárias: `TMB × fator` (1.2 / 1.375 / 1.55 / 1.725)
- Peso ideal (Devine):
  - Homem: `50 + 2.3*(pol - 60)`
  - Mulher: `45.5 + 2.3*(pol - 60)`
  - (pol = altura(cm)/2.54)
- % gordura (US Navy): estimativa com `log10()` usando circunferências (ver tela Ajuda).

Como rodar
1. Abrir no Android Studio
2. Iniciar um emulador, com API 34+
3. Executar: Run ▶ app

Observações
- Entradas são validadas 
- O histórico permanece salvo mesmo após fechar o app.
- A exportação CSV usa FileProvider para gerar e compartilhar o arquivo com segurança.
