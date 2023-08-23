package ru.practicum.sprint10koh8

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

data class ProblemState(
    val description: String
) : ListItem

class ProblemStateViewHolder(
    parent: ViewGroup
) : ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.v_problem_state, parent, false)
) {

    val description: TextView = itemView.findViewById(R.id.description)
    val retry: Button = itemView.findViewById(R.id.retry)

    fun bind(problemState: ProblemState) {
        description.text = problemState.description
    }

}