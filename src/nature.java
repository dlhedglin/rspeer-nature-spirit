import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import tasks.*;

@ScriptMeta(name = "nature",  desc = "Script description", developer = "Developer's Name", category = ScriptCategory.MONEY_MAKING)
public class nature extends TaskScript {
    private static final Task[] TASKS = {new eatFood(), new goDrezel(), new goGrotto(), new getSpell(), new getBlessed(),
    new collectFungus(), new doRutual(), new blessSickle()};
    @Override
    public void onStart() {
        submit(TASKS);

    }
}
