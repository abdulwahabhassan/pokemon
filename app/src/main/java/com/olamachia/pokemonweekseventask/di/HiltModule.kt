package com.olamachia.pokemonweekseventask.di

import com.olamachia.pokemonweekseventask.data.api.PokemonApi
import com.olamachia.pokemonweekseventask.data.api.ServiceModule
import com.olamachia.pokemonweekseventask.data.datasource.PokemonRemoteDatasource
import com.olamachia.pokemonweekseventask.data.datasource.PokemonRemoteDatasourceImpl
import com.olamachia.pokemonweekseventask.data.datasource.PokemonRemotePagingSource
import com.olamachia.pokemonweekseventask.data.mappers.*
import com.olamachia.pokemonweekseventask.data.repository.PokemonRepositoryImpl
import com.olamachia.pokemonweekseventask.domain.repository.PokemonRepository
import com.olamachia.pokemonweekseventask.domain.usecases.GetPokemonByLimitUseCase
import com.olamachia.pokemonweekseventask.domain.usecases.GetPokemonDetailsUseCase
import com.olamachia.pokemonweekseventask.domain.usecases.GetPokemonPagedUseCase
import com.olamachia.pokemonweekseventask.ui.adapter.PokemonPagingAdapter
import com.olamachia.pokemonweekseventask.ui.views.PokemonDetailsFragment
import com.olamachia.pokemonweekseventask.ui.views.PokemonFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PokemonApiModule {

    private const val POKEMON_API_BASE_URL = "https://pokeapi.co/api/v2/"

    @Provides
    fun providesPokemonApi () : PokemonApi {
        return ServiceModule().createPokemonApi(POKEMON_API_BASE_URL)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object ResponseMapperModule {

    @Provides
    fun provideStatMapper() : StatMapper = StatMapper()

    @Provides
    fun provideStatsMapper(
        statMapper: StatMapper
    ) : StatsMapper = StatsMapper(statMapper)

    @Provides
    fun provideMoveMapper() : MoveMapper = MoveMapper()

    @Provides
    fun provideMovesMapper(
        moveMapper: MoveMapper
    ) : MovesMapper = MovesMapper(moveMapper)

    @Provides
    fun provideSpeciesMapper() : SpeciesMapper = SpeciesMapper()

    @Provides
    fun provideTypeMapper(
        speciesMapper: SpeciesMapper
    ) : TypeMapper = TypeMapper(speciesMapper)

    @Provides
    fun provideHomeMapper() : HomeMapper = HomeMapper()

    @Provides
    fun provideOtherMapper(
        homeMapper: HomeMapper
    ) : OtherMapper = OtherMapper(homeMapper)

    @Provides
    fun provideSpritesMapper(
        otherMapper: OtherMapper
    ) : SpritesMapper = SpritesMapper(otherMapper)


    @Provides
    fun providePokemonPagedMapper(
        pokemonMapper: PokemonMapper
    ) : PokemonPagedMapper = PokemonPagedMapper(pokemonMapper)

    @Provides
    fun providePokemonMapper() : PokemonMapper = PokemonMapper()

    @Provides
    fun providePokemonDetailsMapper(
        abilityMapper: AbilityMapper,
        speciesMapper: SpeciesMapper,
        spritesMapper: SpritesMapper,
        typeMapper: TypeMapper,
        statsMapper: StatsMapper,
        movesMapper: MovesMapper
    ) : PokemonDetailsMapper =
        PokemonDetailsMapper(
            abilityMapper,
            speciesMapper,
            typeMapper,
            spritesMapper,
            statsMapper,
            movesMapper
        )

    @Provides
    fun provideAbilityMapper(
        speciesMapper: SpeciesMapper
    ) : AbilityMapper = AbilityMapper(speciesMapper)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class PokemonRemoteDatasourceModule {

    @Binds
    abstract fun bindPokemonRemoteDatasource(
        pokemonRemoteDatasourceImpl: PokemonRemoteDatasourceImpl
    ): PokemonRemoteDatasource
}

@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatchersModule {

    @Singleton
    @Provides
    fun provideDispatcherIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}


@Module
@InstallIn(SingletonComponent::class)
object PokemonRemotePagingSourceModule{

    @Provides
    fun providePokemonRemotePagingSource(
        service: PokemonApi,
        pokemonMapper: PokemonMapper
    ) : PokemonRemotePagingSource {
        return PokemonRemotePagingSource(
            service,
            pokemonMapper
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
object PokemonRepositoryModule {

    @Provides
    fun providesPokemonRepository(
        pokemonRemotePagingDataSource: PokemonRemotePagingSource,
        pokemonRemoteDataSource: PokemonRemoteDatasource
    ): PokemonRepository {
        return PokemonRepositoryImpl(
            pokemonRemotePagingDataSource,
            pokemonRemoteDataSource
        )
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @ViewModelScoped
    @Provides
    fun provideGetPokemonPagedUseCase(
        pokemonRepository: PokemonRepository
    ) : GetPokemonPagedUseCase {
        return GetPokemonPagedUseCase(pokemonRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideGetPokemonDetailsUseCase(
        pokemonRepository: PokemonRepository
    ) : GetPokemonDetailsUseCase {
        return GetPokemonDetailsUseCase(pokemonRepository)
    }

    @ViewModelScoped
    @Provides
    fun provideGetPokemonByLimitUseCase(
        pokemonRepository: PokemonRepository
    ) : GetPokemonByLimitUseCase {
        return GetPokemonByLimitUseCase(pokemonRepository)
    }
}