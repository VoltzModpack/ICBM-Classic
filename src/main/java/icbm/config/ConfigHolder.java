package icbm.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public final class ConfigHolder {

	private static final Pair<ClientConfig, ForgeConfigSpec> client = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
	private static final Pair<ServerConfig, ForgeConfigSpec> server = new ForgeConfigSpec.Builder().configure(ServerConfig::new);

	public static ForgeConfigSpec getClientSpec() {
		return client.getRight();
	}

	public static ForgeConfigSpec getServerSpec() {
		return server.getRight();
	}

}

