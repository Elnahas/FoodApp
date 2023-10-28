package elnahas.foodapp.data.repository

import elnahas.foodapp.data.remote.CategoryRemote
import elnahas.foodapp.data.remote.FoodApiService
import elnahas.foodapp.data.remote.PopularMealRemote
import elnahas.foodapp.data.remote.RandomMealRemote
import elnahas.foodapp.util.Resource
import javax.inject.Inject

class FoodHomeRepository @Inject constructor(val api: FoodApiService) {

    suspend fun getRandomFood(): RandomMealRemote {
        return api.getRandomFood()
    }

    suspend fun getPopularMealRemote(): Resource<PopularMealRemote> {

        val response = try {

            api.getPopularMeal()

        } catch (ex: Exception) {

            return Resource.Error(ex.message.toString())
        }

        return Resource.Success(response)
    }


    suspend fun getCategoryRemote(): CategoryRemote {
        return api.getCategory()
    }


}