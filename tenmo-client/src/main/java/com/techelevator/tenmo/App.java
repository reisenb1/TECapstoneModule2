package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TenmoService;

import java.math.BigDecimal;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final TenmoService tenmoService = new TenmoService();

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            String token = currentUser.getToken();
            tenmoService.setAuthToken(token);
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
        String message = String.format("Your current account balance is: $%5.2f", tenmoService.getBalance());
        System.out.println("-----------------------------");
        System.out.println(message);
	}

	private void viewTransferHistory() {
        List<Transfer> transfers = tenmoService.getMyPastTransfers();
		consoleService.printAllTransfers(transfers);

        int transferId = consoleService.promptForInt("Please enter transfer ID to view details (0 to cancel):");
        if (transferId == 0) {
            return;
        }

        Transfer transfer = tenmoService.getTransferByTransferId(transferId);
        User user = tenmoService.getUserByAccountId(transfer.getAccountIdTo());
        consoleService.printTransfer(transfer, user);

	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub

		
	}

	private void sendBucks(){
        User[] users = tenmoService.getAllUsers();
        consoleService.printAllUsers(users);

        int userId = consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel):");
        if (userId == currentUser.getUser().getId()) {
            System.out.println("Cannot transfer money to yourself.");
            return;
        } else if (userId == 0) {
            return;
        }
        BigDecimal amount = consoleService.promptForBigDecimal("Enter amount:");

        if (amount.compareTo(new BigDecimal(0.00)) <= 0) {
            System.out.println("Must transfer an amount over $0.00.");
            return;
        } else if (amount.compareTo(tenmoService.getBalance()) > 0) {
            System.out.println("Cannot transfer more money than is in your account.");
            return;
        }

        Account toAccount = tenmoService.getAccount(userId);
        toAccount.setBalance(toAccount.getBalance().add(amount));
        tenmoService.updateAccount(toAccount);

        Account fromAccount = tenmoService.getMyAccount();
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        tenmoService.updateAccount(fromAccount);

        Transfer newTransfer = new Transfer();
        newTransfer.setTransferTypeId(2);
        newTransfer.setTransferStatusId(2);
        newTransfer.setAccountIdFrom(fromAccount.getAccountId());
        newTransfer.setAccountIdTo(toAccount.getAccountId());
        newTransfer.setAmount(amount);

        tenmoService.createTransfer(newTransfer);

	}

	private void requestBucks() {

		// TODO Auto-generated method stub
		
	}

}
