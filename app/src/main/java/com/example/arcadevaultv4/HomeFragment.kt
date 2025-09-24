package com.example.arcadevaultv4

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var gameList: MutableList<GameItem> // Mutable list
    private lateinit var adapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize mutable list
        gameList = mutableListOf(
            GameItem("Tascheemon", "GBA", "@raw/tascheemon"),
            GameItem("Mario Kart", "GBA"),
            GameItem("Zelda", "GBA")
        )

        // Initialize adapter AFTER gameList
        adapter = GameAdapter(gameList, object : GameAdapter.Listener {
            override fun onMoreClicked(view: View, game: GameItem) {
                val popup = PopupMenu(requireContext(), view)
                popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
                popup.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.action_open -> {
                            openGame(game)
                            true
                        }
                        R.id.action_delete -> {
                            deleteGame(game)
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
            }

            override fun onOpen(game: GameItem) { openGame(game) }

            override fun onDelete(game: GameItem) { deleteGame(game) }
        })

        recyclerView.adapter = adapter
        return view
    }

    private fun openGame(game: GameItem) {
        (activity as? MainActivity)?.saveLastOpenedGame(game.title)
        val intent = Intent(requireContext(), GameDetailActivity::class.java)
        intent.putExtra("GAME_TITLE", game.title)
        startActivity(intent)
    }

    private fun deleteGame(game: GameItem) {
        val index = gameList.indexOfFirst { it.title == game.title }
        if (index != -1) {
            gameList.removeAt(index)
            adapter.notifyItemRemoved(index)
            adapter.notifyItemRangeChanged(index, gameList.size)

            if (gameList.isEmpty()) {
                view?.findViewById<TextView>(R.id.empty_view)?.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
            Toast.makeText(requireContext(), "Deleted ${game.title}", Toast.LENGTH_SHORT).show()
        }
    }


    // Fragment lifecycle toasts
    override fun onStart() { super.onStart(); Toast.makeText(requireContext(), "HomeFragment: onStart", Toast.LENGTH_SHORT).show() }
    override fun onResume() { super.onResume(); Toast.makeText(requireContext(), "HomeFragment: onResume", Toast.LENGTH_SHORT).show() }
    override fun onPause() { super.onPause(); Toast.makeText(requireContext(), "HomeFragment: onPause", Toast.LENGTH_SHORT).show() }
    override fun onStop() { super.onStop(); Toast.makeText(requireContext(), "HomeFragment: onStop", Toast.LENGTH_SHORT).show() }
    override fun onDestroy() { super.onDestroy(); Toast.makeText(requireContext(), "HomeFragment: onDestroy", Toast.LENGTH_SHORT).show() }
}
