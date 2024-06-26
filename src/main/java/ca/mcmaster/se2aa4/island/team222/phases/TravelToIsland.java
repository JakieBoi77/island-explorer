package ca.mcmaster.se2aa4.island.team222.phases;

import java.util.Map;

import ca.mcmaster.se2aa4.island.team222.Drone;
import ca.mcmaster.se2aa4.island.team222.Value;
import ca.mcmaster.se2aa4.island.team222.actions.*;
import ca.mcmaster.se2aa4.island.team222.directions.RelativeDirection;
import ca.mcmaster.se2aa4.island.team222.pois.AllPOIS;
import ca.mcmaster.se2aa4.island.team222.responses.Response;

public class TravelToIsland implements Phase {
    
    private boolean reachedEnd = false;
    private MoveToIsland currentState  = MoveToIsland.TURN_TO_ISLAND;
    private Drone drone;
    private AllPOIS allPOIS;
    private int groundRange;

    public enum MoveToIsland {
        TURN_TO_ISLAND,
        ECHOING,
        FLYING
    }

    public TravelToIsland(Drone drone, AllPOIS allPOIS) {
        this.drone = drone;
        this.allPOIS = allPOIS;
    }

    @Override
    public Action getNextDecision() {
        Action nextAction;
        switch(this.currentState) {
            case TURN_TO_ISLAND:
                nextAction = drone.heading(RelativeDirection.RIGHT);
                break;
            case ECHOING:
                nextAction = drone.echo(RelativeDirection.FORWARD);
                break;
            case FLYING:
                nextAction = drone.fly();
                break;
            default:
                throw new IllegalStateException(String.format("Undefined state: %s", this.currentState));
        }
        return nextAction;
    }

    @Override
    public void react(Response response) {
        this.drone.useBattery(response.getCost());
        Map<String, Value> data = response.getData();
        switch(this.currentState) {
            case TURN_TO_ISLAND:
                this.currentState = MoveToIsland.ECHOING;
                break;
            case ECHOING:
                String found = data.get("found").getStringValue();
                int range = data.get("range").getIntValue();
                if(found.equals("GROUND") && range == 0) {
                    this.reachedEnd = true;
                } else {
                    this.currentState = MoveToIsland.FLYING;
                }
                this.groundRange= range;   
                break;
            case FLYING:
                this.groundRange -= 1;
                if(this.groundRange < 2) {
                    this.reachedEnd = true;
                }
                this.currentState = MoveToIsland.FLYING;        
                break;
            default:
                throw new IllegalStateException(String.format("Undefined state: %s", this.currentState));
        }
    }

    @Override
    public Phase getNextPhase() {
        return new ScanLine(this.drone, this.allPOIS);
    }

    @Override
    public boolean reachedEnd() {
        return this.reachedEnd;
    }

    @Override
    public boolean isFinal() {
        return false;
    }

    @Override
    public AllPOIS getAllPOIS(){
        return this.allPOIS;
    }
}