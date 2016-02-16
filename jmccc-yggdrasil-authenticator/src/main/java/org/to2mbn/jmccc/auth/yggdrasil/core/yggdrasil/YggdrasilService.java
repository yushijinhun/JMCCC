package org.to2mbn.jmccc.auth.yggdrasil.core.yggdrasil;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;
import org.to2mbn.jmccc.auth.AuthenticationException;
import org.to2mbn.jmccc.auth.yggdrasil.core.RemoteAuthenticationException;
import org.to2mbn.jmccc.auth.yggdrasil.core.io.JSONHttpRequester;

abstract public class YggdrasilService implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient JSONHttpRequester requester;
	private SignaturedPropertiesDeserializer propertiesDeserializer;
	private YggdrasilAPIProvider api;

	public YggdrasilService() {
		this(new SignaturedPropertiesDeserializer(), new DefaultYggdrasilAPIProvider());
	}

	public YggdrasilService(SignaturedPropertiesDeserializer propertiesDeserializer, YggdrasilAPIProvider api) {
		this.propertiesDeserializer = propertiesDeserializer;
		this.api = api;
	}

	protected void checkResponse(JSONObject response) throws AuthenticationException {
		if (response == null) {
			throw new AuthenticationException("empty response");
		}

		try {
			if (response.has("error") && !response.getString("error").isEmpty()) {
				throw new RemoteAuthenticationException(response.getString("error"), response.optString("errorMessage", null), response.optString("cause", null));
			}
		} catch (JSONException e) {
			throw new ResponseFormatException(e);
		}
	}

	protected JSONHttpRequester getRequester() {
		if (requester == null) {
			requester = new JSONHttpRequester();
		}
		return requester;
	}

	protected SignaturedPropertiesDeserializer getPropertiesDeserializer() {
		return propertiesDeserializer;
	}

	protected YggdrasilAPIProvider getApi() {
		return api;
	}

}
