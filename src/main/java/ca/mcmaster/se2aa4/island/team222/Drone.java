package ca.mcmaster.se2aa4.island.team222;

import ca.mcmaster.se2aa4.island.team222.actions.*;
import ca.mcmaster.se2aa4.island.team222.directions.*;

public class Drone {

    private int battery;
    private CardinalDirection direction;
    private Orientation orientation;
    private ScanStatus status;
    private Coordinate coordinates;

    public Drone(int battery, CardinalDirection direction) {
        this.battery = battery;
        this.direction = direction;
        this.orientation = Orientation.LEFT;
        this.status = ScanStatus.NONE;
        this.coordinates = new Coordinate(0, 0);
    }

    public int getBattery() {
        return this.battery;
    } 

    public void useBattery(int cost) {
        this.battery -= cost;
    }

    public CardinalDirection getDirection() {
        return this.direction;
    }

    public ScanStatus getStatus() {
        return this.status;
    } 

    public void setStatus() {
        this.status = ScanStatus.HALF;
    }
    
    public Orientation getOrientation() {
        return this.orientation;
    } 

    public void switchOrientation() {
        if(orientation == Orientation.LEFT) {
            orientation = Orientation.RIGHT;
        } else {
            orientation = Orientation.LEFT;
        }
    }
    
    public Coordinate getCoordinates(){
        return this.coordinates;
    }

    public Action fly() {
        
        switch(direction) {
            case N:
                coordinates.updateY(1);
                break;
            case S:
                coordinates.updateY(-1);
                break;
            case E:
                coordinates.updateX(1);
                break;
            case W:
                coordinates.updateX(-1);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
        return new Action(ActionType.FLY);

    }

    public Action echo(RelativeDirection direction) {
        CardinalDirection echoDirection;
        switch(direction) {
            case LEFT:
                echoDirection = this.direction.nextLeft();
                break;
            case RIGHT:
                echoDirection = this.direction.nextRight();
                break;
            case FORWARD:
                echoDirection = this.direction;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }

        return new Action(ActionType.ECHO, echoDirection);
    }

    public Action heading(RelativeDirection directionRelative) {
        CardinalDirection headingDirection = this.direction;
        switch(directionRelative) {
            case LEFT:
        
                if(headingDirection.equals(CardinalDirection.N)){
                    coordinates.updateXandY(-1, 1);
                }
                else if(headingDirection.equals(CardinalDirection.S)){
                    coordinates.updateXandY(1, -1);
                }
                else if(headingDirection.equals(CardinalDirection.E)){
                    coordinates.updateXandY(1, 1);
                }
                else if(headingDirection.equals(CardinalDirection.W)){
                    coordinates.updateXandY(-1, -1);
                }
                headingDirection = this.direction.nextLeft();
                break;

            case RIGHT:

                if(headingDirection.equals(CardinalDirection.N)){
                    coordinates.updateXandY(1, 1);
                }
                else if(headingDirection.equals(CardinalDirection.S)){
                    coordinates.updateXandY(-1, -1);
                }
                else if(headingDirection.equals(CardinalDirection.E)){
                    coordinates.updateXandY(1, -1);
                }
                else if(headingDirection.equals(CardinalDirection.W)){
                    coordinates.updateXandY(-1, 1);
                }
                headingDirection = this.direction.nextRight();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + directionRelative);
        }
        this.direction = headingDirection;
        return new Action(ActionType.HEADING, headingDirection);
    }

    public Action scan() {
        return new Action(ActionType.SCAN);
    }

    public Action stop() {
        return new Action(ActionType.STOP);
    }
}
