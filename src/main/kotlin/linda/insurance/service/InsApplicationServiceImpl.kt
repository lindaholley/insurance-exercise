package linda.insurance.service

import linda.insurance.api.PlaidService
import linda.insurance.datasource.dao.CustomerDao
import linda.insurance.model.enums.PolicyTypes
import linda.insurance.util.getPremium
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InsApplicationServiceImpl
    @Autowired constructor(private val plaidService: PlaidService,
                           private val customerDao: CustomerDao): InsApplicationService {

    override suspend fun create(customerId: Int) {
        val publicTokenResponse = plaidService.getPublicToken()
        val accessTokenResponse = plaidService.getAccessToken(publicTokenResponse.public_token)

        // store item
        customerDao.saveCustomer(customerId, accessTokenResponse)
    }

    override fun accountVerified(itemId: String, accountId: String) {

        customerDao.accountVerified(itemId, accountId)
    }

    override suspend fun checkBalance(itemId: String, accountId: String?): Boolean {

        // get access token
        val accessToken = customerDao.getAccessToken(itemId)

        val balanceResponse = accessToken?.let{
            plaidService.getBalance(accessToken, accountId)
        }

        val available = balanceResponse?.available
        if (available != null) {
            return available > getPremium(PolicyTypes.TEN_YEAR.ordinal)
        }
        return false
    }
}