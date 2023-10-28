package elnahas.foodapp.presentation.fooddetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import elnahas.foodapp.R
import elnahas.foodapp.databinding.FragmentFoodDetailBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FoodDetailFragment : Fragment() {

    lateinit var binding: FragmentFoodDetailBinding

    val args: FoodDetailFragmentArgs by navArgs()
    val foodDetailViewModel: FoodDetailViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFoodDetailBinding.inflate(inflater, container, false)


        foodDetailViewModel.getMealDetails(args.idmeal)


        lifecycleScope.launch {

            foodDetailViewModel.detailMutableStateFlow.collect { mealDetails ->

                if (mealDetails.idMeal.isNullOrEmpty())
                {
                    binding.progressHorizontal.visibility  = View.VISIBLE
                }
                else
                {

                    binding.progressHorizontal.visibility  = View.GONE

                    binding.apply {

                        txtArea.text = mealDetails.strArea
                        txtCategoryName.text = mealDetails.strCategory
                        txtDescription.text = mealDetails.strInstructions
                        collapsingToolbarLayout.title = mealDetails.strMeal

                        Glide.with(imgMealDetail)
                            .load(mealDetails.strMealThumb).into(imgMealDetail)

                    }


                }



            }

        }


        return binding.root
    }

}