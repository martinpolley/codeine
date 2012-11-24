package yami;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class VersionResourceReader
{
	private static final String BUNDLE_NAME = "yami.version"; //$NON-NLS-1$
	
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	private VersionResourceReader()
	{
	}
	
	public static String getString(String key)
	{
		try
		{
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e)
		{
			return '!' + key + '!';
		}
	}
}