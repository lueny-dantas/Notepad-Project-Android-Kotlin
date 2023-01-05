package br.com.alura.ceep.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.alura.ceep.database.AppDatabase
import br.com.alura.ceep.databinding.ActivityListNotesBinding
import br.com.alura.ceep.extensions.goTo
import br.com.alura.ceep.repository.NoteRepository
import br.com.alura.ceep.ui.recyclerview.adapter.ListNotesAdapter
import br.com.alura.ceep.webComunication.services.NoteWebClient
import kotlinx.coroutines.launch

class ActivityListNotes : AppCompatActivity() {

    private val binding by lazy { ActivityListNotesBinding.inflate(layoutInflater) }
    private val adapter by lazy { ListNotesAdapter(this) }
    private val repository by lazy {
        NoteRepository(
            AppDatabase(this).noteDao(),
            NoteWebClient()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureFab()
        configureRecyclerView()
        configureSwipeRefresh()
        lifecycleScope.launch {
            launch { synchronize() }
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchNotes()
            }
        }
    }

    private suspend fun synchronize() {
        repository.synchronize()
    }

    private fun configureFab() {
        binding.activityListNotesFab.setOnClickListener {
            Intent(this, ActivityFormNote::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun configureRecyclerView() {
        binding.activityListNotesRecyclerview.adapter = adapter
        adapter.whenClickItem = { note ->
            goTo(ActivityFormNote::class.java) {
                putExtra(NOTE_ID, note.id)
            }
        }
    }

    private fun configureSwipeRefresh() {
        binding.activityListNotesSwipe.setOnRefreshListener {
            lifecycleScope.launch {
                synchronize()
                binding.activityListNotesSwipe.isRefreshing = false
            }
        }
    }

    private suspend fun searchNotes() {
        repository.searchAll()
            .collect { notesFounds ->
                binding.activityListNotesMensageWithoutNotes.visibility =
                    if (notesFounds.isEmpty()) {
                        binding.activityListNotesRecyclerview.visibility = GONE
                        VISIBLE
                    } else {
                        binding.activityListNotesRecyclerview.visibility = VISIBLE
                        adapter.update(notesFounds)
                        GONE
                    }
            }
    }
}