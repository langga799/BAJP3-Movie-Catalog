package com.langga.moviecatalog.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.langga.moviecatalog.data.source.local.LocalDataSource
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity
import com.langga.moviecatalog.data.source.remote.RemoteDataSource
import com.langga.moviecatalog.utils.*
import com.langga.moviecatalog.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock


class MovieTvRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutor = mock(AppExecutors::class.java)

    private val movieTvRepository = FakeRepository(remote, local, appExecutor)
    private val movieResponse = DataLocal.generateRemoteLocalMovies()
    private val movieId = movieResponse[0].id
    private val tvShowResponse = DataLocal.generateRemoteLocalTvShow()
    private val tvShowId = tvShowResponse[0].id
    private val sort = SortUtils.RATING

    @Test
    fun getListMovie() {
        val localMovie =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies(sort)).thenReturn(localMovie)
        movieTvRepository.getAllMovies(sort)

        val movieEntity =
            Resource.success(PagedListUtils.mockPagedList(DataLocal.generateLocalMovies()))
        verify(local).getAllMovies(sort)
        assertNotNull(movieEntity.data)
        assertEquals(movieResponse.size.toLong(), movieEntity.data?.size?.toLong())
    }

    @Test
    fun getListTvShow() {
        val localTvShow =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getAllTvShow(sort)).thenReturn(localTvShow)
        movieTvRepository.getAllTvShows(sort)

        val tvShowEntity =
            Resource.success(PagedListUtils.mockPagedList(DataLocal.generateLocalTvShow()))
        verify(local).getAllMovies(sort)
        assertNotNull(tvShowEntity.data)
        assertEquals(tvShowResponse.size.toLong(), tvShowEntity.data?.size?.toLong())
    }

    @Test
    fun getMovieById() {
        val localMovie = MutableLiveData<MovieEntity>()
        localMovie.value = DataLocal.generateLocalMovies()[0]
        `when`(local.getMovieById(movieId)).thenReturn(localMovie)

        val movieEntity = LiveDataTestUtil.getValue(movieTvRepository.getMovieById(movieId)).data
        verify(local).getMovieById(movieId)
        val movieResponses = movieResponse[0]
        assertNotNull(movieEntity)
        if (movieEntity != null) {
            assertEquals(movieResponses.overview, movieEntity.overview)
            assertEquals(movieResponses.originalLanguage, movieEntity.originalLanguage)
            assertEquals(movieResponses.originalTitle, movieEntity.originalTitle)
            assertEquals(movieResponses.posterPath, movieEntity.posterPath)
            assertEquals(movieResponses.backdropPath, movieEntity.backDropPath)
            assertEquals(movieResponses.releaseDate, movieEntity.releaseDate)
            assertEquals(movieResponses.popularity, movieEntity.popularity)
            assertEquals(movieResponses.voteAverage, movieEntity.voteAverage)
            assertEquals(movieResponses.id, movieEntity.id)
            assertEquals(movieResponses.voteCount, movieEntity.voteCount)
        }
    }

    @Test
    fun getTvShowById() {
        val localTvShow = MutableLiveData<TvShowEntity>()
        localTvShow.value = DataLocal.generateLocalTvShow()[0]
        `when`(local.getTvShowById(tvShowId)).thenReturn(localTvShow)

        val tvShowEntity = LiveDataTestUtil.getValue(movieTvRepository.getTvShowById(tvShowId)).data
        verify(local).getTvShowById(tvShowId)
        val tvShowResponse = tvShowResponse[0]
        assertNotNull(tvShowEntity)
        if (tvShowEntity != null) {
            assertEquals(tvShowResponse.overview, tvShowEntity.overview)
            assertEquals(tvShowResponse.originalLanguage, tvShowEntity.originalLanguage)
            assertEquals(tvShowResponse.originalName, tvShowEntity.originalName)
            assertEquals(tvShowResponse.posterPath, tvShowEntity.posterPath)
            assertEquals(tvShowResponse.backdropPath, tvShowEntity.backDropPath)
            assertEquals(tvShowResponse.firstAirDate, tvShowEntity.firstAirDate)
            assertEquals(tvShowResponse.popularity, tvShowEntity.popularity)
            assertEquals(tvShowResponse.voteAverage, tvShowEntity.voteAverage)
            assertEquals(tvShowResponse.id, tvShowEntity.id)
            assertEquals(tvShowResponse.voteCount, tvShowEntity.voteCount)
        }
    }

    @Test
    fun getFavoriteMovie(){
        val dataFavoriteMovie = mock(DataSource.Factory::class.java)  as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllFavoriteMovie()).thenReturn(dataFavoriteMovie)
        movieTvRepository.getFavoriteMovie()

        val favoriteMovieEntity = Resource.success(PagedListUtils.mockPagedList(DataLocal.generateLocalMovies()))
        verify(local).getAllFavoriteMovie()
        assertNotNull(favoriteMovieEntity.data)
        assertEquals(movieResponse.size.toLong(), favoriteMovieEntity.data?.size?.toLong())
    }

    @Test
    fun getFavoriteTvShow(){
        val dataFavoriteTvShow = mock(DataSource.Factory::class.java)  as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getAllFavoriteTvShow()).thenReturn(dataFavoriteTvShow)
        movieTvRepository.getFavoriteTvShow()

        val favoriteTvShowEntity = Resource.success(PagedListUtils.mockPagedList(DataLocal.generateLocalTvShow()))
        verify(local).getAllFavoriteTvShow()
        assertNotNull(favoriteTvShowEntity.data)
        assertEquals(tvShowResponse.size.toLong(), favoriteTvShowEntity.data?.size?.toLong())
    }

}