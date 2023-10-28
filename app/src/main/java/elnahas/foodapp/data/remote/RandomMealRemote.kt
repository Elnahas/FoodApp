package elnahas.foodapp.data.remote

import com.google.gson.annotations.SerializedName

data class RandomMealRemote(
    @SerializedName("meals")
    val randomMeals: List<RandomMeal>
)