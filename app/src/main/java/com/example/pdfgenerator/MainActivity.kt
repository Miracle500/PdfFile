@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.pdfgenerator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pdfgenerator.ui.theme.PdfGeneratorTheme
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.PDPageContentStream
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var inputText by remember { mutableStateOf("") }
            val pdfFilePath = "${externalCacheDir?.absolutePath}/generated_pdf.pdf"

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextInput(onTextChanged = { inputText = it })
                GeneratePdfButton {
                    generatePdf(inputText, pdfFilePath)
                    Toast.makeText(
                        this@MainActivity,
                        "PDF generated at: $pdfFilePath",
                        Toast.LENGTH_SHORT
                    ).show()
                    //Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun TextInput(onTextChanged: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = {
            text = it
            onTextChanged(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        label = { Text("Enter text for the PDF") }
    )
}
@Composable
fun GeneratePdfButton(onGenerateClick: () -> Unit) {
    Button(
        onClick = { onGenerateClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Generate PDF")
    }
}

private fun generatePdf(text: String, filePath: String) {
        val document = PDDocument()
        val page = PDPage(PDRectangle.A4)
        document.addPage(page)
        val contentStream = PDPageContentStream(document, page)
        val font = PDType1Font.HELVETICA_BOLD
        val fontSize = 12f

        contentStream.setFont(font, fontSize)
        contentStream.beginText()
        contentStream.newLineAtOffset(50f, 750f)
        contentStream.showText("WELCOME!")
        contentStream.endText()

        contentStream.close()

        val outputFile = File("your_output_path.pdf")
        document.save(outputFile)
        document.close()
    }


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PdfGeneratorTheme {
    }
}