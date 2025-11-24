import org.apache.pdfbox.Loader
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File
import java.io.IOException
import java.util.regex.Pattern

class TicketParser {
    val receiptItemPattern = Pattern.compile(
        "^(.+?)\\s+([.\d,]+(?:kg)?)\\s+([.\d,]+)\\s+([.\d,]+)(?:\\s+[.\d,]+)?\\s*$"
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

    /*
    fun parseReceiptText(rawText: String): Receipt {
        val lines = rawText.split('\n')
        val items = mutableListOf<ReceiptItem>()
        var totalAmount: Float? = null

        /*
         * THE NEW ROBUST REGEX (Handling all your examples)
         * Groups:
         * 1: (.+?)                      -> Description (non-greedy from start)
         * 2: ([.\d,]+(?:kg)?)          -> Quantity/Weight (e.g., "1", "6", "0,330kg")
         * 3: ([.\d,]+)                  -> Unit Price (e.g., "1,59", "8,09")
         * 4: ([.\d,]+)                  -> Total Price (e.g., "1,59", "2,67", "13,98")
         * Non-capturing: (?:\s+[.\d,]+)? -> Optional Leeggoed price at the end (ignored)
         */
        val itemPattern = Pattern.compile(
            "^(.+?)\\s+([.\d,]+(?:kg)?)\\s+([.\d,]+)\\s+([.\d,]+)(?:\\s+[.\d,]+)?\\s*$"
        )

        // Regex for the final total (remains simple)
        val totalPattern = Pattern.compile(
            "TOTAL\\s+([\\d,.]+)\\s*(EUR|€|BGN)?",
            Pattern.CASE_INSENSITIVE
        )

        for (line in lines) {
            val trimmedLine = line.trim()

            // 1. Try to match a line item
            val itemMatcher = itemPattern.matcher(trimmedLine)
            if (itemMatcher.find()) {
                try {
                    // Group 1: Description
                    val description = itemMatcher.group(1).trim()
                    // Group 2: Quantity/Weight (stored as string for later unit handling)
                    val quantityStr = itemMatcher.group(2)
                    // Group 3: Unit Price
                    val unitPrice = itemMatcher.group(3).replace(',', '.').toDouble()
                    // Group 4: Total Price
                    val totalPrice = itemMatcher.group(4).replace(',', '.').toDouble()

                    items.add(ReceiptItem(description, quantityStr, unitPrice, totalPrice))
                } catch (e: Exception) {
                    println("Warning: Could not parse item line: $trimmedLine. Error: ${e.message}")
                }
                continue
            }

            // 2. Try to match the final total
            val totalMatcher = totalPattern.matcher(trimmedLine)
            if (totalMatcher.find()) {
                try {
                    val totalStr = totalMatcher.group(1).replace(',', '.')
                    totalAmount = totalStr.toDouble()
                } catch (e: Exception) {
                    println("Warning: Could not parse total amount from line: $trimmedLine. Error: ${e.message}")
                }
            }
        }

        return Receipt(items, totalAmount)
    }
    */
}