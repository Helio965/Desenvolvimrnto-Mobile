package br.ceub.agendaacademica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Segunda Activity: "Minha Agenda".
// Recebe os dados das 3 disciplinas pelo Intent, calcula situação e prioridade
// de cada atividade e mostra a tabela com o resumo geral.
class ResultadoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1) Recebe os dados enviados pela MainActivity (mesmas chaves usadas no putExtra)
        // "?: """ usa um texto vazio caso a chave não seja encontrada
        val disciplina1 = intent.getStringExtra("DISCIPLINA_1") ?: ""
        val atividade1 = intent.getStringExtra("ATIVIDADE_1") ?: ""
        val dias1 = intent.getIntExtra("DIAS_1", 0)
        val valor1 = intent.getDoubleExtra("VALOR_1", 0.0)
        val concluida1 = intent.getBooleanExtra("CONCLUIDA_1", false)

        val disciplina2 = intent.getStringExtra("DISCIPLINA_2") ?: ""
        val atividade2 = intent.getStringExtra("ATIVIDADE_2") ?: ""
        val dias2 = intent.getIntExtra("DIAS_2", 0)
        val valor2 = intent.getDoubleExtra("VALOR_2", 0.0)
        val concluida2 = intent.getBooleanExtra("CONCLUIDA_2", false)

        val disciplina3 = intent.getStringExtra("DISCIPLINA_3") ?: ""
        val atividade3 = intent.getStringExtra("ATIVIDADE_3") ?: ""
        val dias3 = intent.getIntExtra("DIAS_3", 0)
        val valor3 = intent.getDoubleExtra("VALOR_3", 0.0)
        val concluida3 = intent.getBooleanExtra("CONCLUIDA_3", false)

        // 2) Processamento: situação e prioridade de cada atividade
        val situacao1 = calcularSituacao(concluida1, dias1)
        val prioridade1 = calcularPrioridade(dias1, valor1)

        val situacao2 = calcularSituacao(concluida2, dias2)
        val prioridade2 = calcularPrioridade(dias2, valor2)

        val situacao3 = calcularSituacao(concluida3, dias3)
        val prioridade3 = calcularPrioridade(dias3, valor3)

        // 3) Resumo geral com contadores
        val totalAtividades = 3
        var atividadesConcluidas = 0
        var atividadesUrgentes = 0

        if (concluida1) {
            atividadesConcluidas = atividadesConcluidas + 1
        }
        if (concluida2) {
            atividadesConcluidas = atividadesConcluidas + 1
        }
        if (concluida3) {
            atividadesConcluidas = atividadesConcluidas + 1
        }

        val atividadesPendentes = totalAtividades - atividadesConcluidas

        if (situacao1 == "URGENTE") {
            atividadesUrgentes = atividadesUrgentes + 1
        }
        if (situacao2 == "URGENTE") {
            atividadesUrgentes = atividadesUrgentes + 1
        }
        if (situacao3 == "URGENTE") {
            atividadesUrgentes = atividadesUrgentes + 1
        }

        println("RESULTADO: $disciplina1 | $atividade1 | $dias1 dias | $valor1 pontos | $situacao1 | $prioridade1")
        println("RESULTADO: $disciplina2 | $atividade2 | $dias2 dias | $valor2 pontos | $situacao2 | $prioridade2")
        println("RESULTADO: $disciplina3 | $atividade3 | $dias3 dias | $valor3 pontos | $situacao3 | $prioridade3")

        setContent {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE8EEF7)) // cor de fundo personalizada
                    .safeDrawingPadding()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Minha Agenda",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D2B6B)
                )

                Text(
                    text = "Resultado das Disciplinas",
                    fontSize = 18.sp,
                    color = Color(0xFF0D2B6B)
                )

                Image(
                    painter = painterResource(R.drawable.calendario_agenda),
                    contentDescription = "Calendário da agenda",
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .height(130.dp)
                )

                // Tabela com as 3 atividades, na ordem do cadastro
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(12.dp))
                ) {
                    LinhaCabecalho()
                    LinhaAtividade(disciplina1, atividade1, dias1, situacao1, prioridade1)
                    HorizontalDivider(color = Color(0xFFE0E6EF))
                    LinhaAtividade(disciplina2, atividade2, dias2, situacao2, prioridade2)
                    HorizontalDivider(color = Color(0xFFE0E6EF))
                    LinhaAtividade(disciplina3, atividade3, dias3, situacao3, prioridade3)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Resumo geral
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Resumo Geral",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D2B6B)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LinhaResumo("Total de atividades", totalAtividades)
                    LinhaResumo("Atividades concluídas", atividadesConcluidas)
                    LinhaResumo("Atividades pendentes", atividadesPendentes)
                    LinhaResumo("Atividades urgentes", atividadesUrgentes)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        // Fecha esta Activity e volta para a MainActivity,
                        // que já está na Disciplina 1 (não cria outra Activity na pilha)
                        println("RESULTADO: voltando ao início")
                        finish()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text(
                        text = "VOLTAR AO INÍCIO",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // Regra 1 - Situação da atividade (a ordem das verificações importa)
    fun calcularSituacao(concluida: Boolean, dias: Int): String {
        var situacao = ""

        if (concluida) {
            situacao = "CONCLUÍDA"
        } else if (dias <= 0) {
            situacao = "PRAZO ENCERRADO"
        } else if (dias <= 3) {
            situacao = "URGENTE"
        } else if (dias <= 7) {
            situacao = "ATENÇÃO"
        } else {
            situacao = "NO PRAZO"
        }

        return situacao
    }

    // Regra 2 - Prioridade da atividade (independente da situação)
    fun calcularPrioridade(dias: Int, valor: Double): String {
        var prioridade = ""

        if (dias <= 3 && valor >= 20.0) {
            prioridade = "ALTA"
        } else if (dias <= 7) {
            prioridade = "MÉDIA"
        } else {
            prioridade = "BAIXA"
        }

        return prioridade
    }
}

// Cabeçalho da tabela
@Composable
fun LinhaCabecalho() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1A3A6B), RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .padding(horizontal = 4.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextoCabecalho("Disciplina", Modifier.weight(1.65f))
        TextoCabecalho("Atividade", Modifier.weight(1.1f))
        TextoCabecalho("Prazo (dias)", Modifier.weight(0.6f))
        TextoCabecalho("Situação", Modifier.weight(1.2f))
        TextoCabecalho("Prioridade", Modifier.weight(1.05f))
    }
}

