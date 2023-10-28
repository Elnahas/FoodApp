package elnahas.foodapp.data.repository

import elnahas.foodapp.data.remote.FoodApiService
import elnahas.foodapp.data.remote.MealDetailsRemote
import elnahas.foodapp.util.Resource
import javax.inject.Inject

class FoodDetailsRepository @Inject constructor(val api : FoodApiService) {


    suspend fun getMealDetails(idMeal :String) : Resource<MealDetailsRemote>{

        val response = try {

            api.getDetailMeal(idMeal)

        }
        catch (ex: java.lang.Exception){
            return Resource.Error(ex.message.toString())
        }
        return Resource.Success(response)
    }
}