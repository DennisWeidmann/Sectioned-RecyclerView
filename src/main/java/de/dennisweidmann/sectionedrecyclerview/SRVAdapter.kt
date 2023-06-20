package de.dennisweidmann.sectionedrecyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.lang.ref.WeakReference

class SRVAdapter (
    private val dataSource: WeakReference<SRVDataSource>,
    private val delegate: WeakReference<SRVDelegate>
): RecyclerView.Adapter<ViewHolder>() {

    private var rows = arrayListOf<SRVRow>()

    init {
        reloadData()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val row = rows[viewType]

        return when (row.type) {
            SRVRowType.HEADER -> dataSource.get()?.viewHolderForHeaderInSection(row.indexPath.section, parent)?: SRVDefaultHeader(LayoutInflater.from(parent.context).inflate(
                R.layout.row_default_header, parent, false))
            SRVRowType.ROW -> dataSource.get()?.viewHolderForRowAtIndexPath(row.indexPath, parent)?: SRVDefaultRow(LayoutInflater.from(parent.context).inflate(
                R.layout.row_default_row, parent, false))
            SRVRowType.FOOTER -> dataSource.get()?.viewHolderForFooterInSection(row.indexPath.section, parent)?: SRVDefaultFooter(LayoutInflater.from(parent.context).inflate(
                R.layout.row_default_footer, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return rows.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = rows[position]

        when(row.type) {
            SRVRowType.HEADER -> {
                (holder as? SRVDefaultHeader)?.let {
                    it.defaultLabel.text = dataSource.get()?.titleForHeaderInSection(row.indexPath.section)
                }
                dataSource.get()?.onBindViewHolderForHeaderInSection(holder, row.indexPath.section)
            }
            SRVRowType.ROW -> {
                dataSource.get()?.onBindViewHolderForRowAtIndexPath(holder, row.indexPath)

                delegate.get()?.let {
                    holder.itemView.setOnClickListener { _ ->
                        it.didSelectRowAt(row.indexPath)
                    }
                }
            }
            SRVRowType.FOOTER -> {
                (holder as? SRVDefaultFooter)?.let {
                    it.defaultLabel.text = dataSource.get()?.titleForFooterInSection(row.indexPath.section)
                }
                dataSource.get()?.onBindViewHolderForFooterInSection(holder, row.indexPath.section)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadData() {
        rows.clear()

        dataSource.get()?.let { dataSource ->
            for (section in 0 until dataSource.numberOfSections()) {

                if (dataSource.numberOfRowsInSection(section) > 0 && (!dataSource.titleForHeaderInSection(section).isNullOrEmpty() || dataSource.viewHolderForHeaderInSection(section) != null)) {
                    rows.add(SRVRow(SRVIndexPath(section, -1), SRVRowType.HEADER))
                }

                for (row in 0 until dataSource.numberOfRowsInSection(section)) {
                    rows.add(SRVRow(SRVIndexPath(section, row), SRVRowType.ROW))
                }

                if (dataSource.numberOfRowsInSection(section) > 0 && (!dataSource.titleForFooterInSection(section).isNullOrEmpty() || dataSource.viewHolderForFooterInSection(section) != null)) {
                    rows.add(SRVRow(SRVIndexPath(section, -1), SRVRowType.FOOTER))
                }
            }
        }

        notifyDataSetChanged()
    }

}

private data class SRVRow(
    val indexPath: SRVIndexPath,
    val type: SRVRowType
)

private enum class SRVRowType {
    HEADER, ROW, FOOTER
}

private class SRVDefaultHeader(view: View): ViewHolder(view) {
    val defaultLabel: AppCompatTextView = view.findViewById(R.id.row_default_header_text_view)
}

private class SRVDefaultRow(view: View): ViewHolder(view) {
    //val defaultLabel: AppCompatTextView = view.findViewById(R.id.row_default_row_text_view)
}

private class SRVDefaultFooter(view: View): ViewHolder(view) {
    val defaultLabel: AppCompatTextView = view.findViewById(R.id.row_default_footer_text_view)
}