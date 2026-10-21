# Agenda Acadêmica CEUB

**Disciplina:** Desenvolvimento Mobile (CEUB), atividade prática da Aula 10
**Linguagem:** Kotlin
**Interface:** Jetpack Compose

## Objetivo

Primeira versão de um aplicativo Android para ajudar estudantes do CEUB a organizar
as atividades acadêmicas. O usuário cadastra uma atividade de cada uma de 3 disciplinas
e o aplicativo mostra a **situação** e a **prioridade** de cada uma, além de um resumo geral.

## Telas e Activities

O aplicativo tem **4 telas visuais** (slide 6 da Aula 10) feitas com **2 Activities**:

| Tela visual | Activity | Conteúdo |
|---|---|---|
| 1. Disciplina 1 de 3 | `MainActivity` | Imagem, 5 campos e botão **PRÓXIMA** (fundo azul) |
| 2. Disciplina 2 de 3 | `MainActivity` | Imagem, 5 campos e botão **PRÓXIMA** (fundo verde) |
| 3. Disciplina 3 de 3 | `MainActivity` | Imagem, 5 campos e botão **VER AGENDA** (fundo roxo) |
| 4. Minha Agenda | `ResultadoActivity` | Tabela, Resumo Geral e botão **VOLTAR AO INÍCIO** |

- **MainActivity:** as 3 etapas do cadastro são controladas por estado
  (`remember` e `mutableStateOf`). A variável `etapa` indica se a tela mostra a
  Disciplina 1, 2 ou 3. Cada etapa valida e guarda os dados antes de avançar.
- **ResultadoActivity:** é aberta por um **Intent explícito**, que leva os dados das
  3 disciplinas com `putExtra()`. Ela recupera os dados com `getStringExtra()`,
  `getIntExtra()`, `getDoubleExtra()` e `getBooleanExtra()`, calcula situação e
  prioridade e mostra a tabela e o resumo.
- **VOLTAR AO INÍCIO** chama `finish()`. A `MainActivity`, que já está na pilha e
  voltou para a Disciplina 1, aparece de novo. Assim nenhuma Activity nova é criada.

Campos de cada disciplina:

| Informação | Tipo | Chave no Intent |
|---|---|---|
| Disciplina | `String` | `DISCIPLINA_1`, `DISCIPLINA_2`, `DISCIPLINA_3` |
| Atividade | `String` | `ATIVIDADE_1`, `ATIVIDADE_2`, `ATIVIDADE_3` |
| Dias até a entrega | `Int` | `DIAS_1`, `DIAS_2`, `DIAS_3` |
| Valor da atividade | `Double` | `VALOR_1`, `VALOR_2`, `VALOR_3` |
| Atividade concluída? | `Boolean` | `CONCLUIDA_1`, `CONCLUIDA_2`, `CONCLUIDA_3` |

## Validação

Antes de avançar, o aplicativo verifica se a disciplina e a atividade foram
informadas e converte os números com `toIntOrNull()` (dias) e `toDoubleOrNull()`
(valor). Se a conversão falhar (ex.: dias `abc` ou valor `vinte`), aparece a mensagem
**"Dados inválidos"** e a tela continua na mesma etapa. Dias iguais ou menores que
zero são aceitos, porque fazem parte da regra PRAZO ENCERRADO. O valor aceita
vírgula ou ponto (`7,5` ou `7.5`).

## Regras

**Situação**, verificada nesta ordem:

1. Atividade concluída: **CONCLUÍDA**
2. Senão, dias ≤ 0: **PRAZO ENCERRADO**
3. Senão, de 1 a 3 dias: **URGENTE**
4. Senão, de 4 a 7 dias: **ATENÇÃO**
5. Senão: **NO PRAZO**

**Prioridade**, calculada de forma independente da situação:

1. Dias ≤ 3 **&&** valor ≥ 20 pontos: **ALTA**
2. Senão, dias ≤ 7: **MÉDIA**
3. Senão: **BAIXA**

**Resumo geral:** total de atividades (3), concluídas, pendentes (não concluídas) e
urgentes (situação URGENTE).

Exemplo do slide 6: Desenvolvimento Mobile / Projeto Android / 3 dias / 20 pontos
→ URGENTE e ALTA; Banco de Dados / Lista SQL / 8 / 10 → NO PRAZO e BAIXA;
Engenharia de Software / Trabalho em Grupo / 1 / 30 → URGENTE e ALTA.
Resumo: 3 atividades, 0 concluídas, 3 pendentes, 2 urgentes.

## Principais arquivos

```text
app/src/main/
├── AndroidManifest.xml                  # registra as 2 Activities
├── java/br/ceub/agendaacademica/
│   ├── MainActivity.kt                  # telas 1, 2 e 3 (cadastro)
│   └── ResultadoActivity.kt             # tela 4 (Minha Agenda)
└── res/drawable/                        # imagens usadas com painterResource
    ├── livros_formatura.png
    ├── livro_ideia.png
    ├── notebook_checklist.png
    └── calendario_agenda.png
```

Versões: Android Gradle Plugin 9.0.1, Gradle 9.1.0 (wrapper), Kotlin 2.2.10,
Compose BOM 2026.02.00, `compileSdk` e `targetSdk` 36, `minSdk` 24 (Android 7.0).

## Como abrir no Android Studio

1. Use o **Android Studio Otter 3 Feature Drop (2025.2.3) ou mais recente**.
2. Clone o repositório:
   `git clone https://github.com/Helio965/Desenvolvimrnto-Mobile.git`
   (ou, no Android Studio, **File > New > Project from Version Control**).
3. Abra a pasta do repositório em **File > Open**.
4. Aguarde a sincronização do Gradle (na primeira vez é preciso internet). Se o
   Android Studio pedir o **Android SDK 36**, aceite a instalação.

## Como executar no emulador

1. Abra o **Device Manager** e crie ou inicie um dispositivo virtual (AVD).
2. Selecione o dispositivo e o módulo **app** na barra superior e clique em **Run ▶**.
3. Preencha as 3 disciplinas e toque em **VER AGENDA**.
4. No **Logcat**, filtre por `MAIN:` ou `RESULTADO:` para acompanhar as mensagens do app.

## Como gerar o APK para entrega

1. No Android Studio, acesse **Build > Generate App Bundles or APKs > Generate APKs**
   (o nome do menu pode variar entre versões).
2. Quando terminar, clique em **locate** na notificação ou abra:
   `app/build/outputs/apk/debug/app-debug.apk`

Pelo terminal, o mesmo APK pode ser gerado com:

```bash
./gradlew :app:assembleDebug      # Linux / macOS
gradlew.bat :app:assembleDebug    # Windows
```
