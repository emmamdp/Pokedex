package com.emdp.domain.common.base.usecase

import com.emdp.domain.common.base.result.PokedexResult

interface PokedexBaseUseCase<P, R> {
    suspend operator fun invoke(params: P): PokedexResult<R>
}