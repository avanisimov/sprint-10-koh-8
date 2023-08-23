package ru.practicum.sprint10koh8

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MainActivity : AppCompatActivity(), OnRetryButtonClickListener {

    val itemsAdapter = ItemsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemsRecyclerView: RecyclerView = findViewById(R.id.items)
        itemsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = itemsAdapter
        }

        itemsAdapter.items.add(
            ProblemState(
                description = "Нет доступа к серверу"
            )
        )
    }

    override fun onRetryButtonClick() {
        val myItems = (1..200_000).map {
            ListElement.createRandomElement(it.toString())
        }
        itemsAdapter.items.clear()
        itemsAdapter.items.addAll(
            myItems
        )
        itemsAdapter.notifyDataSetChanged()
    }
}

class ItemsAdapter(
    val onRetryButtonClickListener: OnRetryButtonClickListener
) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val VIEW_TYPE_LIST_ELEMENT = 1
        const val VIEW_TYPE_PROBLEM_STATE = 2
        const val VIEW_TYPE_UNKNOWN = 0
    }

    var items: MutableList<ListItem> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return when (item) {
            is ListElement -> {
                VIEW_TYPE_LIST_ELEMENT
            }

            is ProblemState -> {
                VIEW_TYPE_PROBLEM_STATE
            }

            else -> {
                VIEW_TYPE_UNKNOWN
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LIST_ELEMENT -> ListElementViewHolder(parent)
            VIEW_TYPE_PROBLEM_STATE -> ProblemStateViewHolder(parent)
            else -> throw IllegalStateException("There is no ViewHolder for viewType=$viewType")
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("SPRINT_10", "onBindViewHolder $position $holder")
        when (val item = items[position]) {
            is ListElement -> {
                (holder as ListElementViewHolder).bind(item)
            }

            is ProblemState -> {
                (holder as ProblemStateViewHolder).apply {
                    bind(item)
                    retry.setOnClickListener {
                        onRetryButtonClickListener.onRetryButtonClick()
                    }
                }
            }

            else -> {
                // nothing
            }
        }

    }

}

interface OnRetryButtonClickListener {
    fun onRetryButtonClick()
}


interface ListItem