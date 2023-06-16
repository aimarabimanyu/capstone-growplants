package com.glowplants.app.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glowplants.app.databinding.ItemJenisPenyakitBinding
import com.glowplants.app.helper.viewBinding
import com.glowplants.app.model.response.ArticelResponse

class JenisPenyakitAdapter (
    private val context: Context,
    var data: List<ArticelResponse.Data>,
    val cellClickListener: CellClickListener
) :
    RecyclerView.Adapter<JenisPenyakitAdapter.MyViewHolder>() {

    var posSelect = 999
    inner class MyViewHolder(binding: ItemJenisPenyakitBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var tvJenisPenyakit = binding.tvJenisPenyakit
        var tvDesc = binding.tvDesc
        var imgPenyakit = binding.imgPenyakit

        var parentLy = binding.parentLayout

        fun bind(data: ArticelResponse.Data, pos: Int) {
            tvJenisPenyakit.text = data.name
            tvDesc.text = data.description

            Glide.with(context)
                .load(data.imageUrl)
                .into(imgPenyakit)

            parentLy.setOnClickListener {
                cellClickListener.selectJenisPenyakit(data)
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JenisPenyakitAdapter.MyViewHolder {
        return MyViewHolder(parent.viewBinding(ItemJenisPenyakitBinding::inflate))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = data!![position]
        holder.bind(data, position)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    interface CellClickListener {
        fun selectJenisPenyakit(data: ArticelResponse.Data)
    }
}
