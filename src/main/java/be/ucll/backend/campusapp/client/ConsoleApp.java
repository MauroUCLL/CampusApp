package be.ucll.backend.campusapp.client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.Scanner;

public class ConsoleApp {

    private static final String BASE_URL = "http://localhost:8080";
    private static final WebClient client = WebClient.builder()
            .baseUrl(BASE_URL)
            .build();

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
                case 0 -> { System.out.println("Goodbye!"); return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void userMenu() {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Get all users");
            System.out.println("2. Get user by ID");
            System.out.println("3. Create user");
            System.out.println("4. Add reservatie to user");
            System.out.println("5. Get reservaties for user");
            System.out.println("6. Add room to reservatie");
            System.out.println("0. Back");
            System.out.print("Choice: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> getAllUsers();
                case 2 -> getUserById();
                case 3 -> createUser();
                case 4 -> addReservatie();
                case 5 -> getReservaties();
                case 6 -> addRoomToReservation();
                case 0 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void campusMenu() {
        while (true) {
            System.out.println("\n--- Campus Menu ---");
            System.out.println("1. Get all campuses");
            System.out.println("2. Get campus by ID");
            System.out.println("3. Create campus");
            System.out.println("4. Get rooms for campus");
            System.out.println("5. Get room by campus + roomId");
            System.out.println("0. Back");
            System.out.print("Choice: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> getAllCampuses();
                case 2 -> getCampusById();
                case 3 -> createCampus();
                case 4 -> getCampusRooms();
                case 5 -> getCampusRoomById();
                case 0 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void getAllUsers() {
        System.out.print("Filter name (optional): ");
        String filter = scanner.nextLine();
        String uri = "/users" + (filter.isBlank() ? "" : "?nameMatches=" + filter);

        String response = client.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("\nResponse:\n" + response);
    }

    private static void getUserById() {
        System.out.print("User ID: ");
        long id = Long.parseLong(scanner.nextLine());

        String response = client.get()
                .uri("/users/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
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

        try {
            String response = client.post()
                    .uri("/users")
                    .contentType(MediaType.valueOf("application/json"))
                    .bodyValue(user)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("\nResponse:\n" + response);
        } catch (WebClientResponseException e) {
            System.out.println("\nError " + ": " + e.getResponseBodyAsString());
        }
    }

    private static void addReservatie() {
        System.out.print("User ID: ");
        long userId = Long.parseLong(scanner.nextLine());
        System.out.print("Reservatie ID: ");
        long reservatieId = Long.parseLong(scanner.nextLine());

        String response = client.put()
                .uri("/users/{userId}/reservaties/{reservatieId}", userId, reservatieId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("\nResponse:\n" + response);
    }

    private static void getReservaties() {
        System.out.print("User ID: ");
        long userId = Long.parseLong(scanner.nextLine());

        String response = client.get()
                .uri("/users/{userId}/reservaties", userId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("\nResponse:\n" + response);
    }

    private static void addRoomToReservation() {
        System.out.print("User ID: ");
        long userId = Long.parseLong(scanner.nextLine());
        System.out.print("Reservatie ID: ");
        long reservatieId = Long.parseLong(scanner.nextLine());
        System.out.print("Room ID: ");
        long roomId = Long.parseLong(scanner.nextLine());

        String response = client.put()
                .uri("/users/{userId}/reservaties/{reservatieId}/rooms/{roomId}", userId, reservatieId, roomId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("\nResponse:\n" + response);
    }

    private static void getAllCampuses() {
        String response = client.get()
                .uri("/campuses")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("\nResponse:\n" + response);
    }

    private static void getCampusById() {
        System.out.print("Campus ID (name): ");
        String id = scanner.nextLine();

        String response = client.get()
                .uri("/campuses/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
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

        String response = client.post()
                .uri("/campuses")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(campus)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("\nResponse:\n" + response);
    }

    private static void getCampusRooms() {
        System.out.print("Campus ID: ");
        String id = scanner.nextLine();
        System.out.print("Available from (dd/MM/yyyy or blank): ");
        String from = scanner.nextLine();
        System.out.print("Available until (dd/MM/yyyy or blank): ");
        String until = scanner.nextLine();
        System.out.print("Min seats (blank for none): ");
        String minSeats = scanner.nextLine();

        StringBuilder uri = new StringBuilder("/campuses/" + id + "/rooms");
        boolean first = true;
        if (!from.isBlank()) { uri.append("?availableFrom=").append(from); first = false; }
        if (!until.isBlank()) { uri.append(first ? "?" : "&").append("availableUntil=").append(until); first = false; }
        if (!minSeats.isBlank()) { uri.append(first ? "?" : "&").append("minNumberOfSeats=").append(minSeats); }

        String response = client.get()
                .uri(uri.toString())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("\nResponse:\n" + response);
    }

    private static void getCampusRoomById() {
        System.out.print("Campus ID: ");
        String campus = scanner.nextLine();
        System.out.print("Room ID: ");
        long roomId = Long.parseLong(scanner.nextLine());

        String response = client.get()
                .uri("/campuses/{campus}/rooms/{roomId}", campus, roomId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("\nResponse:\n" + response);
    }

    private record User(String voorNaam, String naam, String mail, String geboorteDatum) {}
    private record Campus(String name, String adres, int parkeerplaatsen) {}
}
