package com.jvprojetos17.sale.service

import com.jvprojetos17.sale.enums.Status
import com.jvprojetos17.sale.exception.NotFoundException
import com.jvprojetos17.sale.extension.toEntity
import com.jvprojetos17.sale.model.Product
import com.jvprojetos17.sale.model.QProduct
import com.jvprojetos17.sale.repository.ProductRepository
import com.jvprojetos17.sale.request.ProductQuantityRequest
import com.jvprojetos17.sale.request.ProductRequest
import com.jvprojetos17.sale.request.ProductStockRequest
import com.querydsl.core.BooleanBuilder
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

@ExtendWith(MockKExtension::class)
class ProductServiceTest {

    @MockK
    private lateinit var productRepository: ProductRepository

    @InjectMockKs
    private lateinit var productService: ProductService

    private fun buildProduct(
        uuid: String? = null,
        description: String = "Produto 1",
        code: String = "cod123",
        price: Double = 19.99,
        stock: Int = 7,
        active: Status = Status.TRUE,
    ) = Product(
        uuid = uuid,
        description = description,
        code = code,
        price = price,
        stock = stock,
        active = active,
    )

    private fun buildProductRequest(
        description: String = "Produto request 1",
        code: String = "request-cod123",
        price: Double = 13.45,
        stock: Int = 5,
    ) = ProductRequest(
        description = description,
        code = code,
        price = price,
        stock = stock,
    )

    private fun buildProductQuantityRequest(
        productId: String? = null,
        quantity: Int = 1,
    ) = ProductQuantityRequest(
        productId = productId!!,
        quantity = quantity,
    )

    @Test
    fun `should return product find by id`() {
        val id = "512as145sda41-14d4d4w3-sddsf4"
        val product = buildProduct().copy(uuid = id)

        every { productRepository.findByUuid(id) } returns product

        val productReturned = productService.findByUuid(id)

        assertEquals(product, productReturned)
        verify(exactly = 1) { productRepository.findByUuid(id) }
    }

    @Test
    fun `should return not found exception when product id you are invalid`() {
        val id = "-999"
        every { productRepository.findByUuid(id) } returns null

        val error = assertThrows<NotFoundException> {
            productService.findByUuid(id)
        }

        assertEquals("Product with id: [$id] not found!", error.message)
        assertEquals("S-204", error.errorCode)
        verify(exactly = 1) { productRepository.findByUuid(id) }
    }

    @Test
    fun `should saved user`() {
        val productFake = buildProduct()
        val productRequestFake = buildProductRequest()
        val productRequestToEntityFake = buildProductRequest().toEntity()

        every { productRepository.save(productRequestToEntityFake) } returns productFake

        productService.save(productRequestFake)

        verify(exactly = 1) { productRepository.save(productRequestToEntityFake) }
    }

    @Test
    fun `should return list of users with status active`() {
        val listProduct = setOf(buildProduct())
        val statusActive = Status.TRUE

        every { productRepository.findByActive(statusActive) } returns listProduct

        productService.getAllByActive(statusActive)

        assertEquals(listProduct.size, 1)
        assertEquals(listProduct.toList()[0].price, 19.99)
        verify(exactly = 1) { productRepository.findByActive(statusActive) }
    }

    @Test
    fun `should return list of users with status inactive`() {
        val listProduct = setOf(buildProduct())
        val statusActive = Status.FALSE

        every { productRepository.findByActive(statusActive) } returns listProduct

        productService.getAllByActive(statusActive)

        assertEquals(listProduct.size, 1)
        assertEquals(listProduct.toList()[0].code, "cod123")
        verify(exactly = 1) { productRepository.findByActive(statusActive) }
    }

    @Test
    fun `should return page of product with filters`() {
        val pageRequest = PageRequest.of(0, 10)
        val listProduct = mutableListOf(buildProduct())
        val pageResponse = PageImpl(listProduct)
        val qProduct = QProduct.product
        val where = BooleanBuilder()
            .and(qProduct.description.contains("Product 1"))
            .and(qProduct.code.contains("cod123"))
            .and(qProduct.active.eq(Status.TRUE))

        every {
            productRepository.findAll(
                where,
                pageRequest,
            )
        } returns pageResponse

        productService.filter(pageRequest, "Product 1", "cod123", Status.TRUE)

        verify(exactly = 1) { productRepository.findAll(where, pageRequest) }
        assertEquals(pageResponse.totalPages, 1)
        assertEquals(pageResponse.content[0].code, "cod123")
        assertEquals(pageResponse.content[0].price, 19.99)
    }

