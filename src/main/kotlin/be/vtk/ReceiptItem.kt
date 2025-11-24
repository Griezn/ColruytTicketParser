package be.vtk

data class ReceiptItem(
    val articleId: Int,
    val description: String,
    val quantity: Float,
    val unitPrice: Float,
    val totalPrice: Float
)
