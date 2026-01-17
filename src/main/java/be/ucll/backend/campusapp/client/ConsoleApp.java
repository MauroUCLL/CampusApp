package be.ucll.backend.campusapp.client;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

public class ConsoleApp {

    private static final String BASE_URL = "http://localhost:8080";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===== UCLL API Console Tester =====");

        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. User Endpoints");
            System.out.println("2. Campus Endpoints");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> userMenu();
                case 2 -> campusMenu();
                case 0 -> {
                    System.out.println("bye");
                    return;
                }
                default -> System.out.println("Invalid");
            }
        }
    }

    private static void userMenu() {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Get all users");
            System.out.println("2. Get user by ID");
            System.out.println("3. Create user");
            System.out.println("0. Back");
            System.out.print("Choice: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> getAllUsers();
                case 2 -> getUserById();
                case 3 -> createUser();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid");
            }
        }
    }

    private static void campusMenu() {
        while (true) {
            System.out.println("\n--- Campus Menu ---");
            System.out.println("1. Get all campuses");
            System.out.println("2. Get campus by ID");
            System.out.println("3. Create campus");
            System.out.println("0. Back");
            System.out.print("Choice: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> getAllCampuses();
                case 2 -> getCampusById();
                case 3 -> createCampus();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid");
            }
        }
    }

    // -------------------- Users --------------------

    private static void getAllUsers() {
        String response = restTemplate.getForObject(BASE_URL + "/user", String.class);
        System.out.println("\nResponse:\n" + response);
    }

    private static void getUserById() {
        System.out.print("User ID: ");
        long id = Long.parseLong(scanner.nextLine());
        String response = restTemplate.getForObject(BASE_URL + "/user/{id}", String.class, id);
        System.out.println("\nResponse:\n" + response);
    }

    private static void createUser() {
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Birthdate (yyyy-MM-dd): ");
        String birthDate = scanner.nextLine();

        User user = new User(firstName, lastName, email, birthDate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        String response = restTemplate.postForObject(BASE_URL + "/user", entity, String.class);
        System.out.println("\nResponse:\n" + response);
    }

    // -------------------- Campuses --------------------

    private static void getAllCampuses() {
        String response = restTemplate.getForObject(BASE_URL + "/campus", String.class);
        System.out.println("\nResponse:\n" + response);
    }

    private static void getCampusById() {
        System.out.print("Campus ID (name): ");
        String id = scanner.nextLine();
        String response = restTemplate.getForObject(BASE_URL + "/campus/{id}", String.class, id);
        System.out.println("\nResponse:\n" + response);
    }

    private static void createCampus() {
        System.out.print("Campus name: ");
        String name = scanner.nextLine();
        System.out.print("Adres: ");
        String adres = scanner.nextLine();
        System.out.print("Parking spots: ");
        int parkeerplaatsen = Integer.parseInt(scanner.nextLine());

        Campus campus = new Campus(name, adres, parkeerplaatsen);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Campus> entity = new HttpEntity<>(campus, headers);

        String response = restTemplate.postForObject(BASE_URL + "/campus", entity, String.class);
        System.out.println("\nResponse:\n" + response);
    }

    // -------------------- Records --------------------

    private record User(String voorNaam, String naam, String mail, String geboorteDatum) { }

    private record Campus(String name, String adres, int parkeerplaatsen) { }
}
