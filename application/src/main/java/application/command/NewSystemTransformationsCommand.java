package application.command;

import java.io.IOException;

import application.domain.AssemblyLine;
import application.domain.CuttingProcess;
import application.domain.MaterialPoints;
import application.event.CommandStatusEvent;
import application.storage.PersistanceStorage;
import planning.method.SystemTransformations;

public class NewSystemTransformationsCommand extends Command {

	public static final String NAME = "new_st";

	PersistanceStorage persistanceStorage = new PersistanceStorage();

	@Override
	public void execute(CommandData data) throws Exception {
		execute((NewSystemTransformationsCommandData) data);
	}

	private void execute(NewSystemTransformationsCommandData data) throws IOException {
		notifyCommandStatus(new CommandStatusEvent("executing command: \"new system transformation\"..."));

		SystemTransformations systemTransformations;
		switch (data.domain) {
		case MaterialPoints.DOMAIN_NAME:
			systemTransformations = MaterialPoints.getSystemTransformations();
			break;
		case CuttingProcess.DOMAIN_NAME:
			systemTransformations = CuttingProcess.getSystemTransformations();
			break;
		case AssemblyLine.DOMAIN_NAME:
			systemTransformations = AssemblyLine.getSystemTransformations();
			break;
		default:
			systemTransformations = new SystemTransformations();
			break;
		}

		persistanceStorage.saveSystemTransformations(systemTransformations, data.systemTransformationsFile);

		notifyCommandStatus(new CommandStatusEvent("done"));
	}
}
