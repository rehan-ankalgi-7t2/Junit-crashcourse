package com.programming.techie;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTest {
    ContactManager contactManager;

    @BeforeAll
    public void setupAll() {
        System.out.println("Should print before ALL TESTS");
    }

    @BeforeEach
    @DisplayName("Initiate ContactManager Object before running tests")
    public void setup() {
        contactManager = new ContactManager();
        System.out.println("Created Contact Manager Object");
    }

    @Test
    public void shouldCreateContact() {
        contactManager.addContact("John", "Doe", "0974112067");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    @Test
    @DisplayName("Should not create contact when first name is null")
    public void shouldThrowRunTimeExceptionWhenFirstNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("", "Doe", "0234567890");
        });
    }

    @Test
    @DisplayName("Should not create contact when last name is null")
    public void shouldThrowRunTimeExceptionWhenLastNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", "", "0234567890");
        });
    }

    @Test
    @DisplayName("Should not create contact when phone number is null")
    public void shouldThrowRunTimeExceptionWhenPhoneNumberIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", "Doe", "");
        });
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Tear down each tests");
    }

    @AfterAll
    public void tearDownAll() {
        System.out.println("Tear down all tests");
    }

    @Test
    @DisplayName("Create contacts only on MAC OS")
    @EnabledOnOs(value = OS.MAC, disabledReason = "Should create only on MAC OS")
    public void shouldCreateContactOnlyOnMAC() {
        contactManager.addContact("John", "Doe", "0974112067");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    @Test
    @DisplayName("Create contacts only on Windows OS")
    @EnabledOnOs(value = OS.WINDOWS, disabledReason = "Should create only on Windows OS")
    public void shouldCreateContactOnlyOnWindows() {
        contactManager.addContact("John", "Doe", "0974112067");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    @Test
    @DisplayName("Create contacts on dev machine")
    public void shouldCreateContactOnDevMachine() {
        Assumptions.assumeFalse("DEV".equals(System.getProperty("ENV")));
        contactManager.addContact("John", "Doe", "0974112067");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    @Nested
    class RepeatedNestedTests {
        @RepeatedTest(value = 5, name = "Repeated test {currentRepetition} of {totalRepetitions}")
        @DisplayName("Repeated Tests")
        public void shouldCreateContactRepeatedly() {
            contactManager.addContact("John", "Doe", "0974112067");
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }
    }
}

@Nested
class ParameterizedNestedTests {
    ContactManager contactManager = new ContactManager();
    @ParameterizedTest
    @DisplayName("Parameterize test with @valueSource")
    @ValueSource(strings = {"0974112067", "0741120672", "0234567890"})
    public void shouldTestPhoneNumberUsingValueSource(String phoneNumber) {
        contactManager.addContact("John", "Doe", phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    @ParameterizedTest
    @DisplayName("Parameterize test with @MethodSource")
    @MethodSource("phoneNumberList")
    public void shouldTestPhoneNumberUsingMethodSource(String phoneNumber) {
        contactManager.addContact("John", "Doe", phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    public static List<String> phoneNumberList () {
        return Arrays.asList("0974112067", "0741120672", "0234567801");
    }


    @ParameterizedTest
    @DisplayName("Parameterize test with @CsvSource")
    @CsvSource({"0974112067", "0741120672", "0234567801"})
    public void shouldTestPhoneNumberUsingCsvSource(String phoneNumber) {
        contactManager.addContact("John", "Doe", phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    @ParameterizedTest
    @DisplayName("Parameterize test with @CsvSourceFile")
    @CsvFileSource(resources = "/data.csv")
    public void shouldTestPhoneNumberUsingCsvSourceFile(String phoneNumber) {
        contactManager.addContact("John", "Doe", phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }
}

