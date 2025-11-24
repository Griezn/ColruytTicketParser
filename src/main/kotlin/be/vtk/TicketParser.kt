package be.vtk

import org.apache.pdfbox.Loader
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File
import java.io.IOException
import java.util.regex.Pattern

class TicketParser {
    val receiptItemPattern: Pattern = Pattern.compile(
        "^(.+?)\\s+(\\d+[A-Z])\\s+([.\\d,]+(?:kg)?)\\s+(\\d+,\\d+)\\s+(\\d+,\\d+).*$"
    )

    fun extractTextFromPdf(pdfPath: String): String? {
        val pdfFile = File(pdfPath)
        if (!pdfFile.exists()) {
            println("Error: PDF file not found at path: $pdfPath")
            return null
        }

        try {
            Loader.loadPDF(pdfFile).use { document ->
                if (document.isEncrypted) {
                    println("Error: The document is encrypted.")
                    return null
                }

                val stripper = PDFTextStripper()
                val rawText = stripper.getText(document)

                return rawText
            }
        } catch (e: IOException) {
            println("An IOException occurred while processing the PDF: ${e.message}")
            e.printStackTrace()
            return null
        } catch (e: Exception) {
            println("An unexpected error occurred: ${e.message}")
            e.printStackTrace()
            return null
        }
    }


    fun parseReceiptText(rawText: String): Receipt {
        val lines = rawText.split('\n')
        val items = mutableListOf<ReceiptItem>()

        for (line in lines) {
            val trimmedLine = line.trim()

            val itemMatcher = receiptItemPattern.matcher(trimmedLine)
            if (itemMatcher.find()) {
                try {
                    val description: String = itemMatcher.group(1).trim()
                    val articleId: Int = itemMatcher.group(2).dropLast(1).toInt()
                    val quantity: Float = itemMatcher.group(3)
                        .replace("kg", "").replace(',', '.').toFloat()
                    val unitPrice: Float = itemMatcher.group(4).replace(',', '.').toFloat()
                    val totalPrice: Float = itemMatcher.group(5).replace(',', '.').toFloat()

                    items.add(ReceiptItem(articleId, description, quantity, unitPrice, totalPrice))
                } catch (e: Exception) {
                    println("Warning: Could not parse item line: $trimmedLine. Error: ${e.message}")
                }
                continue
            }
        }

        return Receipt(items, 0.0f)
    }

}