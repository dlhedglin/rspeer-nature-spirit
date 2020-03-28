package tasks;

import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class doRutual extends Task {
    private static Area grotto = Area.rectangular(3436, 3340, 3445, 3332);
    private static Area grottoBridge = Area.rectangular(3435, 3328, 3445, 3325);
    private static Position orange = new Position(3440, 3335);


    @Override
    public boolean validate() {
        return Varps.get(307) == 50 |  Varps.get(307) == 55;
    }

    @Override
    public int execute() {
        if(!grotto.contains(Players.getLocal()))
        {
            if(grottoBridge.contains(Players.getLocal()))
            {
                SceneObjects.getNearest("Bridge").click();
                Time.sleepUntil(()-> grotto.contains(Players.getLocal()), 3000);
                return 500;
            }
            Movement.walkTo(grottoBridge.getCenter());
            Time.sleepUntil(()-> grottoBridge.contains(Players.getLocal()) || !Players.getLocal().isMoving(), 1500);
        }
        if(Inventory.contains("Mort myre fungus"))
        {
            Inventory.use(a-> a.getName().equals("Mort myre fungus"), SceneObjects.getNearest(3527));
            return 1500;
        }
        if(Inventory.contains(a-> a.getName().contains("spell")))
        {
            Inventory.use(a-> a.getName().contains("spell"), SceneObjects.getNearest(3529));
        }
        Npc spirit = Npcs.getNearest("Filliman Tarlock");
        if(spirit == null)
        {
            SceneObjects.getNearest(a-> a.getName().equals("Grotto") && a.containsAction("Enter")).interact("Enter");
            return 1500;
        }
        if(!Players.getLocal().getPosition().equals(orange))
        {
            Movement.walkTo(orange);
            return 1500;
        }
        if(Dialog.isOpen())
        {
            if(Dialog.canContinue())
            {
                Dialog.processContinue();
                return 500;
            }
            Dialog.process(a-> a.contains("solved the puzzle"));
            return 500;
        }
        spirit.click();
        return 1000;
    }
}
