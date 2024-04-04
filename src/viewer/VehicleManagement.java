package viewer;

import controller.VehicleController;
import dbo.VehicleFileManagerment;
import dto.Vehicle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class VehicleManagement {

    public static Scanner sc = new Scanner(System.in);
    VehicleController vehicleController = new VehicleController();
    VehicleFileManagerment fileManager = new VehicleFileManagerment();

    public static void main(String[] args) throws Exception {
        VehicleManagement manager = new VehicleManagement();
        System.out.println("Welcome to Vehicle Management - @ 2023 by SE171129 - Pham Tan Loc");
        int choice;
        do {
            System.out.println("----------------MENU-----------------");
            System.out.println("|1 - Add new vehicle                |");
            System.out.println("|2 - Check exits vehicle            |");
            System.out.println("|3 - Update vehicle                 |");
            System.out.println("|4 - Delete vehicle                 |");
            System.out.println("|5 - Search vehicle                 |");
            System.out.println("|6 - Display all vehicle            |");
            System.out.println("|7 - Save all vehicle to file       |");
            System.out.println("|8 - Print all vehicle from the file|");
            System.out.println("|9 - Quit application               |");
            System.out.println("-------------------------------------");
            System.out.print("Input your choice: ");
            choice = Integer.parseInt(sc.nextLine());
            while (choice < 1 || choice > 9) {
                System.out.println("Invalid choice. Please try again.");
                System.out.print("Input your choice: ");
                choice = Integer.parseInt(sc.nextLine());
            }
            switch (choice) {
                case 1:
                    manager.addVehicle();
                    break;
                case 2:
                    manager.checkExist();
                    break;
                case 3:
                    manager.updateVehicle();
                    break;
                case 4:
                    manager.deleteVehicle();
                    break;
                case 5:
                    manager.searchVehicle();
                    break;
                case 6:
                    manager.displayVehicle();
                    break;
                case 7:
                    manager.saveAllVehiclesToFile();
                    break;
                case 8:
                    manager.printVehicleList();
                    break;
                case 9:
                    System.out.println("Goodbye ^.^");
                    break;
            }
        } while (choice != 9);
    }

    private void addVehicle() {
        System.out.println("Create a vehicle.");
        String idVehicle = utils.Utils.getString("Input your vehicle id: ");
        if (!checkValidId(idVehicle)) {
            do {
                System.out.println("Vehicle id cannot duplicate or empty. Please try again.");
                idVehicle = utils.Utils.getString("Input your vehicle id: ");
            } while (!checkValidId(idVehicle));
        }
        String nameVehicle = utils.Utils.getString("Input your vehicle name: ");
        String colorVehicle = utils.Utils.getString("Input your vehicle color: ");
        int priceVehicle = utils.Utils.getInt("Input your vehicle price: ", 0, 999999999);
        String brandVehicle = utils.Utils.getString("Input your vehicle brand: ");
        String typeVehicle = utils.Utils.getString("Input your vehicle type: ");
        int productYearVehicle = utils.Utils.getInt("Input your vehicle product year: ", 1950, 2024);
        Vehicle newVehicle = new Vehicle(idVehicle, nameVehicle, colorVehicle, priceVehicle, brandVehicle, typeVehicle, productYearVehicle);
        if (vehicleController.addVehicle(newVehicle)) {
            System.out.println("Add new vehicle success.");
        } else {
            System.out.println("Add new vehicle fail.");
        }
    }

    private void checkExist() {
        String idToCheck = utils.Utils.getString("Input id of vehicle to check exist or not: ");
        if (vehicleController.searchVehicleById(idToCheck) != null) {
            System.out.println("Vehicle is exist.");
        } else {
            System.out.println("Vehicle is does not exist.");
        }
    }

    private void updateVehicle() {
        String idToUpdate = utils.Utils.getString("Input id of vehicle to update: ");
        Vehicle updateVehicle = vehicleController.searchVehicleById(idToUpdate);
        if (updateVehicle != null) {
            String idUpdate;
            idUpdate = utils.Utils.getString("Input your vehicle id: ");
            if (!idToUpdate.equalsIgnoreCase(idUpdate)) {
                if (!checkValidId(idUpdate)) {
                    do {
                        System.out.println("Vehicle id cannot duplicate or empty. Please try again.");
                        idUpdate = utils.Utils.getString("Input your vehicle id: ");
                    } while (!checkValidId(idUpdate));
                }
            }
            updateVehicle.setId(idUpdate);

            updateVehicle.setName(utils.Utils.getString("Input your vehicle name: "));

            updateVehicle.setColor(utils.Utils.getString("Input your vehicle color: "));

            updateVehicle.setPrice(utils.Utils.getInt("Input your vehicle price: ", 0, 999999999));

            updateVehicle.setBrand(utils.Utils.getString("Input your vehicle brand: "));

            updateVehicle.setType(utils.Utils.getString("Input your vehicle type: "));

            updateVehicle.setProductYear(utils.Utils.getInt("Input your vehicle product year: ", 1950, 2025));

            System.out.println("Update vehicle success.");
        } else {
            System.out.println("Vehicle is does not exist.");
        }
    }

    private void deleteVehicle() {
        String idVehicleDelete = utils.Utils.getString("Input vehicle id to delete: ");
        Vehicle vehicleToDelete = vehicleController.searchVehicleById(idVehicleDelete);
        if (utils.Utils.confirmYesNo("Are you sure want to delete vehicle have code: " + idVehicleDelete + " (y/n):")) {
            if (vehicleToDelete != null) {
                if (vehicleController.deleteVehicle(vehicleToDelete.getId())) {
                    System.out.println("Delete vehicle success.");
                } else {
                    System.out.println("Delete vehicle fail.");
                }
            } else {
                System.out.println("Does not found vehicle with vehicle id " + idVehicleDelete);
            }
        } else {
            System.out.println("Delete request has been cancelled.");
        }

    }

    private void searchVehicle() {
        int subChoice;
        do {
            System.out.println("----------------MENU-----------------");
            System.out.println("|1 - Search vehicle by id            |");
            System.out.println("|2 - Search vehicle by name          |");
            System.out.println("-------------------------------------");
            System.out.print("Input your choice: ");
            subChoice = Integer.parseInt(sc.nextLine());

            while (subChoice < 1 || subChoice > 2) {
                System.out.println("Invalid choice. Please try again.");
                System.out.print("Input your choice: ");
                subChoice = Integer.parseInt(sc.nextLine());
            }
            switch (subChoice) {
                case 1:
                    searchVehicleById();
                    break;
                case 2:
                    searchVehicleByName();
                    break;
            }
        } while (subChoice != 1 && subChoice != 2);
    }

    private void searchVehicleById() {
        String idVehicleSearch = utils.Utils.getString("Input vehicle id to search: ");
        Vehicle searchResult = vehicleController.searchVehicleById(idVehicleSearch);
        if (searchResult != null) {
            System.out.println(searchResult.toString());
            System.out.println("Search vehicle success.");
        } else {
            System.out.println("Vehicle does not exits.");
        }
    }

    private void searchVehicleByName() {
        String nameVehicleSearch = utils.Utils.getString("Input vehicle name to search: ");
        List<Vehicle> ouputSearchByName = vehicleController.searchVehicleByName(nameVehicleSearch);
        if (!ouputSearchByName.isEmpty()) {
            for (Vehicle vehicle : ouputSearchByName) {
                System.out.println(vehicle.toString());
            }
            System.out.println("Search vehicle success.");
        } else {
            System.out.println("Does not have vehicle contain " + nameVehicleSearch);
        }
    }

    private void displayVehicle() {
        int subChoice;
        do {
            System.out.println("----------------MENU-----------------");
            System.out.println("|1 - Show all vehicle                |");
            System.out.println("|2 - Show by price                   |");
            System.out.println("-------------------------------------");
            System.out.print("Input your choice: ");
            subChoice = Integer.parseInt(sc.nextLine());

            while (subChoice < 1 || subChoice > 2) {
                System.out.println("Invalid choice. Please try again.");
                System.out.print("Input your choice: ");
                subChoice = Integer.parseInt(sc.nextLine());
            }
            switch (subChoice) {
                case 1:
                    showAllVehicle();
                    break;
                case 2:
                    showVehicleByPrice();
                    break;
            }
        } while (subChoice != 1 && subChoice != 2);
    }

    private void showAllVehicle() {
        List<Vehicle> listShow = vehicleController.getVehicles();
        if (listShow != null) {
            for (Vehicle vehicle : listShow) {
                System.out.println(vehicle.toString());
            }
            System.out.println("Show all vehicle success.");
        } else {
            System.out.println("List vehicle is empty.");
        }
    }

    private void showVehicleByPrice() {
        int standardPrice = utils.Utils.getInt("Input max price: ", 0, 9999999);
        List<Vehicle> listShow = vehicleController.getVehicles();
        if (listShow != null) {
            Comparator<Vehicle> com = new Comparator<Vehicle>() {
                @Override
                public int compare(Vehicle v1, Vehicle v2) {
                    return (int) v2.getPrice() - v1.getPrice();
                }
            };
            Collections.sort(listShow, com);
            for (Vehicle vehicle : listShow) {
                if (vehicle.getPrice() < standardPrice) {
                    System.out.println(vehicle.toString());
                }
            }
            System.out.println("Show vehicle by price success.");
        } else {
            System.out.println("No vehicle have price lower than " + standardPrice);
        }
    }

    public void saveAllVehiclesToFile() throws Exception {
        if (fileManager.writeFile((ArrayList) vehicleController.getVehicles())) {
            System.out.println("Save all vehicles to file success.");
        } else {
            System.out.println("Save all vehicles to file failed.");
        }
    }

    @SuppressWarnings("empty-statement")
    public void printVehicleList() throws Exception {
        int subChoice;
        do {
            System.out.println("----------------MENU-----------------");
            System.out.println("|1 - Print all                       |");
            System.out.println("|2 - Print by year                   |");
            System.out.println("-------------------------------------");
            System.out.print("Input your choice: ");
            subChoice = Integer.parseInt(sc.nextLine());

            while (subChoice < 1 || subChoice > 2) {
                System.out.println("Invalid choice. Please try again.");
                System.out.print("Input your choice: ");
                subChoice = Integer.parseInt(sc.nextLine());
            }
            switch (subChoice) {
                case 1:
                    printAllVehicleFromFile();
                    break;
                case 2:
                    showVehicleByYear();
                    break;
            }
        } while (subChoice != 1 && subChoice != 2);
    }

    private void printAllVehicleFromFile() throws Exception {
        List<Vehicle> listShow = fileManager.readFile();
        if (listShow != null) {
            for (Vehicle vehicle : listShow) {
                System.out.println(vehicle.toString());
            }
            System.out.println("Print all vehicle from file is success.");
        } else {
            System.out.println("List vehicle in file is empty.");
        }
    }

    private void showVehicleByYear() throws Exception {
        int standardYear = utils.Utils.getInt("Input min year: ", 1950, 2030);
        List<Vehicle> listShow = fileManager.readFile();
        List<Vehicle> listToShow = new ArrayList<>();
        for (Vehicle vehicle : listShow) {
            if (vehicle.getProductYear() >= standardYear) {
                listToShow.add(vehicle);
            }
        }
        if (!listToShow.isEmpty()) {
            Comparator<Vehicle> com = new Comparator<Vehicle>() {
                @Override
                public int compare(Vehicle v1, Vehicle v2) {
                    return (int) v1.getProductYear() - v2.getProductYear();
                }
            };
            Collections.sort(listToShow, com);
            for (Vehicle vehicle : listToShow) {
                if (vehicle.getProductYear() >= standardYear) {
                    System.out.println(vehicle.toString());
                }
            }
            System.out.println("Show vehicles after " + standardYear + " success");
        } else {
            System.out.println("No vehicle in file have product year after " + standardYear);
        }
    }

    private boolean checkValidId(String idVehicleCheck) {
        for (Vehicle vehicle : vehicleController.getVehicles()) {
            if (vehicle.getId().equalsIgnoreCase(idVehicleCheck)) {
                return false;
            }
        }
        return true;
    }

}
