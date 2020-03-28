package tasks;

import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;


public class blessSickle extends Task {
    private static Area insideGrotto = Area.rectangular(3435, 9745, 3448, 9732);
    @Override
    public boolean validate() {
        return Varps.get(307) == 60 | Varps.get(307) == 65 | Varps.get(307) == 70 | Varps.get(307) == 75;
    }

    @Override
    public int execute() {
        if(Inventory.contains("Silver sickle (b)"))
            return -1;
        if(insideGrotto.contains(Players.getLocal()))
        {
            if(Dialog.isOpen())
            {
                Dialog.processContinue();
                return 500;
            }
            if(Npcs.getNearest("Nature spirit") == null)
            {
                SceneObjects.getNearest(a-> a.getName().equals("Grotto") && a.containsAction("Search")).interact("Search");
                return 500;
            }
        }
        SceneObjects.getNearest(a-> a.getName().equals("Grotto") && a.containsAction("Enter")).interact("Enter");
        return 1500;
    }
}
