package tasks;

import org.omg.CORBA.INV_FLAG;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class collectFungus extends Task {
    private static Position logSpot = new Position(3436, 3453);
    @Override
    public boolean validate() {
        return Varps.get(307) == 40 | Varps.get(307) == 45;
    }

    @Override
    public int execute() {
        SceneObject barrier = SceneObjects.getNearest("Holy barrier");
        if(barrier != null)
        {
            barrier.click();
            return 3000;
        }
        if(Interfaces.isOpen(580))
        {
            Interfaces.getComponent(580,17).click();
            return 500;
        }
        if(!Inventory.contains("Mort myre fungus"))
        {
            SceneObject log = SceneObjects.getNearest(a -> a.getName().equals("Fungi on log") && a.distance(Players.getLocal()) < 3);
            if(log != null)
            {
                log.click();
                return 1500;
            }
            if(Players.getLocal().getPosition().equals(logSpot))
            {
                Inventory.getFirst(2968).interact(ActionOpcodes.ITEM_ACTION_0);
                return 500;
            }
            Movement.walkTo(logSpot);
            return 1500;
        }

        return 500;
    }
}
