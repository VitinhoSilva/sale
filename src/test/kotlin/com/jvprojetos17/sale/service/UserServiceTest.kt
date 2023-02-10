package com.jvprojetos17.sale.service

import com.jvprojetos17.sale.enums.Profile
import com.jvprojetos17.sale.enums.Status
import com.jvprojetos17.sale.exception.NotFoundException
import com.jvprojetos17.sale.extension.toEntity
import com.jvprojetos17.sale.model.User
import com.jvprojetos17.sale.repository.UserRepository
import com.jvprojetos17.sale.request.UserRequest
import com.querydsl.core.BooleanBuilder
import io.mockk.InternalPlatformDsl.toArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.* // ktlint-disable no-wildcard-imports
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var bCrypt: BCryptPasswordEncoder

    @InjectMocks
    private lateinit var userService: UserService

    private fun buildUser(
        uuid: String? = null,
        name: String = "Vitor",
        cpf: String = "95290118063",
        email: String = "12345678@gmail.com",
        password: String = "654321",
        profile: Set<Profile> = setOf(Profile.CUSTOMER),
        active: Status = Status.TRUE,
    ) = User(
        uuid = uuid,
        name = name,
        cpf = cpf,
        email = email,
        password = password,
        profiles = profile,
        active = active,
    )

    private fun buildUserRequest(
        name: String = "Vitor",
        cpf: String = "95290118063",
        email: String = "12345678@gmail.com",
        password: String = "654321",
    ) = UserRequest(
        name = name,
        cpf = cpf,
        email = email,
        password = password,
    )

    @Test
    fun `should return user find by id`() {
        val userFake = buildUser()
        val uuidRandom = UUID.randomUUID().toString()

        `when`(userRepository.findByUuid(uuidRandom)).thenReturn(userFake)
        val user = userService.findByUuid(uuidRandom)

        assertEquals(userFake, user)
        verify(userRepository, times(1)).findByUuid(uuidRandom)
    }

    @Test
    fun `should return not found exception when user id you are invalid`() {
        val uuidNotValid = "user404"

        `when`(userRepository.findByUuid(uuidNotValid)).thenReturn(null)
        val error = assertThrows<NotFoundException> {
            userService.findByUuid(uuidNotValid)
        }

        assertEquals("User with id: [$uuidNotValid] not found!", error.message)
        assertEquals("S-101", error.errorCode)
        verify(userRepository, times(1)).findByUuid(uuidNotValid)
    }

    @Test
    fun `should saved user`() {
        val fakeCustomer = buildUser()
        val passwordEncrypted = UUID.randomUUID().toString()

        `when`(bCrypt.encode(fakeCustomer.password)).thenReturn(passwordEncrypted)

        userService.save(fakeCustomer)

        // Se fez necessário verificar como any() pois o uuid sempre ao salvar é gerado um novo uuid randômico, com isso não tem como validar um retorno já que passamos um:
        // user com uuid de null para salvar e a chamada para o save() será um user com uuid randômico
        verify(userRepository, times(1)).save(any())
        verify(bCrypt, times(1)).encode(fakeCustomer.password)
    }

    @Test
    fun `should return list of users with status active`() {
        val listUser = setOf(buildUser())
        val statusActive = Status.TRUE

        `when`(userRepository.findByActive(statusActive)).thenReturn(listUser)

        userService.getAllByActive(statusActive)

        verify(userRepository, times(1)).findByActive(statusActive)
        assertEquals(listUser.size, 1)
        assertEquals(listUser.toList()[0].active, Status.TRUE)
    }

    @Test
    fun `should return list of users with status inactive`() {
        val listUser = setOf(buildUser().copy(active = Status.FALSE))
        val statusInactive = Status.FALSE

        `when`(userRepository.findByActive(statusInactive)).thenReturn(listUser)

        userService.getAllByActive(statusInactive)

        verify(userRepository, times(1)).findByActive(statusInactive)
        assertEquals(listUser.size, 1)
        assertEquals(listUser.toList()[0].active, Status.FALSE)
    }

    @Test
    fun `should return page of users with filters`() {
        val page = PageRequest.of(0, 10)
        val listUser = mutableListOf(buildUser())
        val pageResponse = PageImpl(listUser)

        `when`(userRepository.findAll(any(BooleanBuilder::class.java), any(Pageable::class.java))).thenReturn(
            pageResponse,
        )

        userService.filter(page, "Vitor", "95290118063", "123456789", Status.FALSE)

        verify(userRepository, times(1)).findAll(any(BooleanBuilder::class.java), any(Pageable::class.java))
        assertEquals(listUser.size, 1)
        assertEquals(listUser.toList()[0].cpf, "95290118063")
    }

    @Test
    fun `should update user`() {
        val userId = "144as1asasa4s7a-as41a45s"
        val userFake = buildUser().copy(uuid = userId, profiles = setOf(Profile.CUSTOMER))
        val userRequestFake = buildUserRequest()
        val userRequestFakeToEntity = userRequestFake.toEntity().copy(uuid = userId)

        `when`(userRepository.save(userRequestFakeToEntity)).thenReturn(userFake)

        userService.update(userId, userRequestFake)

        verify(userRepository, times(1)).save(userFake)
    }

    @Test
    fun `should change status user to inactive`() {
        val userFake = buildUser()
        val userId = "144as1asasa4s7a-as41a45s"

        `when`(userRepository.findByUuid(userId)).thenReturn(userFake)

        userService.inactivate(userId)

        verify(userRepository, times(1)).save(userFake.copy(active = Status.FALSE))
    }

    @Test
    fun `should change status user to active`() {
        val userFake = buildUser().copy(active = Status.FALSE)
        val userId = "144as1asasa4s7a-as41a45s"

        `when`(userRepository.findByUuid(userId)).thenReturn(userFake)

        userService.activate(userId)

        verify(userRepository, times(1)).save(userFake.copy(active = Status.TRUE))
    }
}
