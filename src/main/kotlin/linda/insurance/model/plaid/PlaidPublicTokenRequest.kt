package linda.insurance.model.plaid

//{
//	"institution_id":"ins_3",
//	"public_key":"c5f2d27962a2401407b5566385a512",
//	"initial_products":["auth"],
//	"options":{
//		"override_username":"user_good",
//		"override_password":"pass_good"
//	}
//}

data class PlaidPublicTokenRequest(var institutionId: String,
                                   var publicKey: String,
                                   var initialProducts: MutableList<String>,
                                   var options: PlaidCredential) {

    data class PlaidCredential(var webhook: String? = null,
                               var overrideUsername: String,
                               var overridePassword: String)
}