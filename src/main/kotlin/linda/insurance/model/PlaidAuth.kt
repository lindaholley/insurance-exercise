package linda.insurance.model

//{
//	"institution_id":"ins_3",
//	"public_key":"c5f2d27962a2401407b5566385a512",
//	"initial_products":["auth"],
//	"options":{
//		"override_username":"user_good",
//		"override_password":"pass_good"
//	}
//}

data class PlaidAuth(var institution_id: String,
                     var public_key: String,
                     var initial_products: MutableList<String>,
                     var options: PlaidCredential) {

    data class PlaidCredential(var override_username: String,
                               var override_password: String)
}