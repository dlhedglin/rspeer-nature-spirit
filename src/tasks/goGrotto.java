package tasks;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class goGrotto extends Task {
    private static Area grotto = Area.rectangular(3436, 3340, 3445, 3332);
    private static Area grottoBridge = Area.rectangular(3435, 3328, 3445, 3325);
    @Override
    public boolean validate() {
        return Varps.get(307) == 5 | Varps.get(307) == 10;
    }

    @Override
    public int execute() {
        SceneObject barrier = SceneObjects.getNearest("Holy barrier");
        if(barrier != null)
        {
            barrier.click();
            return 3000;
        }
        if(!Inventory.contains("Silver sickle"))
        {
            if(Bank.isOpen())
            {
                Bank.withdraw("Silver sickle", 1);
                Time.sleep(1000);
                Bank.withdraw("Lobster", 10);
                return 500;
            }
            Bank.open(BankLocation.CANIFIS);
            return 3000;
        }
        if(Interfaces.isOpen(580))
        {
            Interfaces.getComponent(580,17).click();
            return 500;
        }
        if(!grotto.contains(Players.getLocal()))
        {
            if(grottoBridge.contains(Players.getLocal()))
            {
                SceneObjects.getNearest("Bridge").click();
                Time.sleepUntil(()-> grotto.contains(Players.getLocal()), 3000);
                return 500;
            }
            Movement.walkTo(grottoBridge.getCenter());
            if(Movement.getRunEnergy() > 30)
                Movement.toggleRun(true);
            Time.sleepUntil(()-> grottoBridge.contains(Players.getLocal()) || !Players.getLocal().isMoving(), 1500);
            return 500;
        }
        if(!Inventory.contains("Washing bowl"))
        {
            Pickables.getNearest("Washing Bowl").interact("Take");
            return 1500;
        }
        if(!Inventory.contains("Mirror"))
        {
            Pickables.getNearest("Mirror").interact("Take");
            return 1500;
        }
        if(!Inventory.contains("Journal"))
        {
            SceneObjects.getNearest(a-> a.getName().equals("Grotto tree") && a.containsAction("Search")).interact("Search");
            return 1500;
        }
        if(Dialog.isOpen())
        {
            if(Dialog.canContinue())
            {
                Dialog.processContinue();
                return 500;
            }
            Dialog.process(a-> a.contains("How long"));
            return 500;
        }
        Npc spirit = Npcs.getNearest("Filliman Tarlock");
        if(spirit == null)
        {
            SceneObjects.getNearest(a-> a.getName().equals("Grotto") && a.containsAction("Enter")).interact("Enter");
            return 1500;
        }
        return 500;
    }
}
