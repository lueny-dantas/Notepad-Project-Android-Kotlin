package br.com.alura.ceep.extensions

import android.widget.ImageView
import br.com.alura.ceep.R
import coil.load

fun ImageView.tryloadImage(
    url: String? = null,
    fallback: Int = R.drawable.default_image
) {
    load(url) {
        placeholder(R.drawable.placeholder)
        error(R.drawable.erro)
        fallback(fallback)
    }
}