    @Test
    fun `should update product`() {
        val id = "512as145sda41-14d4d4w3-sddsf4"
        val productFake = buildProduct()
        val productRequestFake = buildProductRequest()
        val productRequestFakeToEntity = buildProductRequest().toEntity().copy(uuid = id)

        every { productRepository.findByUuid(id) } returns productFake
        every { productRepository.save(productRequestFakeToEntity) } returns productFake

        productService.update(id, productRequestFake)

        verify(exactly = 1) { productRepository.save(productRequestFakeToEntity) }
    }

    @Test
    fun `should change status the product to inactive`() {
        val id = "ojsdds82763-344fgr4"
        val productFake = buildProduct(uuid = id)
        val productSavedFake = buildProduct(uuid = id, active = Status.FALSE, code = "cod-1234-SAVED")

        every { productRepository.findByUuid(id) } returns productFake
        every { productRepository.save(productFake.copy(active = Status.FALSE)) } returns productSavedFake

        productService.inactivate(id)

        verify(exactly = 1) { productRepository.findByUuid(id) }
        verify(exactly = 1) { productRepository.save(productFake.copy(active = Status.FALSE)) }
    }

    @Test
    fun `should change status the product to active`() {
        val id = "ojsdds82763-344fgr4"
        val productFake = buildProduct(uuid = id, active = Status.FALSE)
        val productSavedFake = buildProduct(uuid = id, active = Status.TRUE, code = "cod-1234-SAVED")

        every { productRepository.findByUuid(id) } returns productFake
        every { productRepository.save(productFake.copy(active = Status.TRUE)) } returns productSavedFake

        productService.activate(id)

        verify(exactly = 1) { productRepository.findByUuid(id) }
        verify(exactly = 1) { productRepository.save(productFake.copy(active = Status.TRUE)) }
    }

    @Test
    fun `should return true when the product is available in the indicated quantity`() {
        val isAvailableStockByProductAndQuantity = true
        val productId = "jsadhsad8193-2374t24"
        val quantity = 5

        every {
            productRepository.checkAvailableStockByProductIdAndQuantity(
                productId,
                quantity,
            )
        } returns isAvailableStockByProductAndQuantity

        productService.isAvailableStockByProductAndQuantity(productId, quantity)

        verify(exactly = 1) { productRepository.checkAvailableStockByProductIdAndQuantity(productId, quantity) }
    }

    @Test
    fun `should false true when the product is unavailable in the indicated quantity`() {
        val isAvailableStockByProductAndQuantity = false
        val productId = "jsadhsad8193-2374t24"
        val quantity = 15

        every {
            productRepository.checkAvailableStockByProductIdAndQuantity(
                productId,
                quantity,
            )
        } returns isAvailableStockByProductAndQuantity

        productService.isAvailableStockByProductAndQuantity(productId, quantity)

        verify(exactly = 1) { productRepository.checkAvailableStockByProductIdAndQuantity(productId, quantity) }
    }

    @Test
    fun `should low stock by order quantity when the purchase is successful`() {
        val productId = "121-as897232ggasa-1"
        val productFake = buildProduct(uuid = productId)
        val stock = productFake.stock
        val quantityLow = 2
        val productSavedFake = buildProduct(uuid = productId, stock = 5)

        every { productRepository.findByUuid(productId) } returns productFake
        every { productRepository.save(productFake.copy(stock = stock - quantityLow)) } returns productSavedFake

        productService.lowInStock(productId, quantityLow)

        verify(exactly = 1) { productRepository.save(productSavedFake) }
        verify(exactly = 1) { productRepository.findByUuid(productId) }
    }

    @Test
    fun `should add stock quantity informed by product`() {
        val productId = "121-as897232ggasa-1"
        val productFake = buildProduct(uuid = productId)
        val stock = productFake.stock
        val productSavedFake = buildProduct(uuid = productId, stock = 8)
        val listRequestAddStock = ProductStockRequest(listOf(buildProductQuantityRequest(productId = productId)))
        val quantityAdd = listRequestAddStock.productsAndQuantity.toList()[0].quantity

        every { productRepository.findByUuid(productId) } returns productFake
        every { productRepository.save(productFake.copy(stock = stock + quantityAdd)) } returns productSavedFake

        productService.addStock(listRequestAddStock)

        verify(exactly = 1) { productRepository.save(productSavedFake) }
        verify(exactly = 1) { productRepository.findByUuid(productId) }
    }
}
