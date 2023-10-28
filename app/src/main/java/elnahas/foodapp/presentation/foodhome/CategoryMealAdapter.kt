package elnahas.foodapp.presentation.foodhome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import elnahas.foodapp.data.remote.Category
import elnahas.foodapp.data.remote.CategoryRemote
import elnahas.foodapp.data.remote.PopularMeal
import elnahas.foodapp.databinding.ItemCategoryBinding
import elnahas.foodapp.databinding.ItemPopularBinding

class CategoryMealAdapter : RecyclerView.Adapter<CategoryMealAdapter.ViewHolder>() {


    val diffCallBack = object : DiffUtil.ItemCallback<Category>() {

        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallBack)


    class ViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.apply {

            val item = differ.currentList[position]

            txtCategoryName.text = item.strCategory

            Glide.with(imgCategory).load(item.strCategoryThumb)
                .into(imgCategory)


        }
    }
}