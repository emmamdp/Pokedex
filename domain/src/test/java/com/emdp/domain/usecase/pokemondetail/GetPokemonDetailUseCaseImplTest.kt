package com.emdp.domain.usecase.pokemondetail

import com.emdp.domain.common.base.result.PokedexResult.PkError
import com.emdp.domain.common.base.result.PokedexResult.PkSuccess
import com.emdp.domain.model.PokemonDetailModelMother
import com.emdp.domain.model.error.PokedexGenericError.NoConnection
import com.emdp.domain.repository.PokedexRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetPokemonDetailUseCaseImplTest {

    private var repository: PokedexRepository = mockk()

    private lateinit var useCase: GetPokemonDetailUseCase

    @Before
    fun setUp() {
        useCase = GetPokemonDetailUseCaseImpl(repository)
    }

    @Test
    fun `returns success from repository`() = runTest {
        val id = 25
        val model = PokemonDetailModelMother.mock()
        coEvery { repository.getPokemonDetail(id) } returns PkSuccess(model)

        val result = useCase(id)

        assertTrue(result is PkSuccess)
        assertEquals(model, (result as PkSuccess).pkData)
        coVerify(exactly = 1) { repository.getPokemonDetail(id) }
    }

    @Test
    fun `returns error from repository`() = runTest {
        val id = 99999
        val error = NoConnection
        coEvery { repository.getPokemonDetail(id) } returns PkError(error)

        val result = useCase(id)

        assertTrue(result is PkError)
        assertEquals(error, (result as PkError).pkError)
        coVerify(exactly = 1) { repository.getPokemonDetail(id) }
    }
}