package org.to2mbn.jmccc.mcdownloader.provider.extension;

/**
 * Defined some processing priority rankings.
 * <p>
 * The general workflow:
 * 
 * <pre>
 * 1. metadata processing (eg. processing urls defined in the json)
 * 2. 3rd-party processing (eg. forge/liteloader)
 * 3. download from mirrors (eg. download from cdn)
 * 4. download from origin (eg. download from mojang)
 * </pre>
 * 
 * Each of the above phases has three steps: pre-process, process, post-process.
 * 
 * @author yushijinhun
 * @see ExtensionProvider#getRanking()
 */
public final class ExtensionProviderRankings {

	private ExtensionProviderRankings() {}

	/*
	 * Let x=the ranking of a step.
	 * Let l=the length of a step.
	 * Then the range of a step is [x-l/2,x+l/2).
	 * 
	 * Let 0 be the lower-bound of the range of the 1st step.
	 * Let the upper-bound of the range of a step be the lower-bound of the range of the next step.
	 * 
	 * Then we can easily infer that:
	 * The range of step n is [n*l,(n+1)*l).
	 * The ranking of step n is n*l+l/2.
	 */

	private static final int _step_length = 10_000;

	private static final int _ranking(int phases, int steps) {
		return (phases * 3 + steps) * _step_length + _step_length / 2;
	}

	// @formatter:off
	public static final int PRE_PROCESS_METADATA 		= _ranking(3, 2);
	public static final int PROCESS_METADATA 			= _ranking(3, 1);
	public static final int POST_PROCESS_METADATA 		= _ranking(3, 0);

	public static final int PRE_PROCESS_3RD_PARTY 		= _ranking(2, 2);
	public static final int PROCESS_3RD_PARTY 			= _ranking(2, 1);
	public static final int POST_PROCESS_3RD_PARTY 		= _ranking(2, 0);

	public static final int PRE_PROCESS_MIRROR 			= _ranking(1, 2);
	public static final int PROCESS_MIRROR 				= _ranking(1, 1);
	public static final int POST_PROCESS_MIRROR 		= _ranking(1, 0);

	public static final int PRE_PROCESS_ORIGIN 			= _ranking(0, 2);
	public static final int PROCESS_ORIGIN 				= _ranking(0, 1);
	public static final int POST_PROCESS_ORIGIN 		= _ranking(0, 0);
	// @formatter:on

}
