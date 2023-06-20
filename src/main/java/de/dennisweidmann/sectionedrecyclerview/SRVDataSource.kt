package de.dennisweidmann.sectionedrecyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder

interface SRVDataSource {
    fun numberOfSections(): Int
    fun numberOfRowsInSection(section: Int): Int

    fun viewHolderForRowAtIndexPath(indexPath: SRVIndexPath, parent: ViewGroup): ViewHolder
    fun onBindViewHolderForRowAtIndexPath(viewHolder: ViewHolder, indexPath: SRVIndexPath)

    fun titleForHeaderInSection(section: Int): String? { return null }
    fun viewHolderForHeaderInSection(section: Int, parent: ViewGroup? = null): ViewHolder? { return null }
    fun onBindViewHolderForHeaderInSection(viewHolder: ViewHolder, section: Int) { }

    fun titleForFooterInSection(section: Int): String? { return null }
    fun viewHolderForFooterInSection(section: Int, parent: ViewGroup? = null): ViewHolder? { return null }
    fun onBindViewHolderForFooterInSection(viewHolder: ViewHolder, section: Int) { }
}