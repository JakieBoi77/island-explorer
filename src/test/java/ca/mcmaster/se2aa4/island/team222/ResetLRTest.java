package ca.mcmaster.se2aa4.island.team222;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team222.actions.Action;
import ca.mcmaster.se2aa4.island.team222.actions.ActionType;
import ca.mcmaster.se2aa4.island.team222.directions.CardinalDirection;
import ca.mcmaster.se2aa4.island.team222.directions.Orientation;
import ca.mcmaster.se2aa4.island.team222.phases.Phase;
import ca.mcmaster.se2aa4.island.team222.phases.ResetLR;
import ca.mcmaster.se2aa4.island.team222.responses.NormalResponse;
import ca.mcmaster.se2aa4.island.team222.responses.Response;

public class ResetLRTest {
    private Drone drone;
    private Response defaultResponse;
    
    public enum State {
        TURN,
        FORWARD,
        SECOND_TURN,
        THIRD_TURN,
        FOURTH_TURN,
        SECOND_FORWARD
    }

    @BeforeEach
    public void setUp() {
        drone = new Drone(15000, CardinalDirection.E);
        JSONObject obj = new JSONObject();
        obj.put("cost", 1);
        obj.put("status", "OK");
        defaultResponse = new NormalResponse(obj, ActionType.FLY);
    }

    @AfterEach
    public void tearDown() {
        drone = null;
    }

    @Test
    public void testResetLeft() {
        Phase currentPhase = new ResetLR(drone, new AllPOIS(new ArrayList<>()), Orientation.LEFT);
        State currentState = State.TURN;
        
        while(!currentPhase.reachedEnd()) {
            Action nextAction = currentPhase.getNextDecision();
            if(currentState == State.TURN) {
                testAction(nextAction, CardinalDirection.S, ActionType.HEADING);
                currentState = State.FORWARD;
            } else if(currentState == State.FORWARD) {
                testAction(nextAction, ActionType.FLY);
                currentState = State.SECOND_TURN;
            } else if(currentState == State.SECOND_TURN) {
                testAction(nextAction, CardinalDirection.W, ActionType.HEADING);
                currentState = State.THIRD_TURN;
            } else if(currentState == State.THIRD_TURN) {
                testAction(nextAction, CardinalDirection.N, ActionType.HEADING);
                currentState = State.FOURTH_TURN;
            } else if(currentState == State.FOURTH_TURN) {
                testAction(nextAction, CardinalDirection.E, ActionType.HEADING);
                currentState = State.SECOND_FORWARD;
            } else if(currentState == State.SECOND_FORWARD) {
                testAction(nextAction, ActionType.FLY);
            } 

            currentPhase.react(defaultResponse);
        }
    }

    @Test
    public void testResetRight() {
        Phase currentPhase = new ResetLR(drone, new AllPOIS(new ArrayList<>()), Orientation.RIGHT);
        State currentState = State.TURN;
        while(!currentPhase.reachedEnd()) {
            Action nextAction = currentPhase.getNextDecision();
            if(currentState == State.TURN) {
                testAction(nextAction, CardinalDirection.N, ActionType.HEADING);
                currentState = State.FORWARD;
            } else if(currentState == State.FORWARD) {
                testAction(nextAction, ActionType.FLY);
                currentState = State.SECOND_TURN;
            } else if(currentState == State.SECOND_TURN) {
                testAction(nextAction, CardinalDirection.W, ActionType.HEADING);
                currentState = State.THIRD_TURN;
            } else if(currentState == State.THIRD_TURN) {
                testAction(nextAction, CardinalDirection.S, ActionType.HEADING);
                currentState = State.FOURTH_TURN;
            } else if(currentState == State.FOURTH_TURN) {
                testAction(nextAction, CardinalDirection.E, ActionType.HEADING);
                currentState = State.SECOND_FORWARD;
            } else if(currentState == State.SECOND_FORWARD) {
                testAction(nextAction, ActionType.FLY);
            } 

            currentPhase.react(defaultResponse);
        }
    }

    
    private void testAction(Action action, CardinalDirection dir, ActionType type) {
        assertEquals(dir, action.getDirection());
        assertEquals(type, action.getType());
    }

    private void testAction(Action action, ActionType type) {
        assertEquals(type, action.getType());
    }
}
