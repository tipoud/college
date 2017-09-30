package Main;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
 
public class Ouvrir
{
	public Ouvrir(String adresse)
	{
		if(Desktop.isDesktopSupported())
		{
			if(Desktop.getDesktop().isSupported(java.awt.Desktop.Action.OPEN))
			{
				File file=new File(adresse);
				try
				{
					java.awt.Desktop.getDesktop().open(file);
				}
				catch (IOException exc)
				{
			    	System.out.println("Exception: " + exc.toString());
				}
			}
			else
			{
				System.out.println("La fonction OPEN n'est pas supportée par votre Système d'exploitation");
			}
		}
		else
		{
			System.out.println("La fonction Desktop n'est pas supportée par votre Système d'exploitation");
		}
	}
 
}