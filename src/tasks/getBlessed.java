package tasks;

import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class getBlessed extends Task {
    private static Area grotto = Area.rectangular(3436, 3340, 3445, 3332);
    private static Area drezelsRoom = Area.rectangular(3436, 9901, 3443, 9886);
    private static Area trapDoor = Area.rectangular(3421, 3485, 3425, 3483);

    @Override
    public boolean validate() {
        return Varps.get(307) == 35;
    }

    @Override
    public int execute() {
        if(grotto.contains(Players.getLocal()))
        {
            SceneObjects.getNearest("Bridge").interact("Jump");
            return 1500;
        }
        if(drezelsRoom.contains(Players.getLocal()))
        {
            if(Dialog.isOpen())
            {
                if(Dialog.canContinue())
                {
                    Dialog.processContinue();
                    return 500;
                }

            }
            Npcs.getNearest("Drezel").click();
            return 500;
        }
        if(trapDoor.contains(Players.getLocal()))
        {
            SceneObjects.getNearest("Trapdoor").interact(a-> a.contains("Open") | a.contains("Climb"));
            return 500;
        }
        Movement.walkTo(trapDoor.getCenter());
        return 500;
    }
}
