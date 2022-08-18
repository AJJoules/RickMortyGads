package ng.com.ajsprojects.rickmortygads.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ng.com.ajsprojects.rickmortygads.data.model.RickMortyResponse
import ng.com.ajsprojects.rickmortygads.network.RickMortyApiService
import ng.com.ajsprojects.rickmortygads.utils.STARTING_PAGE

class RickMortyDataSource(
    private val apiService: RickMortyApiService
) : PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val position = if (params.key == null) STARTING_PAGE else params.key

        try {
            val response: RickMortyResponse = apiService.getAllCharacters()

            val characters = response.results

            if (characters.isNotEmpty()) {
                LoadResult.Page(
                    data = characters,
                    prevKey = if (position == STARTING_PAGE) null else position!! - 1,
                    nextKey = null
                )
            }

        } catch (e: Exception) {

        }
    }

}