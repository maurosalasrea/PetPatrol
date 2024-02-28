import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petpatrol.MyItem
import com.example.petpatrol.R

class MyListAdapter(private val items: List<MyItem>) : RecyclerView.Adapter<MyListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textViewItem)
        val imageView1: ImageView = view.findViewById(R.id.imageView1)
        val imageView2: ImageView = view.findViewById(R.id.imageView2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item.text
        // Asumiendo que tus iconos son recursos drawable
        holder.imageView1.setImageResource(item.imageResId1)
        holder.imageView2.setImageResource(item.imageResId2)
    }

    override fun getItemCount() = items.size
}
