package org.to2mbn.jmccc.mcdownloader.download.combine;

import java.util.Objects;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CallbackAdapter;
import org.to2mbn.jmccc.mcdownloader.download.tasks.ResultProcessor;

class AndThenDownloadCombinedTask<R, S> extends CombinedTask<S> {

	private final CombinedTask<R> prev;
	private final ResultProcessor<R, CombinedTask<S>> next;

	public AndThenDownloadCombinedTask(CombinedTask<R> prev, ResultProcessor<R, CombinedTask<S>> next) {
		this.prev = Objects.requireNonNull(prev);
		this.next = Objects.requireNonNull(next);
	}

	@Override
	public void execute(final DownloadContext<S> context) throws Exception {
		context.submit(prev, new CallbackAdapter<R>() {

			@Override
			public void done(R result1) {
				try {
					context.submit(next.process(result1), new CallbackAdapter<S>() {

						@Override
						public void done(S result2) {
							context.done(result2);
						}

					}, true);
				} catch (Throwable e) {
					context.failed(e);
				}
			}

		}, true);
	}

}
