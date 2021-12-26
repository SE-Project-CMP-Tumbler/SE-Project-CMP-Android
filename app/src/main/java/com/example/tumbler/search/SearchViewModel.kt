package com.example.tumbler.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tumbler.BaseApplication
import com.example.tumbler.model.entity.Post
import com.example.tumbler.model.entity.search.Blogs
import com.example.tumbler.model.network.RemoteRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val remoteRepository: RemoteRepository) : ViewModel() {

    private var _blogsMutableLiveData = MutableLiveData<List<Blogs>>()
    val blogsLiveData: LiveData<List<Blogs>> get() = _blogsMutableLiveData

    private var _blogsFollowedMutableLiveData=MutableLiveData<MutableList<Boolean>>()
    val blogsFollowedLiveData: LiveData<MutableList<Boolean>> get()=_blogsFollowedMutableLiveData


    fun getRecommendedBlogs()=viewModelScope.launch{
        Log.i("Hala","iN view Model ")
        val blogs:List<Blogs> = remoteRepository.recommendedBlogs(BaseApplication.user.access_token)
        _blogsMutableLiveData.postValue(blogs)
        _blogsFollowedMutableLiveData.postValue(MutableList<Boolean>(blogs.size){
            false
        })

    }


    fun followBlog(blog_id:Int,position:Int)=viewModelScope.launch {
        remoteRepository.followBlog(BaseApplication.user.access_token,blog_id)
        _blogsFollowedMutableLiveData.value!![position]=true
    }
    fun unfollowBlog(blog_id:Int,position:Int)=viewModelScope.launch {
        remoteRepository.unfollowBlog(BaseApplication.user.access_token,blog_id)
        _blogsFollowedMutableLiveData.value!![position]=false
    }
}