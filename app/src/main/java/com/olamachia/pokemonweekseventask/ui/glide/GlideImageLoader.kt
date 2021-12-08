package com.olamachia.pokemonweekseventask.ui.glide

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

class GlideImageLoader(
    private val mImageView: ImageView?,
    private val mProgressBar: ProgressBar?,
    private val mCardBg: CardView?,
    private val mText: TextView?
) {

    fun load(url: String?, options: RequestOptions?) {
        if (url == null || options == null) return
        onConnecting()

        //set Listener & start
        ProgressAppGlideModule.expect(url, object : ProgressAppGlideModule.UIonProgressListener {
            override fun onProgress(bytesRead: Long, expectedLength: Long) {
                mProgressBar?.progress = (100 * bytesRead / expectedLength).toInt()
            }

            override val granularityPercentage: Float
                get() = 1.0f
        })

        if (mImageView != null) {
                Glide.with(mImageView.context)
                    .load(url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(options.skipMemoryCache(true))
                    .listener(object : RequestListener<Drawable?> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable?>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            ProgressAppGlideModule.forget(url)
                            onFinished()
                            return false
                        }

                        @RequiresApi(Build.VERSION_CODES.O)
                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable?>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            ProgressAppGlideModule.forget(url)
                            setCardBackgroundColor(resource, mCardBg)
                            setTextColor(resource, mText)
                            onFinished()
                            return false
                        }

                    })
                    .into(mImageView)
        }
    }

    private fun onConnecting() {
        mProgressBar?.visibility = View.VISIBLE
    }

    private fun onFinished() {
        if (mProgressBar != null && mImageView != null) {
            mProgressBar.visibility = View.GONE
            mImageView.visibility = View.VISIBLE
        }
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun setCardBackgroundColor(
            resource: Drawable?,
            mCardBg: CardView?,
        ) {
            resource?.toBitmap()?.let { Palette.from(it).generate { palette ->
                val bgColor = palette?.getDarkMutedColor(palette.getMutedColor(Color.BLACK))
                if (bgColor != null) {
                    mCardBg?.setCardBackgroundColor(bgColor) }
            }

            }
        }

        fun setTextColor(
            resource: Drawable?,
            mText: TextView?,
        ) {
            resource?.toBitmap()?.let { Palette.from(it).generate { palette ->
                val textColor = palette?.getLightVibrantColor(Color.WHITE)
                if (textColor != null) {
                    mText?.setTextColor(textColor)
                    }
                }
            }
        }
    }

}