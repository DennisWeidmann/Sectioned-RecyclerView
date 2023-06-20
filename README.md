# Sectioned RecyclerView
A simple solution for having sections, headers and footers in an Android RecyclerView.

### Adapter setup
##### Setup the SRVAdapter itself and set it as an adapter of your RecyclerView.
```kotlin
recyclerView.adapter = SRVAdapter(WeakReference(this), WeakReference(this))
```
```kotlin
//This reloads the adapter if changes have been made.
srvAdapter.reloadData()
```

### DataSrouce setup
##### Setup the SRVDataSource by implementing the interface to your Activity.
```kotlin
class MainActivity : AppCompatActivity(), SRVDataSource {
```

```kotlin
override fun numberOfSections(): Int {
  //Define the number of sections that will be used.
  return 4
}

override fun numberOfRowsInSection(section: Int): Int {
  //Define the number of rows in each section that will be used.
  if (section == 1) {
    return 4
  }
  return 1
}
```

```kotlin
override fun viewHolderForRowAtIndexPath(indexPath: SRVIndexPath, parent: ViewGroup): RecyclerView.ViewHolder {
  //Define which kind of ViewHolder should be used on which row and which layout it should have.
  return TestViewHolder(layoutInflater.inflate(R.layout.row_default_row, parent, false))
}

override fun onBindViewHolderForRowAtIndexPath(viewHolder: RecyclerView.ViewHolder, indexPath: SRVIndexPath) {
  (viewHolder as? TestViewHolder)?.let {
    //Setup the contents of the row.
  }
}
```

```kotlin
override fun viewHolderForHeaderInSection(section: Int, parent: ViewGroup?): RecyclerView.ViewHolder? {
  //Define which kind of ViewHolder should be used as a header on which section and which layout it should have.
  return TestViewHolder(layoutInflater.inflate(R.layout.row_default_row, parent, false))
}

override fun onBindViewHolderForHeaderInSection(viewHolder: RecyclerView.ViewHolder, section: Int) {
  (viewHolder as? TestViewHolder)?.let {
    //Setup the contents of the header.
  }
}
```

```kotlin
override fun viewHolderForFooterInSection(section: Int, parent: ViewGroup?): RecyclerView.ViewHolder? {
  //Define which kind of ViewHolder should be used as a footer on which section and which layout it should have.
  return TestViewHolder(layoutInflater.inflate(R.layout.row_default_row, parent, false))
}

override fun onBindViewHolderForFooterInSection(viewHolder: RecyclerView.ViewHolder, section: Int) {
  (viewHolder as? TestViewHolder)?.let {
    //Setup the contents of the footer.
  }
}
```

### Delegate setup
##### Setup the SRVDelegate by implementing the interface to your Activity.
```kotlin
class MainActivity : AppCompatActivity(), SRVDelegate {
```
```kotlin
override fun didSelectRowAt(indexPath: SRVIndexPath) {
  //A row has been clicked in section at row.
}
```
