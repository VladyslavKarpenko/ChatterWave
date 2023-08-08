package com.test.chatterwave.beta.di.hilt.module.auth

import com.test.chatterwave.beta.domain.repository.SignUpRepository
import com.chi.interngram.echo.domain.usecase.*
import com.test.chatterwave.beta.domain.usecase.CheckUserEmailExistsUseCase
import com.test.chatterwave.beta.domain.usecase.CheckUserEmailUseCase
import com.test.chatterwave.beta.domain.usecase.CheckUserNicknameUseCase
import com.test.chatterwave.beta.domain.usecase.CheckUserPhoneExistsUseCase
import com.test.chatterwave.beta.domain.usecase.CheckUserPhoneUseCase
import com.test.chatterwave.beta.domain.usecase.CreateConfirmCodeByEmailUseCase
import com.test.chatterwave.beta.domain.usecase.CreateConfirmCodeByPhoneUseCase
import com.test.chatterwave.beta.domain.usecase.SignUpUserUseCase
import com.test.chatterwave.beta.domain.usecase.ValidateConfirmCodeByEmailUseCase
import com.test.chatterwave.beta.domain.usecase.ValidateConfirmCodeByPhoneUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SignUpUseCaseModule {

    @Provides
    @Singleton
    fun provideCheckUserEmailUseCase(signUpRepository: SignUpRepository): CheckUserEmailUseCase {
        return CheckUserEmailUseCase(signUpRepository)
    }

    @Provides
    @Singleton
    fun provideCheckUserPhoneUseCase(signUpRepository: SignUpRepository): CheckUserPhoneUseCase {
        return CheckUserPhoneUseCase(signUpRepository)
    }

    @Provides
    @Singleton
    fun provideCheckUserNicknameUseCase(signUpRepository: SignUpRepository): CheckUserNicknameUseCase {
        return CheckUserNicknameUseCase(signUpRepository)
    }

    @Provides
    @Singleton
    fun provideCreateConfirmCodeByEmailUseCase(signUpRepository: SignUpRepository): CreateConfirmCodeByEmailUseCase {
        return CreateConfirmCodeByEmailUseCase(signUpRepository)
    }

    @Provides
    @Singleton
    fun provideCreateConfirmCodeByPhoneUseCase(signUpRepository: SignUpRepository): CreateConfirmCodeByPhoneUseCase {
        return CreateConfirmCodeByPhoneUseCase(signUpRepository)
    }

    @Provides
    @Singleton
    fun provideValidateConfirmCodeByEmailUseCase(signUpRepository: SignUpRepository): ValidateConfirmCodeByEmailUseCase {
        return ValidateConfirmCodeByEmailUseCase(signUpRepository)
    }

    @Provides
    @Singleton
    fun provideValidateConfirmCodeByPhoneUseCase(signUpRepository: SignUpRepository): ValidateConfirmCodeByPhoneUseCase {
        return ValidateConfirmCodeByPhoneUseCase(signUpRepository)
    }

    @Provides
    @Singleton
    fun provideSignUpUSerUseCase(signUpRepository: SignUpRepository): SignUpUserUseCase {
        return SignUpUserUseCase(signUpRepository)
    }

    @Provides
    @Singleton
    fun provideCheckUserEmailExistsUseCase(signUpRepository: SignUpRepository): CheckUserEmailExistsUseCase {
        return CheckUserEmailExistsUseCase(signUpRepository)
    }

    @Provides
    @Singleton
    fun provideCheckUserPhoneExistsUseCase(signUpRepository: SignUpRepository): CheckUserPhoneExistsUseCase {
        return CheckUserPhoneExistsUseCase(signUpRepository)
    }

}