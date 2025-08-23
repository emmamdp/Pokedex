package com.emdp.domain.common.base.usecase

import com.emdp.domain.common.base.result.PokedexResult
import com.emdp.domain.model.base.NoParams

typealias SimpleUseCase<R> = PokedexBaseUseCase<NoParams, R>

suspend operator fun <R> SimpleUseCase<R>.invoke(): PokedexResult<R> = this.invoke(NoParams)