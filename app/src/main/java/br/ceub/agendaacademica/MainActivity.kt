package br.ceub.agendaacademica

import android.content.Intent
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Primeira Activity: cadastro das 3 disciplinas em 3 etapas (Disciplina 1, 2 e 3 de 3).
// A troca de etapa é feita apenas com estado (remember + mutableStateOf).
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Desenha atrás das barras do sistema; o safeDrawingPadding() abaixo evita cortes
        enableEdgeToEdge()

        println("MAIN: onCreate")

        setContent {

            // Etapa atual do cadastro: 1, 2 ou 3
            var etapa by remember { mutableStateOf(1) }

            // Campos da etapa atual (o TextField sempre entrega texto: String)
            var disciplina by remember { mutableStateOf("") }
            var atividade by remember { mutableStateOf("") }
            var dias by remember { mutableStateOf("") }
            var valor by remember { mutableStateOf("") }
            var concluida by remember { mutableStateOf(false) }

            // Mensagem mostrada quando algum dado é inválido
            var mensagemErro by remember { mutableStateOf("") }

            // Dados da Disciplina 1, guardados depois de validados e convertidos
            var disciplina1 by remember { mutableStateOf("") }
            var atividade1 by remember { mutableStateOf("") }
            var dias1 by remember { mutableStateOf(0) }
            var valor1 by remember { mutableStateOf(0.0) }
            var concluida1 by remember { mutableStateOf(false) }

            // Dados da Disciplina 2
            var disciplina2 by remember { mutableStateOf("") }
            var atividade2 by remember { mutableStateOf("") }
            var dias2 by remember { mutableStateOf(0) }
            var valor2 by remember { mutableStateOf(0.0) }
            var concluida2 by remember { mutableStateOf(false) }

            // Cor de fundo, cor do botão, imagem e texto do botão de cada etapa
            var corFundo = Color(0xFFE3F2FD)
            var corBotao = Color(0xFF1565C0)
            var imagem = R.drawable.livros_formatura
            var textoBotao = "PRÓXIMA  →"

            if (etapa == 2) {
                corFundo = Color(0xFFE8F5E9)
                corBotao = Color(0xFF2E7D32)
                imagem = R.drawable.livro_ideia
            } else if (etapa == 3) {
                corFundo = Color(0xFFF1E9FB)
                corBotao = Color(0xFF6A3FC6)
                imagem = R.drawable.notebook_checklist
                textoBotao = "VER AGENDA  →"
            }

            // A cor principal do tema acompanha a etapa (botão, checkbox e campo em foco)
            MaterialTheme(colorScheme = lightColorScheme(primary = corBotao)) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(corFundo)
                        .safeDrawingPadding()
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Agenda Acadêmica",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D2B6B)
                    )

                    Text(
                        text = "Disciplina $etapa de 3",
                        fontSize = 18.sp,
                        color = Color(0xFF0D2B6B)
                    )

                    // Imagem local guardada em res/drawable
                    Image(
                        painter = painterResource(imagem),
                        contentDescription = "Imagem acadêmica",
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .height(150.dp)
                    )

                    // Formulário da disciplina
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    ) {

                        TextField(
                            value = disciplina,
                            onValueChange = { disciplina = it },
                            label = { Text("Nome da disciplina") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        TextField(
                            value = atividade,
                            onValueChange = { atividade = it },
                            label = { Text("Nome da atividade") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        TextField(
                            value = dias,
                            onValueChange = { dias = it },
                            label = { Text("Dias até a entrega") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        TextField(
                            value = valor,
                            onValueChange = { valor = it },
                            label = { Text("Valor da atividade") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = "Atividade concluída?")

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = concluida,
                                onCheckedChange = { concluida = it }
                            )
                            Text(text = "Sim")
                        }
                    }

                    if (mensagemErro != "") {
                        Text(
                            text = mensagemErro,
                            color = Color(0xFFC62828),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {

                            // Conversões seguras: se o texto não for um número, o resultado é null
                            val diasNumero = dias.trim().toIntOrNull()
                            // Aceita vírgula ou ponto como separador decimal (ex.: 7,5 ou 7.5)
                            val valorNumero = valor.trim().replace(",", ".").toDoubleOrNull()

                            if (disciplina.isBlank()) {
                                mensagemErro = "Informe o nome da disciplina."
                            } else if (atividade.isBlank()) {
                                mensagemErro = "Informe o nome da atividade."
                            } else if (diasNumero == null) {
                                mensagemErro = "Dados inválidos: os dias até a entrega devem ser um número inteiro."
                            } else if (valorNumero == null) {
                                mensagemErro = "Dados inválidos: o valor da atividade deve ser um número."
                            } else {

                                // Dados válidos
                                mensagemErro = ""

                                if (etapa == 1) {

                                    disciplina1 = disciplina.trim()
                                    atividade1 = atividade.trim()
                                    dias1 = diasNumero
                                    valor1 = valorNumero
                                    concluida1 = concluida

                                    println("MAIN: Disciplina 1 salva: $disciplina1")
                                    etapa = 2

                                } else if (etapa == 2) {

                                    disciplina2 = disciplina.trim()
                                    atividade2 = atividade.trim()
                                    dias2 = diasNumero
                                    valor2 = valorNumero
                                    concluida2 = concluida

                                    println("MAIN: Disciplina 2 salva: $disciplina2")
                                    etapa = 3

                                } else {

                                    // Etapa 3: Intent explícito para abrir a ResultadoActivity
                                    val intent = Intent(
                                        this@MainActivity,
                                        ResultadoActivity::class.java
                                    )

                                    // Disciplina 1
                                    intent.putExtra("DISCIPLINA_1", disciplina1)
                                    intent.putExtra("ATIVIDADE_1", atividade1)
                                    intent.putExtra("DIAS_1", dias1)
                                    intent.putExtra("VALOR_1", valor1)
                                    intent.putExtra("CONCLUIDA_1", concluida1)

                                    // Disciplina 2
                                    intent.putExtra("DISCIPLINA_2", disciplina2)
                                    intent.putExtra("ATIVIDADE_2", atividade2)
                                    intent.putExtra("DIAS_2", dias2)
                                    intent.putExtra("VALOR_2", valor2)
                                    intent.putExtra("CONCLUIDA_2", concluida2)

                                    // Disciplina 3 (dados da etapa atual)
                                    intent.putExtra("DISCIPLINA_3", disciplina.trim())
                                    intent.putExtra("ATIVIDADE_3", atividade.trim())
                                    intent.putExtra("DIAS_3", diasNumero)
                                    intent.putExtra("VALOR_3", valorNumero)
                                    intent.putExtra("CONCLUIDA_3", concluida)

                                    println("MAIN: abrindo ResultadoActivity")
                                    startActivity(intent)

                                    // Quando o usuário voltar da agenda, o cadastro recomeça na Disciplina 1
                                    etapa = 1
                                }

                                // Limpa os campos para a próxima disciplina
                                disciplina = ""
                                atividade = ""
                                dias = ""
                                valor = ""
                                concluida = false
                            }

                            if (mensagemErro != "") {
                                println("MAIN: etapa $etapa - $mensagemErro")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Text(
                            text = textoBotao,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