@Composable
fun TextoCabecalho(texto: String, modifier: Modifier) {
    Text(
        text = texto,
        color = Color.White,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = modifier.padding(horizontal = 2.dp)
    )
}

// Uma linha da tabela: Disciplina | Atividade | Prazo (dias) | Situação | Prioridade
@Composable
fun LinhaAtividade(
    disciplina: String,
    atividade: String,
    dias: Int,
    situacao: String,
    prioridade: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = disciplina,
            fontSize = 11.sp,
            modifier = Modifier
                .weight(1.65f)
                .padding(horizontal = 2.dp)
        )
        Text(
            text = atividade,
            fontSize = 11.sp,
            modifier = Modifier
                .weight(1.1f)
                .padding(horizontal = 2.dp)
        )
        Text(
            text = "$dias",
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(0.6f)
        )
        Etiqueta(situacao, Modifier.weight(1.2f))
        Etiqueta(prioridade, Modifier.weight(1.05f))
    }
}

// Situação ou prioridade com uma cor de destaque
@Composable
fun Etiqueta(texto: String, modifier: Modifier) {
    var corTexto = Color(0xFF1565C0) // CONCLUÍDA: azul
    var corFundo = Color(0xFFE3F2FD)

    if (texto == "URGENTE" || texto == "ALTA") {
        corTexto = Color(0xFFC62828) // vermelho
        corFundo = Color(0xFFFDE4E4)
    } else if (texto == "ATENÇÃO" || texto == "MÉDIA") {
        corTexto = Color(0xFFB45309) // laranja
        corFundo = Color(0xFFFFF0D6)
    } else if (texto == "NO PRAZO" || texto == "BAIXA") {
        corTexto = Color(0xFF2E7D32) // verde
        corFundo = Color(0xFFE3F4E5)
    } else if (texto == "PRAZO ENCERRADO") {
        corTexto = Color(0xFF424242) // cinza
        corFundo = Color(0xFFE6E6E6)
    }

    Text(
        text = texto,
        color = corTexto,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = modifier
            .padding(horizontal = 2.dp)
            .background(corFundo, RoundedCornerShape(6.dp))
            .padding(vertical = 5.dp, horizontal = 2.dp)
    )
}

// Uma linha do resumo geral: título à esquerda e quantidade à direita
@Composable
fun LinhaResumo(titulo: String, quantidade: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Text(
            text = titulo,
            fontSize = 15.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "$quantidade",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
    HorizontalDivider(color = Color(0xFFE0E6EF))
}
