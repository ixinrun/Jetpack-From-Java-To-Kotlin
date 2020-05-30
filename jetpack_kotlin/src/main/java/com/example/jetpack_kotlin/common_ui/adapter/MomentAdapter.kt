package com.example.jetpack_kotlin.common_ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpack_koltin.R
import com.example.jetpack_kotlin.common_data.bean.Moment
import com.kunminx.architecture.utils.loadImage

/**
 * @author Flywith24
 * @date   2020/5/30
 * time   20:31
 * description
 * ListAdapter 强制使用 DiffUtil 比较数据变化，开发者无需手动调用各种 notify 方法亦可使用 recyclerView 的动画
 */
class MomentAdapter(private val listener: OnItemClickListener) : ListAdapter<Moment, MomentAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_moment, parent, false))
        // setOnClickListener 在此处调用，如果在 onBindViewHolder 中调用会执行多次
        holder.itemView.setOnClickListener {
            listener.onItemClick(holder.item)
        }
        return holder
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.tv_name)
        private val tvContent = itemView.findViewById<TextView>(R.id.tv_content)
        private val tvLocation = itemView.findViewById<TextView>(R.id.tv_location)
        private val ivAvatar = itemView.findViewById<ImageView>(R.id.iv_avatar)
        private val ivPreview = itemView.findViewById<ImageView>(R.id.iv_preview)

        lateinit var item: Moment

        fun bind(item: Moment) {
            this.item = item
            tvName.text = item.username
            tvContent.text = item.content
            tvLocation.text = item.location
            //此处使用了扩展函数
            //扩展函数详情可参考https://www.runoob.com/kotlin/kotlin-extensions.html
            ivAvatar.loadImage(item.userAvatar)
            ivPreview.loadImage(item.imgUrl)
        }

    }

    interface OnItemClickListener {
        fun onItemClick(moment: Moment)
    }

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<Moment>() {
            override fun areItemsTheSame(oldItem: Moment, newItem: Moment): Boolean {
                //默认 username 为唯一表示
                return oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: Moment, newItem: Moment): Boolean {
                return oldItem.content == newItem.content &&
                        oldItem.imgUrl == newItem.imgUrl &&
                        oldItem.location == newItem.location &&
                        oldItem.userAvatar == newItem.userAvatar
            }
        }
    }
}