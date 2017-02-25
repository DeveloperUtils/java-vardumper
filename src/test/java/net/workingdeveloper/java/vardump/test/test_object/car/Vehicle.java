package net.workingdeveloper.java.vardump.test.test_object.car;

import java.util.*;

/**
 * Created by Christoph Graupner on 2017-02-25.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
abstract public class Vehicle {
    List<CarPart>    fAdditionalCarParts;
    List<CarPart>    fAllParts;
    Map<Tyre, Brake> fBrakeForTyre;
    Brake[]          fBrakes;
    Chassis          fChassis;
    Engine[]         fEngines;
    int              fMaxVelocity;
    List<Seat>       fSeats;
    StearingWheel    fStearingWheel;
    Tyre[]           fTyres;
    List<Window>     fWindows;

    public Vehicle(Chassis aChassis, Engine[] aEngines, Brake[] aBrakes, Tyre[] aTyres, List<Seat> aSeats, StearingWheel aStearingWheel, int aMaxVelocity) {
        fChassis = aChassis;
        fEngines = aEngines;
        fMaxVelocity = aMaxVelocity;
        fSeats = aSeats;
        fStearingWheel = aStearingWheel;
        fTyres = aTyres;
        fBrakes = aBrakes;

        fAllParts = new ArrayList<>();
        fAllParts.add(fChassis);
        fAllParts.add(fStearingWheel);
        fAllParts.addAll(Arrays.asList(fEngines));
        fAllParts.addAll(Arrays.asList(fTyres));
        fAllParts.addAll(Arrays.asList(fBrakes));
        fAllParts.addAll(fSeats);
        for (CarPart lAllPart : fAllParts) {
            lAllPart.setOwningVehicle(this);
        }
    }

    public List<CarPart> getAdditionalCarParts() {
        return fAdditionalCarParts;
    }

    public void setAdditionalCarParts(List<CarPart> aAdditionalCarParts) {
        fAdditionalCarParts = aAdditionalCarParts;
    }

    public List<CarPart> getAllParts() {
        return fAllParts;
    }

    public void setAllParts(List<CarPart> aAllParts) {
        fAllParts = aAllParts;
    }

    public Map<Tyre, Brake> getBrakeForTyre() {
        return fBrakeForTyre;
    }

    public void setBrakeForTyre(Map<Tyre, Brake> aBrakeForTyre) {
        fBrakeForTyre = aBrakeForTyre;
    }

    public Brake[] getBrakes() {
        return fBrakes;
    }

    public void setBrakes(Brake[] aBrakes) {
        fBrakes = aBrakes;
    }

    public Chassis getChassis() {
        return fChassis;
    }

    public void setChassis(Chassis aChassis) {
        fChassis = aChassis;
    }

    public Engine[] getEngines() {
        return fEngines;
    }

    public void setEngines(Engine[] aEngines) {
        fEngines = aEngines;
    }

    public int getMaxVelocity() {
        return fMaxVelocity;
    }

    public void setMaxVelocity(int aMaxVelocity) {
        fMaxVelocity = aMaxVelocity;
    }

    public List<Seat> getSeats() {
        return fSeats;
    }

    public void setSeats(List<Seat> aSeats) {
        fSeats = aSeats;
    }

    public StearingWheel getStearingWheel() {
        return fStearingWheel;
    }

    public void setStearingWheel(StearingWheel aStearingWheel) {
        fStearingWheel = aStearingWheel;
    }

    public Tyre[] getTyres() {
        return fTyres;
    }

    public void setTyres(Tyre[] aTyres) {
        fTyres = aTyres;
    }

    public List<Window> getWindows() {
        return fWindows;
    }

    public void setWindows(List<Window> aWindows) {
        fWindows = aWindows;
    }
}
