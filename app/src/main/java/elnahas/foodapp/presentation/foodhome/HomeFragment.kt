package elnahas.foodapp.presentation.foodhome

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import elnahas.foodapp.R
import elnahas.foodapp.databinding.FragmentHomeBinding
import elnahas.foodapp.util.Resource
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val foodHomeViewModel: FoodHomeViewModel by viewModels()

    lateinit var popularMealAdapter: PopularMealAdapter
    lateinit var categoryMealAdapter: CategoryMealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)



        setupRecyclerView()

        lifecycleScope.launch {
            foodHomeViewModel.randomFoodMutable.collect { randomMeal ->

                when (randomMeal) {

                    is Resource.Loading -> {

                        binding.progressRandomMeal.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {


                        binding.progressRandomMeal.visibility = View.GONE
                        randomMeal.data?.let { meal ->

                            Glide.with(binding.imgRandom)
                                .load(meal.strMealThumb)
                                .into(binding.imgRandom)

                            binding.imgRandom.setOnClickListener {

                                val bundle = Bundle()
                                bundle.putString("ID_MEAL", meal.idMeal)
                                findNavController().navigate(
                                    R.id.action_homeFragment_to_foodDetailFragment,
                                    bundle
                                )

                            }
                        }

                    }

                    is Resource.Error -> {

                        binding.progressRandomMeal.visibility = View.GONE
                    }
                    else -> {}
                }


            }
        }


        lifecycleScope.launch {

            foodHomeViewModel.popularFoodMutable.collect {

                if (it.isEmpty())
                    binding.progressPopular.visibility = View.VISIBLE
                else {
                    binding.progressPopular.visibility = View.GONE
                    popularMealAdapter.differ.submitList(it)
                }

            }
        }

        lifecycleScope.launch {

            foodHomeViewModel.categoryMutable.collect {

                categoryMealAdapter.differ.submitList(it)

            }
        }



        return binding.root
    }

    private fun setupRecyclerView() {

        popularMealAdapter = PopularMealAdapter()
        categoryMealAdapter = CategoryMealAdapter()

        binding.recyclerPopular.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularMealAdapter

        }


        binding.recyclerCategory.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
            adapter = categoryMealAdapter

        }

    }


}