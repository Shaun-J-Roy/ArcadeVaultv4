package com.example.arcadevaultv4

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GameAdapter(
    private val items: List<GameItem>,
    private val listener: Listener
) : RecyclerView.Adapter<GameAdapter.VH>() {

    interface Listener {
        fun onMoreClicked(view: View, game: GameItem)
        fun onOpen(game: GameItem)
        fun onDelete(game: GameItem)
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.game_title)
        val platform: TextView = itemView.findViewById(R.id.game_console)
        val options: ImageButton = itemView.findViewById(R.id.options_menu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val game = items[position]
        holder.title.text = game.title
        holder.platform.text = game.platform

        // popup from options button
        holder.options.setOnClickListener {
            listener.onMoreClicked(it, game)
        }

        // context menu on long press (shows a simple programmatic menu)
        holder.itemView.setOnLongClickListener {
            // show context menu created here
            val menu = holder.itemView.context as? android.content.Context
            // Build and show a PopupMenu used as context alternative (keeps it simple)
            val pm = android.widget.PopupMenu(holder.itemView.context, holder.itemView)
            pm.menu.add("Open").setOnMenuItemClickListener {
                listener.onOpen(game); true
            }
            pm.menu.add("Delete").setOnMenuItemClickListener {
                listener.onDelete(game); true
            }
            pm.show()
            true
        }

        // click opens details too
        holder.itemView.setOnClickListener { listener.onOpen(game) }
    }

    override fun getItemCount(): Int = items.size
}
