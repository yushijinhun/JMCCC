package org.to2mbn.jmccc.mcdownloader.download.combine;

import java.util.concurrent.Callable;

class MultipleCombinedTask extends CombinedTask<Void> {

	CombinedTask<?>[] tasks;

	public MultipleCombinedTask(CombinedTask<?>[] tasks) {
		this.tasks = tasks;
	}

	@Override
	public void execute(final DownloadContext<Void> context) throws Exception {
		for (CombinedTask<?> task : tasks) {
			if (task != null) {
				context.submit(task, null, true);
			}
		}
		context.awaitAllTasks(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				context.done(null);
				return null;
			}
		});
	}

}