package com.programming.techie;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

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
}

