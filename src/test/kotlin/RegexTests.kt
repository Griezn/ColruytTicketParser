import kotlin.test.Test
import be.vtk.TicketParser
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class RegexTests {
    val ticketParser = TicketParser()

    @Test
    fun `Normal items tests`() {
        val pizza = "EVERYDAY pizza bolognese 350g 35602A       1    1,59     1,59"

        var itemMatcher = ticketParser.receiptItemPattern.matcher(pizza)

        assertTrue(itemMatcher.find())
        assertEquals("EVERYDAY pizza bolognese 350g",itemMatcher.group(1))
        assertEquals("35602A",itemMatcher.group(2))
        assertEquals("1",itemMatcher.group(3))
        assertEquals("1,59",itemMatcher.group(4))
        assertEquals("1,59",itemMatcher.group(5))

        val arizona = "ARIZONA Green tea original niet-br. 50cl 24828A       6    0,793     4,76"
        itemMatcher = ticketParser.receiptItemPattern.matcher(arizona)

        assertTrue(itemMatcher.find())
        assertEquals("ARIZONA Green tea original niet-br. 50cl",itemMatcher.group(1))
        assertEquals("24828A",itemMatcher.group(2))
        assertEquals("6",itemMatcher.group(3))
        assertEquals("0,793",itemMatcher.group(4))
        assertEquals("4,76",itemMatcher.group(5))
    }

    @Test
    fun `Weight items tests`() {
        val worst = "verse worsten varken+kalf 10101A   0,330kg   8,09     2,67 \n"

        val itemMatcher = ticketParser.receiptItemPattern.matcher(worst)

        assertTrue(itemMatcher.find())
        assertEquals("verse worsten varken+kalf",itemMatcher.group(1))
        assertEquals("10101A",itemMatcher.group(2))
        assertEquals("0,330kg",itemMatcher.group(3))
        assertEquals("8,09",itemMatcher.group(4))
        assertEquals("2,67",itemMatcher.group(5))
    }

    @Test
    fun `Leeggoed items tests`() {
        val stella = "STELLA ARTOIS pils 5,2% bak 24x25cl  5006C       1   15,79    15,79    4,50"

        val itemMatcher = ticketParser.receiptItemPattern.matcher(stella)

        assertTrue(itemMatcher.find())
        assertEquals("STELLA ARTOIS pils 5,2% bak 24x25cl",itemMatcher.group(1))
        assertEquals("5006C",itemMatcher.group(2))
        assertEquals("1",itemMatcher.group(3))
        assertEquals("15,79",itemMatcher.group(4))
        assertEquals("15,79",itemMatcher.group(5))
    }
}