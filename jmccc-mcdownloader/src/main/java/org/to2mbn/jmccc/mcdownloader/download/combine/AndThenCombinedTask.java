package org.to2mbn.jmccc.mcdownloader.download.combine;

import java.util.Objects;
import java.util.concurrent.Callable;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CallbackAdapter;
import org.to2mbn.jmccc.mcdownloader.download.tasks.ResultProcessor;

class AndThenCombinedTask<R, S> extends CombinedTask<S> {

	private final CombinedTask<R> prev;
	private final ResultProcessor<R, S> next;

	public AndThenCombinedTask(CombinedTask<R> prev, ResultProcessor<R, S> next) {
		this.prev = Objects.requireNonNull(prev);
		this.next = Objects.requireNonNull(next);
	}

	@Override
	public void execute(final DownloadContext<S> context) throws Exception {
		context.submit(prev, new CallbackAdapter<R>() {

			@Override
			public void done(final R result1) {
				try {
					// invoke ResultProcessor here will block the IO thread
					// so we run it async
					context.submit(new Callable<S>() {

						@Override
						public S call() throws Exception {
							return next.process(result1);
						}

					}, new CallbackAdapter<S>() {

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
