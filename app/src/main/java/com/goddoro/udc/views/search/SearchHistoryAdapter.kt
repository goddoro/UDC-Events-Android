package com.goddoro.udc.views.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.goddoro.common.common.debugE
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.data.model.Event
import com.goddoro.udc.databinding.ItemHistoryBinding
import com.goddoro.udc.databinding.ItemMainPosterBinding
import com.goddoro.udc.views.home.MainPosterAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


/**
 * created By DORO 11/21/20
 */

class SearchHistoryAdapter:
    RecyclerView.Adapter<SearchHistoryAdapter.HistoryViewHolder>() {

    private val TAG = SearchHistoryAdapter::class.java.simpleName


    private val onClick: PublishSubject<String> = PublishSubject.create()
    val clickEvent: Observable<String> = onClick

    private val diff = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<String>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(inflater, parent, false)

        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size



    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {

            binding.root.setOnDebounceClickListener {
                onClick.onNext(differ.currentList[layoutPosition])
            }



        }

        fun bind(item: String) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            debugE(TAG, "Fuck")
            debugE(TAG,differ.currentList)


        }
    }

}

@BindingAdapter("app:recyclerview_search_histories")
fun RecyclerView.setHistoriesAdapter(items: List<String>?) {
    (adapter as? SearchHistoryAdapter)?.run {
        this.submitItems(items)
    }
}