package com.example.vaultx.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vaultx.security.EncryptionManager
import com.google.android.material.button.MaterialButton
import com.example.vaultx.Models.PasswordItem
import com.example.vaultx.R
import com.example.vaultx.Util.DateFormatter
import com.example.vaultx.Util.IconManager

class PasswordAdapter(
    private val onClick: (PasswordItem) -> Unit,
    private val onDelete: (PasswordItem) -> Unit
) : RecyclerView.Adapter<PasswordAdapter.ViewHolder>() {

    private var list = listOf<PasswordItem>()
    private val crypto = EncryptionManager()

    fun submitList(data: List<PasswordItem>) {

        val newList = data.toList()

        val diffUtil = PasswordDiffUtil(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        list = newList
        diffResult.dispatchUpdatesTo(this)

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView = view.findViewById(R.id.tvTitle)
        val email: TextView = view.findViewById(R.id.tvEmail)
        val password: TextView = view.findViewById(R.id.tvPassword)
        val date: TextView = view.findViewById(R.id.tvDate)
        val icon: ImageView = view.findViewById(R.id.icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.password_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        holder.title.text = item.title
        holder.email.text = item.email
        holder.password.text = "••••••••"

        val dateFormatter = DateFormatter()
        holder.date.text = dateFormatter.formatDate(item.updatedAt)

        holder.itemView.setOnClickListener {
            onClick(item)
        }

        holder.itemView.setOnLongClickListener {

            val context = holder.itemView.context
            val view = LayoutInflater.from(context)
                .inflate(R.layout.dialog, null)

            val dialog = AlertDialog.Builder(context)
                .setView(view)
                .create()

            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            val btnCancel = view.findViewById<MaterialButton>(R.id.btnCancel)
            val btnDelete = view.findViewById<MaterialButton>(R.id.btnDelete)

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            btnDelete.setOnClickListener {
                onDelete(item)
                dialog.dismiss()
            }

            dialog.show()

            true
        }

        val iconManager = IconManager()

        holder.icon.setImageResource(
            iconManager.setIcon(item.title)
        )

    }

}