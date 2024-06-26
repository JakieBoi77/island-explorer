package ca.mcmaster.se2aa4.island.team222;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team222.actions.Action;
import ca.mcmaster.se2aa4.island.team222.actions.ActionType;
import ca.mcmaster.se2aa4.island.team222.directions.CardinalDirection;

public class InterlacedTest {

    private Interlaced interlaced;

    @BeforeEach
    public void setUp() {
        interlaced = new Interlaced(100, CardinalDirection.N);
    }

    @Test
    public void testStopCondition() {
        interlaced.decide();
        Action nextAction = interlaced.decide();
        assertEquals(ActionType.STOP, nextAction.getType());
    }
}
