package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.RESTAccountService;
import com.techelevator.tenmo.services.RESTTransferService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.security.auth.login.AccountException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Scanner;

public class App {


    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private RESTAccountService restAccountService = new RESTAccountService(API_BASE_URL);
    private RESTTransferService restTransferService = new RESTTransferService(API_BASE_URL);

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        RESTAccountService.AUTH_TOKEN = currentUser.getToken();
        RESTTransferService.AUTH_TOKEN = currentUser.getToken();
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {
        // TODO Auto-generated method stub
        System.out.println("Your current balance is: $" + restAccountService.getAccountBalance());
    }

    private void viewTransferHistory() {
        // TODO Auto-generated method stub
        handleListUserTransfers();
       // viewTransferHistoryMethod();
    }

    private void viewPendingRequests() {
        // TODO Auto-generated method stub
        System.out.println("Work in progress, please view our Spring 2023 Roadmap!");
    }

    private void sendBucks() {
        // TODO Auto-generated method stub
        TransferDto transferDto = new TransferDto();
        Scanner scanner = new Scanner(System.in);

        transferDto.setTransferTypeId(2);
        transferDto.setTransferStatusId(2);

        boolean goodInput = false;

        while (!goodInput) {
            consoleService.printUsers(restAccountService.listUsers(currentUser.getToken()));
            int sendToId = 0;
            System.out.println("Enter ID of user you are sending to (0 to cancel): ");
            try {
                sendToId = Integer.parseInt(scanner.nextLine()) + 1000;
            } catch (Exception e) {
                System.out.println("Bad input, numbers only");
            }
            if (sendToId == 1000) {
                break;
            } else {
                transferDto.setTransferToId(sendToId);
                System.out.println("Enter Amount: ");
                try {
                    double amountToSend = Double.parseDouble(scanner.nextLine());
                    BigDecimal amountToSendBD = BigDecimal.valueOf(amountToSend);
                    transferDto.setAmount(amountToSendBD);
                    Transfer newTransfer = restTransferService.sendTransfer(transferDto);
                    goodInput = true;

                    System.out.println();
                        System.out.println("Transfer successful.");
                    System.out.println(newTransfer.toString());
                } catch (Exception e) {
                    System.out.println("An error has occurred, please try again.");
                }
            }
        }
    }

    private void requestBucks() {
        // TODO Auto-generated method stub
        System.out.println("Work in progress, please view our Spring 2023 Roadmap!");
    }

    private void handleListUserTransfers() {
        // List  for user transfer history
        Transfer[] transfers = restTransferService.listUserTransfers(currentUser.getUser().getId() + 1000);
        if (transfers != null) {
            consoleService.printUserTransferHistoryMenu(transfers);
        } else {
            consoleService.printErrorMessage();
        }
    }

}
