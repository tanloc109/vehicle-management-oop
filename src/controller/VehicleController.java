package controller;

import java.util.ArrayList;
import java.util.List;
import dto.Vehicle;

public class VehicleController {

    private List<Vehicle> vehicles = new ArrayList<>();

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public boolean addVehicle(Vehicle vehicle) {
        try {
            vehicles.add(vehicle);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkExistVehicle(String idToCheck) {
        try {
            for (Vehicle vehicle : vehicles) {
                if (vehicle.getId().equalsIgnoreCase(idToCheck)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public Vehicle searchVehicleById(String id) {
        try {
            for (Vehicle vehicle : vehicles) {
                if (vehicle.getId().equalsIgnoreCase(id)) {
                    return vehicle;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteVehicle(String id) {
        try {
            for (Vehicle vehicle : vehicles) {
                if (vehicle.getId().equalsIgnoreCase(id)) {
                    vehicles.remove(vehicle);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<Vehicle> searchVehicleByName(String name) {
        try {
            List<Vehicle> list = new ArrayList<>();
            for (Vehicle vehicle : vehicles) {
                if (vehicle.getName().toLowerCase().contains(name.toLowerCase())) {
                    list.add(vehicle);
                }
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }
    
    

}
