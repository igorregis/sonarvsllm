public class SWTSkinPropertiesImpl
	extends SkinPropertiesImpl
	implements SWTSkinProperties
{
	private static Map colorMap = new LightHashMap();

	/**
	 * @param skinPath
	 * @param mainSkinFile
	 */
	public SWTSkinPropertiesImpl(ClassLoader classLoader, String skinPath, String mainSkinFile) {
		super(classLoader, skinPath, mainSkinFile);
	}

	/**
	 * 
	 */
	public SWTSkinPropertiesImpl() {
		super();
	}

	// @see com.aelitis.azureus.ui.swt.skin.SWTSkinProperties#getColor(java.lang.String)
	public Color getColor(String sID) {
		Color color;
		if (colorMap.containsKey(sID)) {
			return (Color) colorMap.get(sID);
		}

		try {
			int[] rgb = getColorValue(sID);
			if (rgb[0] > -1) {
				color = ColorCache.getColor(Utils.getDisplay(), rgb[0], rgb[1], rgb[2]);
			} else {
				color = ColorCache.getColor(Utils.getDisplay(), getStringValue(sID));
			}
		} catch (Exception e) {
			//				IMP.getLogger().log(LoggerChannel.LT_ERROR,
			//						"Failed loading color : color." + colorNames[i]);
			color = null;
		}

		colorMap.put(sID, color);

		return color;
	}

	public void clearCache() {
		super.clearCache();
		colorMap.clear();
	}
