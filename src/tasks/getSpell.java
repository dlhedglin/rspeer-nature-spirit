package tasks;

import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class getSpell extends Task {
    @Override
    public boolean validate() {
        return Varps.get(307) == 20 | Varps.get(307) == 25 | Varps.get(307) == 30;
    }

    @Override
    public int execute() {
        Npc spirit = Npcs.getNearest("Filliman Tarlock");
        if(spirit == null)
        {
            SceneObjects.getNearest(a-> a.getName().equals("Grotto") && a.containsAction("Enter")).interact("Enter");
            return 1500;
        }
        if(Dialog.isOpen())
        {
            if(Dialog.canContinue())
            {
                Dialog.processContinue();
                return 500;
            }
            Inventory.use(a->a.getName().equals("Mirror"), Npcs.getNearest("Filliman Tarlock"));
            Dialog.process(a-> a.contains("can I help") || a.contains("How long"));
            return 500;
        }
        if(Varps.get(307) == 25)
        {
            Inventory.use(a-> a.getName().equals("Journal"), Npcs.getNearest("Filliman Tarlock"));
            return 1500;
        }
        if(Varps.get(307) == 30)
        {
            Npcs.getNearest("Filliman Tarlock").click();
            return 500;
        }
        Inventory.use(a->a.getName().equals("Mirror"), Npcs.getNearest("Filliman Tarlock"));
        return 500;
    }
}
