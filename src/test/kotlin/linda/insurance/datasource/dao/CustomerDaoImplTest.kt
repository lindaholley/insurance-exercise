package linda.insurance.datasource.dao

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import linda.insurance.datasource.repository.CustomerItemsRepository
import linda.insurance.datasource.repository.CustomerStatusRepository
import linda.insurance.model.customer.CustomerItem
import linda.insurance.model.enums.PlaidItemStatus
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.junit.MockitoJUnitRunner

const val testItemId = "test"
const val testAccessToken = "testToken"
const val testAccountId = "accountId"

@RunWith(MockitoJUnitRunner::class)
class CustomerDaoImplTest {

    private val customerStatusRepo: CustomerStatusRepository = mock()
    private val customerItemsRepository: CustomerItemsRepository = mock()

    private lateinit var customerDao: CustomerDaoImpl

    @Before
    fun init() {
        customerDao = CustomerDaoImpl(customerStatusRepo, customerItemsRepository)

        // test data
        val customerItem = CustomerItem(customerItemsId = 1, customerId = 2, itemId = testItemId,
                accessToken = testAccessToken, itemStatus = PlaidItemStatus.AVAILABLE.ordinal)

        // rule
        whenever(customerItemsRepository.findByItemId(testItemId)).thenReturn(customerItem)

        whenever(customerStatusRepo.findByCustomerId(anyInt())).thenReturn(null)
    }

    @Test
    fun badInputTest() {

        customerDao.accountVerified(testItemId, testAccountId)

        verifyZeroInteractions(customerStatusRepo)
    }
}