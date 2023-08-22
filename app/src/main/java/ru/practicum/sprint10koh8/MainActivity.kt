package ru.practicum.sprint10koh8

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemsRecyclerView: RecyclerView = findViewById(R.id.items)

        val itemsAdapter = ItemsAdapter()

        itemsRecyclerView.apply {
            layoutManager = GridLayoutManager(
                this@MainActivity, 3,
                GridLayoutManager.VERTICAL, false
            ).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position % 4 == 0) {
                            3
                        } else {
                            1
                        }
                    }

                }
            }
            adapter = itemsAdapter

        }

        val myItems = (1..20).map {
            ListElement.createRandomElement(it.toString())
        }

        itemsAdapter.items.addAll(myItems)
    }
}

class ItemsAdapter : RecyclerView.Adapter<ListElementViewHolder>() {

    var items: MutableList<ListElement> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListElementViewHolder {
        return ListElementViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ListElementViewHolder, position: Int) {
        holder.bind(items[position])
    }

}