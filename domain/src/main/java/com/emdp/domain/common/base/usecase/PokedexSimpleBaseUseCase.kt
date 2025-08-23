package com.emdp.domain.common.base.usecase

import com.emdp.domain.common.base.result.PokedexResult
import com.emdp.domain.model.base.NoParams

typealias PokedexSimpleUseCase<R> = PokedexBaseUseCase<NoParams, R>

suspend operator fun <R> PokedexSimpleUseCase<R>.invoke(): PokedexResult<R> = this.invoke(NoParams)