package elnahas.foodapp.presentation.fooddetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import elnahas.foodapp.data.remote.MealDetails
import elnahas.foodapp.data.repository.FoodDetailsRepository
import elnahas.foodapp.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(val repository: FoodDetailsRepository)  : ViewModel() {

    private val _detailMutableStateFlow = MutableStateFlow(MealDetails())
    val detailMutableStateFlow : StateFlow<MealDetails>
    get() = _detailMutableStateFlow.asStateFlow()


    fun getMealDetails(idMeal :String) = viewModelScope.launch {

        val response = repository.getMealDetails(idMeal)

        when(response)
        {
            is Resource.Success->{

                response.data?.let {

                    val meal = it.meals[0]

                    _detailMutableStateFlow.emit(meal)
                }


            }
            else->{}
        }

    }

}