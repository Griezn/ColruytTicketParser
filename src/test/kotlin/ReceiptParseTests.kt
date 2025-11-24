import be.vtk.Receipt
import be.vtk.TicketParser
import be.vtk.ReceiptItem
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class ReceiptParseTests {
    val ticketParser = TicketParser()

    @Test
    fun receipt1() {
        val pdfFilePath = "src/test/resources/Kasticket_23092025_19h58_323580145.pdf"

        val extractedContent = ticketParser.extractTextFromPdf(pdfFilePath)

        assertNotNull(extractedContent)
        val receipt: Receipt = ticketParser.parseReceiptText(extractedContent)

        assertEquals(2, receipt.items.size)

        val item1 = ReceiptItem(21165,"MORA oven fun mix 16st", 2.0f, 6.99f, 13.98f)
        assertEquals(item1, receipt.items[0])

        val item2 = ReceiptItem(5006,"STELLA ARTOIS pils 5,2% bak 24x25cl", 1.0f, 15.79f, 15.79f)
        assertEquals(item2, receipt.items[1])
    }

    @Test
    fun receipt2() {
        val pdfFilePath = "src/test/resources/Kasticket_18062025_18h00_295781492.pdf"

        val extractedContent = ticketParser.extractTextFromPdf(pdfFilePath)

        assertNotNull(extractedContent)
        val receipt: Receipt = ticketParser.parseReceiptText(extractedContent)

        assertEquals(3, receipt.items.size)

        val item1 = ReceiptItem(35602,"EVERYDAY pizza bolognese 350g", 1.0f, 1.59f, 1.59f)
        assertEquals(item1, receipt.items[0])

        val item2 = ReceiptItem(24828,"ARIZONA Green tea original niet-br. 50cl", 6.0f, 0.793f, 4.76f)
        assertEquals(item2, receipt.items[1])

        val item3 = ReceiptItem(10101,"verse worsten varken+kalf", 0.33f, 8.09f, 2.67f)
        assertEquals(item3, receipt.items[2])
    }

    @Test
    fun receipt3() {
        val pdfFilePath = "src/test/resources/Kasticket_30092025_17h45_325579348.pdf"

        val extractedContent = ticketParser.extractTextFromPdf(pdfFilePath)

        assertNotNull(extractedContent)
        val receipt: Receipt = ticketParser.parseReceiptText(extractedContent)

        assertEquals(3, receipt.items.size)

        val item1 = ReceiptItem(8484,"FLANDRIEN licht gerijpt sneet 200g", 1.0f, 4.05f, 4.05f)
        assertEquals(item1, receipt.items[0])

        val item2 = ReceiptItem(26499,"EVERYDAY salami z.look sneetjes 225g", 1.0f, 1.63f, 1.63f)
        assertEquals(item2, receipt.items[1])

        val item3 = ReceiptItem(5572,"EVERYDAY Halfvolle melk brik 1L", 2.0f, 0.85f, 1.7f)
        assertEquals(item3, receipt.items[2])
    }
}