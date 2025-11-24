package be.vtk

data class Receipt(
    val items: List<ReceiptItem>,
    val totalPrice: Float,
)
