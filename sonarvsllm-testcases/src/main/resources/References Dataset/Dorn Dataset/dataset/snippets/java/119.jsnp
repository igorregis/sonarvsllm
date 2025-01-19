
/**
 * @author Brian Wing Shun Chan
 */
public class PluginUtil {

	public static <P extends Plugin> List<P> restrictPlugins(
			List<P> plugins, long companyId, long userId)
		throws SystemException {

		List<P> visiblePlugins = new ArrayList<P>(plugins.size());

		for (P plugin : plugins) {
			PluginSetting pluginSetting =
				PluginSettingLocalServiceUtil.getPluginSetting(
					companyId, plugin.getPluginId(), plugin.getPluginType());

			if (pluginSetting.isActive() &&
				pluginSetting.hasPermission(userId)) {

				visiblePlugins.add(plugin);
			}
		}

		return visiblePlugins;
	}

	public static <P extends Plugin> List<P> restrictPlugins(
			List<P> plugins, User user)
		throws SystemException {
