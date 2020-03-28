package tasks;

import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.local.Health;
import org.rspeer.script.task.Task;

public class eatFood extends Task {
    @Override
    public boolean validate() {
        return Health.getPercent() < 50 && Inventory.contains("Lobster");
    }

    @Override
    public int execute() {
        Inventory.getFirst("Lobster").click();
        return 500;
    }
}
