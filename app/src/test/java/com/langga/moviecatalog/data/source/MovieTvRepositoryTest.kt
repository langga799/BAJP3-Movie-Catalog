package com.langga.moviecatalog.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.langga.moviecatalog.data.source.remote.RemoteDataSource
import com.langga.moviecatalog.utils.DataLocal
import com.langga.moviecatalog.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock


class MovieTvRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val movieTvRepository = FakeRepository(remote)
    private val movieResponse = DataLocal.generateRemoteLocalMovies()
    private val movieId = movieResponse[0].id.toString()
    private val tvShowResponse = DataLocal.generateRemoteLocalTvShow()
    private val tvShowId = tvShowResponse[0].id.toString()

    @Test
    fun getListMovie() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadMovieCallback)
                .onAllMovieReceived(movieResponse)
            null
        }.`when`(remote).getListMovies(any())
        val movieEntity = LiveDataTestUtil.getValue(movieTvRepository.getAllMovies())
        verify(remote).getListMovies(any())
        assertNotNull(movieEntity)
        assertEquals(movieResponse.size.toLong(), movieEntity.size.toLong())
    }

    @Test
    fun getListTvShow() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadTvShowCallback)
                .onAllTvShowReceived(tvShowResponse)
            null
        }.`when`(remote).getListTvShow(any())
        val tvShowEntity = LiveDataTestUtil.getValue(movieTvRepository.getAllTvShows())
        verify(remote).getListTvShow(any())
        assertNotNull(tvShowEntity)
        assertEquals(tvShowResponse.size.toLong(), tvShowEntity.size.toLong())
    }

    @Test
    fun getMovieById() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadMovieCallback)
                .onAllMovieReceived(movieResponse)
            null
        }.`when`(remote).getListMovies(any())
        val movieEntity = LiveDataTestUtil.getValue(movieTvRepository.getMovieById(movieId))
        val movieResponses = movieResponse[0]
        verify(remote).getListMovies(any())
        assertNotNull(movieEntity)
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

    @Test
    fun getTvShowById() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadTvShowCallback)
                .onAllTvShowReceived(tvShowResponse)
            null
        }.`when`(remote).getListTvShow(any())
        val tvShowEntity = LiveDataTestUtil.getValue(movieTvRepository.getTvShowById(tvShowId))
        val tvShowResponses = tvShowResponse[0]
        verify(remote).getListTvShow(any())
        assertNotNull(tvShowEntity)
        assertEquals(tvShowResponses.overview, tvShowEntity.overview)
        assertEquals(tvShowResponses.originalLanguage, tvShowEntity.originalLanguage)
        assertEquals(tvShowResponses.originalName, tvShowEntity.originalName)
        assertEquals(tvShowResponses.posterPath, tvShowEntity.posterPath)
        assertEquals(tvShowResponses.backdropPath, tvShowEntity.backDropPath)
        assertEquals(tvShowResponses.firstAirDate, tvShowEntity.firstAirDate)
        assertEquals(tvShowResponses.popularity, tvShowEntity.popularity)
        assertEquals(tvShowResponses.voteAverage, tvShowEntity.voteAverage)
        assertEquals(tvShowResponses.id, tvShowEntity.id)
        assertEquals(tvShowResponses.voteCount, tvShowEntity.voteCount)
    }

}