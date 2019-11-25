package com.test.movieapp.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.movieapp.R

class MoviesAdapter :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {
    private val mData = mutableListOf<MovieData>()

    fun setData(data: List<MovieData>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(data: List<MovieData>) {
        mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount() = mData.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    class MovieViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val picture = itemView.findViewById<ImageView>(R.id.ivPicture)
        private val title = itemView.findViewById<TextView>(R.id.tvTitle)
        private val overview = itemView.findViewById<TextView>(R.id.tvOverview)

        fun bind(item: MovieData) {
            title.text = item.title
            overview.text = item.overview
            if (item.pictureUrl != null) {
                Glide.with(itemView.context)
                    .load(item.pictureUrl)
                    .fitCenter()
                    .into(picture)
            }
        }
    }
}