package tasks;

import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.script.task.Task;

public class goDrezel extends Task {
    @Override
    public boolean validate() {
        return Varps.get(307) == 0;
    }

    @Override
    public int execute() {
        if(Interfaces.isOpen(11))
        {
            Interfaces.getComponent(11, 4).click();
            return 500;
        }
        if(Dialog.isOpen())
        {
            if(Dialog.canContinue())
            {
                Dialog.processContinue();
                return 500;
            }
            Dialog.process(a-> a.contains("anything else") | a.contains("what is it") | a.contains("go and look") | a.contains("Yes,"));
            return 500;
        }
        Npcs.getNearest("Drezel").click();
        Time.sleepUntil(()-> Dialog.isOpen(), 3000);
        return 500;
    }
}
