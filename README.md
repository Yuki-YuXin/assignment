# Yuki's Android Learning Plan guided by Sammy #
## Milestones
1. Bare bones application, including main components and architecture.
* Appropriate choice of architecture.
* Defining components that are needed and how they fit into the architecture.
2. API integration
* Implementation of network layer
3. Displaying list of meteors
* Using appropriate Android components, display a list of meteors received from the API (point 2).
4. List filtering and sorting
* Add interactive filter & sorting UI components to the view 
* Display filtered/sorted list
5. Detail view 
* More complex view logic by adding a detail view and handling navigation 
6. Persistence
* List of meteors should be stored for offline usage.
* Update application logic to use offline state store whenever API is not available. 

## Comments Summary
1. ListAdapter vs. RecyclerViewAdapter -> RecyclerViewAdapter
2. Lifecycle-Aware Components -> lifecycle
```MainActivity.kt
override fun onCreate(savedInstanceState: Bundle?) {
    lifecycle.addObserver(viewModel)
}
```
```MainViewModel.kt
private val repository: MeteorRepository) : ViewModel(), DefaultLifecycleObserver {
    override fun onResume(owner: LifecycleOwner) {
        getMeteorsInfo()
    }
}
```

3. Prevent leak -> onDestroy()
```MainActivity
override fun onDestroy() {
   super.onDestroy()
   Injection.destroy()
   lifecycle.removeObserver(viewModel)}
```
"This is a code vulnerability. You're setting the entire Activity as the listener, which means that the Activity can't be garbage collected as long as there is a reference to it. When is the reference to the activity cleaned up? Furthermore, the Activity doesn't really need to be informed of clicks when the activity is no longer in view. So: it's a good practise to "unset" the listener, for example in 'onDestroy()'."

4. RelativeLayout vs. LinearLayout -> RelativeLayout

5. Don't use hardcoded strings, use string resources instead. -> R.string.name

6. MVVM architecture (especially the functionality of ViewModel)

7. val vs. var -> val 

8. unnecessary init{} -> declaration with initialization 
“Declare context and recyclerItemClickListener as private vals in the MeteorsListAdapter initialisor, and you can use them as variables in the class. ”

9. MutableList vs. List -> List<MeteorData>

10. databinding