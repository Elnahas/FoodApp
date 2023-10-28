package elnahas.foodapp.data.remote

import elnahas.foodapp.util.Resource
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApiService {

    @GET("random.php")
    suspend fun getRandomFood(): RandomMealRemote


    @GET("filter.php?")
    suspend fun getPopularMeal(@Query("c") categoryName: String = "Seafood"): PopularMealRemote


    @GET("lookup.php?")
    suspend fun getDetailMeal(@Query("i") idMeal: String): MealDetailsRemote


    @GET("categories.php")
    suspend fun getCategory(): CategoryRemote
}