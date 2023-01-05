package br.com.alura.ceep.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import br.com.alura.ceep.databinding.FormImageBinding
import br.com.alura.ceep.extensions.tryloadImage

class FormImageDialog(private val context: Context) {

    fun show(
        defaultUrl: String? = null,
        whenImageLoaded: (imagem: String) -> Unit
    ) {
        FormImageBinding.inflate(LayoutInflater.from(context)).apply {

                defaultUrl?.let {
                    formImageviewImage.tryloadImage(it)
                    formUrlImage.setText(it)
                }

                formLoadButtonImage.setOnClickListener {
                    val url = formUrlImage.text.toString()
                    formImageviewImage.tryloadImage(url)
                }

                AlertDialog.Builder(context)
                    .setView(root)
                    .setPositiveButton("Confirmar") { _, _ ->
                        val url = formUrlImage.text.toString()
                        whenImageLoaded(url)
                    }
                    .setNegativeButton("Cancelar") { _, _ ->

                    }
                    .show()
            }


    }

}