package br.com.alura.ceep.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.alura.ceep.R
import br.com.alura.ceep.database.AppDatabase
import br.com.alura.ceep.databinding.ActivityFormNoteBinding
import br.com.alura.ceep.extensions.tryloadImage
import br.com.alura.ceep.model.Note
import br.com.alura.ceep.repository.NoteRepository
import br.com.alura.ceep.ui.dialog.FormImageDialog
import br.com.alura.ceep.webComunication.services.NoteWebClient
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ActivityFormNote : AppCompatActivity() {

    private val binding by lazy { ActivityFormNoteBinding.inflate(layoutInflater) }
    private var image: MutableStateFlow<String?> = MutableStateFlow(null)
    private val reposittory by lazy {
        NoteRepository(
            AppDatabase(this).noteDao(),
            NoteWebClient()
        )
    }
    private var noteId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.activityFormNoteToolbar)
        configureImage()
        tryLoadIdNote()
        lifecycleScope.launch {
            launch {
                tryGetNote()
            }
            launch {
                configureImageUpload()
            }
        }

    }

    private suspend fun configureImageUpload() {
        val imageNote = binding.activityFormNoteImage
        image.collect { newImage ->
            imageNote.visibility =
                if (newImage.isNullOrBlank())
                    GONE
                else {
                    imageNote.tryloadImage(newImage)
                    VISIBLE
                }
        }
    }

    private suspend fun tryGetNote() {
        noteId?.let { id ->
            reposittory.searchById(id)
                .filterNotNull()
                .collect { noteFound ->
                    noteId = noteFound.id
                    image.value = noteFound.image
                    binding.activityFormNoteTitle.setText(noteFound.title)
                    binding.activityFormNoteDescription.setText(noteFound.description)

                }
        }
    }

    private fun tryLoadIdNote() {
        noteId = intent.getStringExtra(NOTE_ID)
    }

    private fun configureImage() {
        binding.activityFormNoteAddImage.setOnClickListener {
            FormImageDialog(this)
                .show(image.value) { imageFound ->
                    binding.activityFormNoteImage
                        .tryloadImage(imageFound)
                    image.value = imageFound
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.form_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.form_note_menu_salve -> {
                salve()
            }
            R.id.form_note_menu_remove -> {
                remove()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun remove() {
        lifecycleScope.launch {
            noteId?.let { id ->
                reposittory.remove(id)
            }
            finish()
        }
    }

    private fun salve() {
        val note = createNote()
        lifecycleScope.launch {
            reposittory.salve(note)
            finish()
        }
    }

    private fun createNote(): Note {
        val title = binding.activityFormNoteTitle.text.toString()
        val description = binding.activityFormNoteDescription.text.toString()
        return noteId?.let { id ->
            Note(
                id = id,
                title = title,
                description = description,
                image = image.value
            )
        } ?: Note(
            title = title,
            description = description,
            image = image.value
        )
    }

}
