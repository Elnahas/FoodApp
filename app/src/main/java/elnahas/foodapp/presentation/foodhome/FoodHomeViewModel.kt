package elnahas.foodapp.presentation.foodhome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import elnahas.foodapp.data.remote.Category
import elnahas.foodapp.data.remote.PopularMeal
import elnahas.foodapp.data.remote.RandomMeal
import elnahas.foodapp.data.repository.FoodHomeRepository
import elnahas.foodapp.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodHomeViewModel @Inject constructor(private val repository: FoodHomeRepository) :
    ViewModel() {

//    private val _randomFoodMutable = MutableLiveData<Meal>()
//
//    val randomFoodMutable: LiveData<Meal>
//        get() = _randomFoodMutable

    //private val _mutableStateFlow: MutableStateFlow<Resource<RandomMeal>?> = MutableStateFlow(null)

    private val _randomFoodMutable =
        MutableStateFlow<Resource<RandomMeal>?>(null)
    val randomFoodMutable: StateFlow<Resource<RandomMeal>?>
        get() = _randomFoodMutable.asStateFlow()

    //For Popular Meal MutableState
    private val _popularFoodMutable = MutableStateFlow<List<PopularMeal>>(emptyList())
    val popularFoodMutable: StateFlow<List<PopularMeal>>
        get() = _popularFoodMutable.asStateFlow()


    private val _categoryMutable = MutableStateFlow<List<Category>>(emptyList())
    val categoryMutable: StateFlow<List<Category>>
        get() = _categoryMutable.asStateFlow()


    init {

        getRandomFood()
        getPopularMealRemote()
        getCategoryRemote()

    }

    fun getRandomFood() = viewModelScope.launch {

        _randomFoodMutable.emit(Resource.Loading())

        try {
            val meal = repository.getRandomFood().randomMeals[0]
            _randomFoodMutable.emit(Resource.Success(meal))
        } catch (ex: Exception) {
            _randomFoodMutable.emit(Resource.Error(ex.message.toString()))
        }


    }

    fun getPopularMealRemote() = viewModelScope.launch {

        val meal = repository.getPopularMealRemote()

        when(meal){

            is Resource.Success->{

                    meal.data?.let {
                        _popularFoodMutable.emit(it.meals)
                    }

            }

            is Resource.Error->{

            }

            else -> {}
        }


    }

    fun getCategoryRemote() = viewModelScope.launch {

        val category = repository.getCategoryRemote()
        _categoryMutable.emit(category.categories)
    }


